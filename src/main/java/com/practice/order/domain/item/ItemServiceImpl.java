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
    private final ItemOptionSeriesFactory itemOptionSeriesFactory;

    @Override
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        Partner partner = this.partnerReader.getPartner(partnerToken);
        Item initItem = command.toEntity(partner.getId());

        Item item = this.itemStore.store(initItem);
        this.itemOptionSeriesFactory.store(command, item);

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
