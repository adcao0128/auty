package com.auty.modules.models;

import java.util.Map;

public class WorkflowConfig {

    private String workflowName;
    private Boolean status;

    public WorkflowConfig(String workflowName, Boolean status) {
        this.workflowName = workflowName;
        this.status = status;
    }

    public WorkflowConfig(){}

    public String getWorkflowName() {
        return this.workflowName;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WorkflowConfig{" +
                "workflowName='" + workflowName + '\'' +
                ", status=" + status +
                '}';
    }

}
