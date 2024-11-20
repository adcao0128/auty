package com.auty.modules.workflows;


import android.content.Intent;

import androidx.annotation.Nullable;

import com.auty.modules.applets.Applet;
import com.auty.modules.responses.AbstractResponse;

import java.util.ArrayList;

public abstract class Workflow {
    protected Applet app;
    protected boolean isActive;


    protected String workflowName;
    protected AbstractResponse response;

    public Workflow(String workflowName, Applet app, AbstractResponse response) {
        this.app = app;
        this.response = response;
        this.workflowName = workflowName;
        this.isActive = false;
    }

    public abstract void registerReceiver();
    public abstract void handle(@Nullable Intent intent);

    public String[] getConfig() {
        return new String[] {this.workflowName, this.response.getResponseName()};
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getWorkflowName() {
        return workflowName;
    }
}
