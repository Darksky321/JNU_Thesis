package com.jnu.thesis.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jnu.thesis.R;

public class FileListAdapter extends BaseAdapter {

	private String filePath;
	private String[] fileName;
	private Activity activity;
	private ListView listView;
	private List<Boolean> check;

	public FileListAdapter(Activity activity, ListView listView) {
		this.activity = activity;
		this.listView = listView;
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				File file = new File(filePath, fileName[position]);
				if (file.isDirectory()) {
					setFilePath(file.toString());
				}
			}
		});
		filePath = Environment.getExternalStorageDirectory().toString();
		setFilePath(filePath);
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return fileName.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		// Log.i("uploadactivity","getview begin");
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.list,
					parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.list_item);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.list_image);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.list_checkbox);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textView.setText(fileName[position]);
		// viewHolder.textView.setTextSize(25);
		// viewHolder.textView.setTextColor(Color.BLACK);
		String fn = fileName[position];
		File mFile = new File(filePath, fn);
		if (mFile.isDirectory()) {// fn.substring(fn.lastIndexOf(".") + 1,
									// fn.length()).equals(fn)
			viewHolder.imageView.setImageResource(R.drawable.folder);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"jpg")) {
			viewHolder.imageView.setImageResource(R.drawable.jpeg);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"png")) {
			viewHolder.imageView.setImageResource(R.drawable.png);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"bmp")) {
			viewHolder.imageView.setImageResource(R.drawable.bmp);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"avi")) {
			viewHolder.imageView.setImageResource(R.drawable.avi);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"doc")) {
			viewHolder.imageView.setImageResource(R.drawable.docx);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"docx")) {
			viewHolder.imageView.setImageResource(R.drawable.docx);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"gif")) {
			viewHolder.imageView.setImageResource(R.drawable.gif);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"mov")) {
			viewHolder.imageView.setImageResource(R.drawable.mov);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"mp3")) {
			viewHolder.imageView.setImageResource(R.drawable.mp3);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"mpeg")) {
			viewHolder.imageView.setImageResource(R.drawable.mpeg);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"pdf")) {
			viewHolder.imageView.setImageResource(R.drawable.pdf);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"ppt")) {
			viewHolder.imageView.setImageResource(R.drawable.pptx);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"pptx")) {
			viewHolder.imageView.setImageResource(R.drawable.pptx);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"txt")) {
			viewHolder.imageView.setImageResource(R.drawable.text);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"wma")) {
			viewHolder.imageView.setImageResource(R.drawable.wma);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"wav")) {
			viewHolder.imageView.setImageResource(R.drawable.wav);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"wmv")) {
			viewHolder.imageView.setImageResource(R.drawable.wmv);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"xls")) {
			viewHolder.imageView.setImageResource(R.drawable.xlsx);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"xlsx")) {
			viewHolder.imageView.setImageResource(R.drawable.xlsx);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"zip")) {
			viewHolder.imageView.setImageResource(R.drawable.zip);
		} else if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(
				"rar")) {
			viewHolder.imageView.setImageResource(R.drawable.zip);
		} else {
			viewHolder.imageView.setImageResource(R.drawable.file);
		}

		viewHolder.checkBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Boolean b = check.get(position);
				check.set(position, !b);
			}
		});

		return convertView;
	}

	public class ViewHolder {
		public ImageView imageView;
		public TextView textView;
		public CheckBox checkBox;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		File file = new File(filePath);
		fileName = file.list();
		check = new ArrayList<Boolean>();
		for (int i = 0; i < file.list().length; i++)
			check.add(false);
		notifyDataSetChanged();
	}

	public String getFilePath() {
		return filePath;
	}

	public List<Boolean> getCheck() {
		return check;
	}

	public void setCheck(List<Boolean> check) {
		this.check = check;
	}

	public String[] getFileName() {
		return fileName;
	}

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

}
