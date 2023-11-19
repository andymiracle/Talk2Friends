package com.example.talk2friends;

import java.util.ArrayList;
import java.util.UUID;

public class Meeting {
    private String name;
    private String meetingID;
    private String time;
    private String location;
    private String topic;
    private String creator;
    private ArrayList<String> attendees;

    public Meeting() {
        this.name = "";
        this.meetingID = "";
        this.time = "";
        this.location = "";
        this.topic = "";
        this.creator = "";
        this.attendees = new ArrayList<String>();
    }

    public Meeting(String name, String time, String location, String topic, String creator, ArrayList<String> attendees) {
        this.name = name;
        this.meetingID = UUID.randomUUID().toString();
        this.time = time;
        this.location = location;
        this.topic = topic;
        this.creator = creator;
        this.attendees = attendees;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeetingID() {
        return this.meetingID;
    }

    public void setMeetingID(String meetingID) {
        this.meetingID = meetingID;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTopic() {
        return this.topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<String> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(ArrayList<String> attendees) {
        this.attendees = attendees;
    }
}
