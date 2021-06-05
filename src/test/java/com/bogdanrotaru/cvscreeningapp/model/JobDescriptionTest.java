package com.bogdanrotaru.cvscreeningapp.model;

import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.JobLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JobDescriptionTest {

    private static JobDescription jobDescription;

    @BeforeAll
    public static void init(){
        jobDescription = new JobDescription(Domain.ENGINEERING,
                "Software engineer",
                JobLevel.JUNIOR,
                3);
    }

    @AfterEach
    public void afterEach(){
        jobDescription.setHasLeadershipExperience(true);
        jobDescription.setJobsChanged(5);
        jobDescription.setSpecificUniversity("UPB");
    }

    @Test
    public void jobDescriptionTest(){
        assertEquals(Domain.ENGINEERING, jobDescription.getDomain());
        assertEquals("Software engineer", jobDescription.getPosition());
        assertNotEquals(JobLevel.MIDDLE, jobDescription.getJobLevel());
        assertEquals(3, jobDescription.getMinimumStudyYears());

        assertFalse(jobDescription.getHasLeadershipExperience());
        assertEquals(-1, jobDescription.getJobsChanged());
        assertNull(jobDescription.getSpecificUniversity());
    }

    @Test
    public void optionalsTest(){
        assertTrue(jobDescription.getHasLeadershipExperience());
        assertEquals(5, jobDescription.getJobsChanged());
        assertNotNull(jobDescription.getSpecificUniversity());
        assertEquals("UPB", jobDescription.getSpecificUniversity());
    }


    @Test
    public void testHasOptionals(){
        assertTrue(jobDescription.hasOptionals());

        jobDescription.setHasLeadershipExperience(false);
        assertTrue(jobDescription.hasOptionals());

        jobDescription.setSpecificUniversity(null);
        assertTrue(jobDescription.hasOptionals());

        jobDescription.setJobsChanged(-400);
        assertFalse(jobDescription.hasOptionals());
    }
}
