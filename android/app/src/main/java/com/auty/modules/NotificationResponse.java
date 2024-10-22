package com.auty.modules;

import android.content.Intent;
import android.util.Log;

public class NotificationResponse extends AbstractResponse {

    private final NotificationApplet responseApp;

    public NotificationResponse(String responseName, String message, Applet responseApp) {
        super(responseName, message);

        this.responseApp = (NotificationApplet) responseApp;

        this.responseApp.createNotificationChannel();
        Log.d(this.responseApp.tag, "Notification channel created for push notifications");

    }

    @Override
    public void respond() {
        this.responseApp.sendNotification(this.responseName, "Something", this.message);
    }

}
