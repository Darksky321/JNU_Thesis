package com.jnu.thesis.util;

import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

/**
 * ���˺�ע�᡾���ڵ���/�����ʺ����͡�
 * ���˺�ע��ָ����ʹ��ָ�����˺ţ�ע�⣺һ���˺ſ����ڶ���豸��¼��ע��APP����������ͨ����̨��ָ�����˺ŷ���������Ϣ
 * 
 * @author Deng
 *
 */
public class XingeRegister {

	/**
	 * ע���˺�
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
	 * @param context ��ǰӦ�������Ķ��󣬲���Ϊnull
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
}
