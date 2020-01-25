package CVScreening.Home;

import CVScreening.CVGenerator.CVGenerator;
import CVScreening.Selection.SelectionController;
import CVScreening.DataModel.CV;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

import static javafx.scene.layout.BackgroundPosition.CENTER;
import static javafx.scene.layout.BackgroundRepeat.NO_REPEAT;
import static javafx.scene.layout.BackgroundRepeat.REPEAT;
import static javafx.scene.layout.BackgroundSize.DEFAULT;

public class HomeController {

    @FXML
    public GridPane gridPane;
    @FXML
    public BorderPane mainBorderPane;

    @FXML
    public void initialize(){

    }

    @FXML
    public void loadCVfiles() {
        ProgressIndicator indicator = loadingAnimation(0);
        mainBorderPane.getChildren().get(0).setDisable(true);
        Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        Task<ObservableList<CV>> task = new Task<ObservableList<CV>>() {
            @Override
            public ObservableList<CV> call() {
                try {
                    return CVGenerator.loadCVs();
                } catch (FileNotFoundException e) {
                    //TODO: throw exception
                    throw new RuntimeException();
                }
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
            public ObservableList<CV> call() {
                return CVGenerator.generateRandomFiles();
            }
        };

        task.setOnSucceeded(e -> {

            indicator.setProgress(1.0f);
            gridPane.add(indicator,1,1);
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
