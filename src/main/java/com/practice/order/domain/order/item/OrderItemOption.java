package com.practice.order.domain.order.item;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "order_item_options")
public class OrderItemOption extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_option_group_id")
    private OrderItemOptionGroup orderItemOptionGroup;
    private Integer ordering;
    private String itemOptionName;
    private Long itemOptionPrice;

    @Builder
    public OrderItemOption(OrderItemOptionGroup orderItemOptionGroup,
                           Integer ordering,
                           String itemOptionName,
                           Long itemOptionPrice) {
        if (orderItemOptionGroup == null) throw new InvalidParamException("OrderItemOption.orderItemOptionGroup");
        if (ordering == null) throw new InvalidParamException("OrderItemOption.ordering");
        if (StringUtils.isEmpty(itemOptionName)) throw new InvalidParamException("OrderItemOption.StringUtils");
        if (itemOptionPrice == null) throw new InvalidParamException("OrderItemOption.itemOptionPrice");

        this.orderItemOptionGroup = orderItemOptionGroup;
        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
        this.itemOptionPrice = itemOptionPrice;
    }
}
