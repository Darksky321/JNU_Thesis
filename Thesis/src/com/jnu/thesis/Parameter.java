package com.jnu.thesis;

public class Parameter {
	public static final String host = "http://192.168.137.1:8080/Thesis_Supervision/";
	public static final String loginAction = "login.action";
	public static final String studentMain = "studentMain.action";
	public static final String submitChoice = "submitchoice.action";

	private static String currentUser = "";
	private static int status = 0;

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		Parameter.currentUser = currentUser;
	}

	public static int getStatus() {
		return status;
	}

	public static void setStatus(int status) {
		Parameter.status = status;
	}

	public static void clear() {
		currentUser = "";
		status = 0;
	}
}
