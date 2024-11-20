package com.auty.modules.responses;

import android.content.Context;
import android.util.Log;

import com.auty.modules.applets.NotificationApplet;

public class NotificationResponse extends AbstractResponse {

    private final NotificationApplet responseApp;

    public NotificationResponse(Context context, String responseName) {
        super(responseName);
        this.responseApp = new NotificationApplet(context, String.format("%sChannel", responseName), responseName);
        this.responseApp.createNotificationChannel();
        Log.d(this.responseApp.tag, "Notification channel created for push notifications");
    }

    @Override
    public void respond(String message) {
        this.responseApp.sendNotification(this.responseName, this.responseName, message);
    }
}
