package com.model.user;

public class User {

	String username;
	String firstName;
	String familyName;
	int id;
	
	public User() {}
	
	public User(String username, String firstName, String familyName, int id) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.familyName = familyName;
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
