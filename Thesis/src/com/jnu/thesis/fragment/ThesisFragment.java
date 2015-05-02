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
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.util.HttpUtil;
import com.jnu.thesis.view.EmbeddedListView;
import com.jnu.thesis.view.EmbeddedListViewAdapter;
import com.tencent.android.tpush.XGPushManager;

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
	private List<ThesisBean> myList;
	private Thread submitThread;
	private Thread thesisThread;
	private Handler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.fragment_thesis, container, false);
		handler = new ThesisHandler(this);
		// ��ȡ�����б�
		thesisThread = new Thread(new ThesisRunnable());
		thesisThread.start();

		// ��ʼ���б�
		initView(v);

		sv.smoothScrollTo(0, 0); // �����ֱ����ʾListView, ����ʾ�����Button

		listViewAdapter = new EmbeddedListViewAdapter(getActivity(),
				new ArrayList<ThesisBean>());
		// listViewThesis.setGroupIndicator(getResources().getDrawable(
		// R.drawable.expand_list_indicator));
		// listViewThesis.setIndicatorBounds(48, 48);
		listViewThesis.setGroupIndicator(null);
		listViewThesis.setAdapter(listViewAdapter);
		listViewAdapter.setListView(listViewThesis);
		listViewThesis.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				for (int i = 0; i < theses.size(); i++) {
					if (groupPosition != i) {
						listViewThesis.collapseGroup(i);
					}
				}
			}

		});

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
				// TODO �Զ����ɵķ������
				thesisThread = new Thread(new ThesisRunnable());
				thesisThread.start();
				displayLoading();
			}
		});

		buttonViewResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Toast.makeText(getActivity(), "�鿴ѡ��������", Toast.LENGTH_SHORT)
						.show();
			}
		});

		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				if (submitThread != null && submitThread.isAlive()) {
					Toast.makeText(getActivity(), "�����ύ...", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				final int[] choices = listViewAdapter.getChoices();
				if (choices[0] < 0 || choices[1] < 0 || choices[2] < 0) {
					Toast.makeText(getActivity(), "��ѡ����������", Toast.LENGTH_SHORT)
							.show();
				} else if (submitThread == null || !submitThread.isAlive()) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle(R.string.button_select);
					builder.setMessage("��һ־Ը��"
							+ theses.get(choices[0]).getName() + "\n�ڶ�־Ը��"
							+ theses.get(choices[1]).getName() + "\n����־Ը��"
							+ theses.get(choices[2]).getName());
					builder.setIcon(android.R.drawable.ic_dialog_alert);
					builder.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO �Զ����ɵķ������
									Toast.makeText(getActivity(), "�����ύ...",
											Toast.LENGTH_SHORT).show();
									submitThread = new Thread(
											new SubmitRunnable());
									submitThread.start();
								}
							});
					builder.setNegativeButton("ȡ��", null);
					builder.show();
				}
			}
		});
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @author Deng
	 *
	 */
	private class ThesisRunnable implements Runnable {

		@Override
		public void run() {
			// TODO �Զ����ɵķ������
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
				// TODO �Զ����ɵ� catch ��
				msg.what = THESIS_FAIL;
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��ȡ�����б�
	 * 
	 * @author Deng
	 *
	 */
	private class SubmitRunnable implements Runnable {

		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			int[] choices = listViewAdapter.getChoices();
			String first = choices[0] + "";
			String second = choices[1] + "";
			String third = choices[2] + "";
			Map<String, String> para = new HashMap<String, String>();
			para.put("Fir_choice", first);
			para.put("Sec_choice", second);
			para.put("Thir_choice", third);
			para.put("first", "on");
			para.put("second", "on");
			para.put("third", "on");
			HttpUtil httpUtil = new HttpUtil();
			try {
				String response = httpUtil.doPost(Parameter.host
						+ Parameter.studentSelect, para);
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
				// TODO �Զ����ɵ� catch ��
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
			// TODO �Զ����ɵķ������
			ThesisFragment fragment = mFragment.get();

			switch (msg.what) {
			case SUBMIT_FAIL:
				Toast.makeText(fragment.getActivity(), "�ύʧ��",
						Toast.LENGTH_SHORT).show();
				break;
			case SUBMIT_SUCCESS:
				fragment.setSubmitButton(true);
				fragment.setMeFragmentData(fragment.getSelectedTheses());
				Toast.makeText(fragment.getActivity(), "�ύ�ɹ�",
						Toast.LENGTH_SHORT).show();
				break;
			case THESIS_FAIL:
				Toast.makeText(fragment.getActivity(), "��ȡ�б�ʧ��",
						Toast.LENGTH_SHORT).show();
				fragment.displayRefresh();
				break;
			case THESIS_SUCCESS:
				String thesesString = (String) msg.obj;
				try {
					if (thesesString != null) {
						List<ThesisBean> retTheses = new ArrayList<ThesisBean>();
						List<ThesisBean> retMyList = new ArrayList<ThesisBean>();
						JSONObject retJson = new JSONObject(thesesString);
						JSONArray thesesList = retJson.getJSONArray("theses");
						JSONArray myList = retJson.getJSONArray("mylist");
						if (retTheses != null) {
							for (int i = 0; i < thesesList.length(); i++) {
								retTheses.add(ThesisBean
										.createFromJSON((thesesList
												.getJSONObject(i))));
							}
						}
						if (retMyList != null) {
							for (int i = 0; i < myList.length(); i++) {
								retMyList.add(ThesisBean.createFromJSON((myList
										.getJSONObject(i))));
							}
						}
						fragment.setTheses(retTheses);
						fragment.setMyList(retMyList);
						if (myList == null || myList.length() == 0) {
							fragment.displayTheses();
							fragment.setSubmitButton(false);
						} else { // ������ѡ, ����ʾ��ѡ����
							fragment.displayTheses();
							fragment.setSubmitButton(true);
							fragment.setMeFragmentData(retMyList);
						}
					} else {

					}
				} catch (JSONException e) {
					// TODO �Զ����ɵ� catch ��
					Toast.makeText(fragment.getActivity(), "��ȡ�б�ʧ��",
							Toast.LENGTH_SHORT).show();
					fragment.displayRefresh();
					e.printStackTrace();
				}
				break;
			}
			super.handleMessage(msg);
		}
	}

	/**
	 * ���Ϊ��, �������ѡ, ��ʾ"�޸�ѡ��" ������ʾ�ύ
	 * 
	 * @param b
	 */
	public void setSubmitButton(boolean b) {
		if (b)
			buttonSubmit.setText(getResources().getString(
					R.string.button_reSelect));
		else
			buttonSubmit.setText(getResources().getString(
					R.string.button_submit));
	}

	public void displayTheses() {
		listViewAdapter.setData(theses);
		buttonSubmit.setVisibility(View.VISIBLE);
		llLoading.setVisibility(View.GONE);
		llRefresh.setVisibility(View.GONE);
		sv.setVisibility(View.VISIBLE);
		listViewAdapter.notifyDataSetChanged();
	}

	public void displayLoading() {
		llLoading.setVisibility(View.VISIBLE);
		llRefresh.setVisibility(View.GONE);
		buttonSubmit.setVisibility(View.GONE);
		sv.setVisibility(View.GONE);
	}

	public void displayRefresh() {
		llLoading.setVisibility(View.GONE);
		llRefresh.setVisibility(View.VISIBLE);
		sv.setVisibility(View.GONE);
		buttonSubmit.setVisibility(View.GONE);
	}

	public List<ThesisBean> getSelectedTheses() {
		int[] choices = listViewAdapter.getChoices();
		List<ThesisBean> list = new ArrayList<ThesisBean>();
		list.add(theses.get(choices[0]));
		list.add(theses.get(choices[1]));
		list.add(theses.get(choices[2]));
		return list;
	}

	public void setMeFragmentData(List<ThesisBean> myList) {
		MeFragment fragment = (MeFragment) getActivity()
				.getSupportFragmentManager().getFragments().get(3);
		fragment.setMyList(myList);
	}

	public EmbeddedListViewAdapter getListViewAdapter() {
		return listViewAdapter;
	}

	public void setListViewAdapter(EmbeddedListViewAdapter listViewAdapter) {
		this.listViewAdapter = listViewAdapter;
	}

	public List<ThesisBean> getTheses() {
		return theses;
	}

	public void setTheses(List<ThesisBean> theses) {
		this.theses = theses;
	}

	public List<ThesisBean> getMyList() {
		return myList;
	}

	public void setMyList(List<ThesisBean> myList) {
		this.myList = myList;
	}

}