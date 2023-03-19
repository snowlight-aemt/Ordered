package com.practice.order.domain.order;

public interface OrderService {
    public String registerOrder(OrderCommand.RegisterOrder command);
}
