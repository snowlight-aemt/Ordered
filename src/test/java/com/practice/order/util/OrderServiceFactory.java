package com.practice.order.util;

import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderService;
import com.practice.order.domain.order.fragment.DeliveryFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderServiceFactory {
    @Autowired
    private OrderService orderService;

    public String registerOrder(String itemToken) {
        OrderCommand.RegisterOrderItemOption optionRequest = OrderCommand.RegisterOrderItemOption.builder()
                .itemOptionName("option name")
                .ordering(1)
                .itemOptionPrice(1_000L)
                .build();

        OrderCommand.RegisterOrderItemOptionGroup itemGroupRequest = OrderCommand.RegisterOrderItemOptionGroup.builder()
                .ordering(1)
                .itemOptionGroupName("group name")
                .orderItemOptions(List.of(optionRequest))
                .build();

        OrderCommand.RegisterOrderItem registerOrderRequest = OrderCommand.RegisterOrderItem.builder()
                .itemToken(itemToken)
                .itemPrice(10_000L)
                .itemName("item name")
                .orderCount(1)
                .orderItemOptionGroups(List.of(itemGroupRequest))
                .build();

        OrderCommand.RegisterOrder command = OrderCommand.RegisterOrder.builder()
                .userId(1L)
                .deliveryFragment(DeliveryFragment.builder()
                        .receiverName("Test Name")
                        .receiverPhone("010-000-0000")
                        .receiverZipcode("000-000")
                        .receiverAddress1("Address1")
                        .receiverAddress2("Address2")
                        .etcMessage("etc message")
                        .build())
                .orderItems(List.of(registerOrderRequest))
                .payMethod("KAKAO_PAY")
                .build();

        return orderService.registerOrder(command);
    }
}