package clientApplication;

public class Message {

    private String username;
    private String message;
    private String id;

    private String newsURL;

    public Message(){}
    public Message(String id,String username, String message, String newsURL) {
        this.username = username;
        this.message = message;
        this.id=id;
        this.newsURL=newsURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsURL() {
        return newsURL;
    }

    public void setNewsURL(String newsURL) {
        this.newsURL = newsURL;
    }
}
