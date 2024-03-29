package com.practice.order.application.order;

import com.practice.order.domain.notification.NotificationService;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderInfo;
import com.practice.order.domain.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderService orderService;
    private final NotificationService notificationService;

    public String registerOrder(OrderCommand.RegisterOrder command) {
        String orderToken = this.orderService.registerOrder(command);
        notificationService.sendKakao(null, null);
        return orderToken;
    }

    public void pay(OrderCommand.PaymentRequest command) {
        this.orderService.paymentOrder(command);
        notificationService.sendKakao(null, null);
    }

    public void updateReceiverInfo(String orderToken, OrderCommand.UpdateReceiverCommand command) {
        this.orderService.updateReceiverInfo(orderToken, command);
        notificationService.sendKakao(null, null);
    }

    public OrderInfo.Main retrieveOrder(String orderToken) {
        return this.orderService.retrieveOrder(orderToken);
    }
}
