# deploy.ps1
$ErrorActionPreference = "Stop"

Write-Host "🧹 Limpando builds anteriores..."

$services = @(
    "configserver",
    "discovery",
    "gateway",
    "order",
    "product")

foreach ($service in $services) {
    $servicePath = Join-Path -Path "services" -ChildPath $service
    Write-Host "🔧 Buildando serviço: $servicePath"
    Set-Location $servicePath
    ./gradlew.bat clean build -x test
    Set-Location ../..  # Volta para a raiz do projeto
}

Write-Host "🐳 Subindo os containers com Docker Compose..."
docker-compose up --build
