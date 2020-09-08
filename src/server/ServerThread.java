package server;

import client.Action;
import client.Client;
import clientApplication.News;
import db.ConnectToDB;

import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.*;
import java.util.HashMap;

/** Based on code in server-client-simple.zip from Canvas
 * 
 * This is the code that implements a single thread
 * 
 * One of these thread objects is started for each client
 * which connects.
				
 * @version 09/03/2020 13:00
 * @author Jess
 */

/* This code reads an object which is an ArrayList from socket 50000 
 * The first element of the arrayList is a a number which indicates what
 * action is requested. 
 * depending on the action requested, the next elements in the
 * arrayList provide the parameters required for the particular action
 * 
 */
public class ServerThread extends Thread {
	
	//instance variable	
    private Socket client = null; 
    
    //Constructor - based on code in server-client-simple.zip from Canvas
    public ServerThread(Socket client) {
    	super("ServerThread");
		this.client = client;
    }
	@SuppressWarnings("unchecked")
    //RUN METHOD - runs automatically when thread is started
    public void run() {
    	
    	System.out.println("This is the server thread for" + client + "running");
    	
		ObjectOutputStream 	toClient;
		ObjectInputStream   fromClient;

		try {
			toClient 	= new ObjectOutputStream(client.getOutputStream());
			fromClient  = new ObjectInputStream(client.getInputStream());
			//We need to read the login user object from the socket
			try {
				System.out.println("\n Waiting to read object from Client");
				//We wait for the message coming from the socket
				ArrayList<String> message = (ArrayList<String>)fromClient.readObject();

				//the first element in the arrayList is a number (it's a string) which 
				//indicates the action required
				
				//we will leave the action as an integer for the time being
				//but intend to convert it back to an enumerated type

				int action = Integer.parseInt(message.get(0));
				
				System.out.println("\n Action required from Client: \n" + action);

				ConnectToDB servertoDB = new ConnectToDB(); //ServerToDB is a DBConnection interfacing object.
//				boolean result = false; //holds response from database access code
				switch (action) {
					// login
					case 1:
						// the Client request is to sign up a new user:
						toClient.writeObject(servertoDB.loginUser(message.get(1),message.get(2)));
						break;
					//signup
					case 2:
						servertoDB.createMessageTable(message.get(1));
						toClient.writeObject(servertoDB.signUpUser(message.get(1),message.get(2)));
						break;

						//addNews
					case 3:
						//obtain headline, publisher, url, receive date and content from message
						toClient.writeObject(servertoDB.addNews(message.get(1),message.get(2),
								message.get(3),message.get(4),message.get(5)));
						break;
					//checkNews ===> if it is false need to check the admin table
					case 4:
						HashMap<String,ArrayList<String>> resultToClient= new HashMap<>();
						ArrayList<String> list = new ArrayList<>();
						int true_rate = servertoDB.getPercetageOftheTrue(message.get(1));
						String str;
						if(true_rate>65){
							list = servertoDB.getProof("true",message.get(1));
							str = Integer.toString(true_rate);
						}
						else if (true_rate<50){
							list= servertoDB.getProof("false",message.get(1));
							str = Integer.toString(true_rate);
						}
						else {
							list=servertoDB.getProof("equal", message.get(1));
							str = Integer.toString(true_rate);
						}

						resultToClient.put(str,list);
						break;
						//voting
					case 5:
						toClient.writeObject(servertoDB.getVote(message.get(1),message.get(2),message.get(3)
						,message.get(4)));
						break;

//						//getNewsList
					case 6:
						toClient.writeObject(servertoDB.getNewsList());
						break;
						//get news
					case 7:
						ArrayList<String> a= new ArrayList<>();
						if(servertoDB.getNews(message.get(1))!=a){
							toClient.writeObject(servertoDB.getNews(message.get(1)));
						}
						else {
							servertoDB.newsToAdmin(message.get(1));
							toClient.writeObject(a);
						}
						break;
						// send user Msg
					case 8:
						toClient.writeObject(servertoDB.setMessage(message.get(1),message.get(2)));
						break;
						//get userMsg
					case 9:
						toClient.writeObject(servertoDB.getMessage(message.get(1)));
						break;
						// get the server message from admin
					case 10:
						toClient.writeObject(servertoDB.getNewsToAdmin());
						break;
						// delete the message once the news is done
					case 11:
						toClient.writeObject(servertoDB.deleteAdminMessage(message.get(1)));
						//login for admin
					case 12:
						toClient.writeObject(servertoDB.loginAdmin(message.get(1),message.get(2)));
						// get the vote number of true and false
					case 13:
						toClient.writeObject(servertoDB.getTotalVote(message.get(1)));
					
				} // end switch	
			} 
			catch (IOException e) {
				System.out.println("\n IOException in Server thread RUN method");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			//Close all the IO
			toClient.close();
			fromClient.close();
			client.close();
		} 
		catch (IOException e) {
			System.out.println("\n IOException in ServerThread - Something went wrong. Ending service to client...");
			return;
		}
    }
	public static void main(String[] args) throws SQLException, IOException {

	}
}
