package com.auty.modules

import android.util.Log

import com.auty.modules.responses.NotificationResponse
import com.auty.modules.responses.MusicResponse
import com.auty.modules.applets.BatteryApplet
import com.auty.modules.applets.WifiApplet
import com.auty.modules.applets.BluetoothApplet
import com.auty.modules.applets.NotificationApplet
import com.auty.modules.applets.MusicApplet
import com.auty.modules.workflows.WifiWorkflow
import com.auty.modules.workflows.BatteryPluggedInWorkflow
import com.auty.modules.workflows.BatteryLowWorkflow
import com.auty.modules.workflows.BluetoothConnectedWorkflow
import com.auty.modules.workflows.MusicWorkflow
import com.auty.modules.workflows.Workflow
import com.auty.modules.models.WorkflowModel
import com.auty.modules.models.UserModel
import com.auty.modules.models.NotificationModel
import com.auty.modules.models.WorkflowConfig
import com.auty.modules.models.DatabaseInit

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableArray
import android.content.Context

class WorkflowModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    private val workflows = arrayListOf<Workflow>()
    private lateinit var databaseInit: DatabaseInit
    private lateinit var workflowModel: WorkflowModel
    private lateinit var userModel: UserModel
    private lateinit var notificationModel: NotificationModel

    override fun getName(): String {
        return "WorkflowModule"
    }

    @ReactMethod
    fun initializeDatabase(promise: Promise) {
        try {
            val context = reactApplicationContext.currentActivity ?: run {
                promise.reject("ACTIVITY_NOT_FOUND", "No active activity context available")
                return
            }
            databaseInit = DatabaseInit(context, null)
            workflowModel = WorkflowModel(databaseInit)
            userModel = UserModel(databaseInit)
            notificationModel = NotificationModel(databaseInit)
            promise.resolve("Database initialized")
        } catch (e: Exception) {
            promise.reject("DB_ERROR", "Error initializing the database", e)
        }
    }

    @ReactMethod
    fun createWorkflowList(username: String, promise: Promise) {
        try {
            val context = reactApplicationContext.currentActivity ?: run {
                promise.reject("ACTIVITY_NOT_FOUND", "No active activity context available")
                return
            }

            val userID = getUserID(username)

            // Initialize notification responses
            Log.d("WorkflowModule", "Initializing NotificationResponses")
            val batteryChargingResponse = NotificationResponse(context, "batteryChargingResponse", userID, notificationModel)
            val batteryLowResponse = NotificationResponse(context, "batteryLowResponse", userID, notificationModel)
            val wifiConnectedResponse = NotificationResponse(context, "wifiConnectedResponse", userID, notificationModel)
            val bluetoothConnectedResponse = NotificationResponse(context, "bluetoothConnectedResponse", userID, notificationModel)
            val musicResponse = MusicResponse(context, "musicResponse", userID, notificationModel)
    
            // Initialize applets
            Log.d("WorkflowModule", "Initializing Applets")
            val batteryApplet = BatteryApplet()
            val bluetoothApplet = BluetoothApplet(context)
            val wifiApplet = WifiApplet(context)
            val musicApplet = MusicApplet(context)
    
            // Create workflows
            Log.d("WorkflowModule", "Creating Workflows")
            val wifiWorkflow = WifiWorkflow(context, wifiApplet, wifiConnectedResponse)
            val batteryPluggedInWorkflow = BatteryPluggedInWorkflow(context, batteryApplet, batteryChargingResponse)
            val batteryLowWorkflow = BatteryLowWorkflow(context, batteryApplet, batteryLowResponse)
            val bluetoothConnectedWorkflow = BluetoothConnectedWorkflow(context, bluetoothApplet, bluetoothConnectedResponse)
            val musicWorkflow = MusicWorkflow(context, musicApplet, musicResponse)
    
            // Add workflows to the list
            Log.d("WorkflowModule", "Adding Workflows to List")
            workflows.clear() // Clear existing workflows before adding new ones
            workflows.addAll(listOf(wifiWorkflow, batteryPluggedInWorkflow, batteryLowWorkflow, bluetoothConnectedWorkflow, musicWorkflow))
    
            Log.d("WorkflowModule", "Workflows created successfully")
            promise.resolve("Workflows created successfully")
        } catch (e: Exception) {
            // Handle errors
            promise.reject("WORKFLOW_CREATION_ERROR", "Failed to create workflows", e)
        }
    }

    @ReactMethod
    fun registerUserWorkflows(loggedInUser: String, promise: Promise) {
        try {
            // Get the userID from the userModel
            Log.d("WorkflowModule", "Get UserID")
            val userID = getUserID(loggedInUser)

            Log.d("WorkflowModule", "Adding all workflows")
            workflows.forEach { workflow ->
                addWorkflows(userID, workflow)
            }

            // Retrieve workflows for the user
            Log.d("WorkflowModule", "Retrieving user workflows")
            val userWorkflows = getWorkflows(userID)

            // Register workflows based on their status
            Log.d("WorkflowModule", "Registering Workflows")
            for ((workflow, status) in userWorkflows) {
                if (status) {
                    workflow.registerReceiver(workflowModel, userID)
                    Log.d("AUTY", String.format("Registered: %s", workflow.workflowName))
                }
            }

            promise.resolve("User workflows registered successfully")
        } catch (e: Exception) {
            promise.reject("WORKFLOW_REGISTRATION_ERROR", "Failed to register user workflows", e)
        }
    }

    @ReactMethod
    fun getUserWorkflows(username: String, promise: Promise) {
        try {
            val userID = getUserID(username)
            val workflowConfigMappings: Map<String, Boolean> = workflowModel.getWorkflowByUser(userID)

            val writableMap: WritableMap = Arguments.createMap()
            workflowConfigMappings.forEach { (workflowName, status) ->
                writableMap.putBoolean(workflowName, status)
                Log.d("AUTY-getUserWorkflows", "WorkflowName: $workflowName, Status: $status")
            }
            promise.resolve(writableMap)
        } catch (e: Exception) {
            promise.reject("WORKFLOW_RETRIEVAL_ERROR", "Failed to retrieve user workflows", e)
        }
    }

    @ReactMethod
    fun registerWorkflow(username: String, workflowName: String, promise: Promise) {
        try {
            val userID = getUserID(username)
            val workerConfig = WorkflowConfig(workflowName, true)
    
            // Retrieve workflows for the user
            Log.d("WorkflowModule", "Retrieving user workflows")
            val userWorkflows = getWorkflows(userID)
    
            // Register workflows based on their status
            Log.d("WorkflowModule", "Registering Workflows")
            for ((workflow, status) in userWorkflows) {
                if (workflow.getWorkflowName() == workflowName) {
                    workflow.registerReceiver(workflowModel, userID)
                    Log.d("AUTY", String.format("Registered: %s", workflow.workflowName))
                }
            }
            workflowModel.updateWorkflow(workerConfig, userID)
            promise.resolve("Workflow registered")
        } catch (e: Exception) {
            promise.reject("WORK_REGISTRATION_ERROR", "Failed to register workflow", e)
        }
    }

    @ReactMethod
    fun unregisterWorkflow(username: String, workflowName: String, promise: Promise) {
        try {
            val userID = getUserID(username)
            val workerConfig = WorkflowConfig(workflowName, true)
    
            // Retrieve workflows for the user
            Log.d("WorkflowModule", "Retrieving user workflows")
            val userWorkflows = getWorkflows(userID)
    
            // Unregister workflows based on their status
            Log.d("WorkflowModule", "Unregistering Workflows")
            for ((workflow, status) in userWorkflows) {
                if (workflow.getWorkflowName() == workflowName) {
                    workflow.unregisterReceiver(workflowModel, userID)
                    Log.d("AUTY", String.format("Unregistered: %s", workflow.workflowName))
                }
            }
            promise.resolve("Workflow unregistered")
        } catch (e: Exception) {
            promise.reject("WORK_REGISTRATION_ERROR", "Failed to unregister workflow", e)
        }
    }

    @ReactMethod
    fun getNotificationList(username: String, promise: Promise) {
        try {
            val userID = getUserID(username)
            val notificationList: List<String> = notificationModel.getNotifications(userID)
            val writableArray: WritableArray = Arguments.createArray()
            for (notification in notificationList) {
                writableArray.pushString(notification)
            }
            Log.d("WorkflowModule", "Notification List Retrieved")
            promise.resolve(writableArray)
        } catch (e: Exception) {
            promise.reject("NOTIFICATION_RETRIEVAL_ERROR", "Failed to retrieve user notifications", e)
        }
    }

    // Helper method to get userID (Assuming getUserID is implemented elsewhere)
    private fun getUserID(loggedInUser: String): Int {
        return userModel.getUserID(loggedInUser)
    }

    // Add workflows for the user
    private fun addWorkflows(userID: Int, workflow: Workflow) {
        // You can modify this to pass an actual WorkflowConfig instance
        val workflowConfig = WorkflowConfig(workflow.getWorkflowName(), workflow.isActive()) // Assuming 'true' is the default status
        if (!workflowModel.addWorkflow(workflowConfig, userID)) {
            Log.d("AUTY", "Workflow already exists: $workflow.getWorkflowName()")
        }
    }

    // Get workflows for the user
    private fun getWorkflows(userID: Int): Map<Workflow, Boolean> {
        val workflowConfigMappings: Map<String, Boolean> = workflowModel.getWorkflowByUser(userID)
        val workflowMapping = mutableMapOf<Workflow, Boolean>()
        
        workflowConfigMappings.forEach { (workflowName, status) ->
            Log.d("AUTY", "WorkflowName: $workflowName, Status: $status")
        
            workflows.forEach { workflow ->
                if (workflow.workflowName == workflowName) {
                    workflowMapping[workflow] = status
                }
            }
        }
        
        return workflowMapping
    }

}
