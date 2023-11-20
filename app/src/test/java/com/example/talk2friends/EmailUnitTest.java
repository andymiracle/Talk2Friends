package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class EmailUnitTest {
    String random_code = "INVALID";
    String sender_email = "talk2friendssender@gmail.com";
    String sender_password = "vcrpuobwoeguntnf";
    String host = "smtp.gmail.com";

    ArrayList<Boolean> isEmailSentList;

    @Test
    public void validInvitationEmailTesting() {
        String address1 = "alice@usc.edu";
        String address2 = "bob@usc.edu";
        String address3 = "mike@usc.edu";

        isEmailSentList = new ArrayList<>();
        isEmailSentList.add(sendEmail(address1));
        isEmailSentList.add(sendEmail(address2));
        isEmailSentList.add(sendEmail(address3));

        assertEquals(true, isEmailSentList.get(0));
        assertEquals(true, isEmailSentList.get(1));
        assertEquals(true, isEmailSentList.get(2));
    }

    @Test
    public void invalidInvitationEmailTesting() {
        String address1 = "a**/asd2@";
        String address2 = "bob@@usc.edu";
        String address3 = ".....@gmail.com";

        isEmailSentList = new ArrayList<>();
        isEmailSentList.add(sendEmail(address1));
        isEmailSentList.add(sendEmail(address2));
        isEmailSentList.add(sendEmail(address3));

        assertEquals(false, isEmailSentList.get(0));
        assertEquals(false, isEmailSentList.get(1));
        assertEquals(false, isEmailSentList.get(2));
    }

    private Boolean sendEmail(String address) {
        return InviteFriendsActivity.sendEmail(host, sender_email, sender_password, address);
    }
}
