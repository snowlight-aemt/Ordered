package com.practice.order.util;

import com.practice.order.domain.item.ItemService;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.interfaces.item.ItemDto;
import com.practice.order.interfaces.item.ItemDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemServiceFactory {
    @Autowired
    ItemService itemService;
    @Autowired
    ItemDtoMapper itemDtoMapper;

    public String registerItem(String partnerToken) {
        var itemDtoRequest = ItemServiceFactory.createRegisterItemRequestWithOption(partnerToken);
        var command = itemDtoMapper.of(itemDtoRequest);
        return this.itemService.registerItem(command, partnerToken);
    }

    public static ItemDto.RegisterItemRequest createRegisterItemRequestWithoutOption(PartnerInfo partnerInfo) {
        return ItemDto.RegisterItemRequest.builder()
                .itemName("Item Test Product 01")
                .itemPrice(200_000L)
                .partnerToken(partnerInfo.getPartnerToken())
                .build();
    }

    public static ItemDto.RegisterItemRequest createRegisterItemRequestWithOption(String partnerToken) {
        var smallOption = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("small")
                .itemOptionPrice(30_000L)
                .ordering(1)
                .build();
        var largeOption = ItemDto.RegisterItemOptionRequest.builder()
                .itemOptionName("large")
                .itemOptionPrice(30_000L)
                .ordering(1)
                .build();

        var colorGroup = ItemDto.RegisterItemOptionGroupRequest.builder()
                .ordering(1)
                .itemOptionGroupName("color")
                .itemOptionList(List.of(smallOption, largeOption))
                .build();

        var itemRequest = ItemDto.RegisterItemRequest.builder()
                .itemName("Item Test Product 01")
                .itemPrice(200_000L)
                .partnerToken(partnerToken)
                .itemOptionGroupList(List.of(colorGroup))
                .build();

        return itemRequest;
    }
}
