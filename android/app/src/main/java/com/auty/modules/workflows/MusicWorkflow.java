package com.auty.modules.workflows;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.Nullable;

import com.auty.modules.applets.Applet;
import com.auty.modules.applets.MusicApplet;
import com.auty.modules.responses.AbstractResponse;
import com.auty.modules.responses.MusicResponse;
import com.auty.modules.triggers.MusicTrigger;

public class MusicWorkflow extends Workflow {

    private MusicTrigger musicTrigger;
    private MusicResponse response;
    private MusicApplet app;

    private Context context;


    public MusicWorkflow(Context context, Applet applet, AbstractResponse response) {
        super("musicWorkflow", applet, response);
        this.context = context;
        this.response = (MusicResponse) response;
        this.app = (MusicApplet) applet;
    }

    @Override
    public void registerReceiver(){
        Log.d("Applet", "Registering music receiver");
        this.musicTrigger = new MusicTrigger(this);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        context.registerReceiver(this.musicTrigger, filter);
    }

    @Override
    public void handle(@Nullable Intent intent) {
        if (intent != null) {
            this.response.respond("Music Response Message");
        }
    }

}