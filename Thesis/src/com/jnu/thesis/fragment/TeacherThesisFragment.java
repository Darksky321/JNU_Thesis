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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jnu.thesis.R;
import com.jnu.thesis.SelectStudentActivity;
import com.jnu.thesis.bean.ThesisBean;

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
		listView.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, thesesName));
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
				"������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ������ѡ"));
		theses.add(new ThesisBean("2", "�ҵ���Ŀ�ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ��ܳ�", "��˴��",
				1, "�ҵ���Ŀ��������ô��"));
		theses.add(new ThesisBean("3", "�Ҳ�������ҵ�����ô�붼�����ǵĴ�", "nico", 1, "����Ҳ����"));
		theses.add(new ThesisBean("4", "�ҵı�ҵ��ƺܿ�", "d", 1, "�ҵĺ󹬺ܶ�"));
		theses.add(new ThesisBean("5", "������δ֪�����������ı�ҵ��Ƶ�����", "a1", 1, "�ҵ�����!"));
		theses.add(new ThesisBean("6", "test6", "f", 1, "bbbb"));
		theses.add(new ThesisBean("7", "test7", "g", 1, "aaaa"));
		theses.add(new ThesisBean("8", "test8", "h", 1, "zcvx"));
		theses.add(new ThesisBean("9", "test9", "i", 1, "zzzz"));
		theses.add(new ThesisBean("10", "test10", "j", 1, "cxzdsa"));
		return theses;
	}
}