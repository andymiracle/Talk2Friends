package com.example.talk2friends;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TimeSort {
    // https://stackoverflow.com/questions/14451976/how-to-sort-date-which-is-in-string-format-in-java
    // function to sort an arrayList of strings in time format
    // maybe we can edit this function to sort the meetings which may have time as 1 of its things or something?

    public void timeSort(ArrayList<String> timeStrings) {
        Collections.sort(timeStrings, new Comparator<String>() {

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            @Override
            public int compare(String o1, String o2)
            {
                try {
                    return dateFormat.parse(o1).compareTo(dateFormat.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
}
