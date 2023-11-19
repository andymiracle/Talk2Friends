package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

public class SortMeetingsUnitTests {

    @Test
    public void SortMeetingsTest()
    {
        ArrayList<Meeting> defaultMeetingList = new ArrayList<>();
        ArrayList<Meeting> correctMeetingList = new ArrayList<>();

        Meeting meeting1 = new Meeting();
        Meeting meeting2 = new Meeting();
        Meeting meeting3 = new Meeting();

        // MM/dd/yyyy hh:mm a
        meeting1.setTime("11/05/2023 02:00 pm");
        meeting2.setTime("12/12/2024 01:00 pm");
        meeting3.setTime("11/05/2023 01:00 am");

        defaultMeetingList.add(meeting1);
        defaultMeetingList.add(meeting2);
        defaultMeetingList.add(meeting3);

        correctMeetingList.add(meeting3);
        correctMeetingList.add(meeting1);
        correctMeetingList.add(meeting2);

        Collections.sort(defaultMeetingList, new MeetingComparator()); // sort meetingList by time

        assertArrayEquals(defaultMeetingList.toArray(), correctMeetingList.toArray());
    }

}
