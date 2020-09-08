package guiAdmin;

import java.io.IOException;


import client.Action;
import client.Client;
import clientApplication.UserRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AdminLoginController {
	public String address = "localhost";
	@FXML
	public TextField user;
	@FXML
	public PasswordField password;
	@FXML
	private Button loginButton;
	@FXML
	private Button signupButton;
	
//	public LoginController(String address) {
//		this.address = address;
//	}
	
	public void initialize() {
	}
	/**
	 * change scene to MainView when push loginButtion
	 * @throws IOException 
	 */
	public void loginButtonPushed(ActionEvent event) throws IOException {
//		UserRequest u = new UserRequest(user.getText(), password.getText());
//		Client c = new Client(address);
//		if (c.userGetIn(Action.ADMINLOGIN, u) == true) {
			Parent mainViewParent;
			try {
				mainViewParent = FXMLLoader.load(getClass().getResource("AdminMainView.fxml"));
				Scene mainViewScene = new Scene(mainViewParent);
				// get stage information
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(mainViewScene);
				window.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
//		}else {
//			Alert a = new Alert(AlertType.ERROR);
//			a.setContentText("Incorrect login information");
//			a.show();
//		}
//		}
	}
}
