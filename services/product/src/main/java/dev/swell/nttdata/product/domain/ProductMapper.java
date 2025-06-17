package dev.swell.nttdata.product.domain;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class ProductMapper {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");

    Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .id(productRequest.id())
                .name(productRequest.name())
                .price(productRequest.price())
                .description(productRequest.description())
                .build();
    }

    ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCreatedAt().format(DATE_FORMATTER)
        );
    }
}
