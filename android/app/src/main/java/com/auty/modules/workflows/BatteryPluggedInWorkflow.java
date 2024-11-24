package com.auty.modules.workflows;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.Nullable;

import com.auty.modules.applets.BatteryApplet;
import com.auty.modules.responses.AbstractResponse;
import com.auty.modules.responses.NotificationResponse;
import com.auty.modules.triggers.BatteryTrigger;
import com.auty.modules.models.WorkflowConfig;
import com.auty.modules.models.WorkflowModel;


public class BatteryPluggedInWorkflow extends Workflow {

    private Context context;
    private BatteryTrigger batteryTrigger;
    private NotificationResponse response;
    private BatteryApplet app;

    public BatteryPluggedInWorkflow(Context context, BatteryApplet batteryApplet, AbstractResponse response) {
        super("batteryPluggedInWorkflow", batteryApplet, response);
        this.context = context;
        this.app = batteryApplet;
        this.response = (NotificationResponse) response;
    }

    @Override
    public void registerReceiver(WorkflowModel workflowModel, int user_id) {
        WorkflowConfig workflowConfig = new WorkflowConfig(this.workflowName, Boolean.TRUE);
        workflowModel.updateWorkflow(workflowConfig, user_id);
        Log.d("AUTY",String.format("Registered %s workflow", this.workflowName));
        this.batteryTrigger = new BatteryTrigger("batteryPluggedInTrigger", this);
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        context.registerReceiver(this.batteryTrigger, filter);
    }

    @Override
    public void unregisterReceiver(WorkflowModel workflowModel, int user_id) {
        WorkflowConfig workflowConfig = new WorkflowConfig(this.workflowName, Boolean.FALSE);
        workflowModel.updateWorkflow(workflowConfig, user_id);
        Log.d("AUTY",String.format("Unregistered %s workflow", this.workflowName));
        context.unregisterReceiver(batteryTrigger);
    }

    @Override
    public void handle(@Nullable Intent intent) {

        if (intent != null) {
            if(this.app.status_call_plugged_in(intent)) {
                System.out.println("Battery Charging");
                response.respond("Battery is charging");
            }

        } else {
            response.respond("Missing intent in the handler");
        }
    }
}

