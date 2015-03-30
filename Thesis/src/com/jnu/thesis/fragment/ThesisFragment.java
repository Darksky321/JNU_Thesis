package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	private List<ThesisBean> theses;
	private Thread submitThread;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.fragment_thesis, container, false);

		// ��ʼ���б�
		theses = getThesesData();

		ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView_thesis);
		sv.smoothScrollTo(0, 0); // �����ֱ����ʾListView, ����ʾ�����Button
		listViewThesis = (EmbeddedListView) v
				.findViewById(R.id.listView_thesis);
		listViewAdapter = new EmbeddedListViewAdapter(getActivity(), theses);
		// listViewThesis.setGroupIndicator(getResources().getDrawable(
		// R.drawable.expand_list_indicator));
		// listViewThesis.setIndicatorBounds(48, 48);
		listViewThesis.setGroupIndicator(null);
		listViewThesis.setAdapter(listViewAdapter);

		// ��ʼ����ť
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
									submitThread = new Thread(new Runnable() {

										@Override
										public void run() {
											// TODO �Զ����ɵķ������
											String first = theses.get(
													choices[0]).getNo();
											String second = theses.get(
													choices[1]).getNo();
											String third = theses.get(
													choices[2]).getNo();
										}

									});
									// submitThread.start();
								}
							});
					builder.setNegativeButton("ȡ��", null);
					builder.show();
				}
			}
		});

		return v;
	}

	private List<ThesisBean> getThesesData() {
		List<ThesisBean> theses = new ArrayList<ThesisBean>();
		theses.add(new ThesisBean("1", "��ҵ����ָ��ϵͳ", "��С��", 2,
				"������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ", 0));
		theses.add(new ThesisBean("2", "�ҵ���Ŀ�ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ�",
				"��˴��", 1, "�ҵ���Ŀ��������ô��", 1));
		theses.add(new ThesisBean("3", "�Ҳ�������ҵ�����ô�붼�����ǵĴ�", "nico", 1, "����Ҳ����",
				0));
		theses.add(new ThesisBean("4", "�ҵı�ҵ��ƺܿ�", "d", 1, "�ҵĺ󹬺ܶ�", 1));
		theses.add(new ThesisBean("5", "������δ֪�����������ı�ҵ��Ƶ�����", "a1", 1, "�ҵ�����!",
				0));
		theses.add(new ThesisBean("6", "test6", "f", 1, "bbbb", 1));
		theses.add(new ThesisBean("7", "test7", "g", 1, "aaaa", 0));
		theses.add(new ThesisBean("8", "test8", "h", 1, "zcvx", 1));
		theses.add(new ThesisBean("9", "test9", "i", 1, "zzzz", 0));
		theses.add(new ThesisBean("10", "test10", "j", 1, "cxzdsa", 1));
		return theses;
	}
}