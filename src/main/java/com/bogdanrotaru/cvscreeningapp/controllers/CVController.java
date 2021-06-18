package com.bogdanrotaru.cvscreeningapp.controllers;


import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.exceptions.CVGeneratorException;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.JobDescription;
import com.bogdanrotaru.cvscreeningapp.model.dto.CvDao;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.JobLevel;
import com.bogdanrotaru.cvscreeningapp.services.GeneratorService;
import com.bogdanrotaru.cvscreeningapp.services.ScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CVController {

    private final ScoreService scoreService;
    private final GeneratorService generatorService;

    public CVController(ScoreService scoreService, GeneratorService generatorService) {
        this.scoreService = scoreService;
        this.generatorService = generatorService;
    }

    @GetMapping("/generate")
    public void generateResumes() throws CVGeneratorException {
        generatorService.generateCVs();
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

    @GetMapping("/status")
    public Map<String, Object> getRepositoryStatus(){
        return scoreService.getRepositoryStatus();
    }

    @GetMapping
    public List<CV> getAllCVs(){
        return scoreService.findAll();
    }

    @GetMapping("/{id}/message")
    public String computeScores(@RequestBody JobDescription jobDescription,
                                @PathVariable String id){
        return scoreService.formulateMessage(id, jobDescription);
    }
}
