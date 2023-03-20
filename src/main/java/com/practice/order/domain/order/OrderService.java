package com.practice.order.domain.order;

public interface OrderService {
    public String registerOrder(OrderCommand.RegisterOrder command);

    public OrderInfo.Main retrieveOrder(String orderToken);

    public void paymentOrder(OrderCommand.PaymentRequest command);
}
