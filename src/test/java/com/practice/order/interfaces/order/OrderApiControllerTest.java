package com.practice.order.interfaces.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.interfaces.item.ItemServiceFactory;
import com.practice.order.interfaces.partner.PartnerServiceFactory;
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
class OrderApiControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ItemServiceFactory itemServiceFactory;

    @Autowired
    PartnerServiceFactory partnerServiceFactory;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    OrderFactory orderFactory;

    @Test
    public void registerOrder() throws Exception {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String partnerToken = partnerInfo.getPartnerToken();
        String itemToken = itemServiceFactory.registerItem(partnerToken);

        OrderDto.RegisterOrderRequest order = this.orderFactory.createRegisterOrderRequest(itemToken);

        mvc.perform(post("/api/v1/orders/init")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("SUCCESS"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.orderToken").isNotEmpty())
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.errorCode").isEmpty())
        ;

    }

}