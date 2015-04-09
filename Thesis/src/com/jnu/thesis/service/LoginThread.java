package com.jnu.thesis.service;

import java.util.HashMap;
import java.util.Map;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.util.HttpUtil;

/**
 * ��½�߳�
 * 
 * @author Deng
 *
 */
public class LoginThread extends Thread {

	private String userName;
	private String password;
	private int status;
	private CallBack callBack;

	/**
	 * ��½�߳�
	 * 
	 * @param userName
	 *            �û���
	 * @param password
	 *            ����
	 * @param status
	 *            ���(1��ʦ2ѧ��)
	 * @param callBack
	 *            �ص��ӿ�, ��½��Ϻ�ִ��
	 */
	public LoginThread(String userName, String password, int status,
			CallBack callBack) {
		// TODO �Զ����ɵĹ��캯�����
		this.userName = userName;
		this.password = password;
		this.status = status;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		super.run();
		// TODO �Զ����ɵķ������
		HttpUtil util = new HttpUtil();
		Map<String, String> para = new HashMap<String, String>();
		para.put("username", userName);
		para.put("password", password);
		para.put("status", status + "");
		String response;
		try {
			response = util
					.doPost(Parameter.host + Parameter.loginAction, para);
			callBack.onFinish(response, null);
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			callBack.onFinish(null, e);
			e.printStackTrace();
		}
	}
}
