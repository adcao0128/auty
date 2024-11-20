package com.auty.modules.triggers;

import android.content.Context;
import android.content.Intent;

import com.auty.modules.workflows.Workflow;


public class BatteryTrigger extends AbstractTrigger {

    public BatteryTrigger(String triggerName, Workflow workflow) {
        super(triggerName, workflow);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.workflow.handle(intent);
    }

}
