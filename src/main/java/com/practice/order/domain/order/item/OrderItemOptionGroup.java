package com.practice.order.domain.order.item;

import com.google.common.collect.Lists;
import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "order_item_option_groups")
public class OrderItemOptionGroup extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;
    private Integer ordering;
    private String itemOptionGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemOptionGroup", cascade = CascadeType.PERSIST)
    private List<OrderItemOption> orderItemOptions = Lists.newArrayList();

    @Builder
    public OrderItemOptionGroup(OrderItem orderItem,
                                Integer ordering,
                                String itemOptionGroupName) {
        if (orderItem == null) throw new InvalidParamException("OrderItemOptionGroup.orderItem");
        if (ordering == null) throw new InvalidParamException("OrderItemOptionGroup.ordering");
        if (StringUtils.isEmpty(itemOptionGroupName)) throw new InvalidParamException("OrderItemOptionGroup.orderItemOptionGroupName");

        this.orderItem = orderItem;
        this.ordering = ordering;
        this.itemOptionGroupName = itemOptionGroupName;
    }
}
