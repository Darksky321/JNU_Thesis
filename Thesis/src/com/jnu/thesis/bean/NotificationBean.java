package com.jnu.thesis.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationBean implements Parcelable {

	private Integer id;
	private Long msg_id;
	private String title;
	private String content;
	private String activity;
	private int notificationActionType;
	private String update_time;
	private String fromName;
	private String fromId;
	private String toId;

	public NotificationBean() {
		// TODO 自动生成的构造函数存根
	}

	public NotificationBean(Parcel in) {
		id = in.readInt();
		msg_id = in.readLong();
		title = in.readString();
		content = in.readString();
		activity = in.readString();
		notificationActionType = in.readInt();
		update_time = in.readString();
		fromName = in.readString();
		fromId = in.readString();
		toId = in.readString();
	}

	@Override
	public int describeContents() {
		// TODO 自动生成的方法存根
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO 自动生成的方法存根
		dest.writeInt(id);
		dest.writeLong(msg_id);
		dest.writeString(title);
		dest.writeString(content);
		dest.writeString(activity);
		dest.writeInt(notificationActionType);
		dest.writeString(update_time);
		dest.writeString(fromName);
		dest.writeString(fromId);
		dest.writeString(toId);
	}

	public static final Parcelable.Creator<NotificationBean> CREATOR = new Creator<NotificationBean>() {
		@Override
		public NotificationBean[] newArray(int size) {
			return new NotificationBean[size];
		}

		@Override
		public NotificationBean createFromParcel(Parcel in) {
			return new NotificationBean(in);
		}
	};

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(Long msg_id) {
		this.msg_id = msg_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getNotificationActionType() {
		return notificationActionType;
	}

	public void setNotificationActionType(int notificationActionType) {
		this.notificationActionType = notificationActionType;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	@Override
	public String toString() {
		return "NotificationBean [id=" + id + ", msg_id=" + msg_id + ", title="
				+ title + ", content=" + content + ", activity=" + activity
				+ ", notificationActionType=" + notificationActionType
				+ ", update_time=" + update_time + ", fromName=" + fromName
				+ ", fromId=" + fromId + ", toId=" + toId + "]";
	}
}
