package com.practice.order.infrastructure.order.payment;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.payment.PayMethod;
import org.springframework.stereotype.Component;

@Component
public class CardApiCaller implements PaymentApiCaller {
    @Override
    public boolean support(PayMethod payMethod) {
        return payMethod == PayMethod.CARD;
    }

    @Override
    public void pay(OrderCommand.PaymentRequest paymentRequest) {
        // Card Api //
    }
}
