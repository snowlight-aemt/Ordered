package com.practice.order.domain.partner;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PartnerServiceImpl implements PartnerService {
    private final PartnerStore partnerStore;

    private final PartnerReader partnerReader;

    @Override
    public PartnerInfo registerPartner(CommandPartner command) {
        var initPartner = command.toEntity();
        Partner partner = partnerStore.store(initPartner);

        return new PartnerInfo(partner);
    }

    @Override
    public PartnerInfo getPartnerInfo(String partnerToken) {
        Partner partner = partnerReader.getPartner(partnerToken);

        return new PartnerInfo(partner);
    }

    @Override
    public PartnerInfo enablePartner(String partnerToken) {
        Partner partner = partnerReader.getPartner(partnerToken);

        partner.enable();

        return new PartnerInfo(partner);
    }

    @Override
    public PartnerInfo disablePartner(String partnerToken) {
        Partner partner = partnerReader.getPartner(partnerToken);

        partner.disable();

        return new PartnerInfo(partner);
    }
}
