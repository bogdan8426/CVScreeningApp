package com.bogdanrotaru.cvscreeningapp.services;

import com.bogdanrotaru.cvscreeningapp.CVGenerator.CVGenerator;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVGeneratorException;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.repositories.CVRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeneratorService {

    private final CVGenerator cvGenerator;
    private final CVRepository cvRepository;

    public GeneratorService(CVRepository cvRepository) {
        this.cvGenerator = new CVGenerator();
        this.cvRepository = cvRepository;
    }

    public void generateCVs(int count) throws CVGeneratorException {
        List<CV> cvs = cvGenerator.generateRandomFiles(count);
        cvRepository.saveAll(cvs);
    }
}
