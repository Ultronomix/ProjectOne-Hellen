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
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Hellen.MyProject.Users.*;
import com.Hellen.MyProject.Common.ErrorResponse;
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
	    	
	    	HttpSession userSession = req.getSession();
	    	userSession.setAttribute("authUser", responseBody);
	    	
	    	resp.getWriter().write((jsonMapper.writeValueAsString(responseBody)));
	    		
	    } catch (InvalidRequestException | JsonMappingException e) {
	    	
	    	resp.setStatus(400); //bad request
	    	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
	    
	    } catch(AuthenticationException e) {
	    	
	    	resp.setStatus(401);
	    	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(401, e.getMessage())));
	    
	    } catch (DataSourceException e) {
	    	
	    	resp.setStatus(500);//internal server error
	    	resp.getWriter().write(jsonMapper.writeValueAsString(new ErrorResponse(500, e.getMessage())));
	    }
	    
	    }
	    @Override
	    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    	req.getSession().invalidate();
	    }
	}

