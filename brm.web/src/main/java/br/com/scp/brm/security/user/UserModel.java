package br.com.scp.brm.security.user;

import org.bson.types.ObjectId;

public class UserModel {
	private ObjectId id;
	private String username;
	private String password;
	private String role;

	protected UserModel() {
	}

	public UserModel(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public ObjectId getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getSHA1Password() {
		return password;
	}

	public String getRole() {
		return role;
	}
}
