package com.practice.order.interfaces.gift;

import com.practice.order.domain.order.fragment.DeliveryFragment;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class GiftDto {

    @Getter
    public static class RegisterOrder {
        @NotNull(message = "buyerUserId 는 필수 값입니다.")
        private Long buyerUserId;
        @NotNull(message = "payMethod 는 필수 값입니다.")
        private String payMethod;
        private List<RegisterOrderItem> orderItems;
        private DeliveryFragment deliveryFragment;

        @Builder
        public RegisterOrder(Long buyerUserId,
                             String payMethod,
                             List<RegisterOrderItem> orderItems) {
            this.buyerUserId = buyerUserId;
            this.payMethod = payMethod;
            this.orderItems = orderItems;
            this.deliveryFragment = new DeliveryFragment(
                                    "TEMP_VALUE",
                                    "TEMP_VALUE",
                                    "TEMP_VALUE",
                                    "TEMP_VALUE",
                                    "TEMP_VALUE",
                                    "TEMP_VALUE");
        }
    }

    @Getter
    @Builder
    public static class RegisterOrderItem {
        @NotNull(message = "orderCount 는 필수 값입니다.")
        private Integer orderCount;
        @NotNull(message = "itemName 는 필수 값입니다.")
        private String itemName;
        @NotNull(message = "itemToken 는 필수 값입니다.")
        private String itemToken;
        @NotNull(message = "itemPrice 는 필수 값입니다.")
        private Long itemPrice;

        private List<RegisterOrderItemOptionGroup> orderItemOptionGroups;
    }

    @Getter
    @Builder
    public static class RegisterOrderItemOptionGroup {
        @NotNull(message = "ordering 는 필수 값입니다.")
        private Integer ordering;
        @NotNull(message = "itemOptionGroupName 는 필수 값입니다.")
        private String itemOptionGroupName;
        private List<RegisterOrderItemOption> orderItemOptions;
    }

    @Getter
    @Builder
    public static class RegisterOrderItemOption {
        @NotNull(message = "ordering 는 필수 값입니다.")
        private Integer ordering;
        @NotNull(message = "itemOptionGroupName 는 필수 값입니다.")
        private String itemOptionName;
        private Long itemOptionPrice;
    }
}
