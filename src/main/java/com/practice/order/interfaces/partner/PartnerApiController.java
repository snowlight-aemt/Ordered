package com.practice.order.interfaces.partner;

import com.practice.order.application.partner.PartnerFacade;
import com.practice.order.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/partner")
public class PartnerApiController {

    private final PartnerFacade partnerFacade;

    @PostMapping("")
    public CommonResponse register(PartnerDto.RegisterRequest request) {
        var command = request.toCommand();
        var partnerInfo = partnerFacade.registerPartner(command);
        var response = new PartnerDto.RegisterResponse(partnerInfo);
        return CommonResponse.success(response);
    }
}
