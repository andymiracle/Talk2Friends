package com.example.talk2friends;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String username;
    private String displayName;
    private String password;
    private int age;
    private String affiliation;
    private HashMap<String, Boolean> interests;

    private ArrayList<String> friends;


    public User() {
        this.username = "";
        this.displayName = "";
        this.password = "";
        this.age = 0;
        this.affiliation = "";
        this.interests = new HashMap<String, Boolean>();
        this.friends = new ArrayList<String>();
    }

    public User(String username, String displayName, String password, int age, String affiliation, HashMap<String, Boolean> interests, ArrayList<String> friends) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
        this.age = age;
        this.affiliation = affiliation;
        this.interests = interests;
        this.friends = friends;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAffiliation() {
        return this.affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public HashMap<String, Boolean> getInterests() {
        return this.interests;
    }

    public void setInterests(HashMap<String, Boolean> interests) {
        this.interests = interests;
    }

    public void printClass() {
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("Age: " + this.age);
        System.out.println("Affiliation: " + this.affiliation);
        //System.out.println("Interest: " + this.interest);
    }


}

