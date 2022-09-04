package com.Hellen.MyProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static void main(String[] args) {
	

		 String dbUrl = "jdbc:postgresql://project1.coofkl4qdmz2.us-east-2.rds.amazonaws.com:5432/postgres?currentSchema=public";
	     String dbUsername = "Hellen";
	     String dbPassword = "yeso125";
	     
	      try {
	    Class.forName("org.postgresql.Driver");
	        Connection conn = DriverManager.getConnection(dbUrl,dbUsername, dbPassword);
	        if(conn != null) {
	       
	        System.out.println("Successfully connected to the database");
	       
	        }
	      }
	      catch (ClassNotFoundException e) {
	    System.out.println("Postgresql Not Found");
	     e.printStackTrace();
	    
	      }
	      catch(SQLException e) {
	     System.out.println("Failed to make connection!");
	     e.printStackTrace();
	     
	      }
		
	}

}
