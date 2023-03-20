package com.practice.order.domain.order.payment;

import com.practice.order.domain.order.OrderCommand;

public interface PaymentProcessor {
    public void pay(OrderCommand.PaymentRequest paymentRequest);
}
