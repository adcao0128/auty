package com.auty.modules.responses;

import android.content.Context;
import android.util.Log;

import com.auty.modules.applets.MusicApplet;
import com.auty.modules.models.NotificationModel;

public class MusicResponse extends AbstractResponse {
    
    private final MusicApplet responseApp;
    private NotificationModel notificationModel;
    private int user_id;
    public boolean responded;

    public MusicResponse(Context context, String responseName, int user_id, NotificationModel notificationModel) {
        super(responseName);
        this.user_id = user_id;
        this.notificationModel = notificationModel;
        this.responseApp = new MusicApplet(context);
        this.responded = false;
    }

    @Override
    public void respond(String message) {
        notificationModel.addNotification(message, user_id);
        this.responseApp.launchMusic();
        this.responded = true;
    }
}
