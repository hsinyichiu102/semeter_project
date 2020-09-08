package guiAdmin;

import java.io.IOException;

import java.sql.Date;

import client.Action;
import client.Client;
import clientApplication.Message;
import clientApplication.News;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminAddNewsController {
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
	@FXML
	private Message selectedMessage;
	@FXML
	private Label urlLabel;

	
	public void initialize() {

	}

	public void initData(Message message) {
//		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//		String strDate = df.format(selectedNews.getReceivedDate());
		selectedMessage = message;
		urlLabel.setText(selectedMessage.getNewsURL());
	}


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
	
	public void addNewsButtonPushed(ActionEvent event) throws IOException {
		News newNews = new News(headlineField.getText(),publisherField.getText(),urlLabel.getText(),
				java.sql.Date.valueOf(dateField.getValue()),contentField.getText(),0,0);
		Client c = new Client("localhost");
		if (c.addNews(Action.ADDNEWS, newNews) == true) {
//		NewsListController.news.add(newNews);
			Parent addNewsParent;
			try {
				addNewsParent = FXMLLoader.load(getClass().getResource("AdminAdded.fxml"));
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
