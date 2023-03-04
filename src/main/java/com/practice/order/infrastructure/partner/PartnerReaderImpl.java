package com.practice.order.infrastructure.partner;

import com.practice.order.common.exception.EntityNotFoundException;
import com.practice.order.domain.partner.Partner;
import com.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartnerReaderImpl implements PartnerReader {
    private final PartnerRepository repository;
    @Override
    public Partner getPartner(String partnerToken) {
        return repository.findByPartnerToken(partnerToken).orElseThrow(EntityNotFoundException::new);
    }
}
