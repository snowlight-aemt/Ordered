package com.practice.order.domain.order;

import com.google.common.collect.Lists;
import com.practice.order.common.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "order_items_option_groups")
public class OrderItemOptionGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemOptionGroupId;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
    private Integer ordering;
    private String orderItemOptionGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemOptionGroup", cascade = CascadeType.PERSIST)
    private List<OrderItemOption> orderItemOptions = Lists.newArrayList();

    @Builder
    public OrderItemOptionGroup(OrderItem orderItem,
                                Integer ordering,
                                String orderItemOptionGroupName) {
        if (orderItem == null) throw new InvalidParamException("OrderItemOptionGroup.orderItem");
        if (ordering == null) throw new InvalidParamException("OrderItemOptionGroup.ordering");
        if (StringUtils.isEmpty(orderItemOptionGroupName)) throw new InvalidParamException("OrderItemOptionGroup.orderItemOptionGroupName");

        this.orderItem = orderItem;
        this.ordering = ordering;
        this.orderItemOptionGroupName = orderItemOptionGroupName;
    }
}
