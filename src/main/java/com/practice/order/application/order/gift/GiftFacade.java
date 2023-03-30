package com.practice.order.application.order.gift;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.gift.GiftOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftFacade {
    private final GiftOrderService giftOrderService;

    public void paymentOrder(OrderCommand.PaymentRequest command) {
        this.giftOrderService.paymentOrder(command);
    }
}
