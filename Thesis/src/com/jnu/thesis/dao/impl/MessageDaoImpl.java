package com.jnu.thesis.dao.impl;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jnu.thesis.bean.MessageBean;
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
		if (db != null)
			db.close();
		if (l >= 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delete(Integer id) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = helper.getWritableDatabase();
		int i = db.delete(TABLE, "id=?", new String[] { id.toString() });
		if (db != null)
			db.close();
		if (i > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteAll() {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = helper.getWritableDatabase();
		int i = db.delete(TABLE, "", null);
		if (db != null)
			db.close();
		if (i > 0)
			return false;
		else
			return true;
	}

	@Override
	public ArrayList<MessageBean> findAllMessage() {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = null;
		ArrayList<MessageBean> messages = new ArrayList<MessageBean>();
		try {
			db = helper.getReadableDatabase();
			Cursor cursor = db.query(TABLE, null, null, null, null, null,
					"update_time DESC");
			while (cursor.moveToNext()) {
				MessageBean msg = new MessageBean();
				msg.setId(cursor.getInt(cursor.getColumnIndex("id")));
				msg.setMsg_id(cursor.getLong(cursor.getColumnIndex("msg_id")));
				msg.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				msg.setActivity(cursor.getString(cursor
						.getColumnIndex("activity")));
				msg.setNotificationActionType(cursor.getInt(cursor
						.getColumnIndex("notificationActionType")));
				msg.setContent(cursor.getString(cursor
						.getColumnIndex("content")));
				msg.setUpdate_time(cursor.getString(cursor
						.getColumnIndex("update_time")));
				msg.setFromName(cursor.getString(cursor
						.getColumnIndex("fromName")));
				msg.setFromId(cursor.getString(cursor.getColumnIndex("fromId")));
				messages.add(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return messages;
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return messages;
	}
}
