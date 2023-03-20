package com.practice.order.infrastructure.order;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.PayMethod;
import org.springframework.stereotype.Component;

@Component
public class NaverPayApiCaller implements PaymentApiCaller {
    @Override
    public boolean support(PayMethod payMethod) {
        return payMethod == PayMethod.NAVER_PAY;
    }

    @Override
    public void pay(OrderCommand.PaymentRequest paymentRequest) {
        // 네이버 //
    }
}
