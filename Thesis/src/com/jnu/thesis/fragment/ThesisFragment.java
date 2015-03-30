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

		// 初始化列表
		theses = getThesesData();

		ScrollView sv = (ScrollView) v.findViewById(R.id.scrollView_thesis);
		sv.smoothScrollTo(0, 0); // 否则会直接显示ListView, 不显示上面的Button
		listViewThesis = (EmbeddedListView) v
				.findViewById(R.id.listView_thesis);
		listViewAdapter = new EmbeddedListViewAdapter(getActivity(), theses);
		// listViewThesis.setGroupIndicator(getResources().getDrawable(
		// R.drawable.expand_list_indicator));
		// listViewThesis.setIndicatorBounds(48, 48);
		listViewThesis.setGroupIndicator(null);
		listViewThesis.setAdapter(listViewAdapter);

		// 初始化按钮
		buttonViewResult = (Button) v.findViewById(R.id.button_view_result);
		buttonSubmit = (Button) v.findViewById(R.id.button_submit);

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
									submitThread = new Thread(new Runnable() {

										@Override
										public void run() {
											// TODO 自动生成的方法存根
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
					builder.setNegativeButton("取消", null);
					builder.show();
				}
			}
		});

		return v;
	}

	private List<ThesisBean> getThesesData() {
		List<ThesisBean> theses = new ArrayList<ThesisBean>();
		theses.add(new ThesisBean("1", "毕业论文指导系统", "孟小华", 2,
				"辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选", 0));
		theses.add(new ThesisBean("2", "我的题目很长很长很长很长很长很长很长很长很长很长很长很长很长很长",
				"邓舜光", 1, "我的题目不可能这么长", 1));
		theses.add(new ThesisBean("3", "我不会做毕业设计怎么想都是你们的错", "nico", 1, "怎样也好了",
				0));
		theses.add(new ThesisBean("4", "我的毕业设计很坑", "d", 1, "我的后宫很多", 1));
		theses.add(new ThesisBean("5", "我们仍未知道那天所做的毕业设计的名字", "a1", 1, "找到你了!",
				0));
		theses.add(new ThesisBean("6", "test6", "f", 1, "bbbb", 1));
		theses.add(new ThesisBean("7", "test7", "g", 1, "aaaa", 0));
		theses.add(new ThesisBean("8", "test8", "h", 1, "zcvx", 1));
		theses.add(new ThesisBean("9", "test9", "i", 1, "zzzz", 0));
		theses.add(new ThesisBean("10", "test10", "j", 1, "cxzdsa", 1));
		return theses;
	}
}