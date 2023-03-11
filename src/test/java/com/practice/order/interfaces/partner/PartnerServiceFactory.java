package com.practice.order.interfaces.partner;

import com.practice.order.domain.partner.PartnerInfo;
import com.practice.order.domain.partner.PartnerService;
import org.springframework.stereotype.Component;

@Component
public class PartnerServiceFactory {

    private final PartnerService partnerService;
    private final PartnerDtoMapper partnerDtoMapper;

    public PartnerServiceFactory(PartnerService partnerService, PartnerDtoMapper partnerDtoMapper) {
        this.partnerService = partnerService;
        this.partnerDtoMapper = partnerDtoMapper;
    }

    public PartnerInfo registerPartner() {
        String partnerName = "Test01";
        String businessNo = "No";
        String email = "test@gmail.com";
        var registerRequest = PartnerDto.RegisterRequest.builder()
                .partnerName(partnerName)
                .businessNo(businessNo)
                .email(email)
                .build();

        return this.partnerService.registerPartner(partnerDtoMapper.of(registerRequest));
    }
}
