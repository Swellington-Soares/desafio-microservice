package dev.swell.nttdata.order.domain.order;

import dev.swell.nttdata.order.domain.product.PurchaseRequest;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
        @NotEmpty(message = "Lista de pedidos precisa ter pelo menos um item.")
        List<PurchaseRequest> products
) {
}
