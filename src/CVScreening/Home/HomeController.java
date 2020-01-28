package CVScreening.Home;

import CVScreening.CVGenerator.CVGenerator;
import CVScreening.DataModel.CV;
import CVScreening.Selection.SelectionController;
import CVScreening.exceptions.CVFilesReadException;
import CVScreening.exceptions.CVFilesWriteException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    public GridPane gridPane;
    @FXML
    public BorderPane mainBorderPane;

    private CVGenerator generator = new CVGenerator();

    @FXML
    public void initialize(){
        Image applicationIcon = new Image(getClass().getResourceAsStream("../logo.png"));
        ImageView logo = new ImageView(applicationIcon);

    }

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
                Parent root = FXMLLoader.load(getClass().getResource("../Selection/selection.fxml"));
                Scene scene = new Scene(root, 900, 550);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException ex) {
                //TODO: throw exception
                ex.printStackTrace();
            }

        });

        new Thread(task).start();

    }

    public void generateFiles() {
        ProgressIndicator indicator = loadingAnimation(1);
        gridPane.getChildren().get(1).setDisable(true);
        gridPane.getChildren().get(0).setDisable(true);

        Task<ObservableList<CV>> task = new Task<ObservableList<CV>>() {
            @Override
            public ObservableList<CV> call() throws CVFilesWriteException {
                ObservableList<CV> cvObservableList = FXCollections.observableArrayList(generator.generateRandomFiles());
                return cvObservableList;
            }
        };

        task.setOnSucceeded(e -> {
            indicator.setProgress(1.0f);
            GridPane.setHalignment(indicator, HPos.CENTER);
            loadCVfiles();});

        new Thread(task).start();

    }

    private ProgressIndicator loadingAnimation(int columnIndex){
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(20,20);
        gridPane.add(progressIndicator,columnIndex,2);
        GridPane.setHalignment(progressIndicator, HPos.CENTER);
        return progressIndicator;
    }
}
