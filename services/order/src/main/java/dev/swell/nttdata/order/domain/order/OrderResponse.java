package dev.swell.nttdata.order.domain.order;

import dev.swell.nttdata.order.domain.product.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long orderId,
        List<ProductResponse> productPurchaseList,
        BigDecimal totalPrice
) {
}
