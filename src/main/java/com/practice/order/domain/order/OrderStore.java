package com.practice.order.domain.order;

import com.practice.order.domain.order.item.OrderItem;
import com.practice.order.domain.order.item.OrderItemOption;
import com.practice.order.domain.order.item.OrderItemOptionGroup;

public interface OrderStore {
    public Order store(Order order);
    public OrderItem store(OrderItem orderItem);
    public OrderItemOptionGroup store(OrderItemOptionGroup orderItemOptionGroup);
    public OrderItemOption store(OrderItemOption orderItemOption);
}
