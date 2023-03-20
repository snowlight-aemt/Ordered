package com.practice.order.domain.order.payment.validator;

import com.practice.order.domain.order.Order;
import com.practice.order.domain.order.OrderCommand;

public interface PaymentValidator {
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest);
}
