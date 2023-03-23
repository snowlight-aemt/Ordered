package com.practice.order.interfaces.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemInfo;
import com.practice.order.domain.item.ItemService;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.util.ItemServiceFactory;
import com.practice.order.util.PartnerServiceFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeItemStatusTest {
    @Autowired MockMvc mvc;

    @Autowired PartnerServiceFactory partnerServiceFactory;
    @Autowired
    ItemServiceFactory itemServiceFactory;

    @Autowired ItemService itemService;
    @Autowired ItemDtoMapper itemDtoMapper;

    @Autowired ObjectMapper objectMapper;

    @DisplayName("상품 판매중 상태 변경")
    @Test
    public void changeOnSales() throws Exception {
        PartnerInfo partnerInfo = this.partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());

        var content = new ItemDto.ChangeStatusItemRequest(itemToken);

        this.mvc.perform(post("/api/v1/items/change-on-sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(content)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value("OK"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.errorCode").isEmpty());

        ItemInfo.Main actual = this.itemService.retrieveItemInfo(itemToken);
        Assertions.assertThat(actual.getStatus()).isEqualTo(Item.Status.ON_SALES);
    }


    @DisplayName("상품 판매 완료 상태 변경")
    @Test
    public void changeEndOfSales() throws Exception {
        PartnerInfo partnerInfo = this.partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());

        var content = new ItemDto.ChangeStatusItemRequest(itemToken);

        this.mvc.perform(post("/api/v1/items/change-end-of-sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(content)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value("OK"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.errorCode").isEmpty());

        ItemInfo.Main actual = this.itemService.retrieveItemInfo(itemToken);
        Assertions.assertThat(actual.getStatus()).isEqualTo(Item.Status.END_OF_SALES);
    }
}
