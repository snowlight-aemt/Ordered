package com.practice.order.interfaces.gift;

import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.order.OrderService;
import com.practice.order.interfaces.order.OrderDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/gift-orders")
@RequiredArgsConstructor
public class GiftApiController {
    private final OrderService orderService;
    private final GiftDtoMapper giftDtoMapper;

    @PostMapping("/init")
    public CommonResponse test(@RequestBody @Valid GiftDto.RegisterOrder registerOrder) {
        String orderToken = orderService.registerOrder(giftDtoMapper.of(registerOrder));
        OrderDto.RegisterOrderResponse response = OrderDto.RegisterOrderResponse
                                                        .builder()
                                                        .orderToken(orderToken)
                                                        .build();
        return CommonResponse.success(response);
    }
}
