package com.practice.order.domain.order;

import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemReader;
import com.practice.order.domain.order.item.OrderItem;
import com.practice.order.domain.order.item.OrderItemOption;
import com.practice.order.domain.order.item.OrderItemOptionGroup;
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

    @Transactional
    @Override
    public String registerOrder(OrderCommand.RegisterOrder command) {
        Order order = orderStore.store(command.toEntity());
        orderItemSeriesFactory.store(order, command.getOrderItems());

        return order.getOrderToken();
    }

    @Transactional(readOnly = true)
    @Override
    public OrderInfo.Main retrieveOrder(String orderToken) {
        Order order = this.orderReader.getOrderBy(orderToken);
        List<OrderItem> orderItems = order.getOrderItems();

        return this.orderInfoMapper.of(order, orderItems);
    }
}
