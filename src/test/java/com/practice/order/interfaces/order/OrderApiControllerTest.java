package com.practice.order.interfaces.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.order.OrderInfo;
import com.practice.order.domain.order.OrderService;
import com.practice.order.domain.order.payment.PayMethod;
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
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDtoMapper orderDtoMapper;

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

    @Test
    public void pay() throws Exception {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String partnerToken = partnerInfo.getPartnerToken();
        String itemToken = itemServiceFactory.registerItem(partnerToken);

        OrderDto.RegisterOrderRequest order = this.orderFactory.createRegisterOrderRequest(itemToken);

        String orderToken = orderService.registerOrder(orderDtoMapper.of(order));
        OrderInfo.Main main = orderService.retrieveOrder(orderToken);

        OrderDto.PaymentRequest command = OrderDto.PaymentRequest.builder()
                .payMethod(PayMethod.KAKAO_PAY)
                .orderToken(main.getOrderToken())
                .amount(main.getTotalAmount())
                .build();

        mvc.perform(post("/api/v1/orders/payment-order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("SUCCESS"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").value("OK"))
                .andExpect(jsonPath("$.message").isEmpty())
                .andExpect(jsonPath("$.errorCode").isEmpty())
        ;
    }

}