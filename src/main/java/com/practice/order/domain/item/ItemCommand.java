package com.practice.order.domain.item;

import lombok.Getter;

import java.util.List;

public class ItemCommand {

    @Getter
    public static class RegisterItemRequest {
        private Long partnerId;
        private String itemName;
        private String itemPrice;
        private List<RegisterItemOptionGroupRequest> itemOptionGroupRequestList;
    }

    @Getter
    public static class RegisterItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<RegisterItemOptionRequest> itemOptionRequestList;
    }

    @Getter
    public static class RegisterItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;
    }
}
