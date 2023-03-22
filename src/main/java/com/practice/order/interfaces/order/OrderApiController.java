package com.practice.order.interfaces.order;

import com.practice.order.application.order.OrderFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.order.OrderCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderApiController {

    private final OrderFacade orderFacade;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping("/init")
    public CommonResponse registerOrder(@RequestBody @Valid OrderDto.RegisterOrderRequest request) {
        OrderCommand.RegisterOrder command = orderDtoMapper.of(request);
        String orderToken = orderFacade.registerOrder(command);
        var response = orderDtoMapper.of(orderToken);
        return CommonResponse.success(response);
    }
}
