package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SortInterestsUnit {
    ArrayList<User> testingList;
    ArrayList<User> correctSortedList;
    ArrayList<Integer> correctScoreList;

    @Before
    public void testing() {
        Singleton.getInstance().setUsername("Mike");

        HashMap<String, Boolean> interestMap = new HashMap<>();
        interestMap.put("Anime", true);
        interestMap.put("Art", true);
        interestMap.put("Hiking", true);
        interestMap.put("Cooking", true);
        interestMap.put("Video Games", true);
        interestMap.put("Travelling", true);
        interestMap.put("Music", true);
        interestMap.put("Dancing", true);
        interestMap.put("Sports", true);
        interestMap.put("Animals", true);
        Singleton.getInstance().setInterests(interestMap);

        testingList = new ArrayList<>();
        User u1 = new User();
        u1.setUsername("Alice");

        HashMap<String, Boolean> interestMap1 = new HashMap<>();
        interestMap1.put("Anime", true);
        interestMap1.put("Art", true);
        interestMap1.put("Hiking", true);
        interestMap1.put("Cooking", true);
        interestMap1.put("Video Games", false);
        interestMap1.put("Travelling", false);
        interestMap1.put("Music", false);
        interestMap1.put("Dancing", false);
        interestMap1.put("Sports", false);
        interestMap1.put("Animals", false);
        u1.setInterests(interestMap1);

        User u2 = new User();
        u2.setUsername("Bob");

        HashMap<String, Boolean> interestMap2 = new HashMap<>();
        interestMap2.put("Anime", true);
        interestMap2.put("Art", true);
        interestMap2.put("Hiking", true);
        interestMap2.put("Cooking", true);
        interestMap2.put("Video Games", true);
        interestMap2.put("Travelling", true);
        interestMap2.put("Music", false);
        interestMap2.put("Dancing", false);
        interestMap2.put("Sports", false);
        interestMap2.put("Animals", false);
        u2.setInterests(interestMap2);

        User u3 = new User();
        u3.setUsername("Chris");

        HashMap<String, Boolean> interestMap3 = new HashMap<>();
        interestMap3.put("Anime", true);
        interestMap3.put("Art", true);
        interestMap3.put("Hiking", true);
        interestMap3.put("Cooking", true);
        interestMap3.put("Video Games", true);
        interestMap3.put("Travelling", true);
        interestMap3.put("Music", true);
        interestMap3.put("Dancing", true);
        interestMap3.put("Sports", false);
        interestMap3.put("Animals", false);
        u3.setInterests(interestMap3);

        testingList.add(u1);
        testingList.add(u2);
        testingList.add(u3);

        correctScoreList = new ArrayList<>();
        correctScoreList.add(4);
        correctScoreList.add(6);
        correctScoreList.add(8);

        correctSortedList = new ArrayList<>();
        correctSortedList.add(u3);
        correctSortedList.add(u2);
        correctSortedList.add(u1);
    }

    @Test
    public void testGetScore() {
        UserComparator comp = new UserComparator();
        ArrayList<Integer> testingScoreList = new ArrayList<>();

        for (int i = 0; i < testingList.size(); ++i) {
            int score = comp.getScore(testingList.get(i));
            testingScoreList.add(score);
        }

        assertEquals(correctScoreList.get(0), testingScoreList.get(0));
        assertEquals(correctScoreList.get(1), testingScoreList.get(1));
        assertEquals(correctScoreList.get(2), testingScoreList.get(2));
    }

    @Test
    public void testSortedUser() {
        System.out.println(testingList.size());
        Collections.sort(testingList, new UserComparator());
        assertEquals(correctSortedList.get(0).getUsername(), testingList.get(0).getUsername());
        assertEquals(correctSortedList.get(1).getUsername(), testingList.get(1).getUsername());
        assertEquals(correctSortedList.get(2).getUsername(), testingList.get(2).getUsername());
    }
}
