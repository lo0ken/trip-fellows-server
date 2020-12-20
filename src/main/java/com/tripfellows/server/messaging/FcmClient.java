package com.tripfellows.server.messaging;

import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FcmClient {

    public void sendMessage(PushNotificationRequest request, Map<String, String> data) {
        Message message = getPreconfiguredMessageWithData(request, data);
        sendAndGetResponse(message);
    }

    private void sendAndGetResponse(Message message) {
        try {
            FirebaseMessaging.getInstance().sendAsync(message).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private Message getPreconfiguredMessageWithData(PushNotificationRequest request, Map<String, String> data) {
        return getPreconfiguredMessageBuilder(request)
                .setTopic(request.getTopic())
                .putAllData(data)
                .build();
    }

    private Message.Builder getPreconfiguredMessageBuilder(PushNotificationRequest request) {
        AndroidConfig androidConfig = getAndroidConfig(request.getTopic());
        ApnsConfig apnsConfig = getApnsConfig(request.getTopic());

        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getMessage())
                .build();

        return Message.builder()
                .setApnsConfig(apnsConfig)
                .setAndroidConfig(androidConfig)
                .setNotification(notification);
    }

    private AndroidConfig getAndroidConfig(String topic) {
        return AndroidConfig.builder()
                .setTtl(Duration.ofMinutes(2).toMillis()).setCollapseKey(topic)
                .setPriority(AndroidConfig.Priority.HIGH)
                .setNotification(AndroidNotification.builder().setTag(topic).build())
                .build();
    }

    private ApnsConfig getApnsConfig(String topic) {
        return ApnsConfig.builder()
                .setAps(Aps.builder()
                        .setCategory(topic)
                        .setThreadId(topic)
                        .build())
                .build();
    }
}
