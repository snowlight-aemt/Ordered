package com.practice.order.interfaces.order;

import com.practice.order.domain.order.OrderInfo;
import com.practice.order.domain.order.fragment.DeliveryFragment;
import com.practice.order.domain.order.payment.PayMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
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

    @Getter
    @Builder
    public static class Main {
        private final String orderToken;
        private final Long userId;
        private final String payMethod;
        private final Long totalAmount;
        private final DeliveryInfo deliveryInfo;
        private final ZonedDateTime orderedAt;
        private final String status;
        private final String statusDescription;
        private final List<OrderItem> orderItems;
    }
    @Getter
    @Builder
    @ToString
    public static class DeliveryInfo {
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;
    }

    @Getter
    @Builder
    public static class OrderItem {
        private final Integer orderCount;
        private final Long partnerId;
        private final Long itemId;
        private final String itemName;
        private final Long totalAmount;
        private final Long itemPrice;
        private final String deliveryStatus;
        private final String deliveryStatusDescription;
        private final List<OrderItemOptionGroup> orderItemOptionGroups;
    }

    @Getter
    @Builder
    public static class OrderItemOptionGroup {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<OrderItemOption> orderItemOptions;
    }

    @Getter
    @Builder
    public static class OrderItemOption {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;
    }
}
