package com.practice.order.infrastructure.order;

import com.google.common.collect.Lists;
import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemReader;
import com.practice.order.domain.order.*;
import com.practice.order.domain.order.item.OrderItem;
import com.practice.order.domain.order.item.OrderItemOption;
import com.practice.order.domain.order.item.OrderItemOptionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderItemSeriesFactoryImpl implements OrderItemSeriesFactory {
    private final OrderStore orderStore;
    private final ItemReader itemReader;

    @Override
    public List<OrderItem> store(Order order, List<OrderCommand.RegisterOrderItem> orderItems) {
        return orderItems.stream().map(orderItemRequest -> {
            Item item = itemReader.getItemBy(orderItemRequest.getItemToken());
            OrderItem orderItem = orderItemRequest.toEntity(order, item);
            orderStore.store(orderItem);

            storeOrderItemOptionGroup(orderItemRequest, orderItem);

            return orderItem;
        }).toList();
    }

    private void storeOrderItemOptionGroup(OrderCommand.RegisterOrderItem orderItemRequest, OrderItem orderItem) {
        for (var orderItemOptionGroupRequest : orderItemRequest.getOrderItemOptionGroups()) {
            OrderItemOptionGroup orderItemOptionGroup = orderItemOptionGroupRequest.toEntity(orderItem);
            orderStore.store(orderItemOptionGroup);

            storeOrderItemOption(orderItemOptionGroupRequest, orderItemOptionGroup);
        }
    }

    private void storeOrderItemOption(OrderCommand.RegisterOrderItemOptionGroup orderItemOptionGroupRequest, OrderItemOptionGroup orderItemOptionGroup) {
        for (var orderItemOptionRequest : orderItemOptionGroupRequest.getOrderItemOptionList()) {
            OrderItemOption orderItemOption = orderItemOptionRequest.toEntity(orderItemOptionGroup);
            orderStore.store(orderItemOption);
        }
    }
}
