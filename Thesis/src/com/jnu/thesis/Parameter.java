package com.jnu.thesis;

public class Parameter {
	// public static final String host =
	// "http://172.17.134.14:8080/Thesis_Supervision/";
	public static final String host = "http://192.168.137.1:8080/Thesis_Supervision/";

	public static final String loginAction = "login.action";
	public static final String studentMain = "studentMainSelect.action";
	public static final String studentSelect = "studentSelectMain.action";
	public static final String teacherMain = "teacherMainSelect.action";
	public static final String teacherRefresh = "teacherMainRelease.action";
	public static final String teacherTopicCreate = "teacherTopicCreate.action";
	public static final String teacherTopicUpdate = "teacherTopicUpdate.action";
	public static final String teacherSelect = "selectMain.action";
	public static final String teacherSure = "selectSure.action";
	public static final String teacherTopicDelete = "teacherTopicDelete.action";
	public static final String uploadFile = "fileUpload.action";

	public static final String ACCOUNT_PWD = "user.pwd";

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
