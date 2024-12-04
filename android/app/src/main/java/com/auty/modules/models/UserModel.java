package com.auty.modules.models;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserModel {
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private DatabaseInit dbInit;

//    private static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_USERS + " (" +
//                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    KEY_USERNAME + " TEXT, " +
//                    KEY_PASSWORD + " TEXT);";
//
//    public UserModel(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        //3rd argument to be passed is CursorFactory instance
//    }

    public  UserModel(DatabaseInit db){
        this.dbInit = db;
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
////        String CREATE_USER_TABLE = String.format("CREATE TABLE %s (" +
////                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
////                "%s VARCHAR(255) NOT NULL, " +
////                "%s VARCHAR(255) NOT NULL" +
////                ")", TABLE_USERS, KEY_ID, KEY_USERNAME, KEY_PASSWORD);
////
////        db.execSQL(CREATE_USER_TABLE);
//        db.execSQL(CREATE_TABLE);
//        Log.d("Auth", "Creating user table");
//    }


//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        String DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_USERS);
//        db.execSQL(DROP_TABLE);
//
//        onCreate(db);
//    }

    public boolean addUser(User user) {

        SQLiteDatabase db = this.dbInit.getDB();

        String username = user.getUsername();
        String password = user.getPassword();

        User obtainedUser = this.getUser(username);

        if (obtainedUser != null) {
            return false;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(KEY_USERNAME, username);
            values.put(KEY_PASSWORD, password);

            db.insert(TABLE_USERS, null, values);
            db.close();

            return true;
        }
    }

    public User getUser(String username) {

        SQLiteDatabase db = this.dbInit.getDB();

        Cursor cursor = db.query(TABLE_USERS,
                new String[] { KEY_ID, KEY_USERNAME, KEY_PASSWORD}, KEY_USERNAME + "=?",
                new String[] { String.valueOf(username) },
                null, null, null, null
        );


        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                return null;
            }
        }
        else {
            return null;
        }

        User user = new User(
                cursor.getString(1),
                cursor.getString(2)
        );

        cursor.close();
        return user;
    }

    public Integer getUserID(String username) {

        SQLiteDatabase db = this.dbInit.getDB();

        Cursor cursor = db.query(TABLE_USERS,
                new String[] { KEY_ID, KEY_USERNAME, KEY_PASSWORD}, KEY_USERNAME + "=?",
                new String[] { String.valueOf(username) },
                null, null, null, null
        );

        int userIDIndex = cursor.getColumnIndex(KEY_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getCount() == 0) {
                return null;
            }
        }
        else {
            return null;
        }

        int userID = cursor.getInt(userIDIndex);

        cursor.close();
        return userID;
    }

    public int updateUser(User user) {

        SQLiteDatabase db = this.dbInit.getDB();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());

        int userID = db.update(TABLE_USERS, values, KEY_USERNAME + " = ?",
                new String[] {String.valueOf(user.getUsername())});

        db.close();
        return  userID;
    }

    public void deleteUser(User user) {

        SQLiteDatabase db = this.dbInit.getDB();

        db.delete( TABLE_USERS, KEY_USERNAME + " = ?",
                new String[] {String.valueOf(user.getUsername())});

        db.close();

        System.out.println("Deleted the user from the database");
    }

    public boolean logIn(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        User checkUser = getUser(username);
        if (checkUser == null) {
            return false;
        }

        String passwordDB = checkUser.getPassword();

        if (User.checkPassword(password, passwordDB) == true) {
            System.out.println("Logged User In");
            return true;
        } else {
            System.out.println("Wrong credentials");
            return false;
        }

    }

    public boolean register(User user) {
        String username = user.getUsername();

        if (!addUser(user)) {
            System.out.println("User was not registered");
            return false;
        } else {
            System.out.println("User registered successfully");
            return  true;
        }
    }

    public boolean resetPassword(User user) {
        String username = user.getUsername();

        User checkUser = getUser(username);
        if (checkUser == null) {
            return false;
        }

        String hashedPassword = User.hashPassword(user.getPassword());

        user.setPassword(hashedPassword);

        if (updateUser(user) == 0) {
            System.out.println("Failed to reset user password");
            return false;
        } else {
            System.out.println("Reset the user password");
            return true;
        }
    }

}