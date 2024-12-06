package com.auty.modules

import com.auty.modules.models.UserModel
import com.auty.modules.models.User
import com.auty.modules.models.DatabaseInit
import com.auty.UserAuthenticationPackage

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise

class UserAuthModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    private lateinit var userModel: UserModel
    private lateinit var databaseInit: DatabaseInit

    override fun getName(): String {
        return "UserAuthModule"
    }

    @ReactMethod
    fun initializeDatabase(promise: Promise) {
        try {
            val context = reactApplicationContext.currentActivity ?: run {
                promise.reject("ACTIVITY_NOT_FOUND", "No active activity context available")
                return
            }
            databaseInit = DatabaseInit(context, null)
            userModel = UserModel(databaseInit)
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
    
    @ReactMethod
    fun getUserID(username: String, promise: Promise) {
        try {
            val userID: Int? = userModel.getUserID(username)
            if (userID != null) {
                promise.resolve(userID)
            } else {
                promise.reject("USER_NOT_FOUND", "User not found")
            }
        } catch (e: Exception) {
            promise.reject("DATABASE_ERROR", "Failed to retrieve user ID", e)
        }
    }
}
