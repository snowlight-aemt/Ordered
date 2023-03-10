package com.practice.order.interfaces.item;

import com.practice.order.application.item.ItemFacade;
import com.practice.order.common.response.CommonResponse;
import com.practice.order.domain.item.ItemCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemFacade itemFacade;

    @PostMapping()
    public CommonResponse<ItemDto.RegisterResponse> registerItem(@RequestBody ItemDto.RegisterRequest request) {
        ItemCommand.RegisterItemRequest command = ItemCommand.RegisterItemRequest.builder()
                .itemName(request.getItemName())
                .itemPrice(request.getItemPrice())
                .build();

        String itemToken = itemFacade.registerItem(command, request.getPartnerToken());
        ItemDto.RegisterResponse response = ItemDto.RegisterResponse.builder()
                .itemToken(itemToken)
                .build();
        return CommonResponse.success(response);
    }
}
