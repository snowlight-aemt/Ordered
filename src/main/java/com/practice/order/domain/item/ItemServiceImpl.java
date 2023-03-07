package com.practice.order.domain.item;

import com.practice.order.domain.partner.Partner;
import com.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemOptionGroupStore itemOptionGroupStore;
    private final ItemOptionStore itemOptionStore;

    @Override
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        Partner partner = this.partnerReader.getPartner(partnerToken);
        Long partnerId = partner.getId();

        Item initItem = Item.builder()
                            .itemName(command.getItemName())
                            .itemPrice(command.getItemPrice())
                            .partnerId(partnerId)
                            .build();
        Item item = this.itemStore.store(initItem);

        for (var requestItemOptionGroup : command.getItemOptionGroupRequestList()) {
            ItemOptionGroup initItemOptionGroup = ItemOptionGroup.builder()
                                .item(item)
                                .itemOptionGroupName(requestItemOptionGroup.getItemOptionGroupName())
                                .ordering(requestItemOptionGroup.getOrdering())
                                .build();

            ItemOptionGroup itemOptionGroup = this.itemOptionGroupStore.store(initItemOptionGroup);

            for (var requestItemOption : requestItemOptionGroup.getItemOptionRequestList()) {
                ItemOption initItemOption = ItemOption.builder()
                            .itemOptionName(requestItemOption.getItemOptionName())
                            .itemOptionPrice(requestItemOption.getItemOptionPrice())
                            .itemOptionGroup(itemOptionGroup).build();

                this.itemOptionStore.store(initItemOption);
            }
        }

        return item.getItemToken();
    }

    @Override
    public void changeOnSale(String ItemToken) {

    }

    @Override
    public void changeEndOfSale(String ItemToken) {

    }

    @Override
    public ItemInfo.Main retrieveItemInfo(String ItemToken) {
        return null;
    }
}
