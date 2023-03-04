package com.practice.order.infrastructure.partner;

import com.practice.order.common.exception.InvalidParamException;
import com.practice.order.domain.partner.Partner;
import com.practice.order.domain.partner.PartnerStore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class PartnerStoreImpl implements PartnerStore {
    private final PartnerRepository repository;

    @Override
    public Partner store(Partner partner) {
        if (StringUtils.isEmpty(partner.getPartnerToken())) throw new InvalidParamException("partner.getPartnerToken()");
        if (StringUtils.isEmpty(partner.getBusinessNo())) throw new InvalidParamException("partner.getBusinessNo()");
        if (StringUtils.isEmpty(partner.getPartnerName())) throw new InvalidParamException("partner.getPartnerName()");
        if (StringUtils.isEmpty(partner.getEmail())) throw new InvalidParamException("partner.getEmail()");
        if (partner.getStatus() == null) throw new InvalidParamException("partner.getStatus()");

        return repository.save(partner);
    }
}
