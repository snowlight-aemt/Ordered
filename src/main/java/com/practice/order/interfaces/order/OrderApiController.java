package com.practice.order.interfaces.order;

import com.practice.order.application.order.OrderFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.order.OrderCommand;
import com.practice.order.domain.order.OrderInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{orderToken}")
    public CommonResponse retrieveOrder(@PathVariable String orderToken) {
        OrderInfo.Main orderResult = orderFacade.retrieveOrder(orderToken);
        OrderDto.Main response = this.orderDtoMapper.of(orderResult);
        return CommonResponse.success(response);
    }

    @PostMapping("/payment-order")
    public CommonResponse payment(@RequestBody @Valid OrderDto.PaymentRequest request) {
        OrderCommand.PaymentRequest paymentRequest = orderDtoMapper.of(request);
        orderFacade.pay(paymentRequest);
        return CommonResponse.success("OK");
    }
}
