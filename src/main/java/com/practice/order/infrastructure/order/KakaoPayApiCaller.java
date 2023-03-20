package com.practice.order.infrastructure.order;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.PayMethod;
import org.springframework.stereotype.Component;

@Component
public class KakaoPayApiCaller implements PaymentApiCaller {
    @Override
    public boolean support(PayMethod payMethod) {
        return payMethod == PayMethod.KAKAO_PAY;
    }

    @Override
    public void pay(OrderCommand.PaymentRequest paymentRequest) {
        // Kakao pay //
    }
}
