package com.Hellen.MyProject.Users;

import java.io.Serializable;
import java.util.Objects;

public class UserResponse implements Serializable{
	
	private String id;
	
	private String givenName;
	
	private String surname;
	
	private String email;
	
	private String username;
	
	private String password;
	
	private String role;
	
	private boolean is_active;
	
	public UserResponse(User subject) {
		this.setId(subject.getUserId());
		this.setGivenName(subject.getGivenName());
		this.setSurname(subject.getSurname());
		this.setEmail(subject.getEmail());
		this.setUsername(subject.getUsername());
		this.setIs_active(subject.getIsActive());
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
	public boolean getIs_active() {
		return is_active;
	}
	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, username,email,givenName, surname, is_active, role);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		UserResponse userResponse = (UserResponse) obj;
		return Objects.equals(id, userResponse.id) && Objects.equals(username, userResponse.username)
				&& Objects.equals(surname, userResponse.surname) && Objects.equals(is_active, userResponse.is_active)
	            && Objects.equals(email, userResponse.email) && Objects.equals(givenName, userResponse.givenName)
	            && Objects.equals(role, userResponse.role);           
	
	}
	
	@Override
	public String toString() {
		return "UserResponse{" +
	    "id='" + id + '\'' +
	    ", givenName='" + givenName + '\'' +
	    ", surname='" + surname + '\'' +
	    ", email='" + email + '\'' +
	    ", username='" + username + '\'' +
	    ", is_active='" + is_active + '\'' +
	    ", password='" + password + '\'' +
	    ", role='" + role + '\'' +
	    '}';
	
	}
}
