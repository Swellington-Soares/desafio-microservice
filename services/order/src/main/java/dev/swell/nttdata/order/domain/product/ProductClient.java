package dev.swell.nttdata.order.domain.product;

import dev.swell.nttdata.order.domain.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@FeignClient(
        name = "product-service",
        url = "${spring.application.config.product-url}"
)
public interface ProductClient {

    @GetMapping
    Optional<List<ProductResponse>> getAllProducts();

    @GetMapping("/{productId}")
    Optional<ProductResponse> findProductById( @PathVariable Long productId);

    @DeleteMapping("/{productId}")
    void deleteProductById( @PathVariable Long productId);
}
