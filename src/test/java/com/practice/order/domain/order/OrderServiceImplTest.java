package com.practice.order.domain.order;

import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.interfaces.item.ItemServiceFactory;
import com.practice.order.interfaces.partner.PartnerServiceFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void registerOrder() {
        PartnerInfo partnerInfo = partnerServiceFactory.registerPartner();
        String itemToken = itemServiceFactory.registerItem(partnerInfo.getPartnerToken());

        String token = orderServiceFactory.registerOrder(itemToken);

        assertThat(token).isNotEmpty();
        assertThat(token).isNotNull();
    }
}