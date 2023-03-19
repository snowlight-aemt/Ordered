package com.practice.order.domain.order;

import java.util.List;

public interface OrderItemSeriesFactory {
    public void store(Order order, List<OrderCommand.RegisterOrderItem> orderItems);
}
