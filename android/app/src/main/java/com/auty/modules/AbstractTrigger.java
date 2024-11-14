package com.auty.modules;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public abstract class AbstractTrigger<T> extends Service {
    private String triggerName; //name of specific trigger
    private Applet app;
    private T condition; //some condition with a specified type, automatically typecast
    //point getter to a new field


    public String getTriggerName() {
        return triggerName;
    }

    public T getCondition(){
        return this.condition;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public AbstractTrigger(String triggerName, T condition ){
        this.triggerName = triggerName;
        this.condition = condition;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){
        boolean resultValue = this.handleService();

        if (resultValue) {
            Intent broadcastIntent = new Intent(String.format("com.example.%s", this.triggerName));

            broadcastIntent.putExtra("result", resultValue);

            sendBroadcast(broadcastIntent);
        }
//        stopSelf();

        return START_NOT_STICKY;
    }
    // type is optional
    public abstract boolean handleService();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
