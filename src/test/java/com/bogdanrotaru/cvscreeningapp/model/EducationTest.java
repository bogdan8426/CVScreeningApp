package com.bogdanrotaru.cvscreeningapp.model;


import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.TimeInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EducationTest {

    private static Education education;

    @BeforeAll
    public static void initialize() throws CVFilesReadException {
        education = new Education(TimeInterval.parse("From 2019-10-12 to 2020-10-12 (1 years and 0 months)"),
                "name",
                Domain.SCIENCE);
    }

    @Test
    public void educationTest(){
        assertNotNull(education.getTimeInterval());
        assertNotNull(education.getUniversityName());

        assertEquals(education.getDomain(), Domain.SCIENCE);

        education.setDomain(Domain.ENGINEERING);
        assertEquals(education.getDomain(), Domain.ENGINEERING);

        education.setTimeInterval(null);
        assertNull(education.getTimeInterval());

        education.setUniversityName("123");
        assertEquals(education.getUniversityName(),"123");

    }
}
