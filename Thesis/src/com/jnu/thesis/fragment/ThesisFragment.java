package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.view.EmbeddedListView;

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
				// TODO �Զ����ɵķ������
				Toast.makeText(getActivity(), "�鿴ѡ��������", Toast.LENGTH_SHORT)
						.show();
			}
		});

		ScrollView sv = (ScrollView) v.findViewById(R.id.thesis_fragment);
		sv.smoothScrollTo(0, 0); // �����ֱ����ʾListView, ����ʾ�����Button

		listViewThesis = (EmbeddedListView) v
				.findViewById(R.id.listView_thesis);
		listViewThesis.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_expandable_list_item_1, getThesisData()));
		return v;
	}

	private List<String> getThesisData() {
		List<String> data = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			data.add("����" + i);
		}
		return data;
	}
}