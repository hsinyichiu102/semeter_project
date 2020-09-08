package guiAdmin;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAddedController extends Application {

    /**
     * button to go back to mainView page
     * @param event
     */
    public void mainViewButtonPushed(ActionEvent event) {
        Parent mainViewParent;
        try {
            mainViewParent = FXMLLoader.load(getClass().getResource("AdminMainView.fxml"));
            Scene mainViewScene = new Scene(mainViewParent);
            // gets the Stage information
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(mainViewScene);
            window.show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
