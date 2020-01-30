package CVScreening.fx.results;

import CVScreening.message.Message;
import CVScreening.message.MessageException;
import CVScreening.message.MessageService;
import CVScreening.model.CV;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ResultsController {
    @FXML
    private VBox mainVBox;
    @FXML
    private HBox centerContent;
    @FXML
    private Label positionSearched;
    @FXML
    private Label noCVs;
    @FXML
    private Label noMatches;
    @FXML
    private List<CV> cvs;

    private static final Logger log = Logger.getLogger(ResultsController.class.getName());
    private MessageService messageService = new MessageService();

    public void initialize(List<CV> cvs, String selectedPosition) {
        this.cvs = cvs;
        positionSearched.setText(" - Searched for a " + selectedPosition + "... \n");
        positionSearched.setMaxWidth(150.0);
        positionSearched.setWrapText(true);
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
            name.setOnMouseEntered(event -> name.setOpacity(1));
            name.setOnMouseExited(event -> name.setOpacity(0.67));

            ProgressBar score = new ProgressBar(cv.getScore());
            Label scoreText = new Label((int) (cv.getScore() * 100) + "%");
            scoreText.setOpacity(0.67);
            HBox scoreBox = new HBox(score, scoreText);
            Button messageButton = new Button("Message");
            messageButton.setOnAction(sendMessages(cv));

            nameColumn.getChildren().add(name);
            scoreColumn.getChildren().add(scoreBox);
            buttonsColumn.getChildren().add(messageButton);
        }

        centerContent.setSpacing(40);
        centerContent.getChildren().addAll(nameColumn, scoreColumn, buttonsColumn);

        noCVs.setText(" - Scanned " + cvs.size() + " CV files.\n");
        noCVs.setMaxWidth(150.0);
        noCVs.setWrapText(true);

        long count = cvs.stream().map(CV::getScore).filter(score -> score >= 0.5).count();
        noMatches.setText(" - Found " + count + " candidates with >50% match.\n");
        noMatches.setMaxWidth(150.0);
        noMatches.setWrapText(true);
    }

    private EventHandler<ActionEvent> sendMessages(CV cv) {
        return event -> {
            try {
                messageService.
                        sendNotification(new Message()
                                .setFrom("Recruiter")
                                .setTo(cv)
                                .setTitle("Hi, " + cv.getInfo().getFirstName() + "!")
                                .setBody("Your cv matches " + (int) (cv.getScore() * 100) + "% to our ideal candidate," +
                                        "\n\t\t" + "Would you like to work for our company?"));
            } catch (MessageException e) {
                log.warning("Message sending failed! ");
            }
        };
    }

    private EventHandler<? super MouseEvent> showCVDialog(CV cv) {
        return (EventHandler<MouseEvent>) event -> {
            if (!Desktop.isDesktopSupported()) {
                log.severe("Desktop is not supported");
                return;
            }

            String path = "C:\\Users\\Bogdan\\Desktop\\Facultate\\Java advanced\\CV Screening Application\\src\\CVScreening\\CVs\\"
                    + "CV_" + cv.getInfo().getFirstName() + "_" + cv.getInfo().getLastName() + ".xml";
            File file = new File(path);
            Desktop desktop = Desktop.getDesktop();
            if (file.exists()) {
                try {
                    desktop.open(file);
                } catch (IOException e) {
                    log.severe("Could not open cv file, please open manually.");
                }
            }
        };
    }

    @FXML
    public void showSelectionScreen() {
        Stage stage = (Stage) mainVBox.getScene().getWindow();
        messageService.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../selection/selection.fxml"));
            Scene scene = new Scene(root, 900, 550);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            log.severe("Could not load the selection screen!");
        }
    }

    @FXML
    public void handleExit() {
        messageService.close();
        Platform.exit();
    }

    public void setCenterContent(HBox centerContent) {
        this.centerContent = centerContent;
    }

    public void setPositionSearched(Label positionSearched) {
        this.positionSearched = positionSearched;
    }

    public void setNoCVs(Label noCVs) {
        this.noCVs = noCVs;
    }

    public void setNoMatches(Label noMatches) {
        this.noMatches = noMatches;
    }
}
