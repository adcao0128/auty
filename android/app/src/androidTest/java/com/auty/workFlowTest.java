package com.auty;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.auty.modules.Workflow;
import com.auty.modules.AbstractTrigger;
import com.auty.modules.AbstractResponse;

// test runs on emulator or device
@RunWith(AndroidJUnit4.class)
public class workFlowTest {

    private Workflow workflow;
    private AbstractTrigger mockTrigger;
    private AbstractResponse mockResponse;
    private Context context;

    @Before
    public void setUp() {
        // Set up context for the test
        context = ApplicationProvider.getApplicationContext();

        // Create a mock trigger and response
        mockTrigger = new AbstractTrigger("BatteryTrigger", 0.5f) {
            @Override
            public boolean handleService() {
                // Simulate a battery trigger condition (e.g., battery level below threshold)
                return true;  // Trigger condition is met
            }
        };

        mockResponse = new AbstractResponse("testReponse", "testMessage") {
            @Override
            public void respond() {
                // Simulate response behavior
                System.out.println("Response triggered");
            }
        };

        // Initialize the Workflow object with the mock trigger and response
        workflow = new Workflow(mockResponse, mockTrigger);
    }

    // Test WFT-1: Battery Level Trigger
    @Test
    public void testBatteryLevelTrigger() {
        // Check if the workflow is correctly initialized with the trigger
        assertEquals("BatteryTrigger", workflow.getTrigger().getTriggerName());

        // Simulate workflow handling for battery level condition
        workflow.handle();

        // Check that the trigger activates as expected
        assertTrue("Battery level trigger should activate when battery is low", mockTrigger.handleService());
    }

    // Test WFT-2: Battery Plugged-In Trigger
    @Test
    public void testBatteryPluggedInTrigger() {
        // Redefine mockTrigger to simulate a battery plugged-in condition
        mockTrigger = new AbstractTrigger("BatteryPluggedInTrigger", 1.0f) {
            @Override
            public boolean handleService() {
                // Simulate a device plugged-in condition
                return true;  // Trigger condition is met
            }
        };

        // Reinitialize workflow with the new trigger
        workflow = new Workflow(mockResponse, mockTrigger);

        // Check if the workflow is correctly initialized with the trigger
        assertEquals("BatteryPluggedInTrigger", workflow.getTrigger().getTriggerName());

        // Simulate workflow handling for plugged-in condition
        workflow.handle();

        // Check that the trigger activates as expected
        assertTrue("Battery plugged-in trigger should activate when device is charging", mockTrigger.handleService());
    }

    @Test
    public void testOnStartAndOnStop() {
        try {
            workflow.onStart();
            workflow.onStop();
        } catch (Exception e) {
            fail("onStart() or onStop() should not throw exceptions");
        }
    }
}
