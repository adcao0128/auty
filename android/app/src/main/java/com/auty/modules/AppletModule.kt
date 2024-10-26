package com.auty.modules

import com.auty.modules.BatteryApplet
import com.auty.modules.NotificationApplet
import com.auty.AppletPackage

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class AppletModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private lateinit var batteryApplet: BatteryApplet
    private lateinit var notificationApplet: NotificationApplet

    override fun getName(): String {
        return "AppletModule"
    }

    @ReactMethod
    fun initializeApplets(promise: Promise) {
        try {
            val context: Context = reactApplicationContext.applicationContext
            notificationApplet = NotificationApplet(context)
            batteryApplet = BatteryApplet(context, notificationApplet)
            promise.resolve("Applets initialized")
        } catch (e: Exception) {
            promise.reject("APPLET_ERROR", "Error initializing applets", e)
        }
    }
}
