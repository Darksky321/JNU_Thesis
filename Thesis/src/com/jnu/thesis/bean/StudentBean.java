package com.jnu.thesis.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentBean {

	private String no;
	private String name;

	public StudentBean(String no, String name) {
		super();
		this.no = no;
		this.name = name;
	}

	public StudentBean() {
		// TODO 自动生成的构造函数存根
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static StudentBean createFromJSON(JSONObject jo) {
		StudentBean sb = new StudentBean();
		try {
			sb.setName(jo.getString("studentName"));
			sb.setNo(jo.getString("studentNo"));
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return sb;
	}
}
