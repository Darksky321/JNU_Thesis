package com.jnu.thesis.dao.impl;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jnu.thesis.dao.UserDao;
import com.jnu.thesis.db.DatabaseHelper;

public class UserDaoImpl implements UserDao {

	private DatabaseHelper helper = null;
	private static final String TABLE = "user";
	private static UserDaoImpl instance;

	private UserDaoImpl(Context context) {
		helper = DatabaseHelper.getInstance(context);
	}

	public synchronized static UserDaoImpl getInstance(Context context) {
		if (null == instance) {
			instance = new UserDaoImpl(context);
		}
		return instance;
	}

	/**
	 * 增加用户
	 * 
	 * @param params
	 *            用户属性 id, password, status
	 * @return
	 */
	@Override
	public boolean addUser(String[] params) {
		// TODO 自动生成的方法存根
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("id", (String) params[0]);
			values.put("password", params[1]);
			values.put("status", params[2]);
			long row = database.insert(TABLE, null, values);
			if (row > 0)
				flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	/**
	 * 删除用户
	 * 
	 * @param params
	 *            id
	 * @return
	 */
	@Override
	public boolean deleteUser(String[] params) {
		// TODO 自动生成的方法存根
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("id", (String) params[0]);
			long row = database.delete(TABLE, "id=?", params);
			if (row > 0)
				flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	/**
	 * 
	 * @id 用户id
	 * @param params
	 *            password, status
	 * @return
	 */
	@Override
	public boolean updateUser(String id, String[] params) {
		// TODO 自动生成的方法存根
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("password", params[0]);
			values.put("status", params[1]);
			long row = database.update(TABLE, values, "id=?",
					new String[] { id });
			if (row > 0)
				flag = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return flag;
	}

	/**
	 * 查找用户
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, String> viewPerson(String[] selectionArgs) {
		// TODO 自动生成的方法存根
		SQLiteDatabase database = null;
		Map<String, String> person = new HashMap<String, String>();
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.query(TABLE, null, "id=?", selectionArgs,
					null, null, null);
			while (cursor.moveToNext()) {
				person.put("id", selectionArgs[0]);
				person.put("password",
						cursor.getString(cursor.getColumnIndex("password")));
				person.put("status",
						cursor.getInt(cursor.getColumnIndex("status")) + "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return person;
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return person;
	}

	@Override
	public Map<String, String> findAllUser() {
		// TODO 自动生成的方法存根
		SQLiteDatabase database = null;
		Map<String, String> person = new HashMap<String, String>();
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.query(TABLE, null, null, null, null, null,
					null);
			while (cursor.moveToNext()) {
				person.put("id", cursor.getString(cursor.getColumnIndex("id")));
				person.put("password",
						cursor.getString(cursor.getColumnIndex("password")));
				person.put("status",
						cursor.getInt(cursor.getColumnIndex("status")) + "");
			}
		} catch (Exception e) {
			// TODO: handle exception
			return person;
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return person;
	}

	@Override
	public String getCurrentUserId() {
		// TODO 自动生成的方法存根
		SQLiteDatabase database = null;
		String id = "";
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.query(TABLE, null, null, null, null, null,
					null);
			while (cursor.moveToNext()) {
				id = cursor.getString(cursor.getColumnIndex("id"));
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return id;
	}
}
