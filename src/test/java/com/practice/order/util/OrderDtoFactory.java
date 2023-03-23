package com.practice.order.util;

import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemOption;
import com.practice.order.domain.item.ItemOptionGroup;
import com.practice.order.domain.item.ItemReader;
import com.practice.order.domain.order.fragment.DeliveryFragment;
import com.practice.order.interfaces.order.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// TODO MOCK 으로 대체... 고민
@Component
public class OrderDtoFactory {
    @Autowired
    ItemReader itemReader;

    @Transactional
    public OrderDto.RegisterOrderRequest convertRegisterOrderRequest(String itemToken) {
        Item item = this.itemReader.getItemBy(itemToken);
        ItemOptionGroup itemOptionGroup = item.getItemOptionGroups().get(0);
        ItemOption itemOption = itemOptionGroup.getItemOptionList().get(0);

        var orderItemOption = getRegisterOrderItemOptionRequest(itemOption);
        var orderItemOptionGroup = getRegisterOrderItemOptionGroupRequest(
                                                                itemOptionGroup,
                                                                orderItemOption);
        var orderItem = getRegisterOrderItemRequest(item, orderItemOptionGroup);
        return getRegisterOrderRequest(orderItem);
    }

    private static OrderDto.RegisterOrderRequest getRegisterOrderRequest(OrderDto.RegisterOrderItemRequest orderItem) {
        return OrderDto.RegisterOrderRequest.builder()
                .userId(1000L)
                .payMethod("KAKAO_PAY")
                .deliveryFragment(DeliveryFragment.builder()
                        .receiverName("name")
                        .receiverPhone("000-000-0000")
                        .receiverZipcode("000-000")
                        .receiverAddress1("address 1")
                        .receiverAddress2("address 2")
                        .etcMessage("etc message")
                        .build())
                .orderItems(List.of(orderItem))
                .build();
    }

    private static OrderDto.RegisterOrderItemRequest getRegisterOrderItemRequest(Item item, OrderDto.RegisterOrderItemOptionGroupRequest orderItemOptionGroup) {
        return OrderDto.RegisterOrderItemRequest.builder()
                .orderCount(1)
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .itemToken(item.getItemToken())
                .orderItemOptionGroups(List.of(orderItemOptionGroup))
                .build();
    }

    private static OrderDto.RegisterOrderItemOptionGroupRequest getRegisterOrderItemOptionGroupRequest(ItemOptionGroup itemOptionGroup, OrderDto.RegisterOrderItemOptionRequest orderItemOption) {
        return OrderDto.RegisterOrderItemOptionGroupRequest.builder()
                .ordering(1)
                .itemOptionGroupName(itemOptionGroup.getItemOptionGroupName())
                .orderItemOptions(List.of(orderItemOption))
                .build();
    }

    private static OrderDto.RegisterOrderItemOptionRequest getRegisterOrderItemOptionRequest(ItemOption itemOption) {
        return OrderDto.RegisterOrderItemOptionRequest.builder()
                .ordering(1)
                .itemOptionName(itemOption.getItemOptionName())
                .itemOptionPrice(itemOption.getItemOptionPrice())
                .build();
    }
}
