package com.practice.order.domain.item;

import org.springframework.stereotype.Component;

@Component
public interface ItemStore {
    public Item store(Item item);
}
