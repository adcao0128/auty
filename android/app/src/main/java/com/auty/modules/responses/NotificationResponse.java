package com.auty.modules.responses;

import android.content.Context;
import android.util.Log;

import com.auty.modules.applets.NotificationApplet;
import com.auty.modules.models.NotificationModel;

public class NotificationResponse extends AbstractResponse {

    private final NotificationApplet responseApp;
    private NotificationModel notificationModel;
    private int user_id;

    public NotificationResponse(Context context, String responseName, int user_id, NotificationModel notificationModel) {
        super(responseName);
        this.responseApp = new NotificationApplet(context, String.format("%sChannel", responseName), responseName);
        this.responseApp.createNotificationChannel();
        this.user_id = user_id;
        this.notificationModel = notificationModel;
        Log.d(this.responseApp.tag, "Notification channel created for push notifications");
    }

    @Override
    public void respond(String message) {
        notificationModel.addNotification(message, user_id);
        this.responseApp.sendNotification(this.responseName, this.responseName, message);
    }
}
