package guiAdmin;

import client.Action;
import client.Client;
import clientApplication.Message;
import clientApplication.News;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;

public class ServerMessageController extends Application {
@FXML
	private Button mainViewButton;
	//configure the table
	@FXML
	private TableView<Message> tableView;
	@FXML
	private TableColumn<Message,String> messageID;
	@FXML
	private TableColumn<Message,String> newsURL;
	@FXML
	private TableColumn<Message,String> serverMessage;

	public static ObservableList<Message> messagesList = FXCollections.observableArrayList();


	public void initialize() throws IOException {
		//set up columns in the table
		messageID.setCellValueFactory(new PropertyValueFactory<Message,String>("id"));
		newsURL.setCellValueFactory(new PropertyValueFactory<Message,String>("newsURL"));
		serverMessage.setCellValueFactory(new PropertyValueFactory<Message,String>("message"));
		tableView.setItems(getMessagesList());
	}


	@SuppressWarnings("deprecation")
	public ObservableList<Message> getMessagesList() throws IOException {
		Message m = new Message();
		Client c = new Client("localhost");
		ArrayList<String> list = c.getMessage(Action.GETSERVERMSG,"");
		int i =0;
		while (i<list.size()){
			m.setId(list.get(i));
			m.setNewsURL(list.get(i+1));
			m.setMessage(list.get(i+2));
			messagesList.add(m);
			i=i+3;
		}

//		news.add(new News("Boris has a micropenis","BhamNews","www.google.com",new Date(System.currentTimeMillis()),"content1"));
//		news.add(new News("Boris has a nanopenis","BhamNews","www.google.com",new Date(20,03,02),"content2"));
//		news.add(new News("Boris has no penis","BhamNews","www.google.com",new Date(20,03,03),"content3"));
		return messagesList;
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

        /**
         * button to show addNews page
         * @param event
         */
        public void addNewsButtonPushed(javafx.event.ActionEvent event) {
            Parent addNewsParent;
            try {
                addNewsParent = FXMLLoader.load(getClass().getResource("AdminAddNews.fxml"));
                Scene addNewsScene = new Scene(addNewsParent);

                AdminAddNewsController addLink = new AdminAddNewsController();
				addLink.initData(tableView.getSelectionModel().getSelectedItem());
                // gets the Stage information
                Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(addNewsScene);
                window.show();
            } catch (IOException e) {
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
