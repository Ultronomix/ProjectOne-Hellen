package com.Hellen.MyProject.Users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserServlet extends HttpServlet {
	 
	private final UserService userService;
	
	public UserServlet(UserService userService) {this.userService = userService;}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		ObjectMapper jsonMapper = new ObjectMapper();
		resp.setContentType("application/json");
		//List<User> allUsers = userDAO.getAllUsers();
		//resp.getWriter().write(jsonMapper.writeValueAsString(allUsers));
		//resp.getWriter().write(("testDoGet"));
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//List<User> allUsers = userDAO.getAllUsers();
		resp.getWriter().write("POST to /users works");
	      
	}

}
