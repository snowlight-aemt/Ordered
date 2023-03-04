package com.practice.order.domain.notification;

public interface NotificationService {

    public void sendEmail(String email, String title, String description);
    public void sendKakao(String email, String description);
    public void sendSms(String email, String description);

}
