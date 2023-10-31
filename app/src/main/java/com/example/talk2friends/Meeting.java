package com.example.talk2friends;

import java.util.ArrayList;

public class Meeting {
    private String meetingID;
    private String time;
    private String location;
    private String topic;
    private ArrayList<String> attendees;

    public Meeting() {
        this.meetingID = "";
        this.time = "";
        this.location = "";
        this.topic = "";
        this.attendees = new ArrayList<String>();
    }

    public Meeting(String meetingID, String time, String location, String topic, ArrayList<String> attendees) {
        this.meetingID = meetingID;
        this.time = time;
        this.location = location;
        this.topic = topic;
        this.attendees = attendees;
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

    public ArrayList<String> getAttendees() {
        return this.attendees;
    }

    public void setAttendees(ArrayList<String> attendees) {
        this.attendees = attendees;
    }
}
