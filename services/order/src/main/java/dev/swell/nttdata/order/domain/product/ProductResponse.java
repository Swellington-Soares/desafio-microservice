package dev.swell.nttdata.order.domain.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String createdAt
) {
}
