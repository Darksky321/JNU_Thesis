package com.jnu.thesis.fragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.EmbeddedListView;
import com.jnu.thesis.view.EmbeddedListViewAdapter;

public class ThesisFragment extends Fragment {

	private static final int SUBMIT_FAIL = 0;
	private static final int SUBMIT_SUCCESS = 1;
	private static final int THESIS_FAIL = 2;
	private static final int THESIS_SUCCESS = 3;

	private Button buttonViewResult;
	private Button buttonSubmit;
	private EmbeddedListView listViewThesis;
	private EmbeddedListViewAdapter listViewAdapter;
	private ScrollView sv;
	private Button buttonRefresh;
	private LinearLayout llLoading;
	private LinearLayout llRefresh;

	private List<ThesisBean> theses;
	private Thread submitThread;
	private Thread thesisThread;
	private Handler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.fragment_thesis, container, false);
		handler = new ThesisHandler(this);
		// 获取论文列表
		thesisThread = new Thread(new ThesisRunnable());
		thesisThread.start();

		// 初始化列表
		initView(v);

		sv.smoothScrollTo(0, 0); // 否则会直接显示ListView, 不显示上面的Button

		listViewAdapter = new EmbeddedListViewAdapter(getActivity(),
				new ArrayList<ThesisBean>());
		// listViewThesis.setGroupIndicator(getResources().getDrawable(
		// R.drawable.expand_list_indicator));
		// listViewThesis.setIndicatorBounds(48, 48);
		listViewThesis.setGroupIndicator(null);
		listViewThesis.setAdapter(listViewAdapter);

		return v;
	}

	private void initView(View v) {
		sv = (ScrollView) v.findViewById(R.id.scrollView_thesis);
		listViewThesis = (EmbeddedListView) v
				.findViewById(R.id.listView_thesis);
		llLoading = (LinearLayout) v.findViewById(R.id.loading);
		llRefresh = (LinearLayout) v.findViewById(R.id.refresh);
		buttonRefresh = (Button) v.findViewById(R.id.button_refresh);
		buttonViewResult = (Button) v.findViewById(R.id.button_view_result);
		buttonSubmit = (Button) v.findViewById(R.id.button_submit);

		buttonRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				thesisThread = new Thread(new ThesisRunnable());
				thesisThread.start();
				llRefresh.setVisibility(View.GONE);
				llLoading.setVisibility(View.VISIBLE);
			}
		});

		buttonViewResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "查看选题结果你妹", Toast.LENGTH_SHORT)
						.show();
			}
		});

		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (submitThread != null && submitThread.isAlive()) {
					Toast.makeText(getActivity(), "正在提交...", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				final int[] choices = listViewAdapter.getChoices();
				if (choices[0] < 0 || choices[1] < 0 || choices[2] < 0) {
					Toast.makeText(getActivity(), "请选择三个课题", Toast.LENGTH_SHORT)
							.show();
				} else if (submitThread == null || !submitThread.isAlive()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle(R.string.button_select);
					builder.setMessage("第一志愿："
							+ theses.get(choices[0]).getName() + "\n第二志愿："
							+ theses.get(choices[1]).getName() + "\n第三志愿："
							+ theses.get(choices[2]).getName());
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO 自动生成的方法存根
									Toast.makeText(getActivity(), "正在提交...",
											Toast.LENGTH_SHORT).show();
									submitThread = new Thread(
											new SubmitRunnable());
									submitThread.start();
								}
							});
					builder.setNegativeButton("取消", null);
					builder.show();
				}
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
				ret = httpUtil.doGet(Parameter.host + Parameter.studentMain,
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

	/**
	 * 获取论文列表
	 * 
	 * @author Deng
	 *
	 */
	private class SubmitRunnable implements Runnable {

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			int[] choices = listViewAdapter.getChoices();
			String first = theses.get(choices[0]).getNo();
			String second = theses.get(choices[1]).getNo();
			String third = theses.get(choices[2]).getNo();
			Map<String, String> para = new HashMap<String, String>();
			para.put("Fir_choice", first);
			para.put("Sec_choice", second);
			para.put("Thir_choice", third);
			HttpUtil httpUtil = new HttpUtil();
			try {
				String response = httpUtil.doPost(Parameter.host
						+ Parameter.submitChoice, para);
				JSONObject jsonObject = new JSONObject(response);
				String result = jsonObject.getString("result");
				Message msg = Message.obtain();
				if (result != null && result.equals("success")) {
					msg.what = SUBMIT_SUCCESS;
				} else {
					msg.what = SUBMIT_FAIL;
				}
				handler.sendMessage(msg);
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				Message msg = Message.obtain();
				msg.what = SUBMIT_FAIL;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}

	}

	private static class ThesisHandler extends Handler {
		private final WeakReference<ThesisFragment> mFragment;

		ThesisHandler(ThesisFragment mFragment) {
			this.mFragment = new WeakReference<ThesisFragment>(mFragment);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO 自动生成的方法存根
			ThesisFragment fragment = mFragment.get();

			switch (msg.what) {
			case SUBMIT_FAIL:
				Toast.makeText(fragment.getActivity(), "提交成功",
						Toast.LENGTH_SHORT).show();
				break;
			case SUBMIT_SUCCESS:
				Toast.makeText(fragment.getActivity(), "提交失败",
						Toast.LENGTH_SHORT).show();
				break;
			case THESIS_FAIL:
				Toast.makeText(fragment.getActivity(), "获取列表失败",
						Toast.LENGTH_SHORT).show();
				fragment.getLlLoading().setVisibility(View.GONE);
				fragment.getLlRefresh().setVisibility(View.VISIBLE);
				break;
			case THESIS_SUCCESS:
				String thesesString = (String) msg.obj;
				try {
					if (thesesString != null) {
						List<ThesisBean> theses = new ArrayList<ThesisBean>();
						JSONObject retJson = new JSONObject(thesesString);
						JSONArray jsonArray = retJson.getJSONArray("theses");
						for (int i = 0; i < jsonArray.length(); i++) {
							theses.add(ThesisBean.createFromJSON((jsonArray
									.getJSONObject(i))));
						}
						fragment.getLlLoading().setVisibility(View.GONE);
						fragment.getSv().setVisibility(View.VISIBLE);
						fragment.getListViewAdapter().setData(theses);
						fragment.getListViewAdapter().notifyDataSetChanged();
					}
				} catch (JSONException e) {
					// TODO 自动生成的 catch 块
					Toast.makeText(fragment.getActivity(), "获取列表失败",
							Toast.LENGTH_SHORT).show();
					fragment.getLlLoading().setVisibility(View.GONE);
					fragment.getLlRefresh().setVisibility(View.VISIBLE);
					e.printStackTrace();
				}
				break;
			}
			super.handleMessage(msg);
		}
	}

	private static List<ThesisBean> getThesesData() {
		List<ThesisBean> theses = new ArrayList<ThesisBean>();
		theses.add(new ThesisBean("1", "毕业论文指导系统", "孟小华", 2,
				"辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选", 0,
				"date"));
		theses.add(new ThesisBean("2", "我的题目很长很长很长很长很长很长很长很长很长很长很长很长很长很长",
				"邓舜光", 1, "我的题目不可能这么长", 1, "date"));
		theses.add(new ThesisBean("3", "我不会做毕业设计怎么想都是你们的错", "nico", 1, "怎样也好了",
				0, "date"));
		theses.add(new ThesisBean("4", "我的毕业设计很坑", "d", 1, "我的后宫很多", 1, "date"));
		theses.add(new ThesisBean("5", "我们仍未知道那天所做的毕业设计的名字", "a1", 1, "找到你了!",
				0, "date"));
		theses.add(new ThesisBean("6", "test6", "f", 1, "bbbb", 1, "date"));
		theses.add(new ThesisBean("7", "test7", "g", 1, "aaaa", 0, "date"));
		theses.add(new ThesisBean("8", "test8", "h", 1, "zcvx", 1, "date"));
		theses.add(new ThesisBean("9", "test9", "i", 1, "zzzz", 0, "date"));
		theses.add(new ThesisBean("10", "test10", "j", 1, "cxzdsa", 1, "date"));
		return theses;
	}

	public List<ThesisBean> getTheses() {
		return theses;
	}

	public void setTheses(List<ThesisBean> theses) {
		this.theses = theses;
	}

	public EmbeddedListViewAdapter getListViewAdapter() {
		return listViewAdapter;
	}

	public void setListViewAdapter(EmbeddedListViewAdapter listViewAdapter) {
		this.listViewAdapter = listViewAdapter;
	}

	public ScrollView getSv() {
		return sv;
	}

	public void setSv(ScrollView sv) {
		this.sv = sv;
	}

	public Button getButtonRefresh() {
		return buttonRefresh;
	}

	public void setButtonRefresh(Button buttonRefresh) {
		this.buttonRefresh = buttonRefresh;
	}

	public LinearLayout getLlLoading() {
		return llLoading;
	}

	public void setLlLoading(LinearLayout llLoading) {
		this.llLoading = llLoading;
	}

	public LinearLayout getLlRefresh() {
		return llRefresh;
	}

	public void setLlRefresh(LinearLayout llRefresh) {
		this.llRefresh = llRefresh;
	}
}