package com.jnu.thesis.test;

import java.util.HashMap;
import java.util.Map;

import android.test.AndroidTestCase;
import android.util.Log;

import com.jnu.thesis.dao.impl.MessageDaoImpl;
import com.jnu.thesis.dao.impl.UserDaoImpl;
import com.jnu.thesis.db.DatabaseHelper;
import com.jnu.thesis.util.HttpUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

public class TestCase extends AndroidTestCase {
	private static final String TAG = "mytest";

	public static void test() {
		Log.i(TAG, "test");
	}

	public void loginTest() throws Exception {
		HttpUtil httpUtil = new HttpUtil();
		Map<String, String> para = new HashMap<String, String>();
		para.put("username", "1");
		para.put("password", "456");
		para.put("status", "2");
		String result = httpUtil.doPost(
				"http://192.168.137.1:8080/Thesis_Supervision/login.action",
				para);
		Log.i(TAG, result);
	}

	public void dbTest() {
		DatabaseHelper helper = new DatabaseHelper(getContext());
		helper.getReadableDatabase();
	}

	public void insertTest() {
		UserDaoImpl dao = UserDaoImpl.getInstance(getContext());
		boolean b = dao.addUser(new String[] { "2011051682", "qwerty", "1" });
		Log.i(TAG, b + "");
	}

	public void queryTest() {
		UserDaoImpl dao = UserDaoImpl.getInstance(getContext());
		Map<String, String> person = dao
				.viewPerson(new String[] { "2011051682" });
		Log.i(TAG, person.toString());
	}

	public void updateTest() {
		UserDaoImpl dao = UserDaoImpl.getInstance(getContext());
		boolean b = dao.updateUser("2011051682", new String[] { "qwertyuiop",
				"1" });
		Log.i(TAG, b + "");
	}

	public void deleteText() {
		UserDaoImpl dao = UserDaoImpl.getInstance(getContext());
		boolean b = dao.deleteUser(new String[] { "2011051682" });
		Log.i(TAG, b + "");
	}

	public void xingeRegist() {
		// XGPushManager.registerPush(getContext(), "2011051682");
		// XGPushManager.unregisterPush(getContext());
		XGPushManager.registerPush(getContext(), "2011051682",
				new XGIOperateCallback() {
					@Override
					public void onSuccess(Object data, int flag) {
						Log.d("xinge", "注册成功，设备token为：" + data);
					}

					@Override
					public void onFail(Object data, int errCode, String msg) {
						Log.d("xinge", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
					}
				});
	}

	public void findAllMsg() {
		Log.i(TAG,
				MessageDaoImpl.getInstance(getContext())
						.findAllMessage("2011051682").toString());
	}
}
