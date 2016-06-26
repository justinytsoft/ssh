package com.goldCityWeb.domain;

/**
 * 教育、工作经历
 * 
 * @author Eric
 *
 */
public class ResumeExp {
	private Integer id;
	private Integer resume_id;
	private String timeSolt;
	private String description;
	private Integer type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getResume_id() {
		return resume_id;
	}

	public void setResume_id(Integer resume_id) {
		this.resume_id = resume_id;
	}

	public String getTimeSolt() {
		return timeSolt;
	}

	public void setTimeSolt(String timeSolt) {
		this.timeSolt = timeSolt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
