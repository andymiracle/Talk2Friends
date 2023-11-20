package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

public class SignUpUnitTest {

    @Test
    public void emptyFieldsTest()
    {
        String new_email = "email";
        String new_password = "password";

        assertEquals(SignUpActivity.atLeastOneFieldEmpty(new_email, new_password), false);

        new_email = "email";
        new_password = "";

        assertEquals(SignUpActivity.atLeastOneFieldEmpty(new_email, new_password), true);

        new_email = "";
        new_password = "password";

        assertEquals(SignUpActivity.atLeastOneFieldEmpty(new_email, new_password), true);
    }

}
