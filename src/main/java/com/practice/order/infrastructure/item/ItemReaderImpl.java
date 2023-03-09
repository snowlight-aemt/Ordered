package com.practice.order.infrastructure.item;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {
    private final ItemRepository itemRepository;

    @Override
    public Item getItemBy(String itemToken) {
        return this.itemRepository.findByItemToken(itemToken).orElseThrow(InvalidParamException::new);
    }
}
