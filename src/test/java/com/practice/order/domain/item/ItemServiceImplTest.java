package com.practice.order.domain.item;

import com.practice.order.common.util.TokenGenerator;
import com.practice.order.domain.partner.*;
import com.practice.order.infrastructure.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceImplTest {

    @Autowired
    private PartnerReader partnerReader;

//    @Autowired
//    private ItemStore itemStore;
//
//    @Autowired
//    private ItemOptionGroupStore itemOptionGroupStore;
//
//    @Autowired
//    private ItemOptionStore itemOptionStore;


    @Autowired
    private ItemService itemService;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void registerItem() {
        PartnerCommand partnerCommand = PartnerCommand.builder().partnerName("Name").businessNo("No").email("test@naver.com").build();
        PartnerInfo partnerInfo = this.partnerService.registerPartner(partnerCommand);

        Partner partner = partnerReader.getPartner(partnerInfo.getPartnerToken());

        var itemOptionList = List.of(ItemCommand.RegisterItemOptionRequest.builder()
                .itemOptionName("option-name")
                .itemOptionPrice(20_000L)
                .ordering(1)
                .build());

        var itemOptionGroupList = List.of(ItemCommand.RegisterItemOptionGroupRequest.builder()
                .itemOptionGroupName("group-name")
                .ordering(1)
                .itemOptionRequestList(itemOptionList)
                .build());

        var command = ItemCommand.RegisterItemRequest.builder()
                .partnerId(partner.getId())
                .itemName("item-name")
                .itemPrice(100_000L)
                .itemOptionGroupRequestList(itemOptionGroupList)
                .build();


        String itemToken = itemService.registerItem(command, partnerInfo.getPartnerToken());
        assertNotNull(itemToken);

    }
}