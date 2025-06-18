# deploy.ps1
$ErrorActionPreference = "Stop"

Write-Host "🧹 Limpando builds anteriores..."

# Lista dos serviços e nomes dos jars (ajuste nomes dos jars conforme seu build)
$services = @(
    @{ Name = "configserver"; JarName = "app.jar" },
    @{ Name = "discovery"; JarName = "app.jar" },
    @{ Name = "gateway"; JarName = "app.jar" },
    @{ Name = "order"; JarName = "app.jar" },
    @{ Name = "product"; JarName = "app.jar" }
)

function Wait-ForFile {
    param(
        [string]$filePath,
        [int]$timeoutSeconds = 60
    )

    $startTime = Get-Date
    while (-not (Test-Path $filePath)) {
        if ((Get-Date) -gt $startTime.AddSeconds($timeoutSeconds)) {
            Write-Error "❌ Timeout esperando o arquivo $filePath ser criado."
            exit 1
        }
        Write-Host "⏳ Esperando o arquivo $filePath ser criado..."
        Start-Sleep -Seconds 2
    }
    Write-Host "✅ Arquivo $filePath encontrado!"
}

# Build dos serviços com espera pelo JAR
foreach ($service in $services) {
    $servicePath = Join-Path -Path "services" -ChildPath $service.Name
    Write-Host "🔧 Buildando serviço: $servicePath"
    Set-Location $servicePath
    ./gradlew.bat clean build -x test
    Set-Location ../..  # Volta para a raiz do projeto

    # ✅ AGORA sim verifica o arquivo no caminho correto
    $jarPath = Join-Path -Path (Join-Path $servicePath "build/libs") -ChildPath $service.JarName
    Wait-ForFile -filePath $jarPath -timeoutSeconds 60
}


Write-Host "🐳 Subindo os containers com Docker Compose..."
docker-compose up --build -d

function Wait-ForContainersHealthy {
    param(
        [string[]] $containerNames,
        [int] $timeoutSeconds = 180
    )

    $startTime = Get-Date
    while ((Get-Date) -lt $startTime.AddSeconds($timeoutSeconds)) {
        $allHealthy = $true
        foreach ($name in $containerNames) {
            $containerInfoRaw = docker inspect --format='{{json .State}}' $name 2>$null
            if (-not $containerInfoRaw) {
                Write-Host "⚠️ Container $name não encontrado."
                $allHealthy = $false
                continue
            }

            $containerInfo = $containerInfoRaw | ConvertFrom-Json

            if ($containerInfo.Status -ne "running") {
                Write-Host "⛔ Container $name não está rodando (status: $($containerInfo.Status))"
                $allHealthy = $false
            }
            elseif ($containerInfo.Health) {
                if ($containerInfo.Health.Status -ne "healthy") {
                    Write-Host "⏳ Container $name não está healthy (health: $($containerInfo.Health.Status))"
                    $allHealthy = $false
                }
            }
        }

        if ($allHealthy) {
            Write-Host "✅ Todos os containers estão rodando e healthy."
            return $true
        }

        Write-Host "⏳ Aguardando containers ficarem prontos..."
        Start-Sleep -Seconds 5
    }

    Write-Error "❌ Timeout esperando containers ficarem healthy."
    return $false
}

# Ajuste aqui para os nomes dos containers do seu docker-compose
$containersToWait = @(
    "ms-configserver",
    "ms-discovery",
    "ms-gateway",
    "ms-pedidos",
    "ms-produtos",
    "keycloak-ms"
)

$success = Wait-ForContainersHealthy -containerNames $containersToWait -timeoutSeconds 180
if (-not $success) {
    Write-Error "❌ Containers não ficaram prontos no tempo esperado."
    exit 1
}

Write-Host "🎉 Deploy concluído com sucesso!"
