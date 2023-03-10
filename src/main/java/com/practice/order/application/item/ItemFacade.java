package com.practice.order.application.item;

import com.practice.order.domain.item.ItemCommand;
import com.practice.order.domain.item.ItemInfo;
import com.practice.order.domain.item.ItemService;
import com.practice.order.domain.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemFacade {
    private ItemService itemService;
    private NotificationService notificationService;

    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        String itemToken = this.itemService.registerItem(command, partnerToken);
        notificationService.sendEmail(null, null, null);
        return itemToken;
    }

    public void changeOnSaleItem(String itemToken) {
        this.itemService.changeOnSale(itemToken);
    }

    public void changeEndOnSaleItem(String itemToken) {
        this.itemService.changeEndOfSale(itemToken);
    }

    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        return this.itemService.retrieveItemInfo(itemToken);
    }
}
