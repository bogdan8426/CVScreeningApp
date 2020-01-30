package CVScreening.tests.generator;

import CVScreening.CVGenerator.root.InputReader;
import CVScreening.model.helpers.Domain;
import CVScreening.model.helpers.Sex;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InputReaderTest {

    private InputReader reader = new InputReader();

    @Test
    public void companiesTest() throws FileNotFoundException {
        List<String> list = reader.getCompanies();

        assertNotNull(list);
        assertEquals("JPMorgan Chase", list.get(1));
        assertTrue(list.size() > 350);
    }

    @Test
    public void jobsTest() throws FileNotFoundException {
        Map<Domain, Map<String, String>> jobs = reader.getJobs();

        assertNotNull(jobs);
        assertTrue(jobs.containsKey(Domain.FINANCE));
        assertTrue(jobs.containsKey(Domain.ENGINEERING));
        assertTrue(jobs.containsKey(Domain.MEDICAL));

        assertTrue(jobs.get(Domain.LAW).containsKey("Judge"));
        assertEquals(" In this job, people drive or operate equipment and machinery to" +
                        " build and repair roads, buildings, and more. Apprenticeships and training " +
                        "programs are available, but it's also very common to learn on the job.",
                jobs.get(Domain.CONSTRUCTION).get("Construction Equipment Operator"));

    }

    @Test
    public void namesTest() throws FileNotFoundException{
        Map<String, Sex> names = reader.getNames();

        assertNotNull(names);
        assertTrue(names.size() > 1400);
        assertTrue(names.containsKey("Aaliyah,Cooper"));
        assertTrue(names.containsValue(Sex.FEMALE));
        assertTrue(names.containsValue(Sex.MALE));
        assertTrue(names.containsValue(Sex.NOT_SPECIFIED));
    }

    @Test
    public void universitiesTest() throws FileNotFoundException {
        List<String> list = reader.getUniversities();

        assertNotNull(list);
        assertEquals("Harvard University", list.get(2));
        assertEquals("Pennsylvania State University", list.get(92));
        assertEquals(100, list.size());
    }
}
