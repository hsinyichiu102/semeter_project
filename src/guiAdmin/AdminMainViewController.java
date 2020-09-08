package guiAdmin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * the second page when the admin login
 */

public class AdminMainViewController {
	@FXML
	private Button logoutButton;
	@FXML
	private Button linkSearch; 
	@FXML
	private Button addNewsButton;
	@FXML
	private Button browseNewsButton;
	@FXML
	private TextField linkField;
	
	public void initialize() {
	}
	
	/**
	 * button to logout
	 * @param event start the action
	 */
	public void logoutButtonPushed(ActionEvent event) {
		Parent loginParent;
		try {
			loginParent = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
			Scene loginScene = new Scene(loginParent);
	        // gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	        window.setScene(loginScene);
	        window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * button to show message from server
	 * @param event start the action
	 */
	public void messageFromServerButtonPushed(ActionEvent event) {
		Parent addNewsParent;
		try {
			addNewsParent = FXMLLoader.load(getClass().getResource("ServerMessage.fxml"));
			Scene addNewsScene = new Scene(addNewsParent);
	        // gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	        window.setScene(addNewsScene);
	        window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * button to show user Message
	 * @param event start the message
	 */
	public void userMessageButtonPushed(ActionEvent event) {
		Parent browseNewsParent;
		try {
			browseNewsParent = FXMLLoader.load(getClass().getResource("UserMessage.fxml"));
			Scene browseNewsScene = new Scene(browseNewsParent);
	        // gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	        window.setScene(browseNewsScene);
	        window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
