package com.jnu.thesis.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.tencent.xinge.ClickAction;
import com.tencent.xinge.Message;
import com.tencent.xinge.Style;
import com.tencent.xinge.XingeApp;

/**
 * 信鸽推送服务
 * 
 * @author Deng
 * 
 */
public class XingePush {
	private static final int ACCESSID = 2100091809;
	private static final String SECRETKEY = "b145983627cbc216fc1a7d45fd85d060";

	/**
	 * 推送到单个Android设备
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            正文
	 * @param fromName
	 *            发件人
	 * @param fromId
	 *            发件人id
	 * @param deviceToken
	 *            设备token
	 * @param flag
	 *            推送给学生为true, 推送给教师为false
	 * @return 成功返回true,否则false
	 */
	public static boolean pushSingleAndroid(String title, String content,
			String fromName, String fromId, String deviceToken, boolean flag) {
		XingeApp xinge = new XingeApp(ACCESSID, SECRETKEY);
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);
		message.setStyle(getStyle());
		if (flag)
			message.setAction(getClickAction1());
		else
			message.setAction(getClickAction2());
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("fromName", fromName);
		custom.put("fromId", fromId);
		message.setCustom(custom);
		JSONObject ret = xinge.pushSingleDevice(deviceToken, message);
		int result;
		try {
			result = (Integer) ret.get("ret_code");
			if (result == 0)
				return true;
			else
				return false;
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 推送到一个账号
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            正文
	 * @param fromName
	 *            发件人
	 * @param fromId
	 *            发件人id
	 * @param account
	 *            账号
	 * @param flag
	 *            推送给学生为true, 推送给教师为false
	 * @return 成功则返回true,否则false
	 */
	public static boolean pushSingleAccount(String title, String content,
			String fromName, String fromId, String account, boolean flag) {

		XingeApp xinge = new XingeApp(ACCESSID, SECRETKEY);
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);
		message.setStyle(getStyle());
		if (flag)
			message.setAction(getClickAction1());
		else
			message.setAction(getClickAction2());
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("fromName", fromName);
		custom.put("fromId", fromId);
		message.setCustom(custom);
		JSONObject ret = xinge.pushSingleAccount(XingeApp.DEVICE_ALL, account,
				message);
		System.out.println(ret);
		int result;
		try {
			result = (Integer) ret.get("ret_code");
			if (result == 0)
				return true;
			else
				return false;
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param title
	 *            标题
	 * @param content
	 *            正文
	 * @param fromName
	 *            发件人姓名
	 * @param fromId
	 *            发件人id
	 * @param account
	 *            账号列表
	 * @param flag
	 *            发给学生 /教师
	 * @return
	 */
	public static boolean pushAccounts(String title, String content,
			String fromName, String fromId, List<String> account, boolean flag) {
		XingeApp xinge = new XingeApp(ACCESSID, SECRETKEY);
		Message message = new Message();
		message.setType(Message.TYPE_NOTIFICATION);
		message.setTitle(title);
		message.setContent(content);
		message.setStyle(getStyle());
		if (flag)
			message.setAction(getClickAction1());
		else
			message.setAction(getClickAction2());
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("fromName", fromName);
		custom.put("fromId", fromId);
		message.setCustom(custom);
		JSONObject ret = xinge.pushAccountList(XingeApp.DEVICE_ALL, account,
				message);
		System.out.println(ret);
		int result;
		try {
			result = (Integer) ret.get("ret_code");
			if (result == 0)
				return true;
			else
				return false;
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return false;
	}

	public static ClickAction getClickAction1() {
		ClickAction clickAction = new ClickAction();
		clickAction.setActionType(ClickAction.TYPE_ACTIVITY);
		clickAction.setAtyAttrIntentFlag(0x4020000);
		clickAction.setActivity("com.jnu.thesis.activity.MainActivity");
		return clickAction;
	}

	public static ClickAction getClickAction2() {
		ClickAction clickAction = new ClickAction();
		clickAction.setActionType(ClickAction.TYPE_ACTIVITY);
		clickAction.setAtyAttrIntentFlag(0x4020000);
		clickAction.setActivity("com.jnu.thesis.activity.TeacherMainActivity");
		return clickAction;
	}

	public static Style getStyle() {
		Style style = new Style(1, 0, 1, 1, 0);
		return style;
	}

	public static void pushMessageByTag(String tag, String content,
			String fromName) {
		XingeApp xinge = new XingeApp(ACCESSID, SECRETKEY);
		XGMessage message = new XGMessage();
		List<String> tagList = new ArrayList<String>();
		tagList.add(tag);
		Map<String, Object> custom = new HashMap<String, Object>();
		custom.put("fromName", fromName);
		custom.put("tag", tag);
		message.setCustom(custom);
		message.setContent(content);
		message.setType(Message.TYPE_MESSAGE);
		message.setXGData(content, custom);
		xinge.pushTags(XingeApp.DEVICE_ALL, tagList, "OR", message);
	}
}
