package com.auty.modules.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {

    public String username;
    public String password;

    public User(String username, String password, String  passwordRepeat) throws Exception {
        this.username = username;

        if (password.equals(passwordRepeat)) {
            this.password = hashPassword(password);
        } else {
            throw new Exception("Passwords are not matching");
        }

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public User(String username) {
        this.username = username;
    }


    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }






}
