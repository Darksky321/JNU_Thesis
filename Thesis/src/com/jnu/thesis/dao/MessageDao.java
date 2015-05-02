package com.jnu.thesis.dao;

import java.util.List;

import com.jnu.thesis.bean.MessageBean;

public interface MessageDao {

	public boolean hasUnRead();

	public List<MessageBean> findUnRead();

	public boolean save(String content, String update_time, String fromName,
			String tag, int read);

}
