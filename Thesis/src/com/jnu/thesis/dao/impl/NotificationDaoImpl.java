package com.jnu.thesis.dao.impl;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jnu.thesis.bean.NotificationBean;
import com.jnu.thesis.dao.NotificationDao;
import com.jnu.thesis.db.DatabaseHelper;
import com.qq.xgdemo.po.XGNotification;

public class NotificationDaoImpl implements NotificationDao {
	private DatabaseHelper helper = null;
	private static final String TABLE = "notification";
	private static NotificationDaoImpl instance;

	private NotificationDaoImpl(Context context) {
		helper = DatabaseHelper.getInstance(context);
	}

	public synchronized static NotificationDaoImpl getInstance(Context context) {
		if (null == instance) {
			instance = new NotificationDaoImpl(context);
		}
		return instance;
	}

	public boolean save(XGNotification notification, String customContent,
			String toId) {
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
		values.put("toID", toId);
		long l = db.insert(TABLE, null, values);
		if (db != null)
			db.close();
		if (l >= 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean delete(String id) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = helper.getWritableDatabase();
		int i = db.delete(TABLE, "id=?", new String[] { id });
		if (db != null)
			db.close();
		if (i > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean deleteAll(String toId) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = helper.getWritableDatabase();
		int i = db.delete(TABLE, "toId=?", new String[] { toId });
		if (db != null)
			db.close();
		if (i > 0)
			return false;
		else
			return true;
	}

	@Override
	public ArrayList<NotificationBean> findAllNotifications(String toId) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = null;
		ArrayList<NotificationBean> notis = new ArrayList<NotificationBean>();
		try {
			db = helper.getReadableDatabase();
			Cursor cursor = db.query(TABLE, null, "toId=?",
					new String[] { toId }, null, null, "update_time DESC");
			while (cursor.moveToNext()) {
				NotificationBean noti = new NotificationBean();
				noti.setId(cursor.getInt(cursor.getColumnIndex("id")));
				noti.setMsg_id(cursor.getLong(cursor.getColumnIndex("msg_id")));
				noti.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				noti.setActivity(cursor.getString(cursor
						.getColumnIndex("activity")));
				noti.setNotificationActionType(cursor.getInt(cursor
						.getColumnIndex("notificationActionType")));
				noti.setContent(cursor.getString(cursor
						.getColumnIndex("content")));
				noti.setUpdate_time(cursor.getString(cursor
						.getColumnIndex("update_time")));
				noti.setFromName(cursor.getString(cursor
						.getColumnIndex("fromName")));
				noti.setFromId(cursor.getString(cursor.getColumnIndex("fromId")));
				notis.add(noti);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return notis;
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return notis;
	}

	@Override
	public ArrayList<NotificationBean> findAllNotifications() {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = null;
		ArrayList<NotificationBean> notis = new ArrayList<NotificationBean>();
		try {
			db = helper.getReadableDatabase();
			Cursor cursor = db.query(TABLE, null, null, null, null, null,
					"update_time DESC");
			while (cursor.moveToNext()) {
				NotificationBean noti = new NotificationBean();
				noti.setId(cursor.getInt(cursor.getColumnIndex("id")));
				noti.setMsg_id(cursor.getLong(cursor.getColumnIndex("msg_id")));
				noti.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				noti.setActivity(cursor.getString(cursor
						.getColumnIndex("activity")));
				noti.setNotificationActionType(cursor.getInt(cursor
						.getColumnIndex("notificationActionType")));
				noti.setContent(cursor.getString(cursor
						.getColumnIndex("content")));
				noti.setUpdate_time(cursor.getString(cursor
						.getColumnIndex("update_time")));
				noti.setFromName(cursor.getString(cursor
						.getColumnIndex("fromName")));
				noti.setFromId(cursor.getString(cursor.getColumnIndex("fromId")));
				notis.add(noti);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return notis;
		} finally {
			if (db != null) {
				db.close();
			}
		}
		return notis;
	}
}
