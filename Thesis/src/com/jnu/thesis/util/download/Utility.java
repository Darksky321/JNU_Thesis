package com.jnu.thesis.util.download;

public class Utility {
	public Utility() {
	}

	public static void sleep(int nSecond) {
		try {
			Thread.sleep(nSecond);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void log(String sMsg) {
		System.out.println(sMsg);
	}

	public static void log(int sMsg) {
		System.out.println(sMsg);
	}
}
