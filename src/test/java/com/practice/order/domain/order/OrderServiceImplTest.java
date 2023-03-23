package com.practice.order.domain.order;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.order.payment.PayMethod;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.util.ItemServiceFactory;
import com.practice.order.util.OrderServiceFactory;
import com.practice.order.util.PartnerServiceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private PartnerServiceFactory partnerServiceFactory;
    @Autowired
    private ItemServiceFactory itemServiceFactory;
    @Autowired
    private OrderServiceFactory orderServiceFactory;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderReader orderReader;

    @DisplayName("주문 생성 서비스")
    @Test
    public void registerOrder() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());

        String token = orderServiceFactory.registerOrder(itemToken);

        assertThat(token).isNotEmpty();
        assertThat(token).isNotNull();
    }

    @DisplayName("주문 결제 서비스")
    @Test
    public void paymentOrder() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());
        String orderToken = orderServiceFactory.registerOrder(itemToken);
        var command = OrderCommand.PaymentRequest.builder()
                                                .orderToken(orderToken)
                                                .amount(11_000L)
                                                .payMethod(PayMethod.KAKAO_PAY)
                                                .build();

        orderService.paymentOrder(command);

        Order actual = this.orderReader.getOrderBy(orderToken);
        assertThat(actual.getStatus()).isEqualTo(Order.Status.ORDER_COMPLETE);
    }

    @DisplayName("주문 결제 서비스 - 주문 가격 불일치")
    @Test
    public void paymentOrder_payAmountValidator() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());
        String orderToken = orderServiceFactory.registerOrder(itemToken);
        var command = OrderCommand.PaymentRequest.builder()
                .orderToken(orderToken)
                .amount(10_000L)
                .payMethod(PayMethod.KAKAO_PAY)
                .build();

        assertThatThrownBy(() -> {
            orderService.paymentOrder(command);
        })
            .isInstanceOf(InvalidParamException.class)
            .hasMessage("주문가격이 불일치합니다.");
    }

    @DisplayName("주문 결제 서비스 - 주문 과정 불일치")
    @Test
    public void paymentOrder_payMethodValidator() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());
        String orderToken = orderServiceFactory.registerOrder(itemToken);
        var command = OrderCommand.PaymentRequest.builder()
                .orderToken(orderToken)
                .amount(11_000L)
                .payMethod(PayMethod.TOSS_PAY)
                .build();

        assertThatThrownBy(() -> {
            orderService.paymentOrder(command);
        })
            .isInstanceOf(InvalidParamException.class)
            .hasMessage("주문 과정에서의 결제수단이 다릅니다.");
    }


    @DisplayName("주문 결제 서비스 - 이미 주문 완료")
    @Test
    public void paymentOrder_payStatusValidator() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());
        String orderToken = orderServiceFactory.registerOrder(itemToken);
        var command = OrderCommand.PaymentRequest.builder()
                .orderToken(orderToken)
                .amount(11_000L)
                .payMethod(PayMethod.KAKAO_PAY)
                .build();
        orderService.paymentOrder(command);

        assertThatThrownBy(() -> {
            orderService.paymentOrder(command);
        })
            .isInstanceOf(InvalidParamException.class)
            .hasMessage("이미 결제완료된 주문입니다.");
    }

    @DisplayName("주문 조회 서비스 (주문 총 금액)")
    @Test
    public void retrieveOrder() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());
        String orderToken = orderServiceFactory.registerOrder(itemToken);

        OrderInfo.Main orderInfo = orderService.retrieveOrder(orderToken);
        assertThat(orderInfo).isNotNull();
        assertThat(orderInfo.getOrderToken()).isEqualTo(orderToken);
        assertThat(orderInfo.getTotalAmount()).isEqualTo(11_000L);
    }
}