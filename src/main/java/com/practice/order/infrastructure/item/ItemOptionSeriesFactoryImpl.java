package com.practice.order.infrastructure.item;

import com.practice.order.domain.item.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ItemOptionSeriesFactoryImpl implements ItemOptionSeriesFactory {
    private final ItemOptionGroupStore itemOptionGroupStore;
    private final ItemOptionStore itemOptionStore;
    @Override
    public List<ItemOptionGroup> store(ItemCommand.RegisterItemRequest command, Item item) {
        var initItemOptionGroupList = command.getItemOptionGroupRequestList();
        if (initItemOptionGroupList == null) return Collections.emptyList();

        return initItemOptionGroupList.stream()
                .map(requestItemOptionGroup -> {
                    ItemOptionGroup initItemOptionGroup = ItemOptionGroup.builder()
                            .item(item)
                            .itemOptionGroupName(requestItemOptionGroup.getItemOptionGroupName())
                            .ordering(requestItemOptionGroup.getOrdering())
                            .build();

                    ItemOptionGroup itemOptionGroup = this.itemOptionGroupStore.store(initItemOptionGroup);

                    requestItemOptionGroup.getItemOptionRequestList().forEach(requestItemOption -> {
                        ItemOption initItemOption = ItemOption.builder()
                                .itemOptionName(requestItemOption.getItemOptionName())
                                .itemOptionPrice(requestItemOption.getItemOptionPrice())
                                .itemOptionGroup(itemOptionGroup)
                                .ordering(requestItemOptionGroup.getOrdering())
                                .build();

                        this.itemOptionStore.store(initItemOption);
                    });

                    return itemOptionGroup;
                })
                .collect(Collectors.toList());
    }
}
