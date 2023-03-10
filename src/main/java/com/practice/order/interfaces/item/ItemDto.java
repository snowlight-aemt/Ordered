package com.practice.order.interfaces.item;

import com.practice.order.domain.item.Item;
import com.practice.order.domain.item.ItemCommand;
import lombok.*;

import java.util.List;

public class ItemDto {

    @Getter
    @Builder
    public static class RegisterItemRequest {
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
        private List<ItemCommand.RegisterItemOptionRequest> itemOptionList;
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

    @Getter
    @Setter
    @ToString
    public static class ChangeStatusItemRequest {
        private String itemToken;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final String itemToken;
        private final Long partnerId;
        private final String itemName;
        private final Long itemPrice;
        private final Item.Status status;
        private final List<ItemOptionGroupInfo> itemOptionGroupList;
    }

    @Getter
    @Builder
    @ToString
    public static class ItemOptionGroupInfo {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<ItemOptionInfo> itemOptionList;
    }

    @Getter
    @Builder
    @ToString
    public static class ItemOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;
    }
}
