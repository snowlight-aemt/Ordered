package com.practice.order.interfaces.order.payment.kakao;

import com.practice.order.application.order.OrderFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderInfo;
import com.practice.order.domain.order.payment.PayMethod;
import com.practice.order.interfaces.order.OrderDto;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
@Slf4j
@RestController
@RequestMapping("/payment-order")
@RequiredArgsConstructor
public class kakaoPayController {
    private final OrderFacade orderFacade;

    private final RestTemplate restTemplate;

    @PostMapping("/kakao/payment")
    public CommonResponse payment(@RequestBody OrderDto.PaymentWaitRequest paymentRequest) {
        log.info("kakao/payment");
        OrderCommand.PaymentWaitRequest paymentWaitRequest = OrderCommand.PaymentWaitRequest.builder()
                                        .orderToken(paymentRequest.getOrderToken())
                                        .userToken(paymentRequest.getUserToken())
                                        .quantity(paymentRequest.getQuantify())
                                        .itemName(paymentRequest.getItemName())
                                        .payMethod(PayMethod.KAKAO_PAY)
                                        .totalAmount(paymentRequest.getAmount())
                                        .taxFreeAmount(paymentRequest.getTaxFreeAmount().intValue())
                                        .build();
        OrderInfo.PaymentProcessorResponse response = orderFacade.inPay(paymentWaitRequest);

        return CommonResponse.success(response);
    }

    @GetMapping("/kakao/success")
    public CommonResponse paymentCalledKakao(@RequestParam("pg_token") @NotBlank String pgToken,
                                             @RequestParam("order_token") @NotBlank String orderToken,
                                             @RequestParam("amount") @NotBlank Integer amount) {
        log.info("kakao/success");
        OrderCommand.PaymentRequest paymentRequest = OrderCommand.PaymentRequest.builder()
                .orderToken(orderToken)
                .amount(amount.longValue())
                .pgToken(pgToken)
                .payMethod(PayMethod.KAKAO_PAY)
                .build();

        ResponseEntity<CommonResponse> stringResponseEntity = this.restTemplate.postForEntity("http://localhost:28080/api/v1/orders/payment-order", paymentRequest, CommonResponse.class);
        return stringResponseEntity.getBody();
    }
}

