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
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;
import androidx.test.core.app.ApplicationProvider;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModelTest {
    private UserModel userModel;

    private DatabaseInit dbInit;
    private Context context;
    private int user_id;

    @Before
    public void createDB() {
        this.context = ApplicationProvider.getApplicationContext();
        this.dbInit = new DatabaseInit(this.context, ":memory:");

        userModel = new UserModel(this.dbInit);
    }

    @Test
    public void addUserTest() throws Exception {
        String username = "username1";
        String password = "password";
        String repeatedPassword = "password";
        
        User registering_user;

        try {
            registering_user = new User(username, password, repeatedPassword);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertNotNull(registering_user);
        
        // perform first adding
        boolean status_first = userModel.addUser(registering_user);
        assertTrue(status_first);

        // // add same user second time
        // boolean status_second = userModel.addUser(registering_user);
        // assertFalse(status_second);
    }

    @Test 
    public void getUserTest() throws Exception {
        String username = "username1";
        String password = "password";

        User obtainedUserNull = userModel.getUser(username);
        assertNotNull(obtainedUserNull);
        
        // User addedUser = new User(username, password, password);
        // boolean status = userModel.addUser(addedUser);
        // assertTrue(status);

        // User obtainedUser = userModel.getUser(username);
        // assertNotNull(obtainedUser);
    }

    @Test
    public void getUserIDTest() throws Exception {
        String username = "username1";
        String password = "password";

        // int obtainedUserIDNull = userModel.getUserID(username);
        // assertNull(obtainedUserIDNull);

        // User addedUser;
        // try {
        //     addedUser = new User(username, password, password);
        // } catch (Exception e) {
        //     throw new RuntimeException(e);
        // }
        // boolean status = userModel.addUser(addedUser);
        
        int obtainedUserID = userModel.getUserID(username);
        assertNotNull(obtainedUserID);
    }
}
