package com.Hellen.MyProject.Common;
 
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	private static ConnectionFactory connFact;
	
	private Properties dbProps = new Properties();
	
	public ConnectionFactory() {
		
		try {
			
			 Class.forName("org.postgresql.Driver");//load the driver
		     dbProps.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
		
		} catch (IOException e){
			throw new RuntimeException("Could not read from the properties file.");
		
		} catch(ClassNotFoundException e) {
			throw new RuntimeException("Failed to load postgresql to JDBC driver", e);
		}
	}
	
	public static ConnectionFactory getInstance() {
		if(connFact == null) {
			connFact = new ConnectionFactory();
		}
		return connFact;
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbProps.getProperty("db-url"),dbProps.getProperty("db-username"), dbProps.getProperty("db-password"));
	}

}
