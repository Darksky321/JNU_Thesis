package com.jnu.thesis.view;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.thesis.R;
import com.jnu.thesis.activity.EditThesisActivity;
import com.jnu.thesis.bean.ThesisBean;

public class TeacherThesisListViewAdapter extends BaseAdapter {

	private Activity activity;
	private List<ThesisBean> theses;

	public TeacherThesisListViewAdapter(Activity activity,
			List<ThesisBean> theses) {
		super();
		this.activity = activity;
		this.theses = theses;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return theses.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return theses.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(activity).inflate(
					R.layout.view_t_thesis, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			viewHolder.button = (ImageButton) convertView
					.findViewById(R.id.button_menu);
			viewHolder.textViewQuantity = (TextView) convertView
					.findViewById(R.id.textView_quantity);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(theses.get(position).getName());
		viewHolder.textViewQuantity.setText("选题人数："
				+ theses.get(position).getChosenQuantity() + "/"
				+ theses.get(position).getCount());
		viewHolder.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				showMenu(v, position);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		public TextView textView;
		public TextView textViewQuantity;
		public ImageButton button;
	}

	public void setData(List<ThesisBean> thesisBeans) {
		theses = thesisBeans;
		notifyDataSetChanged();
	}

	/**
	 * 显示菜单
	 * 
	 * @param button
	 *            在button右下角显示
	 * @param groupPosition
	 *            所选中的button编号
	 */
	private void showMenu(View button, final int groupPosition) {
		activity.getLayoutInflater();
		View v = LayoutInflater.from(activity).inflate(
				R.layout.view_t_thesis_menu, null);
		final PopupWindow pw = new PopupWindow(v, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		pw.setContentView(v);
		pw.setOutsideTouchable(true);
		// 用物理键返回取消, 或者点击外面取消, 需要设置背景
		pw.setBackgroundDrawable(new BitmapDrawable());
		pw.setFocusable(true);
		pw.showAsDropDown(button);
		pw.setClippingEnabled(false);
		ImageButton buttonEdit = (ImageButton) v.findViewById(R.id.button_edit);
		ImageButton buttonDelete = (ImageButton) v
				.findViewById(R.id.button_delete);
		buttonEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
				Intent intent = new Intent();
				intent.setClass(activity, EditThesisActivity.class);
				intent.putExtra("name", theses.get(groupPosition).getName());
				intent.putExtra("count", theses.get(groupPosition).getCount()
						+ "");
				intent.putExtra("detail", theses.get(groupPosition).getDetail());
				intent.putExtra("type", "edit");
				activity.startActivity(intent);
			}
		});
		buttonDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
				if (theses.get(groupPosition).getChosenQuantity() != 0) {
					Toast.makeText(activity, "已有学生选题，不能删除", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
	}
}
