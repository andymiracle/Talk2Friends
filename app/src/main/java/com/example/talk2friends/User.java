package com.example.talk2friends;

import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private String password;
    private Boolean isVerified;
    private ArrayList<String> friends;


    public User() {
        this.username = "";
        this.email = "";
        this.password = password;
        this.isVerified = false;
        this.friends = new ArrayList<String>();
    }

    public User(String username, String email, String password, Boolean isVerified, ArrayList<String> friends) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isVerified = isVerified;
        this.friends = friends;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail(){
        return this.username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }


}
