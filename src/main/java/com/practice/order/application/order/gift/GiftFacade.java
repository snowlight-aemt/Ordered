package com.practice.order.application.order.gift;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GiftFacade {
    private final OrderService orderService;

    public void registerGiftOrder(OrderCommand.RegisterOrder command) {
        String orderToken = this.orderService.registerOrder(command);

    }
}
