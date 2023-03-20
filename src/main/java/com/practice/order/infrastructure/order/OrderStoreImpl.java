package com.practice.order.infrastructure.order;

import com.practice.order.domain.order.*;
import com.practice.order.domain.order.item.OrderItem;
import com.practice.order.domain.order.item.OrderItemOption;
import com.practice.order.domain.order.item.OrderItemOptionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStoreImpl implements OrderStore {
    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final OrderItemOptionGroupRepository itemOptionGroupRepository;
    private final OrderItemOptionRepository itemOptionRepository;

    @Override
    public Order store(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderItem store(OrderItem orderItem) {
        return itemRepository.save(orderItem);
    }

    @Override
    public OrderItemOptionGroup store(OrderItemOptionGroup orderItemOptionGroup) {
        return itemOptionGroupRepository.save(orderItemOptionGroup);
    }

    @Override
    public OrderItemOption store(OrderItemOption orderItemOption) {
        return itemOptionRepository.save(orderItemOption);
    }
}
