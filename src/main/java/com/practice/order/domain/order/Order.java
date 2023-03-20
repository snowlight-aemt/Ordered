package com.practice.order.domain.order;

import com.google.common.collect.Lists;
import com.practice.order.common.exception.IllegalStatusException;
import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.common.util.TokenGenerator;
import com.practice.order.domain.AbstractEntity;
import com.practice.order.domain.order.fragment.DeliveryFragment;
import com.practice.order.domain.order.item.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends AbstractEntity {
    private static final String ORDER_PREFIX = "ord_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderToken;
    private Long userId;
    private String payMethod;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems = Lists.newArrayList();

    @Embedded
    private DeliveryFragment deliveryFragment;

    private ZonedDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    public boolean isAlreadyPaymentComplete() {
        return this.getStatus() != Status.INIT;
    }


    @Getter
    @RequiredArgsConstructor
    public enum Status {
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료"),
        DELIVERY_PREPARE("배송준비"),
        IN_DELIVERY("배송중"),
        DELIVERY_COMPLETE("배송완료");

        private final String description;
    }

    @Builder
    public Order(Long userId,
                 DeliveryFragment deliveryFragment,
                 String payMethod) {

        if (userId == null) throw new InvalidParamException("Order.userId");
        if (deliveryFragment == null) throw new InvalidParamException("Order.deliveryFragment");
        if (StringUtils.isEmpty(payMethod)) throw new InvalidParamException("Order.payMethod");

        this.userId = userId;
        this.deliveryFragment = deliveryFragment;
        this.payMethod = payMethod;

        this.orderToken = TokenGenerator.randomCharacterWithPrefix(ORDER_PREFIX);
        this.status = Status.INIT;
        this.orderedAt = ZonedDateTime.now();
    }

    public Long calculateTotalAmount() {
        return this.orderItems.stream()
                .mapToLong(OrderItem::calculateTotalAmount)
                .sum();
    }

    public void orderComplete() {
        if (this.status != Status.INIT) throw new IllegalStatusException();

        this.status = Status.ORDER_COMPLETE;
    }
}
