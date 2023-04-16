package com.practice.order.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.order.domain.order.gift.GiftMessageChannelSender;
import com.practice.order.domain.order.gift.GiftPaymentCompleteMessage;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftMessageAwsSqsSender implements GiftMessageChannelSender {
    private final String SQS_QUEUE_NAME = "order-payComplete.fifo";
    private final SqsTemplate sqsTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void paymentComplete(GiftPaymentCompleteMessage message) {
        try {
            sqsTemplate.send(SQS_QUEUE_NAME, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
