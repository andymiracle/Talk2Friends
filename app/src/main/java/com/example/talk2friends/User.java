package com.example.talk2friends;

import java.util.ArrayList;

public class User {

    private String username;
    private String password;
    private int age;
    private String affiliation;
    private String likes;

    private ArrayList<String> friends;


    public User() {
        this.username = "";
        this.password = "";
        this.age = 0;
        this.affiliation = "";
        this.likes = "";
        this.friends = new ArrayList<String>();
    }

    public User(String username, String password, int age, String affiliation, String likes, ArrayList<String> friends) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.affiliation = affiliation;
        this.likes = likes;
        this.friends = friends;
    }

    public String getUsername(){
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

    public void printClass() {
        System.out.println("Username: " + this.username);
        System.out.println("Password: " + this.password);
        System.out.println("Age: " + this.age);
        System.out.println("Affiliation: " + this.affiliation);
    }


}

