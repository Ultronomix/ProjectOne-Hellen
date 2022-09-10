package com.Hellen.MyProject.Auth;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Hellen.MyProject.Users.*;
import com.Hellen.MyProject.Exceptions.*;


	public class AuthServlet extends HttpServlet {
		private final AuthService authService;
		final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
	
	    public AuthServlet(AuthService authService) {
	    	this.authService = authService;
	    }
	    
	    @Override
	    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	
	       ObjectMapper jsonMapper = new ObjectMapper();
	       resp.setContentType("application/json");
	    
	    try {
	    	
	    	Credentials credentials = jsonMapper.readValue(req.getInputStream(),Credentials.class);
	    	UserResponse responseBody = authService.authenticate(credentials);
	    	resp.setStatus(200);
	    	resp.getWriter().write((jsonMapper.writeValueAsString(responseBody)));
	    		
	    } catch (InvalidRequestException | JsonMappingException e) {
	    	
	    	resp.setStatus(400);
	    	Map<String, Object> errorResponse = new HashMap<>();
	    	errorResponse.put("statusCode", 400);
	    	errorResponse.put("message", e.getMessage());
	    	errorResponse.put("timestamp", LocalDateTime.now().format(format));
	    	resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
	    
	    } catch(AuthenticationException e) {
	    	
	    	resp.setStatus(401);
	    	Map<String, Object> errorResponse = new HashMap<>();
	    	errorResponse.put("statusCode", 401); //unauthorized user.
	    	errorResponse.put("message", e.getMessage());
	    	errorResponse.put("timestamp", LocalDateTime.now().format(format));
	    	resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
	    
	    } catch (DataSourceException e) {
	    	
	    	resp.setStatus(500);//internal server error
	    	Map<String, Object> errorResponse = new HashMap<>();
	    	errorResponse.put("statusCode", 500);
	    	errorResponse.put("message", e.getMessage());
	    	errorResponse.put("timestamp", LocalDateTime.now().format(format));
	    	resp.getWriter().write(jsonMapper.writeValueAsString(errorResponse));
	    }
	    
	    }
	}

