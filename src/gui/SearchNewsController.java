package gui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import client.Action;
import client.Client;
import clientApplication.GetVote;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SearchNewsController {
	private int countReal;
	private int countFake;
	private News selectedNews;
	@FXML
	private Label headlineLabel;
	@FXML
	private Label publisherLabel;
	@FXML
	private Label urlLabel;
	@FXML
	private Label dateLabel;
	@FXML
	private Label contentLabel;
	@FXML
	private Button realButton;
	@FXML
	private Button fakeButton;
	@FXML
	private Label realCountLabel;
	@FXML
	private Label fakeCountLabel;
	@FXML
	private Circle light;
	
	/**
	 * button to go to page with all news in a table
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
	
	/**
	 * getting all attributes of the selected news
	 * @param news
	 */
	public void initData(News news) {

		selectedNews = news;
		headlineLabel.setText(selectedNews.getHeadline());
		publisherLabel.setText(selectedNews.getPublisher());
		urlLabel.setText(selectedNews.getUrl());
		dateLabel.setText(selectedNews.getReceivedDate().toString());
		contentLabel.setWrapText(true);
		contentLabel.setText(selectedNews.getContent());
		realCountLabel.setText(String.valueOf(selectedNews.getRealVote()));
		fakeCountLabel.setText(String.valueOf(selectedNews.getFakeVote()));
		
	}
	
	/**
	 * method for pressing real button
	 * @param event
	 * @throws IOException
	 */
	public void realButtonPushed(ActionEvent event) throws IOException {
		GetVote vote = new GetVote(null,"true",null,null);
		Client c = new Client(LoginController.address);
		if(c.vote(Action.GETVOTE,vote) == true){
		selectedNews.setRealVote(selectedNews.getRealVote()+1);
		changeCircleColor(selectedNews.getRealVote(),selectedNews.getFakeVote());
		Parent realButtonParent;
		try {
			realButtonParent = FXMLLoader.load(getClass().getResource("Voted.fxml"));
			Scene realButtonScene = new Scene(realButtonParent);
	        // gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	        window.setScene(realButtonScene);
	        window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText("invalid action");
		a.show();
	}
	}

	/**
	 * method for pressing fake button
	 * @param event
	 * @throws IOException
	 */
	public void fakeButtonPushed(ActionEvent event) throws IOException {
		GetVote vote = new GetVote(null,null,"false",null);
		Client c = new Client(LoginController.address);
		if(c.vote(Action.GETVOTE,vote) == true){
		selectedNews.setFakeVote(selectedNews.getFakeVote()+1);
		changeCircleColor(selectedNews.getRealVote(),selectedNews.getFakeVote());
		Parent fakeButtonParent;
		try {
			fakeButtonParent = FXMLLoader.load(getClass().getResource("Voted.fxml"));
			Scene fakeButtonScene = new Scene(fakeButtonParent);
	        // gets the Stage information
	        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
	        window.setScene(fakeButtonScene);
	        window.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else {
		Alert a = new Alert(AlertType.ERROR);
		a.setContentText("invalid action");
		a.show();
	}
	}
	
	/**
	 * change circle color base on vote numbers
	 * @param real
	 * @param fake
	 */
	public void changeCircleColor(int real, int fake) {
		if (real == 0 && fake == 0) {
			light.setFill(Color.WHITE);
		}else if(real > fake) {
			light.setFill(Color.GREEN);
		}else if(fake > real) {
			light.setFill(Color.RED);
		}else {
			light.setFill(Color.YELLOW);
		}
		
	}
	
	

}
