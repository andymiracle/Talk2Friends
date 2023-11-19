package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

public class HashUnitTest {

    @Test
    public void hashPassWordTest()
    {
        String correctHash1 = "D468484BE41D9D8AAFEA9F9D765CAE357BB6E2F9C4F332B75555DD6CD578BC6E";
        String correctHash2 = "A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3";
        String correctHash3 = "8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92";

        String password1 = "gabrieldo";
        String password2 = "123";
        String password3 = "123456";

        String hashedPassword1 = LoginActivity.hashPassword(password1);
        String hashedPassword2 = LoginActivity.hashPassword(password2);
        String hashedPassword3 = LoginActivity.hashPassword(password3);

        assertEquals(hashedPassword1, correctHash1);
        assertEquals(hashedPassword2, correctHash2);
        assertEquals(hashedPassword3, correctHash3);
    }
}
