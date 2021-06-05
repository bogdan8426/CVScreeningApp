package com.bogdanrotaru.cvscreeningapp.generator;

import com.bogdanrotaru.cvscreeningapp.CVGenerator.CVGenerator;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVGeneratorException;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CVGeneratorTest {

    private CVGenerator generator = new CVGenerator();

    @Test
    public void loadTest() throws CVFilesReadException {
        ObservableList<CV> cvs = generator.loadCVs();

        assertNotNull(cvs);
        assertNotNull(cvs.get(4));
        assertEquals(1460, cvs.size());
    }

    @Test
    public void generateTest() throws CVGeneratorException {
        List<CV> cvs = generator.generateRandomFiles();

        assertNotNull(cvs);
        assertNotNull(cvs.get(4));
        assertEquals(1460, cvs.size());
    }

}
