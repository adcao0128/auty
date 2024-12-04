package com.example.myapplication;

import android.content.Context;

import com.auty.modules.applets.BatteryApplet;
import com.auty.modules.applets.BluetoothApplet;
import com.auty.modules.applets.WifiApplet;
import com.auty.modules.models.DatabaseInit;
import com.auty.modules.models.User;
import com.auty.modules.models.UserModel;
import com.auty.modules.models.WorkflowConfig;
import com.auty.modules.models.WorkflowModel;
import com.auty.modules.models.NotificationModel;
import com.auty.modules.responses.NotificationResponse;
import com.auty.modules.workflows.BatteryLowWorkflow;
import com.auty.modules.workflows.BatteryPluggedInWorkflow;
import com.auty.modules.workflows.BluetoothConnectedWorkflow;
import com.auty.modules.workflows.WifiWorkflow;
import com.auty.modules.workflows.Workflow;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkflowModelTest {
    private WorkflowModel workflowModel;
    private NotificationModel notificationModel;

    private DatabaseInit dbInit;
    private Context context;
    private int user_id;

    private int registerUser(UserModel userModel) {
        String username = "username";
        String password = "password";
        String repeatedPassword = "password";

        try {
            User registering_user = new User(username, password, repeatedPassword);
            userModel.register(registering_user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return userModel.getUserID(username);
    }

    @Before
    public void createDB() {

        this.context = ApplicationProvider.getApplicationContext();

        this.dbInit = new DatabaseInit(this.context, ":memory:");
        this.workflowModel = new WorkflowModel(this.dbInit);
        this.notificationModel = new NotificationModel(this.dbInit);
        UserModel userModel = new UserModel(this.dbInit);
        this.user_id = registerUser(userModel);
        
    }

    private ArrayList<Workflow> createWorkflowList() {
        NotificationResponse batteryChargingResponse = new NotificationResponse(context, "batteryChargingResponse", user_id, notificationModel);
        NotificationResponse batteryLowResponse = new NotificationResponse(context, "batteryLowResponse", user_id, notificationModel);
        NotificationResponse wifiConnectedResponse = new NotificationResponse(context, "wifiConnectedResponse", user_id, notificationModel);
        NotificationResponse bluetoothConnectedResponse = new NotificationResponse(context, "bluetoothConnectedResponse", user_id, notificationModel);

        BatteryApplet batteryApplet = new BatteryApplet();

        BatteryPluggedInWorkflow batteryPluggedInWorkflow = new BatteryPluggedInWorkflow(this.context, batteryApplet, batteryChargingResponse);
        BatteryLowWorkflow batteryLowWorkflow = new BatteryLowWorkflow(this.context, batteryApplet, batteryLowResponse);

        ArrayList<Workflow> workflows = new ArrayList<>();
        workflows.add(batteryPluggedInWorkflow);
        workflows.add(batteryLowWorkflow);

        return workflows;
    }

    @Test
    public void addWorkflowTest() throws Exception {
        ArrayList<Workflow> createdWorkflows = createWorkflowList();

        Workflow addedWorkflow = createdWorkflows.get(0);

        WorkflowConfig workflowConfig = new WorkflowConfig(addedWorkflow.getWorkflowName(), true);
        boolean first_status = this.workflowModel.addWorkflow(workflowConfig, this.user_id);
        boolean second_status = this.workflowModel.addWorkflow(workflowConfig, this.user_id);
        assertNotEquals(first_status, second_status);
    }

    @Test
    public void getWorkflowTest() throws Exception {
        ArrayList<Workflow> createdWorkflows = createWorkflowList();

        Workflow addedWorkflow = createdWorkflows.get(1);

        // try to obtain workflow before adding
        WorkflowConfig obtainedWorkflowConfig = this.workflowModel.getWorkflow(addedWorkflow.getWorkflowName());
        assertNotNull(obtainedWorkflowConfig);

        WorkflowConfig addedWorkflowConfig = new WorkflowConfig(addedWorkflow.getWorkflowName(), true);
        boolean status = this.workflowModel.addWorkflow(addedWorkflowConfig, user_id);
        obtainedWorkflowConfig = this.workflowModel.getWorkflow(addedWorkflow.getWorkflowName());

        // get workflow after adding
        assertNotNull(obtainedWorkflowConfig);
        assertEquals(addedWorkflowConfig.getWorkflowName(), obtainedWorkflowConfig.getWorkflowName());
        assertEquals(addedWorkflowConfig.getStatus(), obtainedWorkflowConfig.getStatus());
    }

    @Test
    public void getWorkflowByUserTest() throws Exception {
        ArrayList<Workflow> createdWorkflows = createWorkflowList();

        Workflow addedWorkflow = createdWorkflows.get(1);

        WorkflowConfig addedWorkflowConfig = new WorkflowConfig(addedWorkflow.getWorkflowName(), true);
        boolean status = this.workflowModel.addWorkflow(addedWorkflowConfig, this.user_id);
        Map<String, Boolean> obtainedWorkflowConfig = this.workflowModel.getWorkflowByUser(this.user_id);

        assertNotNull(obtainedWorkflowConfig);
        for (Map.Entry<String, Boolean> entry : obtainedWorkflowConfig.entrySet()) {
            String workflowName = entry.getKey();
            Boolean workflowStatus = entry.getValue();
            assertEquals(addedWorkflowConfig.getWorkflowName(), workflowName);
            assertEquals(addedWorkflowConfig.getStatus(), workflowStatus);
        }
    }

    @Test
    public void getWorkflowsTest() throws  Exception{
        ArrayList<Workflow> createdWorkflows = createWorkflowList();

        for( Workflow workflow : createdWorkflows) {
            WorkflowConfig addedWorkflowConfig = new WorkflowConfig(workflow.getWorkflowName(), true);
            boolean status = this.workflowModel.addWorkflow(addedWorkflowConfig, user_id);
        }

        ArrayList<WorkflowConfig> obtainedWorkflows = this.workflowModel.getWorkflows();

        assertNotNull(obtainedWorkflows);
        assertEquals(createdWorkflows.size(), obtainedWorkflows.size());
    }
}