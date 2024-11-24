package com.auty.modules.responses;

import android.content.Context;
import android.util.Log;

import com.auty.modules.applets.MusicApplet;

public class MusicResponse extends AbstractResponse {
    
    private final MusicApplet responseApp;

    public MusicResponse(Context context, String responseName) {
        super(responseName);
        this.responseApp = new MusicApplet(context);
    }

    @Override
    public void respond(String message) {
        this.responseApp.launchMusic();
    }
}
