package com.bogdanrotaru.cvscreeningapp.services;


import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.JobDescription;
import com.bogdanrotaru.cvscreeningapp.repositories.CVRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    private final CVRepository cvRepository;

    public ScoreService(CVRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public List<CV> computeScores(JobDescription jobDescription) {
        return cvRepository.findAll().stream().map(cv -> {
            cv.computeScore(jobDescription);
            return cv;
        }).collect(Collectors.toList());
    }
}
