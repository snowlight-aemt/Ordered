package com.practice.order.domain.order;

import com.practice.order.domain.order.item.OrderItem;

import java.util.List;

public interface OrderItemSeriesFactory {
    public List<OrderItem> store(Order order, List<OrderCommand.RegisterOrderItem> orderItems);
}
