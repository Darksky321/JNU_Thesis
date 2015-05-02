package com.jnu.thesis.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String name = "thesis.db";
	private static int version = 1;
	private static DatabaseHelper instance = null;

	public DatabaseHelper(Context context) {
		super(context, name, null, version);
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO 自动生成的构造函数存根
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 自动生成的方法存根
		String sql1 = "CREATE TABLE user(id varchar(20), password varchar(30), status int)";
		String sql2 = "CREATE TABLE notification (id integer primary key autoincrement,msg_id varchar(64),title varchar(128),activity varchar(256),notificationActionType varchar(512),content text,update_time varchar(16),fromName varchar(16),fromId varchar(16),toId varchar(16));";
		String sql3 = "CREATE TABLE message (id integer primary key autoincrement,content text,update_time varchar(16),fromName varchar(16),tag varchar(16),read integer)";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.execSQL(sql3);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO 自动生成的方法存根

	}

	public synchronized static DatabaseHelper getInstance(Context context) {
		if (instance == null) {
			instance = new DatabaseHelper(context);
		}
		return instance;
	}

}
