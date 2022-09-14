package com.Hellen.MyProject.Users;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Hellen.MyProject.Common.ErrorResponse;
import com.Hellen.MyProject.Exceptions.AuthenticationException;
import com.Hellen.MyProject.Exceptions.DataSourceException;
import com.Hellen.MyProject.Exceptions.InvalidRequestException;
import com.Hellen.MyProject.Exceptions.ResourceCreationResponse;
import com.Hellen.MyProject.Exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServlet extends HttpServlet {
	 
	private final UserService userService;
	
	public UserServlet(UserService userService) {this.userService = userService;}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		resp.setContentType("application/json");
		
		HttpSession userSession = req.getSession(false);
		
		if(userSession == null) {
			resp.setStatus(401);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401,"Requester is not authenticated with server, log in")));
		    return;
		}
		
		UserResponse requester = (UserResponse) userSession.getAttribute("authUser");
		
		String idToSearchFor = req.getParameter("id");
		String roleToSearchFor = req.getParameter("role");
		String usernameToSearchFor = req.getParameter("username");
		String roleToSearchFor1 = req.getParameter("role");
		
		if((!requester.getRole().equals("admin") && !requester.getRole().equals("finance manager")) && !requester.getId().equals(idToSearchFor) && !requester.getRole().equals(roleToSearchFor)) {
		    resp.setStatus(403);
		    resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint")));
		    return;
		}
		
		try {
			
			if(idToSearchFor == null && usernameToSearchFor == null && roleToSearchFor1 == null) {
				List<UserResponse> allUsers = userService.getAllUsers();
				resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
			
			}
			
			if(idToSearchFor != null) {
				UserResponse foundUser = userService.getUserbyId(idToSearchFor);
				resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
			}
			
			if(usernameToSearchFor != null) {
				UserResponse foundUser = userService.getUserbyUsername(usernameToSearchFor);
				resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
			}
			
			if(roleToSearchFor1 != null) {
				UserResponse foundUser = userService.getUserByRole(roleToSearchFor);
				resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
				
			}
			
		} catch(InvalidRequestException | JsonMappingException e) {
			resp.setStatus(400);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
		} catch (ResourceNotFoundException e) {
			resp.setStatus(404);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(404, e.getMessage())));
		} catch (DataSourceException e) {
			resp.setStatus(500);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//List<User> allUsers = userDAO.getAllUsers();
		//resp.getWriter().write("POST to /users works");
		ObjectMapper jsonMapper = new ObjectMapper();
		resp.setContentType("application/json");
		
		try {
			ResourceCreationResponse responseBody = userService
					.register(jsonMapper.readValue(req.getInputStream(), NewUserRequest.class));
			resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
			
		} catch(InvalidRequestException | JsonMappingException e) {
			resp.setStatus(400);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
		} catch (AuthenticationException e) {
			resp.setStatus(409);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
		} catch (DataSourceException e) {
			resp.setStatus(500);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
		}
	      
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("In progress\n");
		
		ObjectMapper jsonMapper = new ObjectMapper();
		resp.setContentType("application/json");
		
		HttpSession userSession = req.getSession(false);
		
		if (userSession == null) {
			resp.setStatus(401);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, "Requester is not authenticated with server, log in")));
			return;
		}
		
		UserResponse requester = (UserResponse) userSession.getAttribute("authUser");
		
		if (!requester.getRole().equals("admin") && !requester.getRole().equals("finance manager")) {
			resp.setStatus(403);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(403, "Requester not permitted to communicate with this endpoint")));
		    return;
		
		}
		
		String idToSearchFor = req.getParameter("id");
				
		UserResponse foundUser = userService.getUserbyId(idToSearchFor);
		resp.getWriter().write(jsonMapper.writeValueAsString(foundUser));
		
		try {
			ResourceCreationResponse responseBody = userService
					.updateUser(jsonMapper.readValue(req.getInputStream(), UpdateUserRequest.class), idToSearchFor);
			resp.getWriter().write(jsonMapper.writeValueAsString(responseBody));
		} catch(InvalidRequestException |  JsonMappingException e) {
			resp.setStatus(400);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
			
		} catch (AuthenticationException e) {
			resp.setStatus(409);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(409, e.getMessage())));
			
		} catch (DataSourceException e) {
			resp.setStatus(500);
			resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
		}
		resp.getWriter().write("\nEmail is: "+ requester.getEmail());
		
	 }
	}


