package com.goldCityWeb.domain;

import java.util.List;

public class Resume {

	private Integer id;
	private Integer userId;
	private String education;
	private String selfEvaluate;
	private String tags;
	private String expectPosts;
	private String area;
	private String college;
	private Integer college_id;
	private String major;
	private String hobby;
	private String phone;
	private Integer status;// 是否在职
	private Integer sex;// 性别

	private String studentName;
	private String head;
	private String detailUrl;
	private String edstate;
	private String exstate;
	private String expectSalary;
	private String name;
	private String birthday;
	private String workType;
	private List<ResumeExp> eduList;// 教育经历
	private List<ResumeExp> expList;// 工作经历

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSelfEvaluate() {
		return selfEvaluate;
	}

	public void setSelfEvaluate(String selfEvaluate) {
		this.selfEvaluate = selfEvaluate;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getExpectPosts() {
		return expectPosts;
	}

	public void setExpectPosts(String expectPosts) {
		this.expectPosts = expectPosts;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ResumeExp> getEduList() {
		return eduList;
	}

	public void setEduList(List<ResumeExp> eduList) {
		this.eduList = eduList;
	}

	public List<ResumeExp> getExpList() {
		return expList;
	}

	public void setExpList(List<ResumeExp> expList) {
		this.expList = expList;
	}

	public String getEdstate() {
		return edstate;
	}

	public void setEdstate(String edstate) {
		this.edstate = edstate;
	}

	public String getExstate() {
		return exstate;
	}

	public void setExstate(String exstate) {
		this.exstate = exstate;
	}

	

	public String getExpectSalary() {
		return expectSalary;
	}

	public void setExpectSalary(String expectSalary) {
		this.expectSalary = expectSalary;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getCollege_id() {
		return college_id;
	}

	public void setCollege_id(Integer college_id) {
		this.college_id = college_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

}
