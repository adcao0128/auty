package com.auty.modules;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserModel extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AUTY";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    public UserModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = String.format("CREATE TABLE %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR(255) NOT NULL, " +
                "%s VARCHAR(255) NOT NULL" +
                ")", TABLE_USERS, KEY_ID, KEY_USERNAME, KEY_PASSWORD);

        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_USERS);

        db.execSQL(DROP_TABLE);

        onCreate(db);
    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

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
        SQLiteDatabase db = this.getWritableDatabase();

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

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());

        return  db.update(TABLE_USERS, values, KEY_USERNAME + " = ?",
                new String[] {String.valueOf(user.getUsername())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete( TABLE_USERS, KEY_USERNAME + " = ?",
                new String[] {String.valueOf(user.getUsername())});

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

    // UNTESTED
//    public boolean resetPassword(User user) {
//        String username = user.getUsername();
//
//        User checkUser = getUser(username);
//        if (checkUser == null) {
//            return false;
//        }
//
//        String hashedPassword = User.hashPassword(user.getPassword());
//
//        user.setPassword(hashedPassword);
//
//        if (updateUser(user) == 0) {
//            System.out.println("Failed to reset user password");
//            return false;
//        } else {
//            System.out.println("Reset the user password");
//            return true;
//        }
//    }
}