package com.jnu.thesis.dao;

import java.util.ArrayList;

import com.jnu.thesis.bean.NotificationBean;
import com.qq.xgdemo.po.XGNotification;

public interface NotificationDao {

	public boolean save(XGNotification notification, String customContent,
			String toId);

	public boolean delete(String id);

	public boolean deleteAll(String toId);

	public ArrayList<NotificationBean> findAllNotifications(String toId);

	public ArrayList<NotificationBean> findAllNotifications();
}
