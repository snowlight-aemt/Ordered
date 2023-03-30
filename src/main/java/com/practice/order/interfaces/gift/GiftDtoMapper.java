package com.practice.order.interfaces.gift;

import com.practice.order.domain.order.OrderCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GiftDtoMapper {
    @Mapping(source = "buyerUserId", target = "userId")
    OrderCommand.RegisterOrder of(GiftDto.RegisterOrder request);
    OrderCommand.RegisterOrderItem of(GiftDto.RegisterOrderItem request);
    OrderCommand.RegisterOrderItemOptionGroup of(GiftDto.RegisterOrderItemOptionGroup request);
    OrderCommand.RegisterOrderItemOption of(GiftDto.RegisterOrderItemOption request);
    OrderCommand.PaymentRequest of(GiftDto.PaymentRequest request);
}
