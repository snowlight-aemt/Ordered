package com.practice.order.infrastructure;

import com.practice.order.domain.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationExecutor implements NotificationService {
    @Override
    public void sendEmail(String email, String title, String description) {
        log.info("email 전송");
    }

    @Override
    public void sendKakao(String email, String description) {
        log.info("kakao 전송");
    }

    @Override
    public void sendSms(String email, String description) {
        log.info("sms 전송");
    }
}
