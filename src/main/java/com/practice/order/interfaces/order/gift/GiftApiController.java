package com.practice.order.interfaces.order.gift;

import com.practice.order.application.order.OrderFacade;
import com.practice.order.application.order.gift.GiftFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.interfaces.order.OrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/gift-orders")
@RequiredArgsConstructor
public class GiftApiController {
    private final OrderFacade orderFacade;
    private final GiftFacade giftFacade;
    private final GiftDtoMapper giftDtoMapper;

    @PostMapping("/init")
    public CommonResponse registerOrder(@RequestBody @Valid GiftDto.RegisterOrder registerOrder) {
        var command = giftDtoMapper.of(registerOrder);
        String orderToken = orderFacade.registerOrder(command);
        var response = OrderDto.RegisterOrderResponse.builder()
                                                        .orderToken(orderToken)
                                                        .build();

        return CommonResponse.success(response);
    }

    @PostMapping("/payment-order")
    public CommonResponse paymentOrder(@RequestBody @Valid GiftDto.PaymentRequest request) {
        OrderCommand.PaymentRequest command = this.giftDtoMapper.of(request);
        this.giftFacade.paymentOrder(command);
        return CommonResponse.success("OK");
    }

    @PostMapping("/{orderToken}/update-receiver-info")
    public CommonResponse updateReceiverInfo(@PathVariable String orderToken,
                                             @RequestBody GiftDto.UpdateReceiverInfoReq request) {
        OrderCommand.UpdateReceiverCommand command = this.giftDtoMapper.of(request);
        this.orderFacade.updateReceiverInfo(orderToken, command);
        return CommonResponse.success("OK");
    }
}
