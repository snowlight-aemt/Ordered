package com.practice.order.interfaces.order.payment.kakao;

public class KakaoRequestCommand {
    public static class Ready {
        String partnerUserId;
        String itemName;
        Integer quantity;
        Integer totalAmount;
        Integer taxFreeAmount;
    }
}
