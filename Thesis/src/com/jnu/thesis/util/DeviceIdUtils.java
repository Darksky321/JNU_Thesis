package com.jnu.thesis.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * 获取广告机ID
 * 
 * @author
 *
 */
public class DeviceIdUtils {

	public static Context context;
	private static String m_szImei = "";
	private static String m_szDevIDShort = "";
	private static String m_szAndroidID = "";
	private static String m_szWLANMAC = "";
	private static String m_szBTMAC = "";
	private static String m_szLongID = "";
	private static String m_szUniqueID = "";

	public static void setContext(Context context) {
		DeviceIdUtils.context = context;
	}

	public static String getIMEI() {
		TelephonyManager TelephonyMgr = (TelephonyManager) context
				.getSystemService(android.content.Context.TELEPHONY_SERVICE);
		m_szImei = TelephonyMgr.getDeviceId();
		if (m_szImei == null) {
			m_szImei = "";
		}
		return m_szImei;
	}

	public static String getPseudoUniqueID() {
		m_szDevIDShort = "35"
				+ // we make this look like a valid IMEI
				Build.BOARD.length() % 10 + Build.BRAND.length() % 10
				+ Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10
				+ Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
				+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10
				+ Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10
				+ Build.TAGS.length() % 10 + Build.TYPE.length() % 10
				+ Build.USER.length() % 10; // 13 digits
		return m_szDevIDShort;
	}

	public static String getAndroidID() {
		m_szAndroidID = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return m_szAndroidID;
	}

	public static String getWLANMAC() {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		m_szWLANMAC = wm.getConnectionInfo().getMacAddress();
		return m_szWLANMAC;
	}

	public static String getBTMAC() {
		BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
		m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		m_szBTMAC = m_BluetoothAdapter.getAddress();
		return m_szBTMAC;
	}

	public static String getCombinedDeviceID() {
		/*
		 * m_szLongID = getIMEI() + getPseudoUniqueID() + getAndroidID() +
		 * getWLANMAC() + getBTMAC();
		 */
		if (!m_szUniqueID.equals("")) {
			return m_szUniqueID;
		}
		m_szLongID = getIMEI() + getPseudoUniqueID() + getAndroidID();
		// compute md5
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
		// get md5 bytes
		byte p_md5Data[] = m.digest();
		// create a hex string
		String m_szUniqueID = new String();
		for (int i = 0; i < p_md5Data.length; i++) {
			int b = (0xFF & p_md5Data[i]);
			// if it is a single digit, make sure it have 0 in front (proper
			// padding)
			if (b <= 0xF)
				m_szUniqueID += "0";
			// add number to string
			m_szUniqueID += Integer.toHexString(b);
		} // hex string to uppercase
		m_szUniqueID = m_szUniqueID.toUpperCase();
		// 32位太长,取其中16位
		m_szUniqueID = "" + m_szUniqueID.charAt(0) + m_szUniqueID.charAt(2)
				+ m_szUniqueID.charAt(4) + m_szUniqueID.charAt(6)
				+ m_szUniqueID.charAt(8) + m_szUniqueID.charAt(10)
				+ m_szUniqueID.charAt(12) + m_szUniqueID.charAt(14)
				+ m_szUniqueID.charAt(16) + m_szUniqueID.charAt(18)
				+ m_szUniqueID.charAt(20) + m_szUniqueID.charAt(22)
				+ m_szUniqueID.charAt(24) + m_szUniqueID.charAt(26)
				+ m_szUniqueID.charAt(28) + m_szUniqueID.charAt(30);
		return m_szUniqueID;
	}
}
