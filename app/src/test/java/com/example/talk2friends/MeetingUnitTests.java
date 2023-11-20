package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

public class MeetingUnitTests {

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

    @Test
    public void longMeetingNameTest()
    {
        // check function correctly checks for meeting names over 16 characters

        String sixteenCharacters = "0123456789123456";

        assertEquals(CreateMeetingActivity.longMeetingName(sixteenCharacters), false);

        String seventeenCharacters = "01234567891234567";

        assertEquals(CreateMeetingActivity.longMeetingName(seventeenCharacters), true);
    }

    @Test
    public void dateFormatTest()
    {
        String correctDate = "12/12/2024 01:00 pm";

        assertEquals(CreateMeetingActivity.validDateFormat(correctDate), true);

        String notADate = "string";

        assertEquals(CreateMeetingActivity.validDateFormat(notADate), false);

        String invalidMonth = "13/01/2023 12:30 pm";

        assertEquals(CreateMeetingActivity.validDateFormat(invalidMonth), false);

        String invalidDay = "12/32/2023 12:30 pm";

        assertEquals(CreateMeetingActivity.validDateFormat(invalidDay), false);

        String invalidTime = "12/31/2023 13:30 pm";

        assertEquals(CreateMeetingActivity.validDateFormat(invalidTime), false);

        String missingAMPM = "13/01/2023 12:30";

        assertEquals(CreateMeetingActivity.validDateFormat(missingAMPM), false);
    }

    @Test
    public void emptyFieldsTest()
    {
        String conversationTopic = "topic";
        String time = "12/12/2024 01:00 pm";
        String location = "location";
        String meetingName = "meeting name";

        assertEquals(CreateMeetingActivity.atLeastOneFieldEmpty(conversationTopic, time, location, meetingName), false);

        conversationTopic = "";
        time = "12/12/2024 01:00 pm";
        location = "location";
        meetingName = "meeting name";

        assertEquals(CreateMeetingActivity.atLeastOneFieldEmpty(conversationTopic, time, location, meetingName), true);

        conversationTopic = "topic";
        time = "";
        location = "location";
        meetingName = "meeting name";

        assertEquals(CreateMeetingActivity.atLeastOneFieldEmpty(conversationTopic, time, location, meetingName), true);

        conversationTopic = "topic";
        time = "12/12/2024 01:00 pm";
        location = "";
        meetingName = "meeting name";

        assertEquals(CreateMeetingActivity.atLeastOneFieldEmpty(conversationTopic, time, location, meetingName), true);

        conversationTopic = "topic";
        time = "12/12/2024 01:00 pm";
        location = "location";
        meetingName = "";

        assertEquals(CreateMeetingActivity.atLeastOneFieldEmpty(conversationTopic, time, location, meetingName), true);
    }
}
