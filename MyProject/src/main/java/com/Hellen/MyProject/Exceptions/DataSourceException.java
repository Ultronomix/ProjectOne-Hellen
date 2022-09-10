package com.Hellen.MyProject.Exceptions;

public class DataSourceException extends RuntimeException {
	 
	public DataSourceException(String message, Throwable cause) {
		  super(message, cause);
	  
	 }
	
  public DataSourceException(Throwable cause) {
	  super("Something went wrong when communicating with the database. Developers please check the logs", cause);
  }
  
}
