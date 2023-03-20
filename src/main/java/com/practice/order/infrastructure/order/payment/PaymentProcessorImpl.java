package com.practice.order.infrastructure.order.payment;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private final List<PaymentApiCaller> paymentApiCallers;

    @Override
    public void pay(OrderCommand.PaymentRequest paymentRequest) {
        PaymentApiCaller paymentApiCaller = routingApiCaller(paymentRequest);
        paymentApiCaller.pay(paymentRequest);
    }

    private PaymentApiCaller routingApiCaller(OrderCommand.PaymentRequest paymentRequest) {
        return this.paymentApiCallers.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(paymentRequest.getPayMethod()))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}
