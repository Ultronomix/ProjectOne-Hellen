package com.Hellen.MyProject.Users;

import java.util.*;
import java.util.stream.Collectors;
import com.Hellen.MyProject.Users.UserResponse;

import com.Hellen.MyProject.Exceptions.InvalidRequestException;
import com.Hellen.MyProject.Exceptions.ResourceCreationResponse;
import com.Hellen.MyProject.Exceptions.ResourceNotFoundException;
import com.Hellen.MyProject.Exceptions.ResourcePersistenceException;

//TODO plug this into the userServlet
public class UserService {

	private final UserDAO userDAO;
	
	 public UserService(UserDAO userDAO) {
		this.userDAO = userDAO; 
	 }
	 
	
	public List<UserResponse> getAllUsers(){
		 List<UserResponse> result = new ArrayList<>();
		 List<User> users = userDAO.getAllUsers();
		 
		 for (User user : users) {
			 result.add(new UserResponse(user));
		 }
		 return result;
	}
	
	public UserResponse getUserbyId(String id) {
		if(id == null || id.trim().length() <= 0) {
			throw new InvalidRequestException("An id must be provided");
		}
		return userDAO.findUserbyId(id).map(UserResponse::new)
				.orElseThrow(ResourceNotFoundException::new);
	}
	
	public UserResponse getUserbyUsername (String username) {
		if(username == null || username.trim().length() < 4) {
			throw new InvalidRequestException("A username must be atleast 4 characters");
		}
		return userDAO.findUserbyUsername(username).map(UserResponse::new)
				.orElseThrow(ResourceNotFoundException::new);
	}
	public UserResponse getUserByRole (String role) {
		if(role == null || role.trim().length() <= 0) {
			throw new InvalidRequestException("A role must be provided");
		}
		return userDAO.findUserByRole(role).map(UserResponse::new)
		      .orElseThrow(ResourceNotFoundException::new);
	}
	public ResourceCreationResponse register (NewUserRequest newUser) {
		
		if(newUser == null) {
			throw new InvalidRequestException("Provided request payload was null");
		}
		if (newUser.getGivenName().trim().length() <= 0 || newUser.getGivenName() == null ||
				newUser.getSurname().trim().length() <= 0 || newUser.getSurname() == null) {
			throw new InvalidRequestException("A non empty given name or surname must be provided");
		}
		if(newUser.getEmail() == null || newUser.getEmail().trim().length() <= 0) {
			throw new InvalidRequestException("Email can't be empty");
		}
		if(newUser.getUsername() == null || newUser.getUsername().trim().length() < 4){
			throw new InvalidRequestException("Username must atleast be 4 characters");
		}
		if(newUser.getPassword() == null || newUser.getPassword().trim().length() < 6){
			throw new InvalidRequestException("Password must atleast be 6 characters");
		}
		if(userDAO.isEmailTaken(newUser.getEmail())) {
			throw new ResourcePersistenceException("Resource not persisted. The email is taken");
		}
		if(userDAO.isUsernameTaken(newUser.getUsername())) {
			throw new ResourcePersistenceException("Resource not persisted. The username is taken");
		}
		
		User userToPersist = newUser.extractEntity();
		String newUserId = userDAO.save(userToPersist);
		return new ResourceCreationResponse(newUserId);
		
	}
	public ResourceCreationResponse updateUser (UpdateUserRequest updateUserRequest, String idToSearchFor) {
		if (updateUserRequest == null) {
			throw new InvalidRequestException("Provided request payload was null");
		}
		if (updateUserRequest.getEmail() != null && updateUserRequest.getEmail().trim().length() > 3) {
			userDAO.updateUserEmail(updateUserRequest.getEmail(), idToSearchFor);
		}
		if (updateUserRequest.getGivenName() != null && updateUserRequest.getGivenName().trim().length() > 4) {
			
		}
        if (updateUserRequest.getSurname() != null && updateUserRequest.getSurname().trim().length() > 4) {
			
		}
        if (updateUserRequest.getIs_active() != false || updateUserRequest.getIs_active() == true) {
			
		}
        String userToUpdate = updateUserRequest.extractEntity().getEmail();
        System.out.println(userToUpdate);
        String updateEmail = userDAO.updateUserEmail(userToUpdate, idToSearchFor);
        System.out.println("update: " + updateEmail);
        return new ResourceCreationResponse(updateEmail);
	}
	
}
