package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.activity.EditThesisActivity;
import com.jnu.thesis.activity.SelectStudentActivity;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.TeacherThesisListViewAdapter;

public class TeacherThesisFragment extends Fragment {

	public static final int THESIS_FAIL = 0;
	public static final int THESIS_SUCCESS = 1;

	private List<ThesisBean> theses;
	private ListView listView;
	private Button buttonRefresh;
	private LinearLayout llLoading;
	private LinearLayout llRefresh;

	private Thread thesisThread;
	private NewThesisReceiver updateListViewReceiver;

	private TeacherThesisListViewAdapter adapter;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			switch (msg.what) {
			case THESIS_FAIL:
				displayRefresh();
				break;

			case THESIS_SUCCESS:
				String thesesString = (String) msg.obj;
				try {
					if (thesesString != null) {
						List<ThesisBean> retTheses = new ArrayList<ThesisBean>();
						JSONObject retJson = new JSONObject(thesesString);
						JSONArray thesesList = retJson.getJSONArray("theses");
						if (retTheses != null) {
							for (int i = 0; i < thesesList.length(); i++) {
								retTheses.add(ThesisBean
										.createFromJSON2((thesesList
												.getJSONObject(i))));
							}
						}
						theses = retTheses;
						adapter.setData(theses);
						displayTheses();
					}
				} catch (Exception e) {
					// TODO: handle exception
					displayRefresh();
				}

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.fragment_t_thesis, container, false);

		thesisThread = new Thread(new ThesisRunnable());
		thesisThread.start();

		initView(v);

		adapter = new TeacherThesisListViewAdapter(getActivity(),
				new ArrayList<ThesisBean>());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent();
				intent.putExtra("thesis", theses.get(position));
				intent.putExtra("getThesisNo", position);
				intent.setClass(getActivity(), SelectStudentActivity.class);
				startActivity(intent);
			}
		});
		// 初始化广播接收器
		updateListViewReceiver = new NewThesisReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.jnu.thesis.activity.NEW_THESIS");
		intentFilter.addAction("com.jnu.thesis.activity.DELETE_THESIS");
		getActivity().registerReceiver(updateListViewReceiver, intentFilter);
		return v;
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		getActivity().unregisterReceiver(updateListViewReceiver);
		super.onDestroy();
	}

	private void initView(View v) {
		listView = (ListView) v.findViewById(R.id.listView_t_thesis);
		buttonRefresh = (Button) v.findViewById(R.id.button_refresh);
		llRefresh = (LinearLayout) v.findViewById(R.id.refresh);
		llLoading = (LinearLayout) v.findViewById(R.id.loading);

		v.findViewById(R.id.button_add).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						Intent intent = new Intent();
						intent.setClass(getActivity(), EditThesisActivity.class);
						intent.putExtra("type", "new");
						startActivity(intent);
					}
				});
		buttonRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				thesisThread = new Thread(new ThesisRunnable());
				thesisThread.start();
				displayLoading();
			}
		});
	}

	/**
	 * 获取论文列表
	 * 
	 * @author Deng
	 *
	 */
	private class ThesisRunnable implements Runnable {

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			HttpUtil httpUtil = new HttpUtil();
			String ret = "";
			Message msg = Message.obtain();
			try {
				ret = httpUtil.doGet(Parameter.host + Parameter.teacherMain,
						null);
				msg.what = THESIS_SUCCESS;
				msg.obj = ret;
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				msg.what = THESIS_FAIL;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	public void displayTheses() {
		adapter.setData(theses);
		llLoading.setVisibility(View.GONE);
		llRefresh.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);
		adapter.notifyDataSetChanged();
	}

	public void displayLoading() {
		llLoading.setVisibility(View.VISIBLE);
		llRefresh.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
	}

	public void displayRefresh() {
		llLoading.setVisibility(View.GONE);
		llRefresh.setVisibility(View.VISIBLE);
		listView.setVisibility(View.GONE);
	}

	public class NewThesisReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			if (intent.getAction().equals(
					"com.jnu.thesis.activity.DELETE_THESIS")) {
				Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT)
						.show();
				thesisThread = new Thread(new ThesisRunnable());
				thesisThread.start();
				displayLoading();
			} else if (intent.getAction().equals(
					"com.jnu.thesis.activity.NEW_THESIS")) {
				thesisThread = new Thread(new ThesisRunnable());
				thesisThread.start();
				displayLoading();
			}
		}
	}

}