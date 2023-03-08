package com.practice.order.domain.item;

public interface ItemService {
    String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken);
    void changeOnSale(String ItemToken);
    void changeEndOfSale(String ItemToken);
    ItemInfo.Main retrieveItemInfo(String ItemToken);
}
