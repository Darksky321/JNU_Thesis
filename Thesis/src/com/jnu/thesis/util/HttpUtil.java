package com.jnu.thesis.util;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import android.util.Log;

/**
 * 通讯工具类
 * 
 * @author Deng
 *
 */
public class HttpUtil {

	private static HttpUtil httpUtil;
	private static HttpClient httpClient;

	private HttpUtil() {
		httpClient = new DefaultHttpClient();
	}

	public static HttpUtil getInstance() {
		if (httpUtil == null) {
			httpUtil = new HttpUtil();
		}
		return httpUtil;
	}

	/**
	 * post
	 * 
	 * @param nvps
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public String postMessage(URL url, List<NameValuePair> nvps)
			throws ClientProtocolException, IOException {
		String resMsg = "";
		Log.i("commutest", nvps.toString());
		HttpPost httpPost = new HttpPost(url.toString());
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		// UrlEncodedFormEntity postEn = new UrlEncodedFormEntity(nvps);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// Log.i("commutest", EntityUtils.toString(postEn, "UTF-8"));
		HttpResponse response = httpClient.execute(httpPost);
		// System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		// do something useful with the response body
		// and ensure it is fully consumed
		resMsg = EntityUtils.toString(entity, "UTF-8");
		return resMsg;
	}

	// get方式,并没有使用
	public String getMessage(URL url, String string)
			throws ClientProtocolException, IOException {
		String resMsg = "";
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		HttpGet httpGet = new HttpGet(url.toString() + "?" + string);
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		// do something useful with the response body
		// and ensure it is fully consumed
		resMsg = EntityUtils.toString(entity, "UTF-8");
		return resMsg;
	}

	public static void destroy() {
		httpClient.getConnectionManager().shutdown();
		httpClient = null;
		httpUtil = null;
	}
}
