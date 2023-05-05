package com.practice.order.infrastructure.order.payment;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.payment.PayMethod;

public interface PaymentApiCaller {
    public boolean support(PayMethod payMethod);

    public default KakaoPaymentDto.PaymentWaitResponse inPay(OrderCommand.PaymentWaitRequest paymentWaitRequest) {
        throw new UnsupportedOperationException("결제 방식 " + getClass() + "은 해당 메서드 기능을 제공하지 않습니다.");
    }

    public void pay(OrderCommand.PaymentRequest paymentRequest);
}
