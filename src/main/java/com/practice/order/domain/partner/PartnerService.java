package com.practice.order.domain.partner;

public interface PartnerService {
    public PartnerInfo registerPartner(CommandPartner command);
    public PartnerInfo getPartnerInfo(String partnerToken);
    public PartnerInfo enablePartner(String partnerToken);
    public PartnerInfo disablePartner(String partnerToken);
}
