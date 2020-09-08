package clientApplication;

import java.io.*;

/**
 * Class to create user objects
 *
 */
public class UserRequest implements Serializable {

	private String username; // the username
	private String password; // the password
	private boolean authenticated; // authenticated or not


	public UserRequest(){}
	public UserRequest(String username, String password) {
		this.username = username;
		this.password = password;
		authenticated = false;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}
	
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}


