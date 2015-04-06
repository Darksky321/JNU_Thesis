package com.jnu.thesis.dao.impl;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jnu.thesis.dao.MessageDao;
import com.jnu.thesis.db.DatabaseHelper;
import com.qq.xgdemo.po.XGNotification;

public class MessageDaoImpl implements MessageDao {
	private DatabaseHelper helper = null;
	private static final String TABLE = "message";
	private static MessageDaoImpl instance;

	private MessageDaoImpl(Context context) {
		helper = DatabaseHelper.getInstance(context);
	}

	public synchronized static MessageDaoImpl getInstance(Context context) {
		if (null == instance) {
			instance = new MessageDaoImpl(context);
		}
		return instance;
	}

	public boolean save(XGNotification notification, String customContent) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("msg_id", notification.getMsg_id());
		values.put("title", notification.getTitle());
		values.put("content", notification.getContent());
		values.put("activity", notification.getActivity());
		values.put("notificationActionType",
				notification.getNotificationActionType());
		values.put("update_time", notification.getUpdate_time());
		String fromName = "";
		String fromId = "";
		try {
			JSONObject custom = new JSONObject(customContent);
			fromName = custom.getString("fromName");
			fromId = custom.getString("fromId");
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		values.put("fromName", fromName);
		values.put("fromId", fromId);
		long l = db.insert(TABLE, null, values);
		if (l >= 0)
			return true;
		else
			return false;
	}
}
