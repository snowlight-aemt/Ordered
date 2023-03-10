package com.practice.order.interfaces.item;

import com.practice.order.application.item.ItemFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.item.ItemInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemFacade itemFacade;
    private final ItemDtoMapper itemDtoMapper;

    @PostMapping
    public CommonResponse<ItemDto.RegisterResponse> registerItem(@RequestBody @Valid ItemDto.RegisterItemRequest request) {
        String partnerToken = request.getPartnerToken();
        var command = itemDtoMapper.of(request);
        String itemToken = itemFacade.registerItem(command, partnerToken);

        var response = itemDtoMapper.of(itemToken);
        return CommonResponse.success(response);
    }

    @PostMapping("/change-on-sales")
    public CommonResponse changeOnSaleItem(@RequestBody @Valid ItemDto.ChangeStatusItemRequest request) {
        String itemToken = request.getItemToken();
        this.itemFacade.changeOnSaleItem(itemToken);
        return CommonResponse.success("OK");
    }

    @PostMapping("/change-end-of-sales")
    public CommonResponse changeEndOfSalesItem(@RequestBody @Valid ItemDto.ChangeStatusItemRequest request) {
        String itemToken = request.getItemToken();
        this.itemFacade.changeEndOnSaleItem(itemToken);
        return CommonResponse.success("OK");
    }

    @GetMapping("{itemToken}")
    public CommonResponse retrieve(@PathVariable("itemToken") String itemToken) {
        ItemInfo.Main itemInfo = this.itemFacade.retrieveItemInfo(itemToken);
        ItemDto.Main response = itemDtoMapper.of(itemInfo);
        return CommonResponse.success(response);
    }
}
