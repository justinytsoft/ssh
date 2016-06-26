package com.goldCityWeb.domain;

import java.util.Date;

public class PostsReply {
	private Integer id;
	private Integer posterId;
	private Integer postsId;
	private String content;
	private Date postDate;
	private Integer upCnt;
	private Integer downCnt;

	private String head_img;
	private String user_name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPosterId() {
		return posterId;
	}

	public void setPosterId(Integer posterId) {
		this.posterId = posterId;
	}

	public Integer getPostsId() {
		return postsId;
	}

	public void setPostsId(Integer postsId) {
		this.postsId = postsId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Integer getUpCnt() {
		return upCnt;
	}

	public void setUpCnt(Integer upCnt) {
		this.upCnt = upCnt;
	}

	public Integer getDownCnt() {
		return downCnt;
	}

	public void setDownCnt(Integer downCnt) {
		this.downCnt = downCnt;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

}
