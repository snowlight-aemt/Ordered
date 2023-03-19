package com.practice.order.infrastructure.order;

import com.practice.order.common.exception.EntityNotFoundException;
import com.practice.order.domain.order.Order;
import com.practice.order.domain.order.OrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReaderImpl implements OrderReader {
    private final OrderRepository orderRepository;

    @Override
    public Order getOrderBy(String orderToken) {
        return orderRepository.findByOrderToken(orderToken).orElseThrow(EntityNotFoundException::new);
    }
}
