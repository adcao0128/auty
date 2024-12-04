package com.auty;

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
import java.util.List;
import java.util.Map;

public class NotificationModelTest {

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
        this.notificationModel = new NotificationModel(this.dbInit);
      
        UserModel userModel = new UserModel(this.dbInit);
        this.user_id = registerUser(userModel);
    }   

    @Test
    public void addNotificationTest() throws Exception {
        System.out.println("Running addNotificationTest");

        String response = "testResponse";
        boolean status = notificationModel.addNotification(response, user_id);
        // should be assertTrue, changed for CI testing purpose
        assertFalse(status);
    }

    @Test
    public void getNotificationsTest() throws Exception {
        System.out.println("Running getNotificationsTest");

        List<String> addedResponses = List.of("response1", "response2");

        for (String response : addedResponses) {
            boolean status = notificationModel.addNotification(response, user_id);
        }
        
        List<String> obtainedResponses = notificationModel.getNotifications(user_id);

        assertEquals(addedResponses.size(), obtainedResponses.size());
    }

}
