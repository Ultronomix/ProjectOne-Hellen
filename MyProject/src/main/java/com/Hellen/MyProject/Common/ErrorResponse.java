package com.Hellen.MyProject.Common;

import java.time.LocalDateTime;

public class ErrorResponse {
	private int statusCode;
	private String message;
	private String timestamp;
	
	public ErrorResponse(int statusCode, String message) {
		this.setStatusCode(statusCode);
		this.setMessage(message);
		this.setTimestamp(LocalDateTime.now().toString());
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "ErrorResponse{" +
	           "statusCode=" + statusCode +
	           ",message='" + message + '\'' +
	           ",timestamp=" + timestamp + '\'' +
	           '}';
	}

}
