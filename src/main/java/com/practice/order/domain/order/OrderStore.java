package com.practice.order.domain.order;

public interface OrderStore {
    public Order store(Order order);
    public OrderItem store(OrderItem orderItem);
    public OrderItemOptionGroup store(OrderItemOptionGroup orderItemOptionGroup);
    public OrderItemOption store(OrderItemOption orderItemOption);
}
