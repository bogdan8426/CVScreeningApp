package CVScreening.tests.model;

import CVScreening.exceptions.CVFilesReadException;
import CVScreening.exceptions.CVGeneratorException;
import CVScreening.model.helpers.TimeInterval;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class TimeIntervalTest {

    private static TimeInterval actualYear;
    private static TimeInterval actualParsedYear;
    private static TimeInterval wrongParsedYear;

    @BeforeAll
    public static void initialize() throws CVFilesReadException {
        actualYear = TimeInterval.between(LocalDate.of(2019, Month.OCTOBER,12 ),
                LocalDate.of(2020, Month.OCTOBER, 12));

        actualParsedYear = TimeInterval.parse("From 2019-10-12 to 2020-10-12 (1 years and 0 months)");
        wrongParsedYear = TimeInterval.parse("From 2019-10-12 to 2020-10-12 (24 years and 0 months)");
    }

    @Test
    public void betweenTest(){
        assertNotNull(actualYear);
        assertEquals(Period.ofYears(1), actualYear.getPeriod());
        assertEquals(Period.between(LocalDate.of(2019, Month.OCTOBER,12 ),
                LocalDate.of(2020, Month.OCTOBER, 12)), actualYear.getPeriod());

        assertEquals(LocalDate.of(2019, Month.OCTOBER,12 ), actualYear.getStartDate());
        assertEquals( LocalDate.of(2020, Month.OCTOBER, 12), actualYear.getEndDate());
    }

    @Test
    public void parseTest() {
        assertNotNull(actualParsedYear);
        assertEquals(Period.ofYears(1), actualParsedYear.getPeriod());

        assertNotNull(wrongParsedYear);
        assertEquals(Period.ofYears(1), wrongParsedYear.getPeriod());
//DateTimeParseException
        Throwable throwable = assertThrows(CVGeneratorException.class, () ->TimeInterval.parse("From 20129-10-12 to 2020-10-12 (1 years and 0 months)"));
        assertTrue(throwable.getMessage().contains("Could not parse"));

    }

}
