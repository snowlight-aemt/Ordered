package com.practice.order.infrastructure.item;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemStore;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ItemStoreImpl implements ItemStore {
    private final ItemRepository itemRepository;

    @Override
    public Item store(Item item) {
        validCheck(item);
        return this.itemRepository.save(item);
    }

    private  void validCheck(Item item) {
        if (StringUtils.isEmpty(item.getItemToken())) throw new InvalidParamException("Item.itemToken");
        if (StringUtils.isEmpty(item.getItemName())) throw new InvalidParamException("Item.itemName");

        if (item.getPartnerId() == null) throw new InvalidParamException("Item.partnerId");
        if (item.getItemPrice() == null) throw new InvalidParamException("Item.itemPrice");
        if (item.getStatus() == null) throw new InvalidParamException("Item.itemStatus");
    }
}
