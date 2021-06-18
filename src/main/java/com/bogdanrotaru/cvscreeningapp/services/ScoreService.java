package com.bogdanrotaru.cvscreeningapp.services;


import com.bogdanrotaru.cvscreeningapp.mappers.CvMapper;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.JobDescription;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.repositories.CVRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.Long.parseLong;
import static java.util.stream.Collectors.toList;

@Service
public class ScoreService {

    private final CVRepository cvRepository;

    public ScoreService(CVRepository cvRepository) {
        this.cvRepository = cvRepository;
    }

    public List<CV> computeScores(JobDescription jobDescription) {
        return cvRepository.findAll().stream()
                .map(CvMapper::map)
                .map(cv -> {
                    cv.computeScore(jobDescription);
                    return cv;
                }).collect(toList());
    }

    public Map<String, Object> getRepositoryStatus() {
        return Map.of(
                "Total resumes: ", cvRepository.count(),
                "First CV found: ", cvRepository.findAll().stream().findFirst()
        );
    }

    public List<CV> findAll() {
        return cvRepository.findAll().stream().map(CvMapper::map).collect(toList());
    }

    public String formulateMessage(String cvId, JobDescription jobDescription) {
        CV cv = cvRepository.findById(cvId).map(CvMapper::map).orElseThrow(() -> new RuntimeException("Not found."));

        StringBuilder stringBuilder = new StringBuilder("Hello " + cv.getInfo().getFirstName() + ",");

        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append(System.getProperty("line.separator"));

        stringBuilder.append("I see you have experience in ").append(jobDescription.getDomain());
        if (jobDescription.getHasLeadershipExperience() && cv.getExperience().stream().anyMatch(experience -> isLeadership(experience.getPosition()))) {
            stringBuilder.append(" and some leadership experience as well.");
        } else {
            stringBuilder.append(".");
        }

        stringBuilder.append(System.getProperty("line.separator"));
        stringBuilder.append("I think we can be a good fit considering we are looking for a ").append(jobDescription.getJobLevel())
                .append(" ").append(jobDescription.getPosition()).append(". ");

        stringBuilder.append("How do you feel about this opportunity?");

        return stringBuilder.toString();

    }

    private boolean isLeadership(String position) {
        List<String> dictionary = Arrays.asList("manager", "management", "administrator", "leader", "lead", "director",
                "coordinator", "supervisor", "captain");

        String[] words = position.split(" ");
        for (String word : words) {
            if (word.length() > 2) {
                if (dictionary.contains(word.toLowerCase())) {
                    return true;
                }
            }
        }
        return false;
    }
}
