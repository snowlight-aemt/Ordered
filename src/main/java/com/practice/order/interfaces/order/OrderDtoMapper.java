package com.practice.order.interfaces.order;

import com.practice.order.domain.order.OrderCommand;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderDtoMapper {
    @Mappings({@Mapping(source = "request.orderItems", target = "orderItems")})
    OrderCommand.RegisterOrder of(OrderDto.RegisterOrderRequest request);

    @Mappings({@Mapping(source = "request.orderItemOptionGroups", target = "orderItemOptionGroups")})
    OrderCommand.RegisterOrderItem of(OrderDto.RegisterOrderItemRequest request);

    OrderCommand.RegisterOrderItemOptionGroup of(OrderDto.RegisterOrderItemOptionGroupRequest request);

    OrderCommand.RegisterOrderItemOption of(OrderDto.RegisterOrderItemOptionRequest request);

    OrderDto.RegisterOrderResponse of(String orderToken);
    OrderCommand.PaymentRequest of(OrderDto.PaymentRequest request);
}
