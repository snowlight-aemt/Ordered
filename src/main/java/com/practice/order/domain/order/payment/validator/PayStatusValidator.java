package com.practice.order.domain.order.payment.validator;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.order.Order;
import com.practice.order.domain.order.OrderCommand;
import org.springframework.stereotype.Component;

@org.springframework.core.annotation.Order(30)
@Component
public class PayStatusValidator implements PaymentValidator {

    @Override
    public void validate(Order order, OrderCommand.PaymentRequest paymentRequest) {
        if (order.isAlreadyPaymentComplete())
            throw new InvalidParamException("이미 결제완료된 주문입니다.");
    }
}
