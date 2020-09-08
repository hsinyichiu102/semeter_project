package clientApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;

public class News implements Serializable {


    private String headline; // the username
    private String publisher; // the password
    private String url;
    private java.sql.Date receivedDate;
    private String content;
    private HashMap<Integer, ArrayList<String>> getFinal; //final answer for the user
    private HashMap<String,ArrayList<String>> newsList;
    private ArrayList<String> newsInform;
    private boolean authenticated; // authenticated or not
    private int realVote = 0;
    private int fakeVote = 0;

    public  News(){}

    public News(String headline, String publisher, String url, Date receivedDate, String content, int realVote, int fakeVote) {

        this.headline = headline;
        this.publisher = publisher;
        this.url = url;
        this.receivedDate = receivedDate;
        this.content = content;
        this.realVote = realVote;
        this.fakeVote = fakeVote;
        authenticated = false;
        getFinal= new HashMap<>();
        newsList = new HashMap<>();
        newsInform= new ArrayList<>();
    }

    /**
	 * @return the realVote
	 */
	public int getRealVote() {
		return realVote;
	}

	/**
	 * @param realVote the realVote to set
	 */
	public void setRealVote(int realVote) {
		this.realVote = realVote;
	}

	/**
	 * @return the fakeVote
	 */
	public int getFakeVote() {
		return fakeVote;
	}

	/**
	 * @param fakeVote the fakeVote to set
	 */
	public void setFakeVote(int fakeVote) {
		this.fakeVote = fakeVote;
	}

	public String getAddNews(){
        return "addNews";
    }

    public String getHeadline() {
        return headline;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getUrl() {
        return url;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public String getContent() {
        return content;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<Integer, ArrayList<String>> getGetFinal() {
        return getFinal;
    }

    public void setGetFinal(HashMap<Integer, ArrayList<String>> getFinal) {
        this.getFinal = getFinal;
    }

    public HashMap<String, ArrayList<String>> getNewsList() {
        return newsList;
    }

    public void setNewsList(HashMap<String, ArrayList<String>> newsList) {
        this.newsList = newsList;
    }

    public ArrayList<String> getNewsInform() {
        return newsInform;
    }
    public void setNewsInform(ArrayList<String> newsInform) {
        this.newsInform = newsInform;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }


}
