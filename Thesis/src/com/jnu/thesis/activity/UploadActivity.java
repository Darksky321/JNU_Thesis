package com.jnu.thesis.activity;

import java.io.File;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.util.FileUtil;
import com.jnu.thesis.view.FileListAdapter;
import com.jnu.thesis.view.FinishListener;

public class UploadActivity extends Activity {

	private ListView listView;
	private FileListAdapter adapter;
	private ProgressDialog dialog;
	private Thread uploadThread;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				try {
					Log.i("filehttp", (String) msg.obj);
					JSONObject jo = new JSONObject((String) msg.obj);
					String res = jo.getString("result");
					if (res.equals("success")) {
						Toast.makeText(UploadActivity.this, "上传成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(UploadActivity.this, "上传失败",
								Toast.LENGTH_SHORT).show();
					}

				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					Toast.makeText(UploadActivity.this, "上传失败",
							Toast.LENGTH_SHORT).show();
					e.printStackTrace();
				}

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
		setContentView(R.layout.activity_upload);
		listView = (ListView) findViewById(R.id.list_file);
		adapter = new FileListAdapter(this, listView);
		listView.setAdapter(adapter);

		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));

		findViewById(R.id.button_upload).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						if (uploadThread == null || !uploadThread.isAlive()) {
							List<Boolean> check = adapter.getCheck();
							for (int i = 0; i < check.size(); ++i) {
								Boolean b = check.get(i);
								final int no = i;
								if (b) {
									dialog = new ProgressDialog(
											UploadActivity.this);
									dialog.setMessage("正在上传");
									dialog.show();
									uploadThread = new Thread(new Runnable() {
										@Override
										public void run() {
											// TODO 自动生成的方法存根
											final File file = new File(adapter
													.getFilePath(), adapter
													.getFileName()[no]);

											// TODO 自动生成的方法存根
											String res = FileUtil
													.uploadFile(file.toString());
											Message msg = Message.obtain();
											msg.what = 1;
											msg.obj = res;
											handler.sendMessage(msg);
										}
									});
									uploadThread.start();
									break;
								}
							}
						} else {
							Toast.makeText(UploadActivity.this, "正在上传",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		String filePath = adapter.getFilePath();
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& !filePath.equals(Environment.getExternalStorageDirectory()
						.getPath())) {
			// When the user center presses, let them pick a contact.
			filePath = filePath.substring(0, filePath.lastIndexOf("/"));
			adapter.setFilePath(filePath);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
		}
		return false;
	}

}
