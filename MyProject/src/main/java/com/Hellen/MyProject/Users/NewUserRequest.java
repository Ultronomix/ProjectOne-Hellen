package com.Hellen.MyProject.Users;

import com.Hellen.MyProject.Common.Request;

public class NewUserRequest implements Request<User> {
 
	private String givenName;
	private String surname;
	private String email;
	private String username;
	private String password;
	private boolean is_active;
	private String id;
	
	
	public boolean isIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "NewUserRequest{" +
	           "givenName='" + givenName + '\'' +
	           ",surname='" + surname + '\'' +
	           ",email='" + email + '\'' +
	           ",username='" + username + '\'' +
	           ",is_active='" + is_active + '\'' +
	           ",password='" + password + '\'' +
	           '}';
	}
	
	@Override
	public User extractEntity() {
		User extractedEntity = new User();
		extractedEntity.setUserId(this.id);
		extractedEntity.setGivenName(this.givenName);
		extractedEntity.setSurname(this.surname);
		extractedEntity.setEmail(this.email);
		extractedEntity.setIs_active(this.is_active);
		extractedEntity.setPassword(this.password);
		//extractedEntity.setRole(this.role);
		return extractedEntity;
	}
}
