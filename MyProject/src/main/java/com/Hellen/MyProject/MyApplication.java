package com.Hellen.MyProject;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.Hellen.MyProject.Auth.AuthService;
import com.Hellen.MyProject.Auth.AuthServlet;
import com.Hellen.MyProject.Reimbursements.Reimb;
import com.Hellen.MyProject.Reimbursements.ReimbDAO;
import com.Hellen.MyProject.Reimbursements.ReimbService;
import com.Hellen.MyProject.Reimbursements.ReimbServlet;
import com.Hellen.MyProject.Users.UserDAO;
import com.Hellen.MyProject.Users.UserServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.Hellen.MyProject.Users.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyApplication {
	
	//private static Logger logger = LogManager.getLogger(Reimb.class);

	public static void main(String[] args) throws LifecycleException {
		
		 //logger.info("Starting MyApplication");
		 
		String docBase = System.getProperty("java.io.tmpdir");
		Tomcat webServer = new Tomcat();
		
		webServer.setBaseDir(docBase);
		webServer.setPort(5000);
		webServer.getConnector();
		
		UserDAO userDAO = new UserDAO();
		ReimbDAO reimbDAO = new ReimbDAO();
		
		AuthService authService = new AuthService(userDAO);
		UserService userService = new UserService(userDAO);
		ReimbService reimbService = new ReimbService(reimbDAO);
		 
		ObjectMapper jsonMapper = new ObjectMapper();
		 
	    UserServlet userServlet = new UserServlet(userService);
        AuthServlet authServlet = new AuthServlet(authService);
        ReimbServlet reimbServlet = new ReimbServlet(reimbService, jsonMapper);
        
        
       
        
		final String rootContext = "/MyApplication";
		webServer.addContext(rootContext, docBase);
		webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
		webServer.addServlet(rootContext, "AuthServlet", authServlet).addMapping("/auth");
		webServer.addServlet(rootContext, "ReimbServlet", reimbServlet).addMapping("/reimb");
		
		webServer.start();
		
		webServer.getServer().await();
		
		
		
	}

}
