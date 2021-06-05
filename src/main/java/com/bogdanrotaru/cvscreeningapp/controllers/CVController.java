package com.bogdanrotaru.cvscreeningapp.controllers;


import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.JobDescription;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.JobLevel;
import com.bogdanrotaru.cvscreeningapp.services.GeneratorService;
import com.bogdanrotaru.cvscreeningapp.services.ScoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CVController {

    private final ScoreService scoreService;
    private final GeneratorService generatorService;

    public CVController(ScoreService scoreService, GeneratorService generatorService) {
        this.scoreService = scoreService;
        this.generatorService = generatorService;
    }

    @GetMapping("/generate")
    public void generateResumes(Integer countPerDomain){
        System.out.println("Generating ... " + countPerDomain);
    }

    @GetMapping("/jobDescription")
    public JobDescription getDummyJobDescription(){
        return new JobDescription(Domain.ENGINEERING,
                "Software Engineer",
                JobLevel.MIDDLE,
                4
                );
    }

    @PutMapping("/computeScores")
    public List<CV> computeScores(@RequestBody JobDescription jobDescription){
        return scoreService.computeScores(jobDescription);
    }
}
