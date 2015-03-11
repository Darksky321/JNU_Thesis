package com.jnu.thesis.bean;

public class ThesisBean {

	private int no;
	private String name;
	private String teacherName;
	private int count;
	private String detail;

	public ThesisBean(int no, String name, String teacherName, int count,
			String detail) {
		super();
		this.no = no;
		this.name = name;
		this.teacherName = teacherName;
		this.count = count;
		this.detail = detail;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}
