package com.bogdanrotaru.cvscreeningapp.javaFX;

import com.bogdanrotaru.cvscreeningapp.exceptions.CVFilesReadException;
import com.bogdanrotaru.cvscreeningapp.fx.home.HomeController;
import com.bogdanrotaru.cvscreeningapp.fx.jobDescription.JobDescriptionController;
import com.bogdanrotaru.cvscreeningapp.fx.results.ResultsController;
import com.bogdanrotaru.cvscreeningapp.fx.selection.SelectionController;
import com.bogdanrotaru.cvscreeningapp.model.CV;
import com.bogdanrotaru.cvscreeningapp.model.Education;
import com.bogdanrotaru.cvscreeningapp.model.Experience;
import com.bogdanrotaru.cvscreeningapp.model.PersonalInfo;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Domain;
import com.bogdanrotaru.cvscreeningapp.model.helpers.JobLevel;
import com.bogdanrotaru.cvscreeningapp.model.helpers.Sex;
import com.bogdanrotaru.cvscreeningapp.model.helpers.TimeInterval;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

public class TestGUI {

    private static HomeController homeController = new HomeController();
    private static SelectionController selectionController = new SelectionController();
    private static JobDescriptionController jobDescriptionController = new JobDescriptionController();
    private static ResultsController resultsController = new ResultsController();
    private static CV cv1;
    private static CV cv2;

    @BeforeAll
    public static void init() throws InterruptedException, CVFilesReadException {
        // Starting the fxThread
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JFXPanel(); // initializes JavaFX environment
                latch.countDown();
            }
        });
        latch.await();

        // Mocks
        GridPane gridPane = new GridPane();
        gridPane.getChildren().add(new Button());
        gridPane.getChildren().add(new Button());
        homeController.setGridPane(gridPane);

        ListView<Domain> listView = new ListView<>();
        listView.setItems(FXCollections.observableArrayList(Domain.values()));
        listView.getSelectionModel().selectFirst();
        selectionController.setDomainsListView(listView);
        selectionController.setCenterTitle(new Label());
        selectionController.setCenterContent(new HBox());
        ListView<String> stringListView = new ListView<>();
        stringListView.setItems(FXCollections.observableArrayList(Collections.singletonList("string")));
        stringListView.getSelectionModel().selectFirst();
        selectionController.setPositionsListView(stringListView);
        selectionController.setHireButton(new Button());

        jobDescriptionController.setJobLevelChoice(
                new ChoiceBox<>(FXCollections.observableArrayList(JobLevel.SENIOR)));
        Slider slider = new Slider();
        slider.setValue(4.0);
        jobDescriptionController.setMinStudyYearsSlider(slider);
        jobDescriptionController.setLeadBackgroundCheckBox(new CheckBox());
        jobDescriptionController.setJobsChangedSlider(slider);
        jobDescriptionController.setUniversityChoice(new ChoiceBox<>());

        Education education = new Education(TimeInterval.parse("From 2019-08-12 to 2020-10-12 (1 years and 2 months)"),
                "name",
                Domain.SCIENCE);
        Experience experience = new Experience(TimeInterval.parse("From 2019-02-12 to 2020-10-12 (1 years and 8 months)"),
                "pos","des","com",
                Domain.CONSTRUCTION);
        Experience experience2 = new Experience(TimeInterval.parse("From 2015-05-12 to 2019-10-12 (3 years and 5 months)"),
                "pos2","des2","com2",
                Domain.SCIENCE);

        PersonalInfo info = new PersonalInfo("1","23","432", null, null);
        info.setBirthday(LocalDate.now());
        info.setSex(Sex.MALE);
        cv1 = new CV(info, Collections.singletonList(education), Arrays.asList(experience,experience2));
        info.setFirstName("asdfs");
        info.setLastName("asdfs");
        cv2 = new CV(info, Collections.singletonList(education), Collections.singletonList(experience));

        resultsController.setCenterContent(new HBox());
        resultsController.setNoCVs(new Label());
        resultsController.setNoMatches(new Label());
        resultsController.setPositionSearched(new Label());
    }

    @Test
    public void guiTest() {
        homeController.loadCVfiles();
        ObservableList<CV> cvs = FXCollections.observableArrayList();
        for(int i =0; i<10; i++){
            cvs.add(cv1);
            cvs.add(cv2);
        }
        SelectionController.setCvs(cvs);
        selectionController.domainSelected();
        jobDescriptionController.initialize(cvs);
        List<CV> result = jobDescriptionController.processResults(Domain.CONSTRUCTION, "Ironworker");
        resultsController.initialize(result, "Ironworker");


        assertNotNull(result);
        assertEquals(20, result.size());
        assertEquals("asdfs", result.get(0).getInfo().getFirstName());

    }

}
