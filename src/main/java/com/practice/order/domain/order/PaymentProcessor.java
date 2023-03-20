package com.practice.order.domain.order;

public interface PaymentProcessor {
    public void pay(OrderCommand.PaymentRequest paymentRequest);
}
