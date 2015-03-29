package com.jnu.thesis.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.util.HttpUtil;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

public class LoginActivity extends Activity {

	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILED = 2;
	private EditText editTextUserName;
	private EditText editTextPassword;
	private Button buttonLogin;
	private TextView textViewTeacherEntry;
	private int status = 1; // 1代表学生登陆2代表教师登陆
	private Thread loginThread;
	private Runnable loginRunnable;
	private static Context context;
	private static ProgressDialog dialog;
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN_FAILED:
				dialog.dismiss();
				Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
				break;
			case LOGIN_SUCCESS:
				Intent intent = new Intent();
				intent.setClass(context, MainActivity.class);
				context.startActivity(intent);
				((Activity) context).finish();
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		init();
		// 信鸽初始化
		InitXinge();
		dialog = new ProgressDialog(this);
		loginRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				try {
					Thread.currentThread();
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} // 搞笑的
				HttpUtil util = new HttpUtil();
				String userName = editTextUserName.getText().toString();
				String password = editTextPassword.getText().toString();
				Map<String, String> para = new HashMap<String, String>();
				para.put("username", userName);
				para.put("password", password);
				String result = "";
				Message msg = Message.obtain();
				try {
					result = util.doPost(
							Parameter.host + Parameter.loginAction, para);
					if (!result.equals(""))
						msg.what = LOGIN_SUCCESS;
					else
						msg.what = LOGIN_FAILED;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					msg.what = LOGIN_FAILED;
					handler.sendMessage(msg);
				}
			}
		};
		buttonLogin.setOnClickListener(new ButtonLoginOnClickListener());
		buttonLogin.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO 自动生成的方法存根
				if (status == 1) {
					Intent intent = new Intent();
					intent.setClass(context, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent();
					intent.setClass(context, TeacherMainActivity.class);
					startActivity(intent);
					finish();
				}
				return true;
			}
		});
		textViewTeacherEntry.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		textViewTeacherEntry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (status == 1) {
					status = 2;
					textViewTeacherEntry.setText(R.string.back);
					buttonLogin.setText(R.string.button_teacher_login);
				} else {
					status = 1;
					textViewTeacherEntry.setText(R.string.teacher_entry);
					buttonLogin.setText(R.string.button_login);
				}
			}
		});
	}

	private void init() {
		editTextUserName = (EditText) findViewById(R.id.editText_userName);
		editTextPassword = (EditText) findViewById(R.id.editText_password);
		buttonLogin = (Button) findViewById(R.id.button_login);
		textViewTeacherEntry = (TextView) findViewById(R.id.textView_teacherEntry);
	}

	public void InitXinge() {
		// 开启logcat输出，方便debug，发布时请关闭
		// XGPushConfig.enableDebug(this, true);
		// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(),
		// XGIOperateCallback)带callback版本
		// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
		// 具体可参考详细的开发指南
		// 传递的参数为ApplicationContext
		Context context = getApplicationContext();
		XGPushManager.registerPush(context);

		// 2.36（不包括）之前的版本需要调用以下2行代码
		Intent service = new Intent(context, XGPushService.class);
		context.startService(service);

		// 其它常用的API：
		// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account,
		// XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
		// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
		// 反注册（不再接收消息）：unregisterPush(context)
		// 设置标签：setTag(context, tagName)
		// 删除标签：deleteTag(context, tagName)
	}

	private class ButtonLoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO 自动生成的方法存根
			if (loginThread == null || !loginThread.isAlive()) {
				dialog.setMessage("登录中...");
				dialog.setIndeterminate(true);
				dialog.setCancelable(false);
				dialog.show();
				loginThread = new Thread(loginRunnable);
				loginThread.start();
				// Intent intent = new Intent();
				// intent.setClass(context, MainActivity.class);
				// startActivity(intent);
				// finish();
			}
		}

	}
}
