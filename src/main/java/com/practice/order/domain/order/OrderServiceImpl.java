package com.practice.order.domain.order;

import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderStore orderStore;
    private final OrderReader orderReader;
    private final ItemReader itemReader;

    @Transactional
    @Override
    public String registerOrder(OrderCommand.RegisterOrder command) {
        Order order = command.toEntity();
        orderStore.store(order);

        for (var orderItemRequest : command.getOrderItems()) {
            Item item = itemReader.getItemBy(orderItemRequest.getItemToken());
            OrderItem orderItem = orderItemRequest.toEntity(order, item);
            orderStore.store(orderItem);

            for (var orderItemOptionGroupRequest : orderItemRequest.getOrderItemOptionGroups()) {
                OrderItemOptionGroup orderItemOptionGroup = orderItemOptionGroupRequest.toEntity(orderItem);
                orderStore.store(orderItemOptionGroup);

                for (var orderItemOptionRequest : orderItemOptionGroupRequest.getOrderItemOptionList()) {
                    OrderItemOption orderItemOption = orderItemOptionRequest.toEntity(orderItemOptionGroup);
                    orderStore.store(orderItemOption);
                }
            }
        }

        return order.getOrderToken();
    }
}
