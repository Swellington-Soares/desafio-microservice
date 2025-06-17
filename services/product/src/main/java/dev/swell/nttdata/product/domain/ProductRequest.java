package dev.swell.nttdata.product.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

        Long id,

        @NotBlank(message = "O campo NOME não pode ser vazio.")
        String name,

        @NotBlank(message = "O campo DESCRIÇÃO não pode ser vazio.")
        String description,

        @Min(value = 0, message = "O valor mínimo para o campo PREÇO é 0.")
        @Positive(message = "Preço do produto não pode ser negativo.")
        BigDecimal price
) { }
