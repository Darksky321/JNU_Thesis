package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.jnu.thesis.R;
import com.jnu.thesis.activity.SelectStudentActivity;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.view.TeacherThesisListViewAdapter;

public class TeacherThesisFragment extends Fragment {
	private List<ThesisBean> theses;
	private List<String> thesesName;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// View v = inflater.inflate(R.layout.thesis_fragment, null);
		View v = inflater.inflate(R.layout.fragment_t_thesis, container, false);
		listView = (ListView) v.findViewById(R.id.listView_t_thesis);
		theses = getThesesData();
		thesesName = new ArrayList<String>();
		for (ThesisBean t : theses) {
			thesesName.add(t.getName());
		}
		listView.setAdapter(new TeacherThesisListViewAdapter(getActivity(),
				(ArrayList<String>) thesesName));
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