package com.goldCityWeb.domain;

public class SysAdv {
	private Integer id;
	private Integer type;
	private String image;
	private String text_content;
	private String content;
	private String create_time;
	private String push_title;
	private String push_content;
	private Integer push_type;

	public Integer getPush_type() {
		return push_type;
	}

	public void setPush_type(Integer push_type) {
		this.push_type = push_type;
	}

	public String getPush_title() {
		return push_title;
	}

	public void setPush_title(String push_title) {
		this.push_title = push_title;
	}

	public String getPush_content() {
		return push_content;
	}

	public void setPush_content(String push_content) {
		this.push_content = push_content;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getText_content() {
		return text_content;
	}

	public void setText_content(String text_content) {
		this.text_content = text_content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
