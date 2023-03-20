package com.practice.order.infrastructure.order.payment;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.payment.PayMethod;

public interface PaymentApiCaller {
    public boolean support(PayMethod payMethod);
    public void pay(OrderCommand.PaymentRequest paymentRequest);
}
