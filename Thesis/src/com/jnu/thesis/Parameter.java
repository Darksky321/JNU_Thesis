package com.jnu.thesis;

public class Parameter {
	public static final String host = "http://127.0.0.1/Thesis_Supervision/";
	public static final String loginAction = "login.action";
	private static String currentUser = "";

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		Parameter.currentUser = currentUser;
	}
}
