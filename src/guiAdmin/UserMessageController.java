package guiAdmin;

import client.Action;
import client.Client;
import clientApplication.Message;
import gui.SearchNewsController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.util.ArrayList;

public class UserMessageController extends Application {

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
        newsURL.setCellValueFactory(new PropertyValueFactory<Message,String>("userName"));
        serverMessage.setCellValueFactory(new PropertyValueFactory<Message,String>("message"));
        tableView.setItems(getMessagesList());
    }

    /**
     * get all the message list for server in db
     * @return messageList to show all the message
     * @throws IOException
     */

    @SuppressWarnings("deprecation")
    public ObservableList<Message> getMessagesList() throws IOException {

//		news.add(new News("Boris has a micropenis","BhamNews","www.google.com",new Date(System.currentTimeMillis()),"content1"));
//		news.add(new News("Boris has a nanopenis","BhamNews","www.google.com",new Date(20,03,02),"content2"));
//		news.add(new News("Boris has no penis","BhamNews","www.google.com",new Date(20,03,03),"content3"));
        return messagesList;
    }

    /**
     * button to go back to mainView page
     * @param event
     */
    public void mainViewButtonPushed(javafx.event.ActionEvent event) {
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
     * pass the selected news object to detailed view
     * @param event
     */
    public void detailedMessageButtonPushed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("MessageBox.fxml"));
            Parent mainViewParent = loader.load();
            Scene mainViewScene = new Scene(mainViewParent);

            //access SearchNewsController and call a method
            MessageBox controller = loader.getController();
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    }
}
