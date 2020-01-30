package CVScreening.tests.model;

import CVScreening.model.helpers.JobLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JobLevelTest {

    @Test
    void getMinimumYearsTest() {
        int actual = JobLevel.JUNIOR.getMinimumYears();
        assertEquals(0,actual);

        actual = JobLevel.MIDDLE.getMinimumYears();
        assertEquals(1,actual);

        actual = JobLevel.SENIOR.getMinimumYears();
        assertEquals(5,actual);

    }
}