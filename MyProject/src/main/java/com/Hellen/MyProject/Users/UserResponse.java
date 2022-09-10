package com.Hellen.MyProject.Users;

public class UserResponse {
	private String id;
	private String givenName;
	private String surname;
	private String email;
	private String username;
	private String password;
	private String role;
	
	public UserResponse(User subject) {
		this.setId(subject.getUserId());
		this.setGivenName(subject.getGivenName());
		this.setSurname(subject.getSurname());
		this.setEmail(subject.getEmail());
		this.setUsername(subject.getUsername());
		this.setPassword(subject.getPassword());
		this.setRole(subject.getRole().getRole());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "UserResponse{" +
	    "id='" + id + '\'' +
	    ", givenName='" + givenName + '\'' +
	    ", surname='" + surname + '\'' +
	    ", email='" + email + '\'' +
	    ", username='" + username + '\'' +
	    ", password='" + password + '\'' +
	    ", role='" + role + '\'' +
	    '}';
	
	}
}
