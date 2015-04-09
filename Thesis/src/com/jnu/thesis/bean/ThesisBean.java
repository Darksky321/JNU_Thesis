package com.jnu.thesis.bean;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class ThesisBean implements Parcelable {

	private String no;
	private String name;
	private String teacherName;
	private int count;
	private String detail;
	private int status;
	private String postTime;

	public ThesisBean() {
		super();
	}

	public ThesisBean(String no, String name, String teacherName, int count,
			String detail, int status, String postTime) {
		super();
		this.no = no;
		this.name = name;
		this.teacherName = teacherName;
		this.count = count;
		this.detail = detail;
		this.status = status;
		this.postTime = postTime;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getPostTime() {
		return postTime;
	}

	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public static Parcelable.Creator<ThesisBean> getCreator() {
		return CREATOR;
	}

	@Override
	public int describeContents() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO 自动生成的方法存根
		dest.writeString(no);
		dest.writeString(name);
		dest.writeString(teacherName);
		dest.writeInt(count);
		dest.writeString(detail);
		dest.writeInt(status);
		dest.writeString(postTime);
	}

	public static final Parcelable.Creator<ThesisBean> CREATOR = new Creator<ThesisBean>() {
		@Override
		public ThesisBean[] newArray(int size) {
			return new ThesisBean[size];
		}

		@Override
		public ThesisBean createFromParcel(Parcel in) {
			return new ThesisBean(in);
		}
	};

	public ThesisBean(Parcel in) {
		no = in.readString();
		name = in.readString();
		teacherName = in.readString();
		count = in.readInt();
		detail = in.readString();
		status = in.readInt();
		postTime = in.readString();
	}

	public static ThesisBean createFromJSON(JSONObject jsonObject) {
		ThesisBean t = new ThesisBean();
		try {
			t.setCount(jsonObject.getInt("studentQuantity"));
			t.setDetail(jsonObject.getString("detail"));
			t.setName(jsonObject.getString("thesisName"));
			t.setNo(jsonObject.getString("thesisNo"));
			t.setPostTime(jsonObject.getString("postTime"));
			t.setStatus(jsonObject.getInt("status"));
			t.setTeacherName(jsonObject.getString("teacherName"));
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			return new ThesisBean();
		}
		return t;
	}

	@Override
	public String toString() {
		return "ThesisBean [no=" + no + ", name=" + name + ", teacherName="
				+ teacherName + ", count=" + count + ", detail=" + detail
				+ ", status=" + status + ", postTime=" + postTime + "]";
	}

}
