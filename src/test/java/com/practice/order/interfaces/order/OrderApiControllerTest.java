package com.practice.order.interfaces.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.order.OrderInfo;
import com.practice.order.domain.order.OrderService;
import com.practice.order.domain.order.payment.PayMethod;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.util.ItemServiceFactory;
import com.practice.order.util.OrderDtoFactory;
import com.practice.order.util.PartnerServiceFactory;
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
    OrderDtoFactory orderDtoFactory;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDtoMapper orderDtoMapper;

    @DisplayName("주문 등록")
    @Test
    public void registerOrder() throws Exception {
        String itemToken = registerItem();
        OrderDto.RegisterOrderRequest order = this.orderDtoFactory.convertRegisterOrderRequest(itemToken);

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

    @DisplayName("주문 결제")
    @Test
    public void pay() throws Exception {
        String itemToken = registerItem();
        var order = this.orderDtoFactory.convertRegisterOrderRequest(itemToken);
        String orderToken = orderService.registerOrder(orderDtoMapper.of(order));

        OrderInfo.Main orderInfo = orderService.retrieveOrder(orderToken);
        OrderDto.PaymentRequest command = OrderDto.PaymentRequest.builder()
                .payMethod(PayMethod.KAKAO_PAY)
                .orderToken(orderInfo.getOrderToken())
                .amount(orderInfo.getTotalAmount())
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

    @DisplayName("주문 결제 - 실패 (결제 방법)")
    @Test
    public void pay_wrong_pay_method() throws Exception {
        String itemToken = registerItem();
        var order = this.orderDtoFactory.convertRegisterOrderRequest(itemToken);
        String orderToken = orderService.registerOrder(orderDtoMapper.of(order));

        OrderInfo.Main orderInfo = orderService.retrieveOrder(orderToken);
        OrderDto.PaymentRequest command = OrderDto.PaymentRequest.builder()
                .payMethod(PayMethod.TOSS_PAY)
                .orderToken(orderInfo.getOrderToken())
                .amount(orderInfo.getTotalAmount())
                .build();

        mvc.perform(post("/api/v1/orders/payment-order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("FAIL"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.message").value("주문 과정에서의 결제수단이 다릅니다."))
                .andExpect(jsonPath("$.errorCode").value("COMMON_INVALID_PARAMETER"))
        ;
    }

    @DisplayName("주문 결제 - 실패 (금액)")
    @Test
    public void pay_wrong_amount() throws Exception {
        String itemToken = registerItem();
        var order = this.orderDtoFactory.convertRegisterOrderRequest(itemToken);
        String orderToken = orderService.registerOrder(orderDtoMapper.of(order));

        OrderInfo.Main orderInfo = orderService.retrieveOrder(orderToken);
        OrderDto.PaymentRequest command = OrderDto.PaymentRequest.builder()
                .payMethod(PayMethod.TOSS_PAY)
                .orderToken(orderInfo.getOrderToken())
                .amount(0L)
                .build();

        mvc.perform(post("/api/v1/orders/payment-order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("FAIL"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andExpect(jsonPath("$.message").value("주문가격이 불일치합니다."))
                .andExpect(jsonPath("$.errorCode").value("COMMON_INVALID_PARAMETER"))
        ;
    }

    private String registerItem() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String partnerToken = partnerInfo.getPartnerToken();
        return itemServiceFactory.registerItem(partnerToken);
    }
}