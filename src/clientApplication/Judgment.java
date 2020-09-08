package clientApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Judgment implements Serializable {
    private String url;
    private String vTrue;
    private String vFalse;
    private String proof;
    private boolean authenticated;
    private HashMap<Boolean, ArrayList<String>> answer;

    public Judgment(){}

    public Judgment(String url, String vTrue, String vFalse, String proof) {
        this.url = url;
        this.vTrue = vTrue;
        this.vFalse = vFalse;
        this.proof = proof;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getvTrue() {
        return vTrue;
    }

    public void setvTrue(String vTrue) {
        this.vTrue = vTrue;
    }

    public String getvFalse() {
        return vFalse;
    }

    public void setvFalse(String vFalse) {
        this.vFalse = vFalse;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public HashMap<Boolean, ArrayList<String>> getAnswer() {
        return answer;
    }

    public void setAnswer(HashMap<Boolean, ArrayList<String>> answer) {
        this.answer = answer;
    }



}
