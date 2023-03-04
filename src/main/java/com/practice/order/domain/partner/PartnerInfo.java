package com.practice.order.domain.partner;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PartnerInfo {
    private Long id;
    private String partnerToken;

    private String businessNo;
    private String partnerName;
    private String email;

    private Partner.Status status;

    public PartnerInfo (Partner partner) {
        this.id = partner.getId();
        this.partnerToken = partner.getPartnerToken();
        this.businessNo = partner.getBusinessNo();
        this.partnerName = partner.getPartnerName();
        this.email = partner.getEmail();
        this.status = partner.getStatus();
    }
}
