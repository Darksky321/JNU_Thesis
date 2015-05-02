package com.jnu.thesis.view;

import android.app.Activity;
import android.os.Environment;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.R;
import com.jnu.thesis.activity.DownloadActivity;
import com.jnu.thesis.util.FileUtil;

public class SharedFileListAdapter extends BaseAdapter {

	private String[] fileName;
	private Activity activity;

	public SharedFileListAdapter(Activity activity) {
		this.activity = activity;
		fileName = new String[] {};
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
			convertView = LayoutInflater.from(activity).inflate(
					R.layout.view_share_file_list, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.list_item);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.list_image);
			viewHolder.button = (ImageButton) convertView
					.findViewById(R.id.list_button);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textView.setText(fileName[position]);
		// viewHolder.textView.setTextSize(25);
		// viewHolder.textView.setTextColor(Color.BLACK);
		String fn = fileName[position];
		if (fn.substring(fn.lastIndexOf(".") + 1, fn.length()).equals(fn)) {
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

		viewHolder.button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				// download
				((DownloadActivity) activity).showDialog();
				// final DownloadInfoBean bean = new DownloadInfoBean(
				// Parameter.host + Parameter.downloadFile + "?fileName="
				// + fileName[position], Environment
				// .getExternalStorageDirectory().toString(), 5);
				// DownloadTask dt;
				// try {
				// dt = new DownloadTask(bean, activity);
				// dt.start();
				// } catch (IOException e) {
				// // TODO 自动生成的 catch 块
				// e.printStackTrace();
				// }
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO 自动生成的方法存根
						int b = FileUtil.downloadFile(Parameter.host
								+ Parameter.downloadFile + "?fileName="
								+ fileName[position], Environment
								.getExternalStorageDirectory().toString());
						if (b == 1) {
							Message msg = Message.obtain();
							msg.what = 2;
							DownloadActivity.handler.sendMessage(msg);
						} else if (b == 0) {
							Message msg = Message.obtain();
							msg.what = 3;
							DownloadActivity.handler.sendMessage(msg);
						} else if (b == 2) {
							Message msg = Message.obtain();
							msg.what = 4;
							DownloadActivity.handler.sendMessage(msg);
						}
					}

				}).start();
			}
		});

		return convertView;
	}

	public class ViewHolder {
		public ImageView imageView;
		public TextView textView;
		public ImageButton button;
	}

	public String[] getFileName() {
		return fileName;
	}

	public void setFileName(String[] fileName) {
		this.fileName = fileName;
	}

}
