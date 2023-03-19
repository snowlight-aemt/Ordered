package com.practice.order.domain.order;

import com.google.common.collect.Lists;
import com.practice.order.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class OrderCommand {

    @Getter
    @Builder
    public static class RegisterOrder {
        private Long userId;
        private String receiverName;
        private String receiverPhone;
        private String receiverZipcode;
        private String receiverAddress1;
        private String receiverAddress2;
        private String etcMessage;
        private String payMethod;
        private List<RegisterOrderItem> orderItems;

        public Order toEntity() {
            return Order.builder()
                    .receiverName(this.receiverName)
                    .receiverPhone(this.receiverPhone)
                    .receiverZipcode(this.receiverZipcode)
                    .receiverAddress1(this.receiverAddress1)
                    .receiverAddress2(this.receiverAddress2)
                    .etcMessage(this.etcMessage)
                    .payMethod(this.payMethod)
                    .userId(this.userId)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RegisterOrderItem {
        private Integer orderCount;
        private String itemName;
        private String itemToken;
        private Long itemPrice;

        private List<RegisterOrderItemOptionGroup> orderItemOptionGroups;

        public OrderItem toEntity(Order order, Item item) {
            return OrderItem.builder()
                    .itemId(item.getId())
                    .itemToken(this.itemToken)
                    .itemName(this.itemName)
                    .itemPrice(this.itemPrice)
                    .partnerId(item.getPartnerId())
                    .order(order)
                    .orderCount(this.orderCount)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItemOptionGroup {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<RegisterOrderItemOption> orderItemOptionList;

        public OrderItemOptionGroup toEntity(OrderItem orderItem) {
            return OrderItemOptionGroup.builder()
                    .orderItem(orderItem)
                    .ordering(ordering)
                    .itemOptionGroupName(itemOptionGroupName)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RegisterOrderItemOption {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;

        public OrderItemOption toEntity(OrderItemOptionGroup orderItemOptionGroup) {
            return OrderItemOption.builder()
                    .orderItemOptionGroup(orderItemOptionGroup)
                    .ordering(ordering)
                    .itemOptionName(itemOptionName)
                    .itemOptionPrice(itemOptionPrice)
                    .build();
        }
    }
}
