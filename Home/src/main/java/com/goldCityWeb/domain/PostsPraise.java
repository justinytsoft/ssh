package com.goldCityWeb.domain;

/**
 * 帖子点赞
 * 
 * @author Admin
 *
 */
public class PostsPraise {
	private Integer id;
	private Integer postsId;
	private Integer userId;
	private Integer praise;
	private Integer editTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPostsId() {
		return postsId;
	}

	public void setPostsId(Integer postsId) {
		this.postsId = postsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public Integer getEditTime() {
		return editTime;
	}

	public void setEditTime(Integer editTime) {
		this.editTime = editTime;
	}
}
