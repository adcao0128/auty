package com.auty;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.auty.modules.BatteryApplet;
import com.auty.modules.BatteryTriggerPluggedIn;

public class BatteryTriggerPluggedInTest {

    private BatteryApplet mockBatteryApplet;
    private BatteryTriggerPluggedIn batteryTriggerPluggedIn;

    @Before
    public void setUp() {
        // Mock the BatteryApplet class
        mockBatteryApplet = mock(BatteryApplet.class);

        // Initialize BatteryTriggerPluggedIn with mock data
        batteryTriggerPluggedIn = new BatteryTriggerPluggedIn("BatteryTrigger", 0.5f, mockBatteryApplet);
    }

    @Test
    public void testHandleService_PluginStatusTrue() {
        // Arrange: Simulate the battery being plugged in
        when(mockBatteryApplet.status_call_plugged_in()).thenReturn(true);

        // Act: Call handleService
        boolean result = batteryTriggerPluggedIn.handleService();

        // Assert: Verify that handleService returns true
        assertTrue("The device should be plugged in", result);
    }

    @Test
    public void testHandleService_PluginStatusFalse() {
        // Arrange: Simulate the battery not being plugged in
        when(mockBatteryApplet.status_call_plugged_in()).thenReturn(false);

        // Act: Call handleService
        boolean result = batteryTriggerPluggedIn.handleService();

        // Assert: Verify that handleService returns false
        assertFalse("The device should not be plugged in", result);
    }
}
