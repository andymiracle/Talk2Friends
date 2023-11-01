package com.example.talk2friends;

import java.util.HashMap;

public class Singleton {
    private String username;
    private String password;
    private HashMap<String, Boolean> interests;

    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null)
            instance = new Singleton();

        return instance;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, Boolean> getInterests() {
        return this.interests;
    }

    public void setInterests(HashMap<String, Boolean> interests) {
        this.interests = interests;
    }


}
