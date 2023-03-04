package com.practice.order.domain.partner;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommandPartner {
    private String businessNo;
    private String partnerName;
    private String email;

    public Partner toEntity() {
        return Partner.builder()
                .businessNo(businessNo)
                .partnerName(partnerName)
                .email(email)
                .build();
    }

}
