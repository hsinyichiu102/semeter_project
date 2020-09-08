package gui;

import java.io.IOException;

import java.sql.Date;

import client.Action;
import client.Client;
import clientApplication.News;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewsController {
	@FXML
	private Button mainViewButton;
	@FXML
	private TextField headlineField;
	@FXML
	private TextField linkField;
	@FXML
	private TextField contentField;
	@FXML
	private TextField publisherField;
	@FXML
	private DatePicker dateField;
	
	public void initialize() {
	}
	
	/**
	 * button to go back to mainView page
	 * @param event
	 */
	public void mainViewButtonPushed(ActionEvent event) {
		Parent mainViewParent;
		try {
			mainViewParent = FXMLLoader.load(getClass().getResource("MainView.fxml"));
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
	
	/**
	 * method to go to the add news page
	 * @param event
	 * @throws IOException
	 */
	public void addNewsButtonPushed(ActionEvent event) throws IOException {
		News newNews = new News(headlineField.getText(),publisherField.getText(),linkField.getText(),java.sql.Date.valueOf(dateField.getValue()),contentField.getText(), 0,0);
		Client c = new Client(LoginController.address);
		if (c.addNews(Action.ADDNEWS, newNews) == true) {
		NewsListController.news.add(newNews);
			Parent addNewsParent;
			try {
				addNewsParent = FXMLLoader.load(getClass().getResource("Added.fxml"));
				Scene addNewsScene = new Scene(addNewsParent);
				// gets the Stage information
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(addNewsScene);
				window.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Alert a = new Alert(AlertType.ERROR);
			a.setContentText("Please check if input is correct");
			a.show();
		}

	}
	
	

}
