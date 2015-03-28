package com.jnu.thesis;

public class Config {
	private static String currentUser = "";

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		Config.currentUser = currentUser;
	}
}
