package com.practice.order.domain.partner;

import java.util.Optional;

public interface PartnerReader {
    Partner getPartner(String partnerToken);
}
