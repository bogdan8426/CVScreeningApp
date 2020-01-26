package CVScreening.JobDescription;

import CVScreening.DataModel.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class JobDescriptionController {
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

    public void processResults(Domain selectedDomain, String selectedPosition) {
        JobDescription jobDescription = new JobDescription(selectedDomain,selectedPosition,
                jobLevelChoice.getValue(), (int) minStudyYearsSlider.getValue());
        jobDescription.setHasLeadershipExperience(leadBackgroundCheckBox.isSelected());
        jobDescription.setJobsChanged((int) jobsChangedSlider.getValue());

        if(!universityChoice.getSelectionModel().isEmpty()){
            jobDescription.setSpecificUniversity(universityChoice.getValue());
        }

        HashMap<String, Double> scores = new HashMap<>();
        for(CV cv: cvs){
            double score = cv.getScore(jobDescription);
            if(score > 0.0){
                scores.put(cv.getInfo().getFirstName() + " " + cv.getInfo().getLastName(), score);
            }
        }
        sortByValue(scores);
//        HashMap<String, Double> finalScores = sortByValue(scores);
//        scores.keySet().forEach(key -> System.out.println(key +": " + new DecimalFormat("#.##").format(finalScores.get(key)*100) + "% match"  ));
    }

    // function to sort hashmap by values
    public static HashMap<String, Double> sortByValue(HashMap<String, Double> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list =
                new LinkedList<Map.Entry<String, Double> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> aa : list) {
            System.out.println(aa.getKey() +": " + new DecimalFormat("#.##").format(aa.getValue()*100) + "% match"  );
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
