package com.practice.order.domain.order.payment;

import com.practice.order.domain.order.Order;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderInfo;

public interface PaymentProcessor {
    public OrderInfo.PaymentProcessorResponse inPay(Order order, OrderCommand.PaymentWaitRequest paymentRequest);
    public void pay(Order order, OrderCommand.PaymentRequest paymentRequest);
}
