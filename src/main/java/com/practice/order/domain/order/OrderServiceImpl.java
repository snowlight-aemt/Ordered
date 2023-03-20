package com.practice.order.domain.order;

import com.practice.order.domain.order.item.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderStore orderStore;
    private final OrderReader orderReader;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderItemSeriesFactory orderItemSeriesFactory;
    private final com.practice.order.domain.order.payment.PaymentProcessor PaymentProcessor;

    @Transactional
    @Override
    public String registerOrder(OrderCommand.RegisterOrder command) {
        Order order = orderStore.store(command.toEntity());
        orderItemSeriesFactory.store(order, command.getOrderItems());

        return order.getOrderToken();
    }

    @Transactional
    @Override
    public void paymentOrder(OrderCommand.PaymentRequest paymentRequest) {
        String orderToken = paymentRequest.getOrderToken();
        Order order = this.orderReader.getOrderBy(orderToken);
        this.PaymentProcessor.pay(paymentRequest);
        order.orderComplete();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderInfo.Main retrieveOrder(String orderToken) {
        Order order = this.orderReader.getOrderBy(orderToken);
        List<OrderItem> orderItems = order.getOrderItems();

        return this.orderInfoMapper.of(order, orderItems);
    }
}
