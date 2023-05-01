package com.practice.order.infrastructure.order.payment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.payment.PayMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@RequiredArgsConstructor
public class KakaoPayApiCaller implements PaymentApiCaller {
    public static final String CID = "TC0ONETIME";
    public static final String PAYMENT_APPROVE_URL = "https://kapi.kakao.com/v1/payment/approve";
    public static final String PAYMENT_READY_URL = "https://kapi.kakao.com/v1/payment/ready";
    public static final String SUCCESS_URL = "http://localhost:28080/payment-order/kakao/success";
    public static final String FAIL_URL = "http://localhost:28080/payment-order/kakao/fail";
    public static final String CANCEL_URL = "http://localhost:28080/payment-order/kakao/cancel";

    @Value("${spring.pay.kakao.credentials.admin-key}")
    private String API_KEY;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    //TODO 인-메모리 고려
    private final ConcurrentMap<String, KakaoPaymentDto.KakaoApproveRequest> kakaoApproveRequests = new ConcurrentHashMap<>();

    @Override
    public boolean support(PayMethod payMethod) {
        return payMethod == PayMethod.KAKAO_PAY;
    }

    @Override
    public KakaoPaymentDto.PaymentWaitResponse inPay(OrderCommand.PaymentWaitRequest paymentWaitRequest) {
        String orderToken = paymentWaitRequest.getOrderToken();
        String approvalUrl = SUCCESS_URL +
                "?order_token=" + orderToken +
                "&amount=" + paymentWaitRequest.getTotalAmount().intValue();

        var readyRequest = KakaoPaymentDto.KakaoReadyRequest.builder()
                .cid(CID)
                .partner_order_id(orderToken)
                .partner_user_id(paymentWaitRequest.getUserToken())
                .item_name(paymentWaitRequest.getItemName())
                .quantity(paymentWaitRequest.getQuantity())
                .total_amount(paymentWaitRequest.getTotalAmount().intValue())
                .tax_free_amount(paymentWaitRequest.getTaxFreeAmount())
                .approval_url(approvalUrl)
                .fail_url(FAIL_URL)
                .cancel_url(CANCEL_URL)
                .build();

        var requestEntity = new HttpEntity<>(convertToMultiValueMap(readyRequest), getKakaoHttpHeaders());
        ResponseEntity<KakaoPaymentDto.KakaoResponse> response = restTemplate.exchange(PAYMENT_READY_URL,
                HttpMethod.POST,
                requestEntity,
                KakaoPaymentDto.KakaoResponse.class);
        KakaoPaymentDto.KakaoResponse kakaoResponse = response.getBody();
        KakaoPaymentDto.KakaoApproveRequest kakaoApproveRequest = KakaoPaymentDto.KakaoApproveRequest.builder()
                .cid(CID)
                .tid(kakaoResponse.getTid())
                .partner_order_id(orderToken)
                .partner_user_id(paymentWaitRequest.getUserToken())
                .total_amount(paymentWaitRequest.getTotalAmount().intValue())
                .build();

        this.kakaoApproveRequests.put(orderToken, kakaoApproveRequest);

        return KakaoPaymentDto.PaymentWaitResponse.builder()
                                            .nextRedirectPcUrl(kakaoResponse.getNext_redirect_pc_url())
                                            .build();
    }

    @Override
    public void pay(OrderCommand.PaymentRequest paymentRequest) {
        KakaoPaymentDto.KakaoApproveRequest kakaoApproveRequest = this.kakaoApproveRequests.get(paymentRequest.getOrderToken());
//        this.map.remove(partnerOrderId);
        kakaoApproveRequest.approve(paymentRequest.getPgToken());
        MultiValueMap<String, String> params = convertToMultiValueMap(kakaoApproveRequest);
        HttpHeaders headers = getKakaoHttpHeaders();
        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, headers);

        restTemplate.exchange(PAYMENT_APPROVE_URL, HttpMethod.POST, body, String.class);
    }

    private HttpHeaders getKakaoHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "KakaoAK " + API_KEY);
        return headers;
    }

    private MultiValueMap<String, String> convertToMultiValueMap(Object readyRequest) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.setAll(objectMapper.convertValue(readyRequest, new TypeReference<>() {}));
        return params;
    }
}
