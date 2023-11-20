package com.example.talk2friends;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;

public class EmailUnitTests {
    String sender_email = "talk2friendssender@gmail.com";
    String sender_password = "vcrpuobwoeguntnf";
    String host = "smtp.gmail.com";

    ArrayList<Boolean> isEmailSentList;

    @Test
    public void validSignUpEmailTest() {
        String address1 = "tester1@usc.edu";
        String address2 = "tester2@usc.edu";
        String address3 = "tester3@usc.edu";

        isEmailSentList = new ArrayList<>();
        isEmailSentList.add(sendEmailForSignUp(address1));
        isEmailSentList.add(sendEmailForSignUp(address2));
        isEmailSentList.add(sendEmailForSignUp(address3));

        assertEquals(true, isEmailSentList.get(0));
        assertEquals(true, isEmailSentList.get(1));
        assertEquals(true, isEmailSentList.get(2));
    }

    @Test
    public void invalidSignUpEmailTest() {
        String address1 = "a**/asd2@";
        String address2 = "bob@@usc.edu";
        String address3 = ".....@gmail.com";

        isEmailSentList = new ArrayList<>();
        isEmailSentList.add(sendEmailForSignUp(address1));
        isEmailSentList.add(sendEmailForSignUp(address2));
        isEmailSentList.add(sendEmailForSignUp(address3));

        assertEquals(false, isEmailSentList.get(0));
        assertEquals(false, isEmailSentList.get(1));
        assertEquals(false, isEmailSentList.get(2));
    }

    @Test
    public void validInvitationEmailTest() {
        String address1 = "tester1@usc.edu";
        String address2 = "tester2@usc.edu";
        String address3 = "tester3@gmail.com";

        isEmailSentList = new ArrayList<>();
        isEmailSentList.add(sendEmailForInvitation(address1));
        isEmailSentList.add(sendEmailForInvitation(address2));
        isEmailSentList.add(sendEmailForInvitation(address3));

        assertEquals(true, isEmailSentList.get(0));
        assertEquals(true, isEmailSentList.get(1));
        assertEquals(true, isEmailSentList.get(2));
    }

    @Test
    public void invalidInvitationEmailTest() {
        String address1 = "a**/asd2@";
        String address2 = "bob@@usc.edu";
        String address3 = ".....@gmail.com";

        isEmailSentList = new ArrayList<>();
        isEmailSentList.add(sendEmailForInvitation(address1));
        isEmailSentList.add(sendEmailForInvitation(address2));
        isEmailSentList.add(sendEmailForInvitation(address3));

        assertEquals(false, isEmailSentList.get(0));
        assertEquals(false, isEmailSentList.get(1));
        assertEquals(false, isEmailSentList.get(2));
    }

    private Boolean sendEmailForSignUp(String address) {
        return SignUpActivity.sendEmail(address);
    }

    private Boolean sendEmailForInvitation(String address) {
        return InviteFriendsActivity.sendEmail(address);
    }
}
