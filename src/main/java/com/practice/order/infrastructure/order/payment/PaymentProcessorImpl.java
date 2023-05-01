package com.practice.order.infrastructure.order.payment;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.order.Order;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderInfo;
import com.practice.order.domain.order.payment.PayMethod;
import com.practice.order.domain.order.payment.PaymentProcessor;
import com.practice.order.domain.order.payment.validator.PaymentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private final List<PaymentValidator> paymentValidators;
    private final List<PaymentApiCaller> paymentApiCallers;

    @Override
    public OrderInfo.PaymentProcessorResponse inPay(Order order, OrderCommand.PaymentWaitRequest paymentWaitRequest) {
        OrderCommand.PaymentRequest paymentRequest = OrderCommand.PaymentRequest.builder()
                                                        .orderToken(paymentWaitRequest.getOrderToken())
                                                        .amount(paymentWaitRequest.getTotalAmount())
                                                        .payMethod(paymentWaitRequest.getPayMethod())
                                                        .build();

        paymentValidators.forEach(validator -> validator.validate(order, paymentRequest));
        PaymentApiCaller paymentApiCaller = routingApiCaller(paymentWaitRequest.getPayMethod());
        KakaoPaymentDto.PaymentWaitResponse paymentWaitResponse = paymentApiCaller.inPay(paymentWaitRequest);


        return OrderInfo.PaymentProcessorResponse.builder()
                .nextRedirectPcUrl(paymentWaitResponse.getNextRedirectPcUrl())
                .build();
    }

    @Override
    public void pay(Order order, OrderCommand.PaymentRequest paymentRequest) {
//        paymentValidators.forEach(validator -> validator.validate(order, paymentRequest));
        PaymentApiCaller paymentApiCaller = routingApiCaller(paymentRequest.getPayMethod());
        paymentApiCaller.pay(paymentRequest);
    }

    private PaymentApiCaller routingApiCaller(PayMethod payMethod) {
        return this.paymentApiCallers.stream()
                .filter(paymentApiCaller -> paymentApiCaller.support(payMethod))
                .findFirst()
                .orElseThrow(InvalidParamException::new);
    }
}
