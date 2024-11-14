package com.auty;

import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.auty.modules.BatteryApplet;
import com.auty.modules.NotificationApplet;

// tests run on an emulator or device
@RunWith(AndroidJUnit4.class)  
public class BatteryAppletTest {

    private BatteryApplet batteryApplet;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        NotificationApplet notificationApplet = new NotificationApplet(context);
        batteryApplet = new BatteryApplet(context, notificationApplet);
    }

    @Test
    public void testStatusCallPluggedIn_Charging() {
        // Simulate intent for charging status
        Intent batteryIntent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent.putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING);

        // Broadcast intent and test if status is correctly identified as charging
        ApplicationProvider.getApplicationContext().sendBroadcast(batteryIntent);
        assertTrue("The device should be charging", batteryApplet.status_call_plugged_in());
    }

    @Test
    public void testStatusCallPluggedIn_NotCharging() {
        // Simulate intent for not charging status
        Intent batteryIntent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent.putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_NOT_CHARGING);

        // Broadcast intent and test if status is correctly identified as not charging
        ApplicationProvider.getApplicationContext().sendBroadcast(batteryIntent);
        assertFalse("The device should not be charging", batteryApplet.status_call_plugged_in());
    }

    @Test
    public void testStatusCallLowBattery_LowBattery() {
        // Simulate intent for low battery status
        Intent batteryIntent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent.putExtra(BatteryManager.EXTRA_LEVEL, 5);  // Set battery percentage to 5%

        // Broadcast intent and test if battery is identified as low
        ApplicationProvider.getApplicationContext().sendBroadcast(batteryIntent);
        assertTrue("The battery should be low", batteryApplet.status_call_low_battery());
    }

    @Test
    public void testStatusCallLowBattery_NotLowBattery() {
        // Simulate intent for sufficient battery status
        Intent batteryIntent = new Intent(Intent.ACTION_BATTERY_CHANGED);
        batteryIntent.putExtra(BatteryManager.EXTRA_LEVEL, 50);  // Set battery percentage to 50%

        // Broadcast intent and test if battery is identified as sufficient
        ApplicationProvider.getApplicationContext().sendBroadcast(batteryIntent);
        assertFalse("The battery should not be low", batteryApplet.status_call_low_battery());
    }
}
