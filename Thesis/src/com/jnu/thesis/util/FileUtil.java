package com.jnu.thesis.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
			return msg;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return "";
		}
	}
}
