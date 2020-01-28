package CVScreening.Results;

import CVScreening.DataModel.CV;
import CVScreening.email.Email;
import CVScreening.email.EmailException;
import CVScreening.email.EmailService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ResultsController {
    @FXML
    public VBox mainVBox;
    @FXML
    public HBox centerContent;
    @FXML
    public Label positionSearched;
    @FXML
    public Label noCVs;
    @FXML
    public Label noMatches;
    @FXML
    private List<CV> cvs;

    public void initialize(List<CV> cvs, String selectedPosition) {
        this.cvs = cvs;
        positionSearched.setText("Searched for a " + selectedPosition +"...");
        displayResults();
    }

    private void displayResults() {
        VBox nameColumn = new VBox();
        VBox scoreColumn = new VBox();
        VBox buttonsColumn = new VBox();
        nameColumn.setSpacing(18.0);
        scoreColumn.setSpacing(18.0);
        buttonsColumn.setSpacing(10.0);
        for (int i = 0; i < 10; i++) {
            CV cv = cvs.get(i);
            Label name = new Label((i + 1) + ". " + cv.getInfo().getFirstName() + " " + cv.getInfo().getLastName());
            name.setOpacity(0.67);
            name.setOnMouseClicked(showCVDialog(cv));

            ProgressBar score = new ProgressBar(cv.getScore());
            Label scoreText = new Label((int) (cv.getScore() * 100) + "%");
            scoreText.setOpacity(0.67);
            HBox scoreBox = new HBox(score, scoreText);
            Button messageButton = new Button("Message");
            messageButton.setOnAction(showMessage(cv));

            nameColumn.getChildren().add(name);
            scoreColumn.getChildren().add(scoreBox);
            buttonsColumn.getChildren().add(messageButton);
        }

        centerContent.setSpacing(40);
        centerContent.getChildren().addAll(nameColumn, scoreColumn, buttonsColumn);

        noCVs.setText("Scanned "+cvs.size() + " CV files.");
        int count=0;
        for(CV cv: cvs){
            if(cv.getScore()>=0.5){
                count++;
            }
        }
        noMatches.setText("Found " + count + " candidates with >50% match.");
    }

    private EventHandler<ActionEvent> showMessage(CV cv) {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(cv.getInfo().getFirstName() + " messaged");
                try {
                    new EmailService().sendNotificationEmail(new Email().setFrom("Recruiter").setTo(cv).setTitle("Hi,").setBody("Come work for us!"));

                } catch (EmailException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    private EventHandler<? super MouseEvent> showCVDialog(CV cv) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //TODO: open the xml file!
                System.out.println(cv);
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
