package com.bogdanrotaru.cvscreeningapp.model;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class CVTest {

    private static CV cv;

    @BeforeAll
    public static void initialize(){
        cv = new CV(null, Collections.emptyList(),Collections.emptyList());
        cv.setInfo(new PersonalInfo());
    }

    @Test
    public void testCV(){
        assertNotNull(cv.getInfo());

        assertTrue(cv.getEducation().isEmpty());
        assertTrue(cv.getExperience().isEmpty());

        assertThrows(NullPointerException.class, () -> cv.getScore());

        cv.setEducation(null);
        cv.setExperience(new ArrayList<>());
        assertNull(cv.getEducation());
        assertTrue(cv.getExperience().isEmpty());

    }
}
