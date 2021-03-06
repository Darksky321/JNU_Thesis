package com.jnu.thesis.test;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Environment;
import android.test.AndroidTestCase;
import android.util.Log;

import com.jnu.thesis.activity.LoginActivity;
import com.jnu.thesis.dao.impl.NotificationDaoImpl;
import com.jnu.thesis.dao.impl.UserDaoImpl;
import com.jnu.thesis.db.DatabaseHelper;
import com.jnu.thesis.util.FileUtil;
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
						Log.d("xinge", "ע��ɹ����豸tokenΪ��" + data);
					}

					@Override
					public void onFail(Object data, int errCode, String msg) {
						Log.d("xinge", "ע��ʧ�ܣ������룺" + errCode + ",������Ϣ��" + msg);
					}
				});
	}

	public void findAllMsg() {
		Log.i(TAG, NotificationDaoImpl.getInstance(getContext())
				.findAllNotifications("2011051682").toString());
	}

	public void intentUri() {
		Intent intent = new Intent();
		// intent.setAction("com.jnu.thesis.MainActivity");
		// intent.setFlags(0x4020000);
		intent.setClass(getContext(), LoginActivity.class);
		getContext().startActivity(intent);
	}

	public void download() {
		int b = FileUtil
				.downloadFile(
						"http://192.168.137.1:8080/Thesis_Supervision/fileDownload.action?fileName=00275236_p.jpg",
						Environment.getExternalStorageDirectory().toString());
		Log.i(TAG, b + "");
	}
}
