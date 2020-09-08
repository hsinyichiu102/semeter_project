package client;

import clientApplication.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.HashMap;

/**
 * Client class has the socket to communicate with server and receive the response
 * @version 2020-03-10
 */

public class Client{

        private Socket socket;
        private ObjectInputStream fromSever;
        private ObjectOutputStream toSever;
        private Action action;

    /**
     * a client socket to connect with the server
     * @param serverName the host
     */
    public Client(String serverName) {
            try {
                socket = new Socket(serverName, 50000);
                System.out.println("Connected");
                toSever = new ObjectOutputStream(socket.getOutputStream());
                fromSever = new ObjectInputStream(socket.getInputStream());
            } catch (UnknownHostException e) {
                System.out.println("Unknown host: " + serverName);
            } catch (IOException e) {
                System.out.println("Couldn't get I/O for the connection to " + serverName);
            }
        }

    /**
     * a method for login and signup
     * @param action
     * @param user
     * @return
     * @throws IOException
     */
    public boolean userGetIn(Action action, UserRequest user) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        boolean getSever= false;
        try {
            switch (action) {
                case LOGIN:
                    sender.add("1");
                    sender.add(user.getUsername());
                    sender.add(user.getPassword());
                    toSever.writeObject(sender);

                    break;
                case SIGNUP:
                    sender.add("2");
                    sender.add(user.getUsername());
                    sender.add(user.getPassword());
                    toSever.writeObject(sender);
                    break;
            }
            getSever = (Boolean) fromSever.readObject();
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    // adding the news
    public boolean addNews(Action action, News news) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        DateFormat date = new SimpleDateFormat("yyyy-mm-dd");
        boolean getSever= false;
        try {
            switch (action) {
                case ADDNEWS:
                    sender.add("3");
                    sender.add(news.getHeadline());
                    sender.add(news.getPublisher());
                       sender.add(news.getUrl());
                       sender.add(date.format(news.getReceivedDate()));
                       sender.add(news.getContent());
                       toSever.writeObject(sender);
                       break;
            }
            getSever = (Boolean) fromSever.readObject();
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    // check the news *** admin setting result should be also passed here
    @SuppressWarnings("unchecked")
    public HashMap<String, ArrayList<String>> checkNews(Action action, News news) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        HashMap<String, ArrayList<String>> getSever = new HashMap<>();
        try {
            switch (action) {
                case CHECKNEWS:
                sender.add("4");
                sender.add(news.getUrl());
                toSever.writeObject(sender);
                break;
            }

            getSever = ((HashMap<String, ArrayList<String>>)fromSever.readObject());
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    //Vote the truth AND USER
    public boolean vote(Action action, GetVote vote) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        boolean getSever= false;
        try {
            switch (action) {
                // for user to vote
                case GETVOTE:
                    sender.add("5");
                    sender.add(vote.getNewsUrl());
                    sender.add(vote.getvTrue());
                    sender.add(vote.getvFalse());
                    sender.add(vote.getProof());
                    toSever.writeObject(sender);
                    break;
            }
            getSever = (Boolean) fromSever.readObject();
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    // get News List
    @SuppressWarnings("unchecked")
    public HashMap<String,ArrayList<String>> getNewsList(Action action) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        HashMap<String,ArrayList<String>> getSever = new HashMap<>();
        try {
            switch (action) {
                // for user to vote
                case  GETNEWSLIST:
                    sender.add("6");
                    toSever.writeObject(sender);
                break;
            }
            getSever= (HashMap<String,ArrayList<String>>) fromSever.readObject();
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    // get complete news inform
    @SuppressWarnings("unchecked")
    public ArrayList<String> getNews(Action action, News news) throws IOException {
           ArrayList<String> sender = new ArrayList<>();
           ArrayList<String> getSever = new ArrayList<>();
        try {
            switch (action) {
                // for user to vote
                case  NEWS:
                    sender.add("7");
                    sender.add(news.getUrl());
                    toSever.writeObject(sender);
            }
            getSever= (ArrayList<String>) fromSever.readObject();
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }
    // store the msg in db
    public boolean setMessage(Action action, Message msg) throws IOException {
        ArrayList<String> sender= new ArrayList<>();
        boolean getSever = false;
        try {
            switch (action){
                case SENDMSG:
                    sender.add("8");
                    sender.add(msg.getUsername());
                    sender.add(msg.getMessage());
                    toSever.writeObject(sender);
            }
            getSever= (boolean) fromSever.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    /**
     * getMessage method is getting the message from db
     * GETMSG label is to getting the all message from the user
     * GETSERVERMSG label is to getting the message from the server when the news is not existed
     * @param action is the label
     * @param request is the request to get two different message
     * @return ArrayList of the message
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public ArrayList<String> getMessage(Action action, String request) throws IOException {
        ArrayList<String> sender= new ArrayList<>();
        ArrayList<String> getSever = new ArrayList<>();
        try {
            switch (action){
                case GETMSG:
                    sender.add("9");
                    sender.add(request);
                    toSever.writeObject(sender);
                case GETSERVERMSG:
                    sender.add("10");
                    sender.add(request);
                    toSever.writeObject(sender);
            }
            getSever= (ArrayList<String>) fromSever.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        toSever.close();
        fromSever.close();
    }
        return getSever;
    }

    /**
     * deleteMsg method to delete the msg if the news is already added by admin
     * @param action a label
     * @param request the delete newsurl
     * @return true if it is deleted
     * @throws IOException
     */
    public boolean deleteMsg(Action action, String request) throws IOException {
        ArrayList<String> sender= new ArrayList<>();
        boolean getSever = false;
        try {
            switch (action){
                case DELSERVERMSG:
                    sender.add("11");
                    sender.add(request);
                    toSever.writeObject(sender);
            }
            getSever= (boolean) fromSever.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    /**
     * a method for login and signup
     * @param action
     * @param user
     * @return
     * @throws IOException
     */
    public boolean adminLogin(Action action, UserRequest user) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        boolean getSever= false;
        try {
            switch (action) {
                case ADMINLOGIN:
                    sender.add("12");
                    sender.add(user.getUsername());
                    sender.add(user.getPassword());
                    toSever.writeObject(sender);

                    break;
            }
            getSever = (Boolean) fromSever.readObject();
            System.out.println("\n Message receive from Server: \n" + getSever);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }

    /**
     * a method to get the total value for true and false
     * @param action a label
     * @param vote to get the date from vote class
     * @return getServer as ArrayList and the 0 position is the total voting number of true,
     * 1 position is the total voting number of false
     */

    public ArrayList<Integer> voteNumber(Action action, GetVote vote) throws IOException {
        ArrayList<String> sender = new ArrayList<>();
        ArrayList<Integer> getSever = new ArrayList<>();
        try {
            switch (action){
                case GETMSG:
                    sender.add("13");
                    sender.add(vote.getNewsUrl());
                    toSever.writeObject(sender);
            }
            getSever= (ArrayList<Integer>) fromSever.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            toSever.close();
            fromSever.close();
        }
        return getSever;
    }


    /**
     * main method to connect with gui
     * @param args get the host number
     * @throws IOException if cannot get anything
     */

    public static void main(String[] args) throws IOException {
    if (args.length != 1)
    {
        System.err.println("Usage: java LaunchClient hostname");
        System.exit(1);
    }

    new Client(args[0]);

    }
}

