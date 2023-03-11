package com.practice.order.interfaces.partner;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PartnerApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("파트너 등록")
    @Test
    void register() throws Exception {
        String partnerName = "Test01";
        String businessNo = "No";
        String email = "test@gmail.com";
        var request = PartnerDto.RegisterRequest.builder()
                    .partnerName(partnerName)
                    .businessNo(businessNo)
                    .email(email)
                    .build();

        this.mvc.perform(post("/api/v1/partners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value("SUCCESS"))
            .andExpect(jsonPath("$.data").exists())
            .andExpect(jsonPath("$.data.partnerToken").isNotEmpty())
            .andExpect(jsonPath("$.data.partnerName").value(partnerName))
            .andExpect(jsonPath("$.data.businessNo").value(businessNo))
            .andExpect(jsonPath("$.data.email").value(email))
            .andExpect(jsonPath("$.data.status").value("ENABLE"))
            .andExpect(jsonPath("$.message").isEmpty())
            .andExpect(jsonPath("$.errorCode").isEmpty());
    }
}