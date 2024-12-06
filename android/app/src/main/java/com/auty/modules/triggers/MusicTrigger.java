package com.auty.modules.triggers;

import android.content.Context;
import android.content.Intent;

import com.auty.modules.workflows.Workflow;

public class MusicTrigger extends AbstractTrigger {
    public MusicTrigger(Workflow workflow) {
        super("musicTrigger", workflow);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.workflow.handle(intent);
    }
}
