package CVScreening.JobDescription;

import CVScreening.DataModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobDescriptionController {
    @FXML
    public TextArea jobBrief;
    @FXML
    public ChoiceBox<JobLevel> jobLevelChoice;
    @FXML
    public Slider minStudyYearsSlider;

    @FXML
    public CheckBox leadBackgroundCheckBox;
    @FXML
    public Slider jobsChangedSlider;
    @FXML
    public ChoiceBox<String> universityChoice;
    @FXML
    public DialogPane mainDialogPane;


    private ObservableList<CV> cvs;

    @FXML
    public void initialize(ObservableList<CV> cvs){
        this.cvs = cvs;
        jobLevelChoice.setItems(FXCollections.observableArrayList(JobLevel.values()));
        jobLevelChoice.setValue(JobLevel.JUNIOR);

        Set<String> universities = new HashSet<>();
        for(CV cv: cvs){
            for(Education education: cv.getEducation()){
                universities.add(education.getUniversityName());
            }
        }
        universityChoice.setItems(FXCollections.observableArrayList(universities));

    }

    public List<CV> processResults(Domain selectedDomain, String selectedPosition) {
        JobDescription jobDescription = new JobDescription(selectedDomain,selectedPosition,
                jobLevelChoice.getValue(), (int) minStudyYearsSlider.getValue());
        jobDescription.setHasLeadershipExperience(leadBackgroundCheckBox.isSelected());
        jobDescription.setJobsChanged((int) jobsChangedSlider.getValue());

        if(!universityChoice.getSelectionModel().isEmpty()){
            jobDescription.setSpecificUniversity(universityChoice.getValue());
        }

        for(CV cv: cvs){
            cv.computeScore(jobDescription);
        }
        return new SortedList<>(cvs, Comparator.comparingDouble(cv -> -cv.getScore()));

}

}
