package com.practice.order.domain.item;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "item_options")
public class ItemOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer ordering;
    private String itemOptionName;
    private Long itemOptionPrice;

    @ManyToOne
    @JoinColumn(name = "item_option_group_id")
    private ItemOptionGroup itemOptionGroup;

    @Builder
    public ItemOption(ItemOptionGroup itemOptionGroup, Integer ordering, String itemOptionName, Long itemOptionPrice) {
        if (itemOptionGroup == null) throw new InvalidParamException("ItemOption.item");
        if (ordering == null) throw new InvalidParamException("ItemOption.ordering");
        if (itemOptionPrice == null) throw new InvalidParamException("ItemOption.itemOptionPrice");
        if (StringUtils.isEmpty(itemOptionName)) throw new InvalidParamException("ItemOption.itemOptionName");

        this.itemOptionGroup = itemOptionGroup;
        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
        this.itemOptionPrice = itemOptionPrice;
    }
}