package db;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

    /**
     * @version 2020-03-07 (update 03-11)
     * @author hs.chiu
     * a connection to database for server
     */

    public class ConnectToDB {

        private DBSource dbsource = null;
        private Connection conn = null;
        private Statement stmt = null;
        private ResultSet resultSet = null;
        private PreparedStatement pStmt = null;

        /**
         * the constructor to open the connection with the database
         */
        public ConnectToDB() {
            try {
                dbsource = new DBConnection();
                conn = dbsource.getConnection();
                stmt = conn.createStatement();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * a signUpUser method to create a new account for the user
         * @param username is the one of the info for user to access to the database(not null)
         * @param password is the password for the user to access to the database (not null)
         * @return boolean true ==> the account is created
         */
        public boolean signUpUser(String username, String password){
            boolean confirm = loginUser(username,password);//check if the data is already in the DB
            int flag = 0; // setting flag to check if the data is created
            //SQL Insert data to the table
            String insertDB = "insert into UserInformation(username, password) VALUES(?,?)";

            try {
                // checkgin if the user is already registered, if no, then continue to create a new account
                if (confirm==false) {
                    pStmt = conn.prepareStatement(insertDB);
                    pStmt.setString(1, username);
                    pStmt.setString(2, password);
                    pStmt.executeUpdate();
                    // once the account is created, then the flag will increase
                    flag = 1;
                }
//            createMessageTable(username);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            // if the account is created, then send the true message to the server
            if(flag == 1) {
                return true;
            }
            // else, send the false message to the server which means the account is already existed
            return false;
        }


        public boolean createMessageTable(String username){

            String sqlCreate = "CREATE TABLE "+ username
                    + "(msg varchar)";
            try {
                pStmt = conn.prepareStatement(sqlCreate);
                pStmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return false;
        }

        /**
         * the loginUser for the user to login to the database,
         * besides, for the signUpUser to check the if the user is existed
         * @param username the userID that have been created in the database
         * @param password the password that have been created in the database
         * @return true ==> the user is existed in the database
         */
        public boolean loginUser(String username, String password){
            String selectSQL = "select * from UserInformation ";
            int flag = 0; // setting the flag to check if the user is existed
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(selectSQL);
                while (resultSet.next()) {// if the username and password is equal then flag =1
                    if (resultSet.getString("username").equals(username)&&
                            resultSet.getString("password").equals(password)){
                        flag = 1;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            if(flag == 1){
                return true;
            }
            return false;//if already registered, then return 1, else return 0
        }

        /**
         * the loginAdmin for the admin to login to the page
         * besides, for the signUpUser to check the if the user is existed
         * @param username the userID that have been created in the database
         * @param password the password that have been created in the database
         * @return true ==> the user is existed in the database
         */
        public boolean loginAdmin(String username, String password){
            String selectSQL = "select * from adminInformation ";
            int flag = 0; // setting the flag to check if the user is existed
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(selectSQL);
                while (resultSet.next()) {// if the username and password is equal then flag =1
                    if (resultSet.getString("username").equals(username)&&
                            resultSet.getString("password").equals(password)){
                        flag = 1;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            if(flag == 1){
                return true;
            }
            return false;//if already registered, then return 1, else return 0
        }

        /**
         * add the news to database
         * newsURL is the PK for the table, thus add it to each table when receiving a new news
         * @param headline the headline of the news
         * @param publisher the origin resource get from
         * @param url the URL of the origin resource
         * @param receiveDate when the data be added in the database
         * @return true if the news added successfully
         */
        public boolean addNews(String headline, String publisher, String url, String receiveDate,String content){
            //add the data to the table addNews
            String insertNews = "insert into addnews(headline, newsURL, receivedDate, publisher,nContent) VALUES(?,?,?,?,?)";

            Boolean confirm = isNewsExist(url);//checking if the news is already in the database

            try {
                // if key is false;
                if(!confirm){
                    // add the news to the addnews table
                    pStmt = conn.prepareStatement(insertNews);
                    pStmt.setString(1, headline);
                    pStmt.setString(2, url);
                    pStmt.setString(3, receiveDate);
                    pStmt.setString(4, publisher);
                    pStmt.setString(5,content);
                    pStmt.executeUpdate();
                    setURLIntoResult(url);
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return false;
        }

        public void setURLIntoResult(String url){
            String result = "INSERT INTO result(newsurl,total_voting,true_rating) values(?,?,?)";
            try {
                if(!checkURLexisted(url)){
                    pStmt = conn.prepareStatement(result);
                    pStmt.setString(1, url);
                    pStmt.setInt(2,0);
                    pStmt.setInt(3, 0);
                    pStmt.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean checkURLexisted(String url){
            String result ="Select * from result";
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(result);
                while (resultSet.next()) {// if the username and password is equal then flag =1
                    if (resultSet.getString("newsurl").equals(url)){
                        return true;
                    }
                }

            }catch (SQLException e) {

            }
            return false;
        }
        /**
         * a helper to check if the news is already in the db
         * @param url the newsurl
         * @return true ==> existed
         */
        public boolean isNewsExist(String url){
            String cNews = "select * from addNews ";
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(cNews);
                while (resultSet.next()) {// if the username and password is equal then flag =1
                    if (resultSet.getString("newsURL").equals(url)){
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return false;
        }
        /**
         * a method to vote the news
         * @param vTrue user vote true then server will send the true String
         * @param vFalse user vote false then server will send the false String
         * @param proof user provide the proof to prove the truth
         * @param url the URL of origin source
         * @return
         */
        public boolean getVote(String url, String vTrue, String vFalse, String proof){
            String vote = "INSERT INTO vote(newsurl, vtrue, vfalse, true_proof, false_proof) VALUES (?,?,?,?,?)";

            int voteTrue =0;
            int voteFalse =0;

            try {
                pStmt = conn.prepareStatement(vote);
                if(vTrue!=null){
                    voteTrue++;
                    pStmt.setString(1,url);
                    pStmt.setInt(2,voteTrue);
                    pStmt.setInt(3,voteFalse);
                    pStmt.setString(4,proof);
                    pStmt.setString(5,null);
                }
                if(vFalse!=null){
                    voteFalse++;
                    pStmt.setString(1,url);
                    pStmt.setInt(2,voteTrue);
                    pStmt.setInt(3,voteFalse);
                    pStmt.setString(4,null);
                    pStmt.setString(5,proof);
                }
                pStmt.executeUpdate();
                updateResult(url);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return false;
        }

        /**
         * a helper to update the voting result in database
         * @param url the origin url resource
         */
        public void updateResult(String url){
            String getVote = "select * from vote"; //check if the url can use like this way
            int valueOfTrue=0;
            int valueOfTotal = 0;
            int valueOfFalse = 0;
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(getVote);
                while (resultSet.next()){
                    if(resultSet.getString("newsURL").equals(url)){
                        valueOfTrue=resultSet.getInt("vTrue");
                        valueOfFalse=resultSet.getInt("vFalse");
                        valueOfTotal+= valueOfFalse+valueOfTrue;
                    }
                }
                updateFinalresult(url, valueOfTrue, valueOfFalse, valueOfTotal,valueOfTrue);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
        }

        /**
         * update the result based on the voting
         * @param url the url of the news
         * @param valueOfTotal total vote
         * @param valueOfTrue percentage of the true value
         * @return if this table is updated successfully
         */
        public boolean updateFinalresult(String url, int valueTrue, int valueFalse, int valueOfTotal, int valueOfTrue){
            String updateData = "update result set total_true=?, total_false=?," +
                    "total_voting =?, true_rating=? where newsURL =?";
            try {
                int valueOfRate = (valueOfTrue/valueOfTotal)*100;
                pStmt = conn.prepareStatement(updateData);
                pStmt.setInt(1,valueTrue);
                pStmt.setInt(2,valueFalse);
                pStmt.setInt(3,valueOfTotal);
                pStmt.setInt(4,valueOfRate);
                pStmt.setString(5,url);
                pStmt.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }


        /**
         * get the percentage of the true result and the users can define by themselves
         * @param url the newsurl
         * @return the percentage
         */
        // get result
        public int getPercetageOftheTrue(String url){
            String rateOftrue = "Select * from result";
            int valueOfTrue=0;

            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(rateOftrue);
                    while (resultSet.next()){
                        if(resultSet.getString("newsURL").equals(url)){
                            valueOfTrue = resultSet.getInt("true_rating");
                        }
                    }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return valueOfTrue;
        }

        /**
         * get the number of the voting of true and false
         * @param url
         * @return voteValue, the 0 position is total voting for true, and 1 position is total voting for false
         */
        public ArrayList<Integer> getTotalVote(String url){
            String rateOftrue = "Select * from result";
            int valueTrue;
            int valueFalse;
            ArrayList<Integer> voteValue=  new ArrayList<>();
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(rateOftrue);
                while (resultSet.next()){
                    if(resultSet.getString("newsURL").equals(url)){
                        valueTrue = resultSet.getInt("total_true");
                        valueFalse= resultSet.getInt("total_false");
                      voteValue.add(valueTrue);
                      voteValue.add(valueFalse);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return voteValue;
        }

        // get proof
        public ArrayList<String> getProof(String fromServer, String url){
            String result = "Select * from vote";
            ArrayList<String> proofList = new ArrayList<>();
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(result);
                while (resultSet.next()){
                if(fromServer=="true"){
                        if(resultSet.getString("newsURL").equals(url)){
                            if(!proofList.contains(resultSet.getString("true_proof"))){
                                proofList.add(resultSet.getString("true_proof"));
                            }

                        }
                    }
                else if(fromServer=="false"){
                    if(resultSet.getString("newsURL").equals(url)){
                        if(!proofList.contains(resultSet.getString("false_proof"))){
                            proofList.add(resultSet.getString("false_proof"));
                        }
                    }
                }
                else {
                    if (resultSet.getString("newsURL").equals(url)) {
                        if(!proofList.contains(resultSet.getString("false_proof"))&&
                                !proofList.contains(resultSet.getString("true_proof"))){
                            proofList.add(resultSet.getString("false_proof"));
                            proofList.add(resultSet.getString("true_proof"));
                            }
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeDB();
            }
            return proofList;
        }

        /**
         * a getter to get all the news in the db
         * @return HashMap store the key and the value which includes all information of the news
         */
        public HashMap<String,ArrayList<String>> getNewsList(){
            ArrayList<String> gNews= new ArrayList<>();
            HashMap<String, ArrayList<String>> mNews= new HashMap<>();
            String news = "select * from addNews";
            String key;
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(news);
                while (resultSet.next()){
                        key = resultSet.getString("newsURL");
                        gNews.add(resultSet.getString("headline"));
                    mNews.put(key,gNews);
                }

            } catch (SQLException e) {

            }finally{
                closeDB();
            }
            return mNews;
        }

        /**
         * a method to get the complete information of the news
         * @param url the news url
         * @return ArrayList with whole information
         */
        public ArrayList<String> getNews(String url){
            ArrayList<String> gNews= new ArrayList<>();
            String news = "select * from addNews";
            try {
                stmt = conn.createStatement();
                resultSet = stmt.executeQuery(news);
                while (resultSet.next()){
                    if(resultSet.getString("newsURL").equals(url)){
                        gNews.add(resultSet.getString("headline"));
                        gNews.add(resultSet.getString("publisher"));
                        gNews.add(resultSet.getString("newsurl"));
                        gNews.add(resultSet.getString("receiveddate"));
                        gNews.add(resultSet.getString("content"));
                    }
                }
            } catch (SQLException e) {

            }finally{
                closeDB();
            }
            return gNews;
        }

        /**
         * put all the message in the database and get the relative message
         * @param username is the message table name
         * @param msg is the message from user or admin
         * @return boolean to server that it is stored successfully
         */

        public boolean setMessage(String username, String msg){
            String addMessage = "insert into ?(msg) values(?,?)";
            try{
                pStmt = conn.prepareStatement(addMessage);
                pStmt.setString(1,username);
                pStmt.setString(2,msg);
                pStmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                closeDB();
            }
            return false;
        }

        /**
         * get the message of the user
         * all the message is put into arraylist and send to server via arraylist
         * @param username to find the message history of the user
         * @return Map to ensure the message is for correct user
         */
        public Map<String, ArrayList<String>> getMessage(String username){
            String getMessage = "select * from"+username;
            ArrayList<String> message = new ArrayList<>();
            Map<String, ArrayList<String>> messagebox = new HashMap<>();
            try {
                while(resultSet.next()) {
                    resultSet = stmt.executeQuery(getMessage);
                    message.add(resultSet.getString("msg"));
                    messagebox.put(username, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeDB();
            }
            return messagebox;
        }

        /**
         * if the news is not existed in db, then system will send a message to admin
         * @param url the news that cannot find in db
         */
        public void newsToAdmin(String url){
            String toAdmin= "insert into addnewsmessage(newsurl,message) values(?,?)";
            try{
                pStmt = conn.prepareStatement(toAdmin);
                pStmt.setString(1,url);
                pStmt.setString(2,"news is not existed in db, need to be updated");
                pStmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                closeDB();
            }
        }

        /**
         * admin meesage box from server which including the news that cannot find in the db
         * @return ArrayList<String> which including all the message from server
         */
        public ArrayList<String> getNewsToAdmin(){
            String getMessage = "select * from addnewsmessage";
            ArrayList<String> message = new ArrayList<>();
            try {
                resultSet= stmt.executeQuery(getMessage);
                while (resultSet.next()) {
                    message.add(Integer.toString(resultSet.getInt("id")));
                    message.add(resultSet.getString("newsurl"));
                    message.add(resultSet.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeDB();
            }
            return message;
        }

        /**
         * once the admin add the news to db, then the message will be deleted
         * @param url the delete message
         * @return true if the message is deleted
         */
        public boolean deleteAdminMessage(String url){
            String delete = "Delete from addnewsmessage where newsurl ="+url;
            try{
                pStmt = conn.prepareStatement(delete);
                pStmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally{
                closeDB();
            }
            return false;
        }

        /**
         * close the connection between server and DB
         */
        public void closeDB(){
            try {
                if (resultSet!=null){
                    resultSet.close();
                    resultSet = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (pStmt != null) {
                    pStmt.close();
                    pStmt = null;
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        public static void main(String[] args) throws SQLException {
            ConnectToDB a = new ConnectToDB();
//        System.out.println(a.loginUser("login","password"));
//        System.out.println(a.addNews("asd","bbc","www","2020-3-07","asadmklmkl"));
//        System.out.println(a.checkNews("www"));
            System.out.println(a.getVote("www","","ml","234"));
//        System.out.println(a.updateFinalresult("www",80,100));
//        System.out.println(a.getJudge("www"));
//            System.out.println(a.checkJudgeTable("www"));
//            System.out.println(a.checkNews("www"));
            System.out.println(a.getPercetageOftheTrue("www"));
            System.out.println(a.getProof("false","www"));
            System.out.println(a.getNews("www"));
        }
    }
