package com.practice.order.interfaces.partner;

import com.practice.order.application.partner.PartnerFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.partner.PartnerCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/partners")
public class PartnerApiController {

    private final PartnerFacade partnerFacade;
    private final PartnerDtoMapper partnerDtoMapper;

    @PostMapping
    public CommonResponse register(@RequestBody @Valid PartnerDto.RegisterRequest request) {
        var command = partnerDtoMapper.of(request);
        var partnerInfo = partnerFacade.registerPartner(command);
        var response = new PartnerDto.RegisterResponse(partnerInfo);
        return CommonResponse.success(response);
    }
}
