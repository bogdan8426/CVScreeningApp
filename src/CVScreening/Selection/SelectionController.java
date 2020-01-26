package CVScreening.Selection;

import CVScreening.DataModel.CV;
import CVScreening.DataModel.Domain;
import CVScreening.DataModel.Experience;

import CVScreening.JobDescription.JobDescriptionController;
import CVScreening.Results.ResultsController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SelectionController {

    @FXML
    public ListView<Domain> domainsListView;

    @FXML
    public AnchorPane centerAnchorPane;

    private static ObservableList<CV> cvs;
    @FXML
    public VBox mainVBox;
    private ListView<String> positionsListView = new ListView<>();
    private Button hireButton = new Button("Hire!");
    @FXML
    public HBox centerContent;
    @FXML
    public Label centerTitle;

    public static void setCvs(ObservableList<CV> cvs) {
        SelectionController.cvs = cvs;
    }

    public static ObservableList<CV> getCvs() {
        return cvs;
    }

    @FXML
    public void initialize(){
        domainsListView.setItems(FXCollections.observableArrayList(Domain.values()));
    }


    @FXML
    public void handleExit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void domainSelected(MouseEvent mouseEvent) {
        Domain selectedDomain = domainsListView.getSelectionModel().getSelectedItem();
        centerTitle.setText("Positions");
        centerContent.getChildren().clear();
        centerContent.spacingProperty().set(10.0d);



        Set<String> positions = new HashSet<>();
        for(CV cv: cvs){
            List<Experience> experienceList = cv.getExperience();
            for(Experience experience: experienceList){
                if(experience.getDomain().equals(selectedDomain)){
                    positions.add(experience.getPosition());
                }
            }
        }

        positionsListView.setMinWidth(400);
        positionsListView.setItems(FXCollections.observableArrayList(positions));
        centerContent.getChildren().add(positionsListView);
        positionsListView.getSelectionModel().selectFirst();

        hireButton.setOnAction(this::showJobDescriptionDialog);

        hireButton.setAlignment(Pos.CENTER);
        hireButton.setMinSize(80,50);
        centerContent.getChildren().add(hireButton);


    }


    private void showJobDescriptionDialog(ActionEvent event) {
        Domain selectedDomain = domainsListView.getSelectionModel().getSelectedItem();
        String selectedPosition = positionsListView.getSelectionModel().getSelectedItem();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        dialog.setTitle("Enter job description for " + selectedPosition);
        dialog.setHeaderText("Use this dialog to create a new job description item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../JobDescription/jobDescription.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
            JobDescriptionController controller = fxmlLoader.getController();
            controller.initialize(cvs);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                List<CV> results = controller.processResults(selectedDomain, selectedPosition);
                Stage stage = (Stage) mainVBox.getScene().getWindow();

                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../Results/results.fxml"));
                    Parent root = loader.load();
                    ResultsController resultsController = loader.getController();
                    resultsController.initialize(results);
                    Scene scene = new Scene(root, 900, 550);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                } catch (IOException e) {
                    //TODO
                    e.printStackTrace();
                }
            }
        } catch(IOException e){
            //TODO
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }



    }
}
