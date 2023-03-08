package com.practice.order.infrastructure.item.itemOptionGroup;

import com.practice.order.domain.item.ItemOptionGroup;
import com.practice.order.domain.item.ItemOptionGroupStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemOptionGroupStoreImpl implements ItemOptionGroupStore {
    private final ItemOptionGroupRepository itemOptionGroupRepository;
    @Override
    public ItemOptionGroup store(ItemOptionGroup itemOptionGroup) {
        return this.itemOptionGroupRepository.save(itemOptionGroup);
    }
}
