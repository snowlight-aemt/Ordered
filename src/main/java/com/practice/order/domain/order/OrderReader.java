package com.practice.order.domain.order;

public interface OrderReader {
    public Order getOrderBy(String orderToken);
}
