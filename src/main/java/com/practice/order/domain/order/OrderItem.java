package com.practice.order.domain.order;

import com.practice.order.common.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = CascadeType.PERSIST)
    private List<OrderItemOptionGroup> orderItemOptionGroups;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    private Integer orderCount;
    private Long partnerId;
    private Long itemId;
    private String itemName;
    private String itemToken;
    private Long itemPrice;

    @Builder
    public OrderItem(Order order,
                     Integer orderCount,
                     Long partnerId,
                     Long itemId,
                     String itemName,
                     String itemToken,
                     Long itemPrice) {
        if (order == null) throw new InvalidParamException("OrderItem.order");
        if (orderCount == null) throw new InvalidParamException("OrderItem.orderCount");
        if (partnerId == null) throw new InvalidParamException("OrderItem.partnerId");
        if (itemId == null) throw new InvalidParamException("OrderItem.itemId");
        if (StringUtils.isEmpty(itemName)) throw new InvalidParamException("OrderItem.itemName");
        if (StringUtils.isEmpty(itemToken)) throw new InvalidParamException("OrderItem.itemToken");
        if (itemPrice == null) throw new InvalidParamException("OrderItem.itemPrice");

        this.order = order;
        this.orderCount = orderCount;
        this.partnerId = partnerId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemToken = itemToken;
        this.itemPrice = itemPrice;

        this.deliveryStatus = DeliveryStatus.INIT;
    }

    @Getter
    @RequiredArgsConstructor
    private enum DeliveryStatus {
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료"),
        DELIVERY_PREPARE("배송준비"),
        IN_DELIVERY("배송중"),
        DELIVERY_COMPLETE("배송완료");

        private final String description;
    }

}
