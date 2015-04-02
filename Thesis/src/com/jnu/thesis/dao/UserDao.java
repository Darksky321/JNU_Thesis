package com.jnu.thesis.dao;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jnu.thesis.db.DatabaseHelper;
import com.jnu.thesis.service.UserService;

public class UserDao implements UserService {

	private DatabaseHelper helper = null;
	private static final String table = "user";

	public UserDao(Context context) {
		helper = new DatabaseHelper(context);
	}

	/**
	 * �����û�
	 * 
	 * @param params
	 *            �û�����
	 * @return
	 */
	@Override
	public boolean addUser(String[] params) {
		// TODO �Զ����ɵķ������
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("id", (String) params[0]);
			values.put("password", params[1]);
			values.put("status", params[2]);
			long row = database.insert(table, null, values);
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
	 * ɾ���û�
	 * 
	 * @param params
	 *            id
	 * @return
	 */
	@Override
	public boolean deleteUser(String[] params) {
		// TODO �Զ����ɵķ������
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("id", (String) params[0]);
			long row = database.delete(table, "id=?", params);
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
	 * @id �û�id
	 * @param params
	 *            password, status
	 * @return
	 */
	@Override
	public boolean updateUser(String id, String[] params) {
		// TODO �Զ����ɵķ������
		boolean flag = false;
		SQLiteDatabase database = null;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("password", params[0]);
			values.put("status", params[1]);
			long row = database.update(table, values, "id=?",
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
	 * �����û�
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, String> viewPerson(String[] selectionArgs) {
		// TODO �Զ����ɵķ������
		SQLiteDatabase database = null;
		Map<String, String> person = new HashMap<String, String>();
		try {
			database = helper.getReadableDatabase();
			Cursor cursor = database.query(table, null, "id=?", selectionArgs,
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
		} finally {
			if (database != null) {
				database.close();
			}
		}
		return person;
	}

	@Override
	public Map<String, String> findAllUser() {
		// TODO �Զ����ɵķ������
		return null;
	}

}
