package com.practice.order.domain.item;

import com.practice.order.domain.partner.Partner;
import com.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final PartnerReader partnerReader;
    private final ItemReader itemReader;
    private final ItemStore itemStore;
    private final ItemOptionSeriesFactory itemOptionSeriesFactory;

    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        Partner partner = this.partnerReader.getPartner(partnerToken);
        Item initItem = command.toEntity(partner.getId());

        Item item = this.itemStore.store(initItem);
        this.itemOptionSeriesFactory.store(command, item);

        return item.getItemToken();
    }

    @Override
    @Transactional
    public void changeOnSale(String itemToken) {
        Item item = itemReader.getItemBy(itemToken);
        item.changeOnSales();
    }

    @Override
    public void changeEndOfSale(String itemToken) {
        Item item = itemReader.getItemBy(itemToken);
        item.changeEndOfSales();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        Item item = this.itemReader.getItemBy(itemToken);
        var itemOptionSeries = this.itemReader.getItemOptionSeries(item);

        return new ItemInfo.Main(item, itemOptionSeries);
    }
}
