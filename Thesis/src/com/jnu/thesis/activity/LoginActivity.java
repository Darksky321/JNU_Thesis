package com.jnu.thesis.activity;

import java.lang.ref.WeakReference;
import java.util.Map;

import org.json.JSONException;
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
import com.jnu.thesis.dao.impl.UserDaoImpl;
import com.jnu.thesis.service.CallBack;
import com.jnu.thesis.service.LoginThread;
import com.jnu.thesis.util.XingeUtil;

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
	 * 1代表学生登录2代表教师登录
	 */
	private int status = 1;
	/**
	 * 数据库中的用户信息
	 */
	private Map<String, String> user;
	private Thread loginThread;
	/**
	 * 正在登录进度框
	 */
	private ProgressDialog dialog;
	private Handler handler = new LoginHandler(this);

	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		Log.i("mytest", "loginResume:" + this.getTaskId());
		super.onResume();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		status = 1;
		// 初始化控件
		initView();
		// 信鸽初始化
		XingeUtil.initXinge(this);
		// 如果数据库中有账号信息, 则使用该账号登录
		UserDao dao = UserDaoImpl.getInstance(getApplicationContext());
		user = dao.findAllUser();
		if (user != null && !user.isEmpty()) {
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
	 * 获取控件, 初始化控件
	 */
	private void initView() {
		editTextUserName = (EditText) findViewById(R.id.editText_userName);
		editTextPassword = (EditText) findViewById(R.id.editText_password);
		buttonLogin = (Button) findViewById(R.id.button_login);
		textViewTeacherEntry = (TextView) findViewById(R.id.textView_teacherEntry);

		dialog = new ProgressDialog(this);

		/**
		 * 登录
		 */
		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				View view = getWindow().peekDecorView();
				if (view != null) {
					InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					inputmanger.hideSoftInputFromWindow(view.getWindowToken(),
							0);
				}
				if (loginThread == null || !loginThread.isAlive()) {
					dialog.setMessage("登录中...");
					dialog.setIndeterminate(true);
					dialog.setCancelable(false);
					dialog.show();
					loginThread = new LoginThread(editTextUserName.getText()
							.toString(), editTextPassword.getText().toString(),
							status, new CallBack() {

								@Override
								public void onFinish(String ret, Exception e) {
									// TODO 自动生成的方法存根
									Message msg = Message.obtain();
									if (e != null) {
										msg.what = LOGIN_ERROR;
										handler.sendMessage(msg);
										e.printStackTrace();
									} else {
										try {
											JSONObject jResult;
											jResult = new JSONObject(ret);
											String result = jResult
													.getString("result");
											if (result.equals("success")) {
												msg.what = LOGIN_SUCCESS;
											} else if (result.equals("fail"))
												msg.what = LOGIN_FAILED;
											else if (result.equals("notexist"))
												msg.what = LOGIN_NOT_EXIST;
											else
												msg.what = LOGIN_ERROR;
											handler.sendMessage(msg);
										} catch (JSONException e1) {
											// TODO 自动生成的 catch 块
											msg.what = LOGIN_ERROR;
											handler.sendMessage(msg);
											e1.printStackTrace();
										}
									}
								}
							});
					loginThread.start();
				}
			}
		});

		/**
		 * 后门
		 */
		buttonLogin.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO 自动生成的方法存根
				if (status == 1) {
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this,
							TeacherMainActivity.class);
					startActivity(intent);
					finish();
				}
				Parameter.setCurrentUser("Deng");
				Parameter.setStatus(status);
				XingeUtil.regist(getApplicationContext(), "2011051682");
				return true;
			}
		});

		/**
		 * 输入法中按发送键登录
		 */
		editTextPassword
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO 自动生成的方法存根
						switch (actionId) {
						case EditorInfo.IME_ACTION_SEND:
							buttonLogin.performClick();
							break;
						}
						return true;
					}
				});
		textViewTeacherEntry.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线

		/**
		 * 进出教师入口, 调整UI
		 */
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
		// if (status == 1) {
		// textViewTeacherEntry.setText(R.string.teacher_entry);
		// buttonLogin.setText(R.string.button_login);
		// } else if (status == 2) {
		// textViewTeacherEntry.setText(R.string.back);
		// buttonLogin.setText(R.string.button_teacher_login);
		// }
	}

	private static class LoginHandler extends Handler {
		private final WeakReference<LoginActivity> mActivity;

		LoginHandler(LoginActivity mActivity) {
			this.mActivity = new WeakReference<LoginActivity>(mActivity);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			LoginActivity activity = mActivity.get();
			switch (msg.what) {
			case LOGIN_FAILED:
				activity.dialog.dismiss();
				UserDao dao = UserDaoImpl.getInstance(activity
						.getApplicationContext());
				dao.deleteUser(new String[] { activity.getEditTextUserName()
						.getText().toString() });
				Toast.makeText(activity, "登录失败", Toast.LENGTH_SHORT).show();
				break;
			case LOGIN_SUCCESS:
				Intent intent = new Intent();
				if (activity.getStatus() == 1) {
					intent.setClass(activity, MainActivity.class);
				} else if (activity.getStatus() == 2) {
					intent.setClass(activity, TeacherMainActivity.class);
				}
				// 如果数据库中没有账户信息, 则保存该账户
				if (activity.getUser() == null || activity.getUser().isEmpty()) {
					UserDao userDao = UserDaoImpl.getInstance(activity
							.getApplicationContext());
					boolean b;
					b = userDao
							.addUser(new String[] {
									activity.getEditTextUserName().getText()
											.toString(),
									activity.getEditTextPassword().getText()
											.toString(),
									activity.getStatus() + "" });
					if (b) {
						Log.i("db", "write user successful");
					} else {
						Log.i("db", "write user failed");
					}
				}

				// 保存账号信息到内存
				Parameter.setCurrentUser(activity.getEditTextUserName()
						.getText().toString());
				Parameter.setStatus(activity.getStatus());

				// 信鸽注册账号
				XingeUtil.regist(activity.getApplicationContext(), activity
						.getEditTextUserName().getText().toString());

				activity.dialog.dismiss();
				activity.finish();
				activity.startActivity(intent);
				break;
			case LOGIN_NOT_EXIST:
				activity.dialog.dismiss();
				Toast.makeText(activity, "用户不存在", Toast.LENGTH_SHORT).show();
				break;
			case LOGIN_ERROR:
				activity.dialog.dismiss();
				Toast.makeText(activity, "网络错误", Toast.LENGTH_SHORT).show();
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

	public ProgressDialog getDialog() {
		return dialog;
	}

	public void setDialog(ProgressDialog dialog) {
		this.dialog = dialog;
	}

}
