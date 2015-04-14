package com.jnu.thesis.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.activity.LoginActivity;
import com.jnu.thesis.activity.SelectedThesisActivity;
import com.jnu.thesis.bean.ThesisBean;
import com.jnu.thesis.dao.UserDao;
import com.jnu.thesis.dao.impl.UserDaoImpl;

public class TeacherMeFragment extends Fragment {

	private TextView textViewMe;
	private Button buttonLogout;
	private List<ThesisBean> myList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_t_me, container, false);
		initView(v);
		return v;
	}

	private void initView(View v) {
		textViewMe = (TextView) v.findViewById(R.id.textView_me);
		buttonLogout = (Button) v.findViewById(R.id.button_logout);

		textViewMe.setText("��ǰ�˺ţ�" + Parameter.getCurrentUser());

		/**
		 * ע���˺�, ɾ�����ݿ���Ϣ
		 */
		buttonLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage("ȷ��Ҫע����");
				builder.setIcon(android.R.drawable.ic_dialog_alert);
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO �Զ����ɵķ������
								// ������ݿ�����
								UserDao dao = UserDaoImpl
										.getInstance(getActivity()
												.getApplicationContext());
								boolean b = dao
										.deleteUser(new String[] { Parameter
												.getCurrentUser() });
								if (b) {
									Log.i("db", "delete user successful");
								} else {
									Log.i("db", "delete user failed");
								}
								// ����ڴ汣����û���Ϣ
								Parameter.clear();
								// ��ע���Ÿ�
								// XingeRegister.unRegist(getActivity()
								// .getApplicationContext());

								Intent intent = new Intent();
								intent.setClass(getActivity(),
										LoginActivity.class);
								startActivity(intent);
								getActivity().finish();
							}
						});
				builder.setNegativeButton("ȡ��", null);
				builder.show();
			}
		});
	}

	public List<ThesisBean> getMyList() {
		return myList;
	}

	public void setMyList(List<ThesisBean> myList) {
		this.myList = myList;
	}
}