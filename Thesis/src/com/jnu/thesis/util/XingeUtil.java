package com.jnu.thesis.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushService;

/**
 * ���˺�ע�᡾���ڵ���/�����ʺ����͡�
 * ���˺�ע��ָ����ʹ��ָ�����˺ţ�ע�⣺һ���˺ſ����ڶ���豸��¼��ע��APP����������ͨ����̨��ָ�����˺ŷ���������Ϣ
 * 
 * @author Deng
 *
 */
public class XingeUtil {

	/**
	 * ע���˺�
	 * 
	 * @param context
	 *            ��ǰӦ�������Ķ��󣬲���Ϊnull
	 * @param account
	 *            �󶨵��˺ţ��󶨺��������˺ŷ���������Ϣ��
	 */
	public static void regist(Context context, String account) {
		// XGPushManager.registerPush(getContext(), "2011051682");
		// XGPushManager.unregisterPush(getContext());
		XGPushManager.registerPush(context, account, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				Log.d("xinge", "ע��ɹ����豸tokenΪ��" + data);
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				Log.d("xinge", "ע��ʧ�ܣ������룺" + errCode + ",������Ϣ��" + msg);
			}
		});
	}

	/**
	 * ��ע���˺�
	 * 
	 * @param context
	 *            ��ǰӦ�������Ķ��󣬲���Ϊnull
	 */
	public static void unRegist(Context context) {
		XGPushManager.unregisterPush(context, new XGIOperateCallback() {
			@Override
			public void onSuccess(Object data, int flag) {
				Log.d("xinge", "��ע��ɹ����豸tokenΪ��" + data);
			}

			@Override
			public void onFail(Object data, int errCode, String msg) {
				Log.d("xinge", "��ע��ʧ�ܣ������룺" + errCode + ",������Ϣ��" + msg);
			}
		});
	}

	/**
	 * ��ʼ���Ÿ�
	 * @param ctx
	 */
	public static void initXinge(Context ctx) {
		// ����logcat���������debug������ʱ��ر�
		// XGPushConfig.enableDebug(this, true);
		// �����Ҫ֪��ע���Ƿ�ɹ�����ʹ��registerPush(getApplicationContext(),
		// XGIOperateCallback)��callback�汾
		// �����Ҫ���˺ţ���ʹ��registerPush(getApplicationContext(),account)�汾
		// ����ɲο���ϸ�Ŀ���ָ��
		// ���ݵĲ���ΪApplicationContext
		Context context = ctx.getApplicationContext();
		XGPushManager.registerPush(context);

		// 2.36����������֮ǰ�İ汾��Ҫ��������2�д���
		Intent service = new Intent(context, XGPushService.class);
		context.startService(service);

		// �������õ�API��
		// ���˺ţ�������ע�᣺registerPush(context,account)��registerPush(context,account,
		// XGIOperateCallback)������accountΪAPP�˺ţ�����Ϊ�����ַ�����qq��openid���������������ҵ��һ��Ҫע���ն����̨����һ�¡�
		// ȡ�����˺ţ���������registerPush(context,"*")����account="*"Ϊȡ���󶨣����󣬸���Ը��˺ŵ����ͽ�ʧЧ
		// ��ע�ᣨ���ٽ�����Ϣ����unregisterPush(context)
		// ���ñ�ǩ��setTag(context, tagName)
		// ɾ����ǩ��deleteTag(context, tagName)
	}
}
