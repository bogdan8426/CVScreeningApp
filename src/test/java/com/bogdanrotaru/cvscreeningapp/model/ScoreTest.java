package com.bogdanrotaru.cvscreeningapp.model;

import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.JobLevel;
import com.bogdanrotaru.cvscreeningapp.model.helpers.TimeInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreTest {

    private static CV cv;
    private JobDescription jobDescription;

    @BeforeAll
    public static void init() throws CVFilesReadException {
        Education education = new Education(TimeInterval.parse("From 2019-08-12 to 2020-10-12 (1 years and 2 months)"),
                "name",
                Domain.SCIENCE);
        Experience experience = new Experience(TimeInterval.parse("From 2019-02-12 to 2020-10-12 (1 years and 8 months)"),
                "pos","des","com",
                Domain.SCIENCE);
        Experience experience2 = new Experience(TimeInterval.parse("From 2015-05-12 to 2019-10-12 (3 years and 5 months)"),
                "pos2","des2","com2",
                Domain.SCIENCE);

        cv = new CV(null, Collections.singletonList(education), Arrays.asList(experience,experience2));
    }

    @BeforeEach
    public void beforeEach(){
        jobDescription = new JobDescription(Domain.ENGINEERING,
                "Software engineer",
                JobLevel.JUNIOR,
                3);
    }

    @Test
    public void requirementScore(){
        cv.computeScore(jobDescription);

        assertEquals(0.0, cv.getScore());

        jobDescription = new JobDescription(Domain.SCIENCE,
                "Software engineer",
                JobLevel.JUNIOR,
                3);
        cv.computeScore(jobDescription);

        assertEquals(0.5, cv.getScore());

        jobDescription = new JobDescription(Domain.SCIENCE,
                "pos2",
                JobLevel.JUNIOR,
                3);
        cv.computeScore(jobDescription);

        assertEquals(0.8, cv.getScore());

        jobDescription = new JobDescription(Domain.SCIENCE,
                "pos2",
                JobLevel.JUNIOR,
                1);
        cv.computeScore(jobDescription);

        assertEquals(1.0, cv.getScore());

    }

    @Test
    public void testOptionals(){
        cv.computeScore(jobDescription);

        assertEquals(0.0, cv.getScore());

        jobDescription = new JobDescription(Domain.SCIENCE,
                "pos2",
                JobLevel.JUNIOR,
                1);
        jobDescription.setHasLeadershipExperience(true);
        cv.computeScore(jobDescription);

        assertEquals(0.9, cv.getScore());

        cv.getExperience().get(0).setPosition("hr director");
        cv.computeScore(jobDescription);

        assertEquals(1.0, cv.getScore());

        jobDescription.setSpecificUniversity("MIT");
        cv.computeScore(jobDescription);

        DecimalFormat df2 = new DecimalFormat("#.##");
        assertEquals("0.95", df2.format(cv.getScore()));

        jobDescription.setJobsChanged(4);
        cv.computeScore(jobDescription);
        assertEquals("0.97", df2.format(cv.getScore()));
    }
}
