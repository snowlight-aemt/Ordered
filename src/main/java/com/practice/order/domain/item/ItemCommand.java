package com.practice.order.domain.item;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ItemCommand {

    @Getter
    @Builder
    public static class RegisterItemRequest {
        private Long partnerId;
        private String itemName;
        private Long itemPrice;
        private List<RegisterItemOptionGroupRequest> itemOptionGroupRequestList;

        public Item toEntity(Long PartnerId) {
            return Item.builder()
                    .itemName(getItemName())
                    .itemPrice(getItemPrice())
                    .partnerId(PartnerId)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class RegisterItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<RegisterItemOptionRequest> itemOptionRequestList;
    }

    @Getter
    @Builder
    public static class RegisterItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;
    }
}
