package com.Hellen.MyProject;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import com.Hellen.MyProject.Users.UserServlet;

public class MyApplication {

	public static void main(String[] args) throws LifecycleException {
		
		String docBase = System.getProperty("java.io.tmpdir");
		Tomcat webServer = new Tomcat();
		
		webServer.setBaseDir(docBase);
		webServer.setPort(5000);
		webServer.getConnector();
		
		UserServlet userServlet = new UserServlet();
		
		final String rootContext = "/ers";
		webServer.addContext(rootContext, docBase);
		webServer.addServlet(rootContext, "UserServlet", userServlet).addMapping("/users");
		
		webServer.start();
		webServer.getServer().await();
		
	}

}
