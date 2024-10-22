package com.auty.modules
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage
import com.auty.modules.UserModel
import com.auty.UserAuthenticationPackage

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class UserAuthModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private lateinit var userModel: UserModel

    override fun getName(): String {
        return "UserAuthModule"
    }

    @ReactMethod
    fun initializeDatabase(promise: Promise) {
        try {
            val context: Context = reactApplicationContext.applicationContext
            userModel = UserModel(context)
            promise.resolve("Database initialized")
        } catch (e: Exception) {
            promise.reject("DB_ERROR", "Error initializing the database", e)
        }
    }

    @ReactMethod
    fun addUser(username: String, password: String, confirmPassword: String, promise: Promise) {
        try {
            val user = User(username, password, confirmPassword)
            val isAdded = userModel.addUser(user)
            if (isAdded) {
                promise.resolve("User registered successfully")
            } else {
                promise.reject("USER_EXISTS", "User already exists")
            }
        } catch (e: Exception) {
            promise.reject("DB_ERROR", "Error registering user", e)
        }
    }

    @ReactMethod
    fun authenticateUser(username: String, password: String, promise: Promise) {
        try {
            val user = User(username, password)
            val isAuthenticated = userModel.logIn(user)
            if (isAuthenticated) {
                promise.resolve("User authenticated")
            } else {
                promise.reject("INVALID_CREDENTIALS", "Invalid username or password")
            }
        } catch (e: Exception) {
            promise.reject("DB_ERROR", "Error authenticating user", e)
        }
    }
}
