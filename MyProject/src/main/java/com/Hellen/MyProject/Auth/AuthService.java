package com.Hellen.MyProject.Auth;

import java.util.Optional;

import com.Hellen.MyProject.Exceptions.AuthenticationException;
import com.Hellen.MyProject.Exceptions.InvalidRequestException;
import com.Hellen.MyProject.Users.UserDAO;
import com.Hellen.MyProject.Users.UserResponse;

public class AuthService {

	private final UserDAO userDAO;
	
	public AuthService(UserDAO userDAO) {
		this.userDAO = userDAO;
		
	}
	
	public UserResponse authenticate(Credentials credentials) {
		
		if(credentials == null || credentials.getUsername().trim().length() < 2 || credentials.getPassword().trim().length() < 6) {
			throw new InvalidRequestException("The provided credentials are invalid");
		}
		
		if (credentials.getUsername().length() < 2) {
			throw new InvalidRequestException("The provided username was not the correct length (must atleast be 2 characters long)");
		}
		
		if (credentials.getPassword().length() < 4) {
			throw new InvalidRequestException("The provided password was not the correct length(must atleast be 4 characters long)");
		}
		
		return userDAO.findUserByUsernameAndPassword(credentials.getUsername(), credentials.getPassword())
	        .map(UserResponse::new)
	        .orElseThrow(AuthenticationException::new);
	}
	
}
