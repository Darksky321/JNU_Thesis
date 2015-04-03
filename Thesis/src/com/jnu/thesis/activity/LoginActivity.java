package com.jnu.thesis.activity;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.dao.UserDao;
import com.jnu.thesis.util.HttpUtil;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

public class LoginActivity extends Activity {

	public static final int LOGIN_SUCCESS = 1;
	public static final int LOGIN_FAILED = 2;
	public static final int LOGIN_NOT_EXIST = 3;
	public static final int LOGIN_ERROR = 4;
	private EditText editTextUserName;
	private EditText editTextPassword;
	private Button buttonLogin;
	private TextView textViewTeacherEntry;
	/**
	 * 1����ѧ����½2�����ʦ��½
	 */
	private int status = 1;
	/**
	 * ���ݿ��е��û���Ϣ
	 */
	private Map<String, String> user;
	private Thread loginThread;
	private Runnable loginRunnable;
	private static Context context;
	/**
	 * ���ڵ�½���ȿ�
	 */
	private static ProgressDialog dialog;
	private Handler handler = new LoginHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context = this;
		status = 1;
		// ��ʼ���ؼ�
		initView();
		// �Ÿ��ʼ��
		InitXinge();
		// ������ݿ������˺���Ϣ, ��ʹ�ø��˺ŵ�½
		UserDao dao = new UserDao(this);
		user = dao.findAllUser();
		if (user != null & !user.isEmpty()) {
			editTextUserName.setText(user.get("id"));
			editTextPassword.setText(user.get("password"));
			int sta = Integer.valueOf(user.get("status"));
			if (sta == 2) {
				textViewTeacherEntry.performClick();
			}
			buttonLogin.performClick();
		}
	}

	/**
	 * ��ȡ�ؼ�, ��ʼ���ؼ�
	 */
	private void initView() {
		editTextUserName = (EditText) findViewById(R.id.editText_userName);
		editTextPassword = (EditText) findViewById(R.id.editText_password);
		buttonLogin = (Button) findViewById(R.id.button_login);
		textViewTeacherEntry = (TextView) findViewById(R.id.textView_teacherEntry);

		dialog = new ProgressDialog(this);

		/**
		 * ��½�߳�
		 */
		loginRunnable = new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				// try {
				// Thread.currentThread();
				// Thread.sleep(2000);
				// } catch (InterruptedException e1) {
				// // TODO �Զ����ɵ� catch ��
				// e1.printStackTrace();
				// } // ��Ц��
				HttpUtil util = new HttpUtil();
				String userName = editTextUserName.getText().toString();
				String password = editTextPassword.getText().toString();
				Map<String, String> para = new HashMap<String, String>();
				para.put("username", userName);
				para.put("password", password);
				para.put("status", status + "");
				JSONObject jResult = null;
				String response;
				Message msg = Message.obtain();
				try {
					response = util.doPost(Parameter.host
							+ Parameter.loginAction, para);
					jResult = new JSONObject(response);
					String result = jResult.getString("result");
					if (result.equals("success")) {
						msg.what = LOGIN_SUCCESS;
						if (status == 1) {
							String thesesString = jResult.getString("theses");
							msg.obj = thesesString;
						}
					} else if (result.equals("fail"))
						msg.what = LOGIN_FAILED;
					else if (result.equals("notexist"))
						msg.what = LOGIN_NOT_EXIST;
					else
						msg.what = LOGIN_ERROR;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO �Զ����ɵ� catch ��
					msg.what = LOGIN_ERROR;
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}
		};

		/**
		 * ��½
		 */
		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				View view = getWindow().peekDecorView();
				if (view != null) {
					InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
							0);
				}
				if (loginThread == null || !loginThread.isAlive()) {
					dialog.setMessage("��¼��...");
					dialog.setIndeterminate(true);
					dialog.setCancelable(false);
					dialog.show();
					loginThread = new Thread(loginRunnable);
					loginThread.start();
				}
			}
		});

		/**
		 * ����
		 */
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
				Parameter.setCurrentUser(user.get("id"));
				Parameter.setStatus(Integer.valueOf(user.get("status")));
				return true;
			}
		});

		/**
		 * ���뷨�а����ͼ���½
		 */
		editTextPassword
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO �Զ����ɵķ������
						switch (actionId) {
						case EditorInfo.IME_ACTION_SEND:
							buttonLogin.performClick();
							break;
						}
						return true;
					}
				});
		textViewTeacherEntry.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // �»���

		/**
		 * ������ʦ���, ����UI
		 */
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
		// if (status == 1) {
		// textViewTeacherEntry.setText(R.string.teacher_entry);
		// buttonLogin.setText(R.string.button_login);
		// } else if (status == 2) {
		// textViewTeacherEntry.setText(R.string.back);
		// buttonLogin.setText(R.string.button_teacher_login);
		// }
	}

	/**
	 * ��ʼ���Ÿ�
	 */
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

	private static class LoginHandler extends Handler {
		private final WeakReference<LoginActivity> mActivity;

		LoginHandler(LoginActivity mActivity) {
			this.mActivity = new WeakReference<LoginActivity>(mActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO �Զ����ɵķ������
			LoginActivity activity = mActivity.get();
			switch (msg.what) {
			case LOGIN_FAILED:
				dialog.dismiss();
				Toast.makeText(activity, "��½ʧ��", Toast.LENGTH_SHORT).show();
				break;
			case LOGIN_SUCCESS:
				Intent intent = new Intent();
				if (activity.getStatus() == 1) {
					intent.setClass(activity, MainActivity.class);
					String thesesString = (String) msg.obj;
					intent.putExtra("theses", thesesString);
				} else if (activity.getStatus() == 2) {
					intent.setClass(activity, TeacherMainActivity.class);
				}
				// ������ݿ���û���˻���Ϣ, �򱣴���˻�
				if (activity.getUser() == null || activity.getUser().isEmpty()) {
					UserDao dao = new UserDao(activity);
					boolean b;
					b = dao.addUser(new String[] {
							activity.getEditTextUserName().getText().toString(),
							activity.getEditTextPassword().getText().toString(),
							activity.getStatus() + "" });
					if (b) {
						Log.i("db", "write user successful");
					} else {
						Log.i("db", "write user failed");
					}
				}
				Parameter.setCurrentUser(activity.getEditTextUserName()
						.getText().toString());
				Parameter.setStatus(activity.getStatus());
				dialog.dismiss();
				activity.finish();
				activity.startActivity(intent);
				break;
			case LOGIN_NOT_EXIST:
				dialog.dismiss();
				Toast.makeText(activity, "�û�������", Toast.LENGTH_SHORT).show();
				break;
			case LOGIN_ERROR:
				dialog.dismiss();
				Toast.makeText(activity, "�������", Toast.LENGTH_SHORT).show();
				break;
			}
			super.handleMessage(msg);
		}
	}

	public EditText getEditTextUserName() {
		return editTextUserName;
	}

	public void setEditTextUserName(EditText editTextUserName) {
		this.editTextUserName = editTextUserName;
	}

	public EditText getEditTextPassword() {
		return editTextPassword;
	}

	public void setEditTextPassword(EditText editTextPassword) {
		this.editTextPassword = editTextPassword;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Map<String, String> getUser() {
		return user;
	}

	public void setUser(Map<String, String> user) {
		this.user = user;
	}

}
