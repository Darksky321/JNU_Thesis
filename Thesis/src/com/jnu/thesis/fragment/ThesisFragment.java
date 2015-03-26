package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.view.EmbeddedListView;
import com.jnu.thesis.view.EmbeddedListViewAdapter;

public class ThesisFragment extends Fragment {
	private Button buttonViewResult;
	private Button buttonSubmit;
	private EmbeddedListView listViewThesis;
	private EmbeddedListViewAdapter listViewAdapter;
	private Thread submitThread;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.fragment_thesis, container, false);
		buttonViewResult = (Button) v.findViewById(R.id.button_view_result);
		buttonSubmit = (Button) v.findViewById(R.id.button_submit);

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
				int[] choices = listViewAdapter.getChoices();
				if (choices[0] < 0 || choices[1] < 0 || choices[2] < 0) {
					Toast.makeText(getActivity(), "��ѡ����������", Toast.LENGTH_SHORT)
							.show();
				} else if (submitThread != null && !submitThread.isAlive()) {
					submitThread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO �Զ����ɵķ������

						}

					});
					// submitThread.start();
				}
			}
		});

		ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView_thesis);
		sv.smoothScrollTo(0, 0); // �����ֱ����ʾListView, ����ʾ�����Button

		listViewThesis = (EmbeddedListView) v
				.findViewById(R.id.listView_thesis);
		listViewAdapter = new EmbeddedListViewAdapter(getActivity(),
				getThesesData());
		listViewThesis.setAdapter(listViewAdapter);
		return v;
	}

	private List<ThesisBean> getThesesData() {
		List<ThesisBean> theses = new ArrayList<ThesisBean>();
		theses.add(new ThesisBean(1, "��ҵ����ָ��ϵͳ", "��С��", 2,
				"������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ"));
		theses.add(new ThesisBean(2, "�ҵ���Ŀ�ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ�", "��˴��",
				1, "�ҵ���Ŀ��������ô��"));
		theses.add(new ThesisBean(3, "�Ҳ�������ҵ�����ô�붼�����ǵĴ�", "nico", 1, "����Ҳ����"));
		theses.add(new ThesisBean(4, "�ҵı�ҵ��ƺܿ�", "d", 1, "�ҵĺ󹬺ܶ�"));
		theses.add(new ThesisBean(5, "������δ֪�����������ı�ҵ��Ƶ�����", "a1", 1, "�ҵ�����!"));
		theses.add(new ThesisBean(6, "test6", "f", 1, "bbbb"));
		theses.add(new ThesisBean(7, "test7", "g", 1, "aaaa"));
		theses.add(new ThesisBean(8, "test8", "h", 1, "zcvx"));
		theses.add(new ThesisBean(9, "test9", "i", 1, "zzzz"));
		theses.add(new ThesisBean(10, "test10", "j", 1, "cxzdsa"));
		return theses;
	}
}