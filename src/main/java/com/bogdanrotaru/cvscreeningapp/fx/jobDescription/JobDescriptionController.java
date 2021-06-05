package com.bogdanrotaru.cvscreeningapp.fx.jobDescription;

import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.JobDescription;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.JobLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobDescriptionController {
    @FXML
    private TextArea jobBrief;
    @FXML
    private ChoiceBox<JobLevel> jobLevelChoice;
    @FXML
    private Slider minStudyYearsSlider;
    @FXML
    private CheckBox leadBackgroundCheckBox;
    @FXML
    private Slider jobsChangedSlider;
    @FXML
    private ChoiceBox<String> universityChoice;

    private ObservableList<CV> cvs;

    @FXML
    public void initialize(ObservableList<CV> cvs) {
        this.cvs = cvs;
        jobLevelChoice.setItems(FXCollections.observableArrayList(JobLevel.values()));
        jobLevelChoice.setValue(JobLevel.JUNIOR);

        Set<String> universities = new HashSet<>();
        cvs.forEach(cv -> cv.getEducation()
                .forEach(education -> universities.add(education.getUniversityName()))
        );

        universityChoice.setItems(FXCollections.observableArrayList(universities));

    }

    public List<CV> processResults(Domain selectedDomain, String selectedPosition) {
        JobDescription jobDescription = new JobDescription(selectedDomain, selectedPosition,
                jobLevelChoice.getValue(), (int) minStudyYearsSlider.getValue());
        jobDescription.setHasLeadershipExperience(leadBackgroundCheckBox.isSelected());
        jobDescription.setJobsChanged((int) jobsChangedSlider.getValue());

        if (!universityChoice.getSelectionModel().isEmpty()) {
            jobDescription.setSpecificUniversity(universityChoice.getValue());
        }

        cvs.forEach(cv -> cv.computeScore(jobDescription));

        return new SortedList<>(cvs, Comparator.comparingDouble(cv -> -cv.getScore()));

    }

    public void setJobLevelChoice(ChoiceBox<JobLevel> jobLevelChoice) {
        this.jobLevelChoice = jobLevelChoice;
    }

    public void setMinStudyYearsSlider(Slider minStudyYearsSlider) {
        this.minStudyYearsSlider = minStudyYearsSlider;
    }

    public void setLeadBackgroundCheckBox(CheckBox leadBackgroundCheckBox) {
        this.leadBackgroundCheckBox = leadBackgroundCheckBox;
    }

    public void setJobsChangedSlider(Slider jobsChangedSlider) {
        this.jobsChangedSlider = jobsChangedSlider;
    }

    public void setUniversityChoice(ChoiceBox<String> universityChoice) {
        this.universityChoice = universityChoice;
    }
}
