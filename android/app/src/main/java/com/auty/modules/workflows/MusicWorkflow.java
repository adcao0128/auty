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
import com.auty.modules.models.WorkflowConfig;
import com.auty.modules.models.WorkflowModel;

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
    public void registerReceiver(WorkflowModel workflowModel, int user_id) {
        WorkflowConfig workflowConfig = new WorkflowConfig(this.workflowName, Boolean.TRUE);
        workflowModel.updateWorkflow(workflowConfig, user_id);
        Log.d("AUTY",String.format("Registered %s workflow", this.workflowName));
        this.musicTrigger = new MusicTrigger(this);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        context.registerReceiver(this.musicTrigger, filter);
    }

    @Override
    public void unregisterReceiver(WorkflowModel workflowModel, int user_id) {
        WorkflowConfig workflowConfig = new WorkflowConfig(this.workflowName, Boolean.FALSE);
        workflowModel.updateWorkflow(workflowConfig, user_id);
        Log.d("AUTY",String.format("Unregistered %s workflow", this.workflowName));
        context.unregisterReceiver(this.musicTrigger);
    }

    @Override
    public void handle(@Nullable Intent intent) {
        if (intent != null) {
            this.response.respond("User sent to Music App");
        }
    }

}