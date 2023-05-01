package com.practice.order.domain.order;

public interface OrderService {
    public String registerOrder(OrderCommand.RegisterOrder command);

    public OrderInfo.Main retrieveOrder(String orderToken);

    public OrderInfo.PaymentProcessorResponse inPaymentOrder(OrderCommand.PaymentWaitRequest command);

    public void paymentOrder(OrderCommand.PaymentRequest command);

    public void updateReceiverInfo(String orderToken, OrderCommand.UpdateReceiverCommand command);
}
