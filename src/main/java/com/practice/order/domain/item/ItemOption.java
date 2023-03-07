package com.practice.order.domain.item;

import com.practice.order.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "item_options")
public class ItemOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ordering;
    private String itemOptionName;
    private Long itemOptionPrice;

    @ManyToOne
    @JoinColumn(name = "item option_group_id")
    private ItemOptionGroup itemOptionGroup;

}