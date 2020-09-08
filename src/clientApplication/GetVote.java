package clientApplication;

import java.io.Serializable;

/**
 * a class for voting
 */

public class GetVote implements Serializable {
    private String newsUrl;
    private String vTrue;
    private String vFalse;
    private String proof;



    private boolean authenticated; // authenticated or not

    public GetVote(){}
    public GetVote(String newsUrl, String vTrue, String vFalse, String proof) {
        this.newsUrl = newsUrl;
        this.vTrue = vTrue;
        this.vFalse = vFalse;
        this.proof = proof;
        authenticated=false;
    }



    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public void setvTrue(String vTrue) {
        this.vTrue = vTrue;
    }

    public void setvFalse(String vFalse) {
        this.vFalse = vFalse;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }



    public String getNewsUrl() {
        return newsUrl;
    }

    public String getvTrue() {
        return vTrue;
    }

    public String getvFalse() {
        return vFalse;
    }

    public String getProof() {
        return proof;
    }


    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
