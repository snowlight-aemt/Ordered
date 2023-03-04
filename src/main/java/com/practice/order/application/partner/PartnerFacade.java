package com.practice.order.application.partner;

import com.practice.order.domain.notification.NotificationService;
import com.practice.order.domain.partner.PartnerCommand;
import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.domain.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerFacade {

    private final PartnerService partnerService;
    private final NotificationService notificationService;

    public PartnerInfo registerPartner(PartnerCommand command) {
        var partnerInfo = partnerService.registerPartner(command);
//        notificationService.send();
        return partnerInfo;

        // 1. partner 등록
        // 2. email 전송
    }
}
