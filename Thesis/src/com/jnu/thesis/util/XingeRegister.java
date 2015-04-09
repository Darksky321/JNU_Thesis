package com.jnu.thesis.util;

import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

/**
 * 绑定账号注册【用于单个/批量帐号推送】
 * 绑定账号注册指的是使用指定的账号（注意：一个账号可能在多个设备登录）注册APP，这样可以通过后台向指定的账号发送推送消息
 * 
 * @author Deng
 *
 */
public class XingeRegister {

	/**
	 * 注册账号
	 * @param context
	 *            当前应用上下文对象，不能为null
	 * @param account
	 *            绑定的账号，绑定后可以针对账号发送推送消息。
	 */
	public static void regist(Context context, String account) {
		// XGPushManager.registerPush(getContext(), "2011051682");
		// XGPushManager.unregisterPush(getContext());
		XGPushManager.registerPush(context, account, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				Log.d("xinge", "注册成功，设备token为：" + data);
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				Log.d("xinge", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
			}
		});
	}

	/**
	 * 反注册账号
	 * @param context 当前应用上下文对象，不能为null
	 */
	public static void unRegist(Context context) {
		XGPushManager.unregisterPush(context, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				Log.d("xinge", "反注册成功，设备token为：" + data);
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				Log.d("xinge", "反注册失败，错误码：" + errCode + ",错误信息：" + msg);
			}
		});
	}
}
