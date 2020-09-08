package guiAdmin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

/**
 * the main start method to start the admin system
 */

public class AdminMain extends Application {
	public void start(Stage stage)throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	

}
