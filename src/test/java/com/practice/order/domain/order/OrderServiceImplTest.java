package com.practice.order.domain.order;

import com.practice.order.domain.order.payment.PayMethod;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.interfaces.item.ItemServiceFactory;
import com.practice.order.interfaces.partner.PartnerServiceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("Order 생성")
    @Test
    public void registerOrder() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());

        String token = orderServiceFactory.registerOrder(itemToken);

        assertThat(token).isNotEmpty();
        assertThat(token).isNotNull();
    }

    @DisplayName("Order 지불")
    @Test
    public void paymentOrder() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());
        String orderToken = orderServiceFactory.registerOrder(itemToken);
        var command = OrderCommand.PaymentRequest.builder()
                                                .orderToken(orderToken)
                                                .amount(10_000L)
                                                .payMethod(PayMethod.TOSS_PAY)
                                                .build();

        orderService.paymentOrder(command);

        Order actual = this.orderReader.getOrderBy(orderToken);
        assertThat(actual.getStatus()).isEqualTo(Order.Status.ORDER_COMPLETE);
    }

    @DisplayName("Order 조회")
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