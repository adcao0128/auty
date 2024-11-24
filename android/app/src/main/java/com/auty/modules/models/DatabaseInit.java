package com.auty.modules.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseInit  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "AUTY";

    private static final String TABLE_USERS = "users";
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private static final String TABLE_WORKFLOWS = "workflows";
    private static final String KEY_ID = "w_id";
    private static final String KEY_WF_NAME = "workflow_name";
    private static final String KEY_STATUS = "status";

    private static  final String TABLE_NOTIFICATIONS = "notifications";
    private static final String KEY_NOTIFICATION_ID = "n_id";
    private static final String KEY_NOTIFICATION_TEXT = "n_text";

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_USERNAME + " TEXT, " +
                    KEY_PASSWORD + " TEXT);";

    private static  final  String CREATE_WORKFLOW_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s VARCHAR(255) NOT NULL, " +
                    "%s INTEGER, " +
                    "%s INTEGER, " +
                    "FOREIGN KEY(%s) REFERENCES %s (%s)" +
                    ")"
            , TABLE_WORKFLOWS, KEY_ID, KEY_WF_NAME, KEY_STATUS, KEY_USER_ID, KEY_USER_ID, TABLE_USERS, KEY_USER_ID
    );

    private  static final String CREATE_NOTIFICATION_TABLE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s VARCHAR(255) NOT NULL, " +
                    "%s INTEGER, " +
                    "FOREIGN KEY(%s) REFERENCES %s (%s)" +
                    ")"
            , TABLE_NOTIFICATIONS, KEY_NOTIFICATION_ID, KEY_NOTIFICATION_TEXT, KEY_USER_ID, KEY_USER_ID, TABLE_USERS, KEY_USER_ID
    );

    public DatabaseInit(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_WORKFLOW_TABLE);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USERS_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_USERS);
        db.execSQL(DROP_USERS_TABLE);

        String DROP_WORKFLOWS_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_WORKFLOWS);
        db.execSQL(DROP_WORKFLOWS_TABLE);

        String DROP_NOTIFICATIONS_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NOTIFICATIONS);
        db.execSQL(DROP_NOTIFICATIONS_TABLE);

        onCreate(db);
    }

    public SQLiteDatabase getDB(){
        return this.getWritableDatabase();
    }
}