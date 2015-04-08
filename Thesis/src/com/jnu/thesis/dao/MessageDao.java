package com.jnu.thesis.dao;

import java.util.ArrayList;

import com.jnu.thesis.bean.MessageBean;
import com.qq.xgdemo.po.XGNotification;

public interface MessageDao {

	public boolean save(XGNotification notification, String customContent);

	public boolean delete(String[] id);

	public boolean deleteAll();

	public ArrayList<MessageBean> findAllMessage();
}
