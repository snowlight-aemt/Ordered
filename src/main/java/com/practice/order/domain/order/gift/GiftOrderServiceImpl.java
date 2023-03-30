package com.practice.order.domain.order.gift;

import com.practice.order.common.exception.IllegalStatusException;
import com.practice.order.domain.order.Order;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderReader;
import com.practice.order.domain.order.OrderService;
import com.practice.order.domain.order.payment.PaymentProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GiftOrderServiceImpl implements GiftOrderService {
    private final OrderReader orderReader;
    private final PaymentProcessor paymentProcessor;
    @Override
    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        Order order = orderReader.getOrderBy(paymentRequest.getOrderToken());

        if (order.getStatus() != Order.Status.INIT) throw new IllegalStatusException();

        this.paymentProcessor.pay(order, paymentRequest);
        order.orderComplete();


    }
}
