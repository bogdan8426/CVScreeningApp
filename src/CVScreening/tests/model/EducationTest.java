package CVScreening.tests.model;

import CVScreening.exceptions.CVFilesReadException;
import CVScreening.model.CV;
import CVScreening.model.Education;
import CVScreening.model.PersonalInfo;
import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.TimeInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;

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
