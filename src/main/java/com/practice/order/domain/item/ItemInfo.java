package com.practice.order.domain.item;

import lombok.Getter;

import java.util.List;

public class ItemInfo {

    @Getter
    public static class Main {
        private String itemToken;
        private Long partnerId;
        private String itemName;
        private Long itemPrice;

        private Item.Status status;
        private List<ItemOptionGroupInfo> itemOptionGroupList;

        public Main(Item item, List<ItemOptionGroupInfo> itemOptionSeries) {
            this.itemToken = item.getItemToken();
            this.partnerId = item.getPartnerId();
            this.itemName = item.getItemName();
            this.itemPrice = item.getItemPrice();

            this.itemOptionGroupList = itemOptionSeries;
        }
    }

    @Getter
    public static class ItemOptionGroupInfo {
        private String itemOptionGroupName;
        private Integer ordering;

        private List<ItemOptionInfo> itemOptionList;

        public ItemOptionGroupInfo(ItemOptionGroup itemOptionGroup, List<ItemOptionInfo> itemOptionList) {
            this.itemOptionGroupName = itemOptionGroup.getItemOptionGroupName();
            this.ordering = itemOptionGroup.getOrdering();
            this.itemOptionList = itemOptionList;
        }
    }

    @Getter
    public static class ItemOptionInfo {
        private Integer ordering;
        private String itemOptionName;
        private Long itemOptionPrice;

        public ItemOptionInfo(ItemOption itemOption) {
            this.ordering = itemOption.getOrdering();
            this.itemOptionName = itemOption.getItemOptionName();
            this.itemOptionPrice = itemOption.getItemOptionPrice();
        }
    }
}
