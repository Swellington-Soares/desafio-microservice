package dev.swell.nttdata.order.domain.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getProducts(),
                order.getTotalPrice()
        );
    }
}
