package com.practice.order.domain.item;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.common.util.TokenGenerator;
import com.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "items")
public class Item extends AbstractEntity {
    public static final String PREFIX_ITEM = "itm_";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemToken;

    private Long partnerId;
    private String itemName;
    private String itemPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "item")
    private List<ItemOptionGroup> itemOptionGroups;

    @Getter
    @AllArgsConstructor
    public enum Status {
        PREPARE("판매준비중"),
        ON_SALES("준비중"),
        END_OF_SALES("판매완료");

        private final String description;
    }

    @Builder
    public Item(Long partnerId, String  itemName, String itemPrice) {
        if (partnerId == null) throw new InvalidParamException("item.partnerId");
        if (StringUtils.isEmpty(itemName)) throw new InvalidParamException("item.itemName");
        if (StringUtils.isEmpty(itemPrice)) throw new InvalidParamException("item.itemPrice");

        this.partnerId = partnerId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;

        this.status = Status.PREPARE;
        this.itemToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_ITEM);
    }

    public void changePrepare() {
        this.status = Status.PREPARE;
    }

    public void changeOnSales() {
        this.status = Status.ON_SALES;
    }

    public void endOfSales() {
        this.status = Status.END_OF_SALES;
    }
}
