
package com.auty.modules.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkflowModel {
    private static final String TABLE_WORKFLOWS = "workflows";

    private static final String KEY_ID = "w_id";
    private static final String KEY_WF_NAME = "workflow_name";
    private static final String KEY_STATUS = "status";
    private static final String KEY_USER_ID = "id";

    private DatabaseInit dbInit;

    public WorkflowModel(DatabaseInit db) {
        this.dbInit = db;
    }

    public boolean addWorkflow(WorkflowConfig workflowConfig, int user_id){

        SQLiteDatabase db = dbInit.getDB();

        String workflow_name = workflowConfig.getWorkflowName();
        Boolean status = workflowConfig.getStatus();

        WorkflowConfig obtainedWorkflowConfig = this.getWorkflow(workflow_name);

        if (obtainedWorkflowConfig != null) {
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put(KEY_WF_NAME, workflow_name);
            values.put(KEY_STATUS, status);
            values.put(KEY_USER_ID, user_id);


            db.insert(TABLE_WORKFLOWS, null, values);
            db.close();

            return true;
        }
    }

    public WorkflowConfig getWorkflow(String workflowName){

        SQLiteDatabase db = this.dbInit.getDB();

        Cursor cursor = db.query(TABLE_WORKFLOWS,
                new String[] {KEY_ID, KEY_WF_NAME, KEY_STATUS},
                KEY_WF_NAME + "=?",
                new String[] {String.valueOf(workflowName)},
                null, null, null, null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                return null;
            }
        } else {
            return  null;
        }

        int workflowNameIndex = cursor.getColumnIndex(KEY_WF_NAME);
//        int triggerNameIndex = cursor.getColumnIndex(KEY_T_NAME);
//        int responseNameIndex = cursor.getColumnIndex(KEY_R_NAME);
//        int responseIndex = cursor.getColumnIndex(KEY_RESPONSE);
        int statusIndex = cursor.getColumnIndex(KEY_STATUS);


        WorkflowConfig workflowConfig = new WorkflowConfig(
                cursor.getString(workflowNameIndex),
//                cursor.getString(triggerNameIndex),
//                cursor.getString(responseNameIndex),
                cursor.getInt(statusIndex) == 1
//                cursor.getString(responseIndex)
        );

        cursor.close();
        return workflowConfig;
    }

    @SuppressLint("Range")
    public ArrayList<WorkflowConfig> getWorkflows() {

        SQLiteDatabase db = this.dbInit.getDB();

        ArrayList<WorkflowConfig> workflowConfigs = new ArrayList<>();

        String[] columns = {
                KEY_ID, KEY_WF_NAME, KEY_STATUS, KEY_USER_ID
        };
        Cursor cursor = db.query(TABLE_WORKFLOWS, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                WorkflowConfig workflowConfig = new WorkflowConfig();
                workflowConfig.setWorkflowName(cursor.getString(cursor.getColumnIndex(KEY_WF_NAME)));
                workflowConfig.setStatus(1 == cursor.getInt(cursor.getColumnIndex(KEY_STATUS)));

                workflowConfigs.add(workflowConfig);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return workflowConfigs;
    }

    @SuppressLint("Range")
    public Map<String, Boolean> getWorkflowByUser(int user_id) {

        SQLiteDatabase db = this.dbInit.getDB();

        Map<String, Boolean> workflowConfigMapping = new HashMap<>();

        Cursor cursor = db.query(
                TABLE_WORKFLOWS,
                new String[] { KEY_WF_NAME, KEY_STATUS},
                KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user_id) },
                null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                WorkflowConfig workflow = new WorkflowConfig();
                workflow.setWorkflowName(cursor.getString(cursor.getColumnIndex(KEY_WF_NAME)));
                workflow.setStatus(cursor.getInt(cursor.getColumnIndex(KEY_STATUS)) == 1);
//                workflow.setUserId(cursor.getLong(cursor.getColumnIndex(KEY_USER_ID)));
                workflowConfigMapping.put(workflow.getWorkflowName(), workflow.getStatus());
            } while (cursor.moveToNext());
        }

        cursor.close();

        return workflowConfigMapping;
    }
}