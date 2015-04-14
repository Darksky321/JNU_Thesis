package com.jnu.thesis.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.R.id;
import com.jnu.thesis.R.layout;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.FinishListener;

public class EditNotificationActivity extends Activity {

	private static final int SUBMIT_FAIL = 0;
	private static final int SUBMIT_SUCCESS = 1;

	private EditText editTextTitle;
	private EditText editTextContent;
	private ImageButton button;
	private Thread sendThread;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case SUBMIT_FAIL:
				Toast.makeText(EditNotificationActivity.this, "发送失败",
						Toast.LENGTH_SHORT).show();
				break;
			case SUBMIT_SUCCESS:
				Toast.makeText(EditNotificationActivity.this, "发送成功",
						Toast.LENGTH_SHORT).show();
				EditNotificationActivity.this.finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_notification);
		initView();
	}

	private void initView() {
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
		editTextTitle = (EditText) findViewById(R.id.editText_title);
		editTextTitle = (EditText) findViewById(R.id.editText_content);
		button = (ImageButton) findViewById(R.id.button_send);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (sendThread != null && !sendThread.isAlive()) {
					sendThread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO 自动生成的方法存根
							HttpUtil httpUtil = new HttpUtil();
							Message msg = Message.obtain();
							try {
								Map<String, String> para = new HashMap<String, String>();
								para.put("", editTextTitle.getText().toString());
								para.put("", editTextContent.getText()
										.toString());
								String str = httpUtil.doGet("", null);
								JSONObject jo = new JSONObject(str);
								if (jo.getString("result").equals("success"))
									msg.what = SUBMIT_SUCCESS;
								else
									msg.what = SUBMIT_FAIL;
								handler.sendMessage(msg);
							} catch (Exception e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
								msg.what = SUBMIT_FAIL;
								handler.sendMessage(msg);
							}

						}

					});
				}
			}
		});
	}
}
