package com.practice.order.domain.item;

import java.util.List;
import java.util.Optional;

public interface ItemReader {
    public Item getItemBy(String itemToken);

    public List<ItemInfo.ItemOptionGroupInfo> getItemOptionSeries(Item item);
}
