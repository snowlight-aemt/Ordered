package com.practice.order.interfaces.item;

import com.practice.order.domain.item.ItemCommand;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ItemDto {

    @Getter
    @Builder
    public static class RegisterRequest {
        private String partnerToken;
        private String itemName;
        private Long itemPrice;
        private List<RegisterItemOptionGroupRequest> itemOptionGroupList;
    }

    @Getter
    @Builder
    public static class RegisterItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<ItemCommand.RegisterItemOptionRequest> itemOptionRequestList;
    }

    @Getter
    @Builder
    public static class RegisterItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;
    }

    @Getter
    @Builder
    public static class RegisterResponse {
        private final String itemToken;
    }
}
