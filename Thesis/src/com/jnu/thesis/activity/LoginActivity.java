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
	private int status = 1; // 1����ѧ����½2�����ʦ��½
	private Thread loginThread;
	private Runnable loginRunnable;
	private static Context context;
	private static ProgressDialog dialog;
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN_FAILED:
				dialog.dismiss();
				Toast.makeText(context, "��½ʧ��", Toast.LENGTH_SHORT).show();
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
		// �Ÿ��ʼ��
		InitXinge();
		dialog = new ProgressDialog(this);
		loginRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				try {
					Thread.currentThread();
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				} // ��Ц��
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
					// TODO �Զ����ɵ� catch ��
					msg.what = LOGIN_FAILED;
					handler.sendMessage(msg);
				}
			}
		};
		buttonLogin.setOnClickListener(new ButtonLoginOnClickListener());
		buttonLogin.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO �Զ����ɵķ������
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
				// TODO �Զ����ɵķ������
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
		// ����logcat���������debug������ʱ��ر�
		// XGPushConfig.enableDebug(this, true);
		// �����Ҫ֪��ע���Ƿ�ɹ�����ʹ��registerPush(getApplicationContext(),
		// XGIOperateCallback)��callback�汾
		// �����Ҫ���˺ţ���ʹ��registerPush(getApplicationContext(),account)�汾
		// ����ɲο���ϸ�Ŀ���ָ��
		// ���ݵĲ���ΪApplicationContext
		Context context = getApplicationContext();
		XGPushManager.registerPush(context);

		// 2.36����������֮ǰ�İ汾��Ҫ��������2�д���
		Intent service = new Intent(context, XGPushService.class);
		context.startService(service);

		// �������õ�API��
		// ���˺ţ�������ע�᣺registerPush(context,account)��registerPush(context,account,
		// XGIOperateCallback)������accountΪAPP�˺ţ�����Ϊ�����ַ�����qq��openid���������������ҵ��һ��Ҫע���ն����̨����һ�¡�
		// ȡ�����˺ţ���������registerPush(context,"*")����account="*"Ϊȡ���󶨣����󣬸���Ը��˺ŵ����ͽ�ʧЧ
		// ��ע�ᣨ���ٽ�����Ϣ����unregisterPush(context)
		// ���ñ�ǩ��setTag(context, tagName)
		// ɾ����ǩ��deleteTag(context, tagName)
	}

	private class ButtonLoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			if (loginThread == null || !loginThread.isAlive()) {
				dialog.setMessage("��¼��...");
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
