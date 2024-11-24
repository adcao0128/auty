package com.auty.modules.models;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationModel {
    private static  final String TABLE_NOTIFICATIONS = "notifications";
    private static final String KEY_NOTIFICATION_ID = "n_id";
    private static final String KEY_NOTIFICATION_TEXT = "n_text";
    private static final String KEY_USER_ID = "id";
    private DatabaseInit dbInit;
    public NotificationModel(DatabaseInit db) {
        this.dbInit = db;
    }
    public boolean addNotification(String response, int user_id){
        SQLiteDatabase db = dbInit.getDB();
        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFICATION_TEXT, response);
        values.put(KEY_USER_ID, user_id);
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close();
        return true;
    }
    public List<String> getNotifications(int user_id){
        SQLiteDatabase db = this.dbInit.getDB();
        List<String> notifications = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NOTIFICATIONS,
                new String[] { KEY_NOTIFICATION_TEXT},
                KEY_USER_ID + " = ?",
                new String[] { String.valueOf(user_id) },
                null, null, null
        );
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String notificationText = cursor.getString(cursor.getColumnIndex(KEY_NOTIFICATION_TEXT));
                notifications.add(notificationText);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notifications;
    }
}