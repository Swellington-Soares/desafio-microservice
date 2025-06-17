package dev.swell.nttdata.order.domain.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Produto é obrigatório")
        Long productId,

        @Positive(message = "Quantidade não pode ser negativa")
        @Min(value = 1, message = "A quantidade mínima é de um")
        Long amount
) {
}
