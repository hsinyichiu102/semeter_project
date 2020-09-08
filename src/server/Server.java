package server;

import java.net.*;
import java.io.*;

/* Based on code in server-client-simple.zip from Canvas
 * 
 * This is a multi-thread SERVER
 * 
 * This code simply establishes a server socket
 * listens out for new clients
 * and then creates a new thread for each
 */
public class Server {
	
    public static void main(String[] args) {
    	
    	System.out.println("\nServer Main code entered");
    	
        ServerSocket serverSocket = null; // This will be shared by all clients.
        /* This is the socket on the server which listens out for connections all 
         * the time and is available for multiple clients to connect to. Each connection
         * is run within a new server thread. 
         */
	
        try {
            serverSocket = new ServerSocket(50000); // Open a server socket:
        } 
		catch (IOException e) {
            System.err.println("\n IOException in Server Main method - Couldn't listen on port: 50000. " // To D0 make port variable
            					+ "\n There's something wrong with the socket on the server.");
            System.exit(-1);
        }

        //*****************************************************************
        //---------------------------------------------
        //THis is the main functionality of the class
        //---------------------------------------------
        
		// Listen to the socket, accept connections from new clients,
		// and start a new thread for each new client:
		try {
			while (true) { // This means do forever
				Socket clientSocket = serverSocket.accept(); //.accept means wait for a client connection
                ServerThread s = new ServerThread(clientSocket);
				s.start(); //start the new thread
			}
		//********************************************************************
        } 
        catch (Exception e) {
			try {
				serverSocket.close(); 
			}
			catch (IOException io) {
				System.err.println("\\n IOException in Server Main method "
									+ "- Couldn't close server socket" 
									+ io.getMessage());
			}
        }
    }
}
