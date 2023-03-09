package com.practice.order.domain.item;

import java.util.Optional;

public interface ItemReader {
    public Item getItemBy(String itemToken);
}
