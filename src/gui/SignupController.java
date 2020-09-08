package gui;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController {
	@FXML
	public TextField username;
	@FXML
	public PasswordField password;
	@FXML
	private Button loginPageButton;
	@FXML
	private Button signUpButton;
	
	public void initialize() {
	}
	
	/**
	 * method to go back to login page
	 * @param event
	 */
	public void loginPageButtonPushed(ActionEvent event) {
		Parent loginParent;
		try {
			loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene loginScene = new Scene(loginParent);
	        // gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	        window.setScene(loginScene);
	        window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * method to register new users
	 * @param event
	 * @throws IOException
	 */
	public void signUpButtonPushed(ActionEvent event) throws IOException {
		UserRequest u= new UserRequest(username.getText(),password.getText());
		Client c = new Client(LoginController.address);
		if(c.userGetIn(Action.SIGNUP,u) == true){
		Parent registeredParent;
		try {
			registeredParent = FXMLLoader.load(getClass().getResource("Registered.fxml"));
			Scene registeredScene = new Scene(registeredParent);
			// get stage information
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(registeredScene);
			window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Please use another set of username and password");
			a.show();
		}
	}
	

}
