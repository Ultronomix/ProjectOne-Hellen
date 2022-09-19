package com.Hellen.MyProject.Common;

import com.Hellen.MyProject.Users.UserResponse;

public class SecurityUtils {

	public static boolean isAdmin(UserResponse subject) {
		return subject.getRole().equals("Admin");
	}
	
	public static boolean isFinanceManager(UserResponse subject) {
		return subject.getRole().equals("manager");
	}
	
	public static boolean requesterOwned(UserResponse subject, String resourceId) {
		return subject.getId().equals(resourceId);
	}
}
