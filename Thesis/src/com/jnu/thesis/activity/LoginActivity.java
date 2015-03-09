package com.jnu.thesis.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jnu.thesis.R;
import com.jnu.thesis.util.HttpUtils;

public class LoginActivity extends Activity {

	private static EditText editText_userName;
	private static EditText editText_password;
	private Button button_login;
	private Thread loginThread;
	private Context context;
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				break;
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
	}

	private void init() {
		editText_userName = (EditText) findViewById(R.id.editText_userName);
		editText_password = (EditText) findViewById(R.id.editText_password);
		button_login = (Button) findViewById(R.id.button_login);
		button_login.setOnClickListener(new ButtonLoginOnClickListener());
		loginThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO �Զ����ɵķ������
				HttpUtils util = new HttpUtils("http://192.168.0.1");
				String userName = editText_userName.getText().toString();
				String password = editText_password.getText().toString();
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs
						.add(new BasicNameValuePair("username", userName));
				nameValuePairs
						.add(new BasicNameValuePair("password", password));
				String result = util.postMessage(nameValuePairs);
				Message msg = Message.obtain();
				msg.what = 1;
				handler.sendMessage(msg);
			}

		});

	}

	private class ButtonLoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			// if (!loginThread.isAlive())
			// loginThread.start();
			Intent intent = new Intent();
			intent.setClass(context, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

}