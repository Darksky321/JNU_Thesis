package com.jnu.thesis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jnu.thesis.bean.MessageBean;
import com.jnu.thesis.dao.MessageDao;
import com.jnu.thesis.db.DatabaseHelper;

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

	@Override
	public boolean hasUnRead() {
		// TODO 自动生成的方法存根
		SQLiteDatabase database = null;
		database = helper.getReadableDatabase();
		Cursor cursor = database.query(TABLE, null, "read=?",
				new String[] { "0" }, null, null, null);
		if (cursor.getCount() == 0) {
			database.close();
			return false;
		} else {
			database.close();
			return true;
		}
	}

	@Override
	public List<MessageBean> findUnRead() {
		// TODO 自动生成的方法存根
		SQLiteDatabase database = null;
		List<MessageBean> list = new ArrayList<MessageBean>();
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.query(TABLE, null, "read=?",
					new String[] { "0" }, null, null, null);
			while (cursor.moveToNext()) {
				MessageBean msg = new MessageBean();
				msg.setContent(cursor.getString(cursor
						.getColumnIndex("content")));
				msg.setFromName(cursor.getString(cursor
						.getColumnIndex("fromName")));
				list.add(msg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return list;
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return list;
	}

	@Override
	public boolean save(String content, String update_time, String fromName,
			String tag, int read) {
		// TODO 自动生成的方法存根
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("content", content);
		values.put("update_time", update_time);
		values.put("content", content);
		values.put("fromName", fromName);
		values.put("tag", tag);
		values.put("read", read);
		long l = db.insert(TABLE, null, values);
		if (db != null)
			db.close();
		if (l >= 0)
			return true;
		else
			return false;
	}
}
