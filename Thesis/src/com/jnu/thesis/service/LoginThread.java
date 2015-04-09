package com.jnu.thesis.service;

import java.util.HashMap;
import java.util.Map;

import com.jnu.thesis.Parameter;
import com.jnu.thesis.util.HttpUtil;

/**
 * 登陆线程
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
	 * 登陆线程
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param status
	 *            身份(1教师2学生)
	 * @param callBack
	 *            回调接口, 登陆完毕后执行
	 */
	public LoginThread(String userName, String password, int status,
			CallBack callBack) {
		// TODO 自动生成的构造函数存根
		this.userName = userName;
		this.password = password;
		this.status = status;
		this.callBack = callBack;
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		super.run();
		// TODO 自动生成的方法存根
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
			// TODO 自动生成的 catch 块
			callBack.onFinish(null, e);
			e.printStackTrace();
		}
	}
}
