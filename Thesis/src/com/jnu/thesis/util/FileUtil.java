package com.jnu.thesis.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.util.Log;

import com.jnu.thesis.Parameter;

public class FileUtil {
	public static String uploadFile(String filePath) {
		try {
			// TODO 自动生成的方法存根
			URL url = new URL(Parameter.host + Parameter.uploadFile);
			int len;
			String end = "\r\n";
			String twoHyphens = "--";
			// String boundary = java.util.UUID.randomUUID().toString();
			String boundary = "V2ymHFg03ehbqgZCaKO6jy";
			// String boundary="7da1772c5504c6";
			// String boundary="******";
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setReadTimeout(1000);
			httpURLConnection.setConnectTimeout(1000);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			httpURLConnection.connect();
			OutputStream outputStream = httpURLConnection.getOutputStream();
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);
			dataOutputStream.writeBytes(twoHyphens + boundary + end);
			String expandName = filePath.substring(
					filePath.lastIndexOf(".") + 1, filePath.length())
					.toLowerCase();
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1,
					filePath.lastIndexOf("."));
			String pmsg = ("content-Disposition: form-data; name=\"file\";filename=\""
					+ fileName + "." + expandName + "\"" + end + "Content-Type: application/octet-stream");
			dataOutputStream.writeBytes(pmsg);
			dataOutputStream.writeBytes(end);
			dataOutputStream.writeBytes(end);
			byte[] buffer = new byte[512];
			FileInputStream fileInputStream = new FileInputStream(filePath);
			while ((len = fileInputStream.read(buffer, 0, 512)) != -1) {
				dataOutputStream.write(buffer, 0, len);
			}
			fileInputStream.close();
			dataOutputStream.writeBytes(end);
			dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens);
			dataOutputStream.writeBytes(end);
			dataOutputStream.flush();
			InputStream inputStream = httpURLConnection.getInputStream();
			String msg = HttpUtil.changeInputStream(inputStream);
			dataOutputStream.close();
			Log.i("filehttp", msg);
			return msg;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "";
		}
	}

	public static int downloadFile(String urlStr, String to) {
		DataInputStream dis = null;
		FileOutputStream fos = null;
		to = to
				+ "/"
				+ urlStr.substring(urlStr.lastIndexOf("=") + 1, urlStr.length());
		System.out.println(to);
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setRequestMethod("GET");
			urlConn.setConnectTimeout(5000);
			urlConn.setReadTimeout(5000);
			urlConn.setDoInput(true);
			System.out.println(urlConn.getRequestProperties());
			urlConn.connect();
			dis = new DataInputStream(urlConn.getInputStream());
			File file = new File(to);
			if (!file.exists())
				file.createNewFile();
			else
				return 2;
			fos = new FileOutputStream(new File(to));
			byte[] b = new byte[1024];
			int i = 0;
			while ((i = dis.read(b)) != -1) {
				fos.write(b, 0, i);
			}
			// System.out.println(urlConn.getHeaderFields());
			return 1;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			Log.e("filehttp", e.toString());
			return 0;
		} finally {
			try {
				if (dis != null)
					dis.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
	}
}
