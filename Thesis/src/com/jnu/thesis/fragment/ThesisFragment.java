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
import android.widget.ScrollView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.view.EmbeddedListView;
import com.jnu.thesis.view.EmbeddedListViewAdapter;

public class ThesisFragment extends Fragment {
	private Button buttonViewResult;
	private EmbeddedListView listViewThesis;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.thesis_fragment, container, false);
		buttonViewResult = (Button) v.findViewById(R.id.button_view_result);
		buttonViewResult.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "查看选题结果你妹", Toast.LENGTH_SHORT)
						.show();
			}
		});

		ScrollView sv = (ScrollView) v.findViewById(R.id.thesis_fragment);
		sv.smoothScrollTo(0, 0); // 否则会直接显示ListView, 不显示上面的Button

		listViewThesis = (EmbeddedListView) v
				.findViewById(R.id.listView_thesis);
		listViewThesis.setAdapter(new EmbeddedListViewAdapter(getActivity(),
				getThesesData()));
		return v;
	}

	private List<ThesisBean> getThesesData() {
		List<ThesisBean> theses = new ArrayList<ThesisBean>();
		theses.add(new ThesisBean(1, "毕业论文指导系统", "孟小华", 2, "辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选辣鸡来选"));
		theses.add(new ThesisBean(2, "我的题目很长很长很长很长很长很长很长很长很长很长很长很长很长很长", "邓舜光", 1, "我的题目不可能这么长"));
		theses.add(new ThesisBean(3, "test3", "c", 1, "adfsadfwe"));
		theses.add(new ThesisBean(4, "test4", "d", 1, "werw"));
		theses.add(new ThesisBean(5, "test5", "e", 1, "wwww"));
		theses.add(new ThesisBean(6, "test6", "f", 1, "bbbb"));
		theses.add(new ThesisBean(7, "test7", "g", 1, "aaaa"));
		theses.add(new ThesisBean(8, "test8", "h", 1, "zcvx"));
		theses.add(new ThesisBean(9, "test9", "i", 1, "zzzz"));
		theses.add(new ThesisBean(10, "test10", "j", 1, "cxzdsa"));
		return theses;
	}
}