package com.auty;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.auty.modules.AbstractTrigger;

// Test class for AbstractTrigger functionality aligned with Section 5.5 requirements
@RunWith(AndroidJUnit4.class)
public class AbstractTriggerTest {

    private AbstractTrigger<Boolean> validTrigger;
    private AbstractTrigger<Boolean> invalidTrigger;

    // Static inner class for testing purposes
    public static class TestTrigger extends AbstractTrigger<Boolean> {
        public TestTrigger(String triggerName, Boolean condition) {
            super(triggerName, condition);
        }

        @Override
        public boolean handleService() {
            return getCondition();  // Returns the condition's result as the trigger state
        }
    }

    @Before
    public void setUp() {
        // Initialize triggers with valid and invalid conditions
        validTrigger = new TestTrigger("ValidTrigger", true);   // Should activate
        invalidTrigger = new TestTrigger("InvalidTrigger", false);  // Should not activate
    }

    // Test AT-1: Valid Trigger Condition
    @Test
    public void testValidTriggerCondition() {
        assertTrue("Trigger should activate for a valid condition", validTrigger.handleService());
    }

    // Test AT-2: Invalid Trigger Condition
    @Test
    public void testInvalidTriggerCondition() {
        assertFalse("Trigger should not activate for an invalid condition", invalidTrigger.handleService());
    }

    // Test AT-3: onStartCommand Invocation
    @Test
    public void testOnStartCommand_TriggerInvoked() {
        // Simulate the onStartCommand lifecycle method call
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AbstractTrigger.class);

        // Ensure that onStartCommand can be invoked without throwing an exception
        int result = validTrigger.onStartCommand(intent, 0, 0);
        assertEquals("Service onStartCommand should return expected value", 0, result);
    }
}
