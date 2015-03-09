package com.jnu.thesis.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

/**
 * 通讯工具类
 * 
 * @author
 *
 */
public class HttpUtils {
	private URL url;

	/**
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param deviceId
	 */
	public HttpUtils(String url) {
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * post
	 * 
	 * @param nvps
	 * @return
	 */
	public String postMessage(List<NameValuePair> nvps) {
		String resMsg = "";
		Log.i("commutest", nvps.toString());
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url.toString());
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		try {
			// UrlEncodedFormEntity postEn = new UrlEncodedFormEntity(nvps);
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			// Log.i("commutest", "post:");
			// Log.i("commutest", EntityUtils.toString(postEn, "UTF-8"));
			HttpResponse response = httpclient.execute(httpPost);
			// System.out.println(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			resMsg = EntityUtils.toString(entity, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return resMsg;
	}

	// get方式,并没有使用
	public String getMessage(String string) {
		String resMsg = "";
		DefaultHttpClient httpclient = new DefaultHttpClient();
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		HttpGet httpGet = new HttpGet(url.toString() + "?" + string);
		try {
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			// do something useful with the response body
			// and ensure it is fully consumed
			resMsg = EntityUtils.toString(entity, "UTF-8");
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return resMsg;
	}

}
