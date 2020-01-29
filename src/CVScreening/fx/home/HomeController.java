package CVScreening.fx.home;

import CVScreening.CVGenerator.CVGenerator;
import CVScreening.exceptions.CVFilesReadException;
import CVScreening.exceptions.CVGeneratorException;
import CVScreening.fx.selection.SelectionController;
import CVScreening.model.CV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;

public class HomeController {

    @FXML
    private GridPane gridPane;
    @FXML
    private BorderPane mainBorderPane;

    private CVGenerator generator = new CVGenerator();

    @FXML
    public void loadCVfiles() {
        ProgressIndicator indicator = loadingAnimation(0);
        gridPane.getChildren().get(1).setDisable(true);
        gridPane.getChildren().get(0).setDisable(true);

        Stage stage = (Stage) mainBorderPane.getScene().getWindow();

        Task<ObservableList<CV>> task = new Task<ObservableList<CV>>() {
            @Override
            public ObservableList<CV> call() throws CVFilesReadException {
                return generator.loadCVs();
            }
        };

        task.setOnSucceeded(e -> {
            indicator.setProgress(1.0f);
            SelectionController.setCvs(task.getValue());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../selection/selection.fxml"));
                Scene scene = new Scene(root, 900, 550);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).severe("Could not load selection screen!");
            }
        });

        new Thread(task).start();

    }

    @FXML
    public void generateFiles() {
        ProgressIndicator indicator = loadingAnimation(1);
        gridPane.getChildren().get(1).setDisable(true);
        gridPane.getChildren().get(0).setDisable(true);

        Task<ObservableList<CV>> task = new Task<ObservableList<CV>>() {
            @Override
            public ObservableList<CV> call() throws CVGeneratorException {
                return FXCollections.observableArrayList(generator.generateRandomFiles());
            }
        };

        task.setOnSucceeded(e -> {
            indicator.setProgress(1.0f);
            GridPane.setHalignment(indicator, HPos.CENTER);
            loadCVfiles();
        });

        new Thread(task).start();

    }

    private ProgressIndicator loadingAnimation(int columnIndex) {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(20, 20);
        gridPane.add(progressIndicator, columnIndex, 2);
        GridPane.setHalignment(progressIndicator, HPos.CENTER);
        return progressIndicator;
    }
}
