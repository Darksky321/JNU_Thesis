package com.jnu.thesis.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.FinishListener;
import com.jnu.thesis.view.SharedFileListAdapter;

public class DownloadActivity extends Activity {

	private ListView listView;
	private static final int GET_FAIL = 0;
	private static final int GET_SUCCESS = 1;
	private static final int DOWNLOAD_SUCCESS = 2;
	private static final int DOWNLOAD_FAIL = 3;
	private static final int ALREADY_EXIST = 4;
	private static SharedFileListAdapter adapter;
	private Thread getThread;
	private static ProgressDialog dialog;
	private DownloadReceiver downloadReceiver;
	private static Context context;

	public static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case GET_FAIL:
				Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
				break;
			case GET_SUCCESS:
				String s = (String) msg.obj;
				try {
					JSONObject jo = new JSONObject(s);
					JSONArray ja = jo.getJSONArray("files");
					String[] files = new String[ja.length()];
					for (int i = 0; i < ja.length(); ++i) {
						files[i] = ja.getString(i);
					}
					adapter.setFileName(files);
					adapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case DOWNLOAD_SUCCESS:
				dialog.dismiss();
				Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
				break;
			case DOWNLOAD_FAIL:
				dialog.dismiss();
				Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
				break;
			case ALREADY_EXIST:
				dialog.dismiss();
				Toast.makeText(context, "文件已存在", Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.activity_download);
		context = this;
		downloadReceiver = new DownloadReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.jnu.thesis.DOWNLOAD_FINISH");
		registerReceiver(downloadReceiver, intentFilter);
		initView();
		adapter = new SharedFileListAdapter(this);
		listView.setAdapter(adapter);
		getThread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO 自动生成的方法存根
				HttpUtil httpUtil = new HttpUtil();
				try {
					String s = httpUtil.doGet(Parameter.host
							+ Parameter.listFile, null);
					Message msg = Message.obtain();
					msg.what = GET_SUCCESS;
					msg.obj = s;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					Message msg = Message.obtain();
					msg.what = GET_FAIL;
					handler.sendMessage(msg);
				}
			}

		});
		getThread.start();
	}

	@Override
	protected void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();
		unregisterReceiver(downloadReceiver);
	}

	private void initView() {
		findViewById(R.id.img).setOnClickListener(new FinishListener(this));
		findViewById(R.id.imageView_back).setOnClickListener(
				new FinishListener(this));
		listView = (ListView) findViewById(R.id.listView_net);
	}

	public void showDialog() {
		dialog = new ProgressDialog(DownloadActivity.this);
		dialog.setMessage("正在下载");
		dialog.show();
	}

	private class DownloadReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO 自动生成的方法存根
			if (intent.getAction().equals("com.jnu.thesis.DOWNLOAD_FINISH")) {
				dialog.dismiss();
			}
		}

	}
}
