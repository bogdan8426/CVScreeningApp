package com.bogdanrotaru.cvscreeningapp.model;

import com.bogdanrotaru.cvscreeningapp.model.helpers.Sex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalInfoTest {

    private static PersonalInfo info;

    @BeforeAll
    public static void initialize(){
        info = new PersonalInfo("1","23","432", null, null);
        info.setBirthday(LocalDate.now());
        info.setSex(Sex.MALE);
    }

    @Test
    public void testCV(){
        assertNotNull(info.getFirstName());
        assertNotNull(info.getLastName());

        assertEquals(info.getBirthday(), LocalDate.now());
        assertNotEquals(info.getSex(), Sex.FEMALE);

    }
}
