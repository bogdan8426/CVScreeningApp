package CVScreening.Results;

import CVScreening.DataModel.CV;
import CVScreening.Selection.SelectionController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ResultsController {
    @FXML
    public VBox mainVBox;
    @FXML
    public HBox centerContent;

    private List<CV> cvs;

    public void initialize(List<CV> cvs) {
        this.cvs = cvs;

        displayResults();
    }

    private void displayResults() {
        VBox nameColumn = new VBox();
        VBox scoreColumn = new VBox();
        VBox buttonsColumn = new VBox();
        nameColumn.setSpacing(18.0);
        scoreColumn.setSpacing(18.0);
        buttonsColumn.setSpacing(10.0);
        for(int i =0; i<10; i++){
            CV cv = cvs.get(i);
            Label name = new Label((i+1)+". "+ cv.getInfo().getFirstName() +" " +cv.getInfo().getLastName());
            name.setOpacity(0.67);
            name.setOnMouseClicked(showCVDialog(cv));

            ProgressBar score = new ProgressBar(cv.getScore());
            Label scoreText = new Label((int) (cv.getScore()*100) + "%");
            scoreText.setOpacity(0.67);
            HBox scoreBox = new HBox(score, scoreText);
            Button messageButton = new Button("Message");
            messageButton.setOnAction(showMessage(cv));

            nameColumn.getChildren().add(name);
            scoreColumn.getChildren().add(scoreBox);
            buttonsColumn.getChildren().add(messageButton);
        }

        centerContent.setSpacing(40);
        centerContent.getChildren().addAll(nameColumn,scoreColumn,buttonsColumn);
    }

    private EventHandler<ActionEvent> showMessage(CV cv) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(cv.getInfo().getFirstName() + " messaged");
            }
        };
    }

    private EventHandler<? super MouseEvent> showCVDialog(CV cv) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(cv.getInfo().getFirstName() + " clicked");
            }
        };
    }

    public void showSelectionScreen(ActionEvent actionEvent) {
        Stage stage = (Stage) mainVBox.getScene().getWindow();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Selection/selection.fxml"));
            Scene scene = new Scene(root, 900, 550);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }


    }

    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }


}
