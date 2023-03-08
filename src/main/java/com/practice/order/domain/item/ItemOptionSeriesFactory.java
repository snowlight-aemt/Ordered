package com.practice.order.domain.item;

import java.util.List;

public interface ItemOptionSeriesFactory {
    public List<ItemOptionGroup> store(ItemCommand.RegisterItemRequest command, Item item);
}
