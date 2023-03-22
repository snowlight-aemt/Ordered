package com.practice.order.interfaces.order;

import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemOption;
import com.practice.order.domain.item.ItemOptionGroup;
import com.practice.order.domain.item.ItemReader;
import com.practice.order.domain.order.fragment.DeliveryFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OrderFactory {

    @Autowired
    ItemReader itemReader;

    @Transactional
    public OrderDto.RegisterOrderRequest createRegisterOrderRequest(String itemToken) {
        Item item = this.itemReader.getItemBy(itemToken);
        ItemOptionGroup itemOptionGroup = item.getItemOptionGroups().get(0);
        ItemOption itemOption = itemOptionGroup.getItemOptionList().get(0);

        var orderItemOption = OrderDto.RegisterOrderItemOptionRequest.builder()
                .ordering(1)
                .itemOptionName(itemOption.getItemOptionName())
                .itemOptionPrice(itemOption.getItemOptionPrice())
                .build();

        var orderItemOptionGroup = OrderDto.RegisterOrderItemOptionGroupRequest.builder()
                .ordering(1)
                .itemOptionGroupName(itemOptionGroup.getItemOptionGroupName())
                .orderItemOptions(List.of(orderItemOption))
                .build();

        var orderItem = OrderDto.RegisterOrderItemRequest.builder()
                .orderCount(1)
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .itemToken(item.getItemToken())
                .orderItemOptionGroups(List.of(orderItemOptionGroup))
                .build();

        var order = OrderDto.RegisterOrderRequest.builder()
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
        return order;
    }
}
