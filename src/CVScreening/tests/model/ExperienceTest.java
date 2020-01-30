package CVScreening.tests.model;

import CVScreening.exceptions.CVFilesReadException;
import CVScreening.model.Education;
import CVScreening.model.Experience;
import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.TimeInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExperienceTest {

    private static Experience experience;

    @BeforeAll
    public static void initialize() throws CVFilesReadException {
        experience = new Experience(TimeInterval.parse("From 2019-10-12 to 2020-10-12 (1 years and 0 months)"),
                "pos","des","com",
                Domain.SCIENCE);
    }

    @Test
    public void experienceTest(){
        assertNotNull(experience.getTimeInterval());
        assertNotNull(experience.getPosition());

        assertEquals(experience.getDomain(), Domain.SCIENCE);
        assertEquals(experience.getDescription(), "des");
        assertEquals(String.class, experience.getCompany().getClass());

        experience.setDomain(Domain.ENGINEERING);
        assertEquals(experience.getDomain(), Domain.ENGINEERING);

        experience.setTimeInterval(null);
        assertNull(experience.getTimeInterval());

        experience.setCompany("123");
        assertEquals(experience.getCompany(),"123");

    }
}
