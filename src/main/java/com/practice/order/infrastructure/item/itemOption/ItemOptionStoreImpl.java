package com.practice.order.infrastructure.item.itemOption;

import com.practice.order.domain.item.ItemOption;
import com.practice.order.domain.item.ItemOptionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemOptionStoreImpl implements ItemOptionStore {
    private final ItemOptionRepository itemOptionRepository;
    @Override
    public ItemOption store(ItemOption itemOption) {
        return this.itemOptionRepository.save(itemOption);
    }
}
