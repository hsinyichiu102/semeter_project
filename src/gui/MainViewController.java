package gui;

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

public class MainViewController {
	@FXML
	private Button logoutButton;
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
	 * @param event
	 */
	public void logoutButtonPushed(ActionEvent event) {
		Parent loginParent;
		try {
			loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
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
	 * button to show addNews page
	 * @param event
	 */
	public void addNewsButtonPushed(ActionEvent event) {
		Parent addNewsParent;
		try {
			addNewsParent = FXMLLoader.load(getClass().getResource("AddNews.fxml"));
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
	 * button to show NewsList page
	 * @param event
	 */
	public void browseNewsButtonPushed(ActionEvent event) {
		Parent browseNewsParent;
		try {
			browseNewsParent = FXMLLoader.load(getClass().getResource("NewsList.fxml"));
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
