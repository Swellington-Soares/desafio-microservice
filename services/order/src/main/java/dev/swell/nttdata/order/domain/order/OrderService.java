package dev.swell.nttdata.order.domain.order;

import dev.swell.nttdata.order.domain.exception.BusinessException;
import dev.swell.nttdata.order.domain.product.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductClient productClient;

    public Order createOrder(OrderRequest request) {
        var products = productClient.getAllProducts()
                .orElseThrow(() -> new BusinessException("Ocorreu um erro ao consultar a lista de produtos."))
                .stream()
                .filter(p ->
                    request.products().stream().anyMatch(b -> b.productId().equals(p.id()))
                ).toList();
        return new Order( products );
    }
}
