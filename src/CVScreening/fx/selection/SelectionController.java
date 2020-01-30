package CVScreening.fx.selection;

import CVScreening.fx.jobDescription.JobDescriptionController;
import CVScreening.fx.results.ResultsController;
import CVScreening.model.CV;
import CVScreening.model.helpers.Domain;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

public class SelectionController {

    @FXML
    private ListView<Domain> domainsListView;
    @FXML
    private VBox mainVBox;
    @FXML
    private HBox centerContent;
    @FXML
    private Label centerTitle;

    private static ObservableList<CV> cvs;
    private ListView<String> positionsListView;
    private Button hireButton;
    private static final Logger log = Logger.getLogger(SelectionController.class.getName());

    @FXML
    public void initialize() {
        domainsListView.setItems(FXCollections.observableArrayList(Domain.values()));
    }

    public static void setCvs(ObservableList<CV> cvs) {
        SelectionController.cvs = cvs;
    }

    public static ObservableList<CV> getCvs(){
        return cvs;
    }

    @FXML
    public void domainSelected() {
        Domain selectedDomain = domainsListView.getSelectionModel().getSelectedItem();
        centerTitle.setText("Positions");
        centerContent.getChildren().clear();
        centerContent.spacingProperty().set(10.0d);

        Set<String> positions = new HashSet<>();
        cvs.forEach(cv -> cv.getExperience()
                .stream()
                .filter(experience -> experience.getDomain().equals(selectedDomain))
                .forEach(experience -> positions.add(experience.getPosition()))
        );

        positionsListView = new ListView<>();
        positionsListView.setMinWidth(400);
        positionsListView.setItems(FXCollections.observableArrayList(positions));
        centerContent.getChildren().add(positionsListView);
        positionsListView.getSelectionModel().selectFirst();

        hireButton = new Button("Hire!");
        hireButton.setOnAction(this::showJobDescriptionDialog);

        hireButton.setAlignment(Pos.CENTER);
        hireButton.setMinSize(80, 50);
        centerContent.getChildren().add(hireButton);
    }

    @FXML
    public void showJobDescriptionDialog(ActionEvent event) {
        Domain selectedDomain = domainsListView.getSelectionModel().getSelectedItem();
        String selectedPosition = positionsListView.getSelectionModel().getSelectedItem();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainVBox.getScene().getWindow());
        dialog.setTitle("Enter job description for " + selectedPosition);
        dialog.setHeaderText("Use this dialog to create a new job description item");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("../JobDescription/jobDescription.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
            JobDescriptionController controller = fxmlLoader.getController();
            controller.initialize(cvs);

            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                List<CV> results = controller.processResults(selectedDomain, selectedPosition);
                Stage stage = (Stage) mainVBox.getScene().getWindow();


                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("../Results/results.fxml"));
                    Parent root = loader.load();
                    ResultsController resultsController = loader.getController();
                    resultsController.initialize(results, selectedPosition);
                    Scene scene = new Scene(root, 900, 550);
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.setOnCloseRequest(ev -> resultsController.handleExit());
                    stage.show();
                } catch (IOException e) {
                    log.severe("Couldn't load the results page!");
                }
            }
        } catch (IOException e) {
            log.severe("Couldn't load the job description dialog!");
        }
    }

    @FXML
    public void handleExit() {
        Platform.exit();
    }

    public void setDomainsListView(ListView<Domain> domainsListView) {
        this.domainsListView = domainsListView;
    }

    public void setMainVBox(VBox mainVBox) {
        this.mainVBox = mainVBox;
    }

    public void setCenterContent(HBox centerContent) {
        this.centerContent = centerContent;
    }

    public void setCenterTitle(Label centerTitle) {
        this.centerTitle = centerTitle;
    }

    public void setPositionsListView(ListView<String> positionsListView) {
        this.positionsListView = positionsListView;
    }

    public void setHireButton(Button hireButton) {
        this.hireButton = hireButton;
    }
}
