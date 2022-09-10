package com.Hellen.MyProject.Users;

import java.util.*;
import java.util.stream.Collectors;

//TODO plug this into the userServlet
public class UserService {

	private final UserDAO userDAO;
	
	 public UserService(UserDAO userDAO) {
		this.userDAO = userDAO; 
	 }
	 
	
	public List<UserResponse> getAllUsers(){
		return null;
	}
	
	public String register(User newUser) {
		return null;
	}
	
}
