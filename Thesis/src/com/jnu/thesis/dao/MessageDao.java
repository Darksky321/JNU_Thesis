package com.jnu.thesis.dao;

import java.util.ArrayList;

import com.jnu.thesis.bean.MessageBean;
import com.qq.xgdemo.po.XGNotification;

public interface MessageDao {

	public boolean save(XGNotification notification, String customContent,
			String toId);

	public boolean delete(String id);

	public boolean deleteAll(String toId);

	public ArrayList<MessageBean> findAllMessage(String toId);
}
