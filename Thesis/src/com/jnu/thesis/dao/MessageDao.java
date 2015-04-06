package com.jnu.thesis.dao;

import com.qq.xgdemo.po.XGNotification;

public interface MessageDao {

	public boolean save(XGNotification notification, String customContent);
}
