package com.Hellen.MyProject.Users;

public class UpdateUserRequest {
   
	private String email;
	private String givenName;
	private String surname;
	private String password;
	private boolean is_active;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public UserResponse extractEntity() {
		User ExtractedEntity = new User();
		ExtractedEntity.setEmail(this.email);
		ExtractedEntity.setPassword(this.password);
		ExtractedEntity.setSurname(this.surname);
		ExtractedEntity.setGivenName(this.givenName);
		ExtractedEntity.setIs_active(this.is_active);
		return null;
		
	}

}
