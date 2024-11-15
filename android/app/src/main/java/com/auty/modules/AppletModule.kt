package com.auty.modules

import com.auty.modules.BatteryApplet
import com.auty.modules.WifiApplet
import com.auty.modules.BluetoothApplet
import com.auty.modules.NotificationApplet
import com.auty.AppletPackage

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class AppletModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private lateinit var batteryApplet: BatteryApplet
    private lateinit var wifiApplet: WifiApplet
    private lateinit var bluetoothApplet: BluetoothApplet
    private lateinit var notificationApplet: NotificationApplet

    override fun getName(): String {
        return "AppletModule"
    }

    @ReactMethod
    fun initializeApplets(promise: Promise) {
        try {
            val context = reactApplicationContext.currentActivity ?: run {
                promise.reject("ACTIVITY_NOT_FOUND", "No active activity context available")
                return
            }
            notificationApplet = NotificationApplet(context)
            wifiApplet = WifiApplet(context, notificationApplet)
            bluetoothApplet = BluetoothApplet(context, notificationApplet)
            batteryApplet = BatteryApplet(context, notificationApplet)
            promise.resolve("Applets initialized")
        } catch (e: Exception) {
            promise.reject("APPLET_ERROR", "Error initializing applets", e)
        }
    }
}
