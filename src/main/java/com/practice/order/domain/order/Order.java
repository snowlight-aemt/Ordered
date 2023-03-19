package com.practice.order.domain.order;

import com.google.common.collect.Lists;
import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.common.util.TokenGenerator;
import com.practice.order.domain.AbstractEntity;
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

    private String receiverName;
    private String receiverZipcode;
    private String receiverPhone;
    private String receiverAddress1;
    private String receiverAddress2;
    private String etcMessage;

    private ZonedDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

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
                 String receiverName,
                 String receiverPhone,
                 String receiverZipcode,
                 String receiverAddress1,
                 String receiverAddress2,
                 String etcMessage,
                 String payMethod) {

        if (userId == null) throw new InvalidParamException("Order.userId");
        if (StringUtils.isEmpty(receiverName)) throw new InvalidParamException("Order.receiverName");
        if (StringUtils.isEmpty(receiverPhone)) throw new InvalidParamException("Order.receiverPhone");
        if (StringUtils.isEmpty(receiverZipcode)) throw new InvalidParamException("Order.receiverZipCode");
        if (StringUtils.isEmpty(receiverAddress1)) throw new InvalidParamException("Order.receiverAddress1");
        if (StringUtils.isEmpty(receiverAddress2)) throw new InvalidParamException("Order.receiverAddress2");
        if (StringUtils.isEmpty(etcMessage)) throw new InvalidParamException("Order.etcMessage");
        if (StringUtils.isEmpty(payMethod)) throw new InvalidParamException("Order.payMethod");

        this.userId = userId;
        this.receiverName = receiverName;
        this.receiverZipcode = receiverZipcode;
        this.receiverPhone = receiverPhone;
        this.receiverAddress1 = receiverAddress1;
        this.receiverAddress2 = receiverAddress2;
        this.etcMessage = etcMessage;
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
}
