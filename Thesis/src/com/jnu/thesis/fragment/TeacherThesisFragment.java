package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
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
	private List<String> thesesName;
	private ListView listView;
	private Button buttonRefresh;
	private LinearLayout llLoading;
	private LinearLayout llRefresh;

	private Thread thesisThread;

	private TeacherThesisListViewAdapter adapter;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO �Զ����ɵķ������
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
				new ArrayList<String>());
		listView.setAdapter(adapter);

		theses = getThesesData();
		thesesName = new ArrayList<String>();
		for (ThesisBean t : theses) {
			thesesName.add(t.getName());
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent();
				intent.putExtra("thesis", theses.get(position));
				intent.setClass(getActivity(), SelectStudentActivity.class);
				startActivity(intent);
			}
		});
		return v;
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
						// TODO �Զ����ɵķ������
						Intent intent = new Intent();
						intent.setClass(getActivity(), EditThesisActivity.class);
						startActivity(intent);
					}
				});
		buttonRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				thesisThread = new Thread(new ThesisRunnable());
				thesisThread.start();
				displayLoading();
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
				ret = httpUtil.doGet(Parameter.host + Parameter.teacherMain,
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

	private List<ThesisBean> getThesesData() {
		List<ThesisBean> theses = new ArrayList<ThesisBean>();
		theses.add(new ThesisBean("1", "��ҵ����ָ��ϵͳ", "��С��", 2,
				"������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ", 0,
				"date"));
		theses.add(new ThesisBean("2", "�ҵ���Ŀ�ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ�",
				"��˴��", 1, "�ҵ���Ŀ��������ô��", 1, "date"));
		theses.add(new ThesisBean("3", "�Ҳ�������ҵ�����ô�붼�����ǵĴ�", "nico", 1, "����Ҳ����",
				0, "date"));
		theses.add(new ThesisBean("4", "�ҵı�ҵ��ƺܿ�", "d", 1, "�ҵĺ󹬺ܶ�", 1, "date"));
		theses.add(new ThesisBean("5", "������δ֪�����������ı�ҵ��Ƶ�����", "a1", 1, "�ҵ�����!",
				0, "date"));
		theses.add(new ThesisBean("6", "test6", "f", 1, "bbbb", 1, "date"));
		theses.add(new ThesisBean("7", "test7", "g", 1, "aaaa", 0, "date"));
		theses.add(new ThesisBean("8", "test8", "h", 1, "zcvx", 1, "date"));
		theses.add(new ThesisBean("9", "test9", "i", 1, "zzzz", 0, "date"));
		theses.add(new ThesisBean("10", "test10", "j", 1, "cxzdsa", 1, "date"));
		return theses;
	}
}