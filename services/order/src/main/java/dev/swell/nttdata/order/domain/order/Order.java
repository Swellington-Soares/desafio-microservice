package dev.swell.nttdata.order.domain.order;

import dev.swell.nttdata.order.domain.product.ProductResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {

    private final Long id = UUID.randomUUID().getMostSignificantBits();

    private final List<ProductResponse> products;

    public Order(List<ProductResponse> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return  products.stream()
                .map(ProductResponse::price)
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal("0.0"));
    }
}
