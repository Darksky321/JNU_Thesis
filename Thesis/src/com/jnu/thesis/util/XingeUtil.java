package com.jnu.thesis.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

/**
 * 绑定账号注册【用于单个/批量帐号推送】
 * 绑定账号注册指的是使用指定的账号（注意：一个账号可能在多个设备登录）注册APP，这样可以通过后台向指定的账号发送推送消息
 * 
 * @author Deng
 *
 */
public class XingeUtil {

	/**
	 * 注册账号
	 * 
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
	 * 
	 * @param context
	 *            当前应用上下文对象，不能为null
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

	/**
	 * 初始化信鸽
	 * @param ctx
	 */
	public static void initXinge(Context ctx) {
		// 开启logcat输出，方便debug，发布时请关闭
		// XGPushConfig.enableDebug(this, true);
		// 如果需要知道注册是否成功，请使用registerPush(getApplicationContext(),
		// XGIOperateCallback)带callback版本
		// 如果需要绑定账号，请使用registerPush(getApplicationContext(),account)版本
		// 具体可参考详细的开发指南
		// 传递的参数为ApplicationContext
		Context context = ctx.getApplicationContext();
		XGPushManager.registerPush(context);

		// 2.36（不包括）之前的版本需要调用以下2行代码
		Intent service = new Intent(context, XGPushService.class);
		context.startService(service);

		// 其它常用的API：
		// 绑定账号（别名）注册：registerPush(context,account)或registerPush(context,account,
		// XGIOperateCallback)，其中account为APP账号，可以为任意字符串（qq、openid或任意第三方），业务方一定要注意终端与后台保持一致。
		// 取消绑定账号（别名）：registerPush(context,"*")，即account="*"为取消绑定，解绑后，该针对该账号的推送将失效
		// 反注册（不再接收消息）：unregisterPush(context)
		// 设置标签：setTag(context, tagName)
		// 删除标签：deleteTag(context, tagName)
	}
}
