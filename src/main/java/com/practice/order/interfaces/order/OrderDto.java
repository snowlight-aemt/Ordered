package com.practice.order.interfaces.order;

import com.practice.order.domain.order.fragment.DeliveryFragment;
import com.practice.order.domain.order.payment.PayMethod;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class OrderDto {

    @Getter
    @Builder
    public static class RegisterOrderRequest {
        private Long userId;
        private DeliveryFragment deliveryFragment;
        private String payMethod;
        private List<OrderDto.RegisterOrderItemRequest> orderItems;
    }

    @Getter
    @Builder
    public static class RegisterOrderItemRequest {
        private Integer orderCount;
        private String itemName;
        private String itemToken;
        private Long itemPrice;

        private List<OrderDto.RegisterOrderItemOptionGroupRequest> orderItemOptionGroups;
    }

    @Getter
    @Builder
    public static class RegisterOrderItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<OrderDto.RegisterOrderItemOptionRequest> orderItemOptions;
    }

    @Getter
    @Builder
    public static class RegisterOrderItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;
    }

    @Getter
    @Builder
    public static class RegisterOrderResponse {
        private String orderToken;
    }

    @Getter
    @Builder
    public static class PaymentRequest {
        private String orderToken;
        private Long amount;
        private PayMethod payMethod;
    }
}
