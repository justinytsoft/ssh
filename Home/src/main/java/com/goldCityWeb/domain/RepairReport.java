package com.goldCityWeb.domain;

import java.util.Date;

/**
 * 报修
 * 
 * @author Eric
 *
 */
public class RepairReport {
	private Integer id;
	private Integer userId;
	private String dormitoryNo;
	private String reporterName;
	private String studentNo;
	private String telNo;
	private String plannedTime;
	private String repairContent;
	private Boolean is_fixed;// 是否已完成
	private Date create_time;
	private String college_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDormitoryNo() {
		return dormitoryNo;
	}

	public void setDormitoryNo(String dormitoryNo) {
		this.dormitoryNo = dormitoryNo;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getPlannedTime() {
		return plannedTime;
	}

	public void setPlannedTime(String plannedTime) {
		this.plannedTime = plannedTime;
	}

	public String getRepairContent() {
		return repairContent;
	}

	public void setRepairContent(String repairContent) {
		this.repairContent = repairContent;
	}

	public Boolean getIs_fixed() {
		return is_fixed;
	}

	public void setIs_fixed(Boolean is_fixed) {
		this.is_fixed = is_fixed;
	}

	public Date getcreate_time() {
		return create_time;
	}

	public void setcreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

}
