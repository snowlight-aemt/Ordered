package com.practice.order.infrastructure.item;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.item.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {
    private final ItemRepository itemRepository;

    @Override
    public Item getItemBy(String itemToken) {
        return this.itemRepository.findByItemToken(itemToken).orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<ItemInfo.ItemOptionGroupInfo> getItemOptionSeries(Item item) {
        List<ItemOptionGroup> itemOptionGroups = item.getItemOptionGroups();

        return itemOptionGroups.stream().map(itemOptionGroup -> {
            List<ItemOption> itemOptionList = itemOptionGroup.getItemOptionList();
            var itemOptionInfoList = itemOptionList.stream()
                    .map(ItemInfo.ItemOptionInfo::new)
                    .collect(Collectors.toList());

            return new ItemInfo.ItemOptionGroupInfo(itemOptionGroup, itemOptionInfoList);
        }).collect(Collectors.toList());
    }
}
