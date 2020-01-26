package CVScreening;

import CVScreening.CVGenerator.CVGenerator;
import CVScreening.DataModel.CV;
import CVScreening.DataModel.Domain;
import CVScreening.DataModel.JobDescription;
import CVScreening.DataModel.JobLevel;
import javafx.application.Application;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Home/home.fxml"));
        primaryStage.setTitle("CV Screening Application");
        primaryStage.setScene(new Scene(root, 900,300));
        primaryStage.centerOnScreen();
        Image applicationIcon = new Image(getClass().getResourceAsStream("logo.png"));
        primaryStage.getIcons().add(applicationIcon);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
