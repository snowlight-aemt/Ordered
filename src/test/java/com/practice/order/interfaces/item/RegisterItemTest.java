package com.practice.order.interfaces.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.item.ItemOptionSeriesFactory;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.domain.partner.PartnerService;
import com.practice.order.interfaces.partner.PartnerServiceFactory;
import com.practice.order.interfaces.partner.PartnerDtoMapper;
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
public class RegisterItemTest {
    @Autowired
    private MockMvc mvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired
    PartnerService partnerService;
    @Autowired
    PartnerDtoMapper partnerDtoMapper;
    @Autowired
    ItemOptionSeriesFactory itemOptionSeriesFactory;

    @Autowired
    private PartnerServiceFactory partnerFactory;

    @DisplayName("상품 생성 - 옵션 없음")
    @Test
    void sut_register_without_option() throws Exception {
        PartnerInfo partnerInfo = partnerFactory.registerPartner();
        var itemRequest = ItemServiceFactory.createRegisterItemRequestWithoutOption(partnerInfo);

        mvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("SUCCESS"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itemToken").isNotEmpty())
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.errorCode").isEmpty());
    }

    @DisplayName("상품 생성 - 옵션 있음")
    @Test
    void sut_register() throws Exception {
        PartnerInfo partnerInfo = partnerFactory.registerPartner();
        var itemRequest = ItemServiceFactory.createRegisterItemRequestWithOption(partnerInfo.getPartnerToken());

        mvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("SUCCESS"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.itemToken").isNotEmpty())
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.errorCode").isEmpty());
    }



}
