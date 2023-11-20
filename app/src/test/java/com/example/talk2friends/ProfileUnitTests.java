package com.example.talk2friends;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileUnitTests {

    @Test
    public void validIntegerAgeTest()
    {
        String validInteger = "21";
        assertEquals(EditProfileActivity.validIntegerChecker(0, validInteger), true);

        String nonValidInteger = "21blahblah";
        assertEquals(EditProfileActivity.validIntegerChecker(0, nonValidInteger), false);

        String tooLargeInteger = "222222222222222222222222222222222";
        assertEquals(EditProfileActivity.validIntegerChecker(0, tooLargeInteger), false);
    }

    @Test
    public void NegativeIntegerAgeTest()
    {
        int negativeInteger = -1;
        assertEquals(EditProfileActivity.negativeIntegerChecker(negativeInteger), true);

        int zero = 0;
        assertEquals(EditProfileActivity.negativeIntegerChecker(zero), false);

        int positiveInteger = 1;
        assertEquals(EditProfileActivity.negativeIntegerChecker(positiveInteger), false);
    }

    @Test
    public void emptyFieldsTest()
    {
        String name = "name";
        String age = "age";
        String affiliation = "affiliation";
        String interest1 = "interest1";
        String interest2 = "interest2";
        String interest3 = "interest3";
        String interest4 = "interest4";
        String interest5 = "interest5";
        String interest6 = "interest6";
        String interest7 = "interest7";
        String interest8 = "interest8";
        String interest9 = "interest9";
        String interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), false);

        name = "";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "";
        interest9 = "interest9";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "";
        interest10 = "interest10";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);

        name = "name";
        age = "age";
        affiliation = "affiliation";
        interest1 = "interest1";
        interest2 = "interest2";
        interest3 = "interest3";
        interest4 = "interest4";
        interest5 = "interest5";
        interest6 = "interest6";
        interest7 = "interest7";
        interest8 = "interest8";
        interest9 = "interest9";
        interest10 = "";

        assertEquals(EditProfileActivity.atLeastOneFieldEmpty(name, age, affiliation, interest1, interest2, interest3, interest4, interest5, interest6, interest7, interest8, interest9, interest10), true);
    }

}
