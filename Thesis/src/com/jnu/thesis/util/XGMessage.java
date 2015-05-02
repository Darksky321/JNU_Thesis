package com.jnu.thesis.util;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.tencent.xinge.Message;

public class XGMessage extends Message {

	private String xgContent;
	private Map<String, Object> xgCustom_content;

	public void setXGData(String content, Map<String, Object> customContent) {
		xgContent = content;
		xgCustom_content = customContent;
	}

	@Override
	public String toJson() {
		// TODO 自动生成的方法存根
		JSONObject jo = new JSONObject();
		try {
			jo.put("content", xgContent);
			jo.put("title", "");
			jo.put("accept_time", new JSONArray());
			JSONObject custom = new JSONObject();
			custom.put("tag", xgCustom_content.get("tag"));
			custom.put("fromName", xgCustom_content.get("fromName"));
			jo.put("custom_content", custom);
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		Log.i("mytest", jo.toString());
		return jo.toString();
	}

}
