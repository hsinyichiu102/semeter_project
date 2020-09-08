package gui;

import java.io.IOException;
import java.sql.Date;

import clientApplication.News;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NewsListController {
	@FXML
	private Button mainViewButton;
	@FXML
	private TextField filterField;
	//configure the table
	@FXML
	private TableView<News> tableView;
	@FXML
	private TableColumn<News,String> headlineColumn;
	@FXML
	private TableColumn<News,String> publisherColumn;
	@FXML
	private TableColumn<News,String> urlColumn;
	@FXML
	private TableColumn<News,java.sql.Date> dateColumn;
	@FXML
	private TableColumn<News,String> contentColumn;
	
	public static ObservableList<News> news = FXCollections.observableArrayList();
	
	
	public void initialize() {
		//set up columns in the table
		headlineColumn.setCellValueFactory(new PropertyValueFactory<News,String>("headline"));
		publisherColumn.setCellValueFactory(new PropertyValueFactory<News,String>("publisher"));
		urlColumn.setCellValueFactory(new PropertyValueFactory<News,String>("url"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<News,java.sql.Date>("receivedDate"));
		contentColumn.setCellValueFactory(new PropertyValueFactory<News,String>("content"));
		tableView.setItems(getNews());
        
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		FilteredList<News> filteredData = new FilteredList<>(getNews(),b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(news -> {
				// If filter text is empty, display all persons.

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();

				if (news.getHeadline().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches headline.
				} else if (news.getPublisher().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches publisher.
				} else if (news.getUrl().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches url.
				} else if (news.getReceivedDate().toString().indexOf(lowerCaseFilter) != -1)
					return true; // Filter matches date.
				else
					return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<News> sortedData = new SortedList<>(filteredData);

		// 4. Bind the SortedList comparator to the TableView comparator.
		// Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableView.comparatorProperty());

		// 5. Add sorted (and filtered) data to the table.
		tableView.setItems(sortedData);
	}
	
	
	@SuppressWarnings("deprecation")
	public ObservableList<News> getNews(){

		return news;
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
	 * pass the selected news object to detailed view
	 * @param event
	 */
	public void detailedNewsButtonPushed(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("SearchNews.fxml"));
			Parent mainViewParent = loader.load();
			Scene mainViewScene = new Scene(mainViewParent);
			
			// access SearchNewsController and call a method
			SearchNewsController controller = loader.getController();
			controller.initData(tableView.getSelectionModel().getSelectedItem());
			
			// gets the Stage information
			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(mainViewScene);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
