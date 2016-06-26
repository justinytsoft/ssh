package com.goldCityWeb.domain;

import java.util.Date;
import java.util.List;

public class Posts {

	private Integer id;
	private Integer posterId;
	private String creater;
	private Integer state;
	private Integer tagsMain_id;
	private String tagsMain;
	private String tagsSub;
	private Float price;
	private String tagsMajor;
	private String tagsCareer;
	private String tagsArea;
	private String title;
	private String previewUrl;
	private String content;
	private String detailUrl;
	private Date postDate;
	private Date updateDate;
	private Integer upCnt;
	private Integer downCnt;
	private Integer replyCnt;
	private String old_level;
	private String create_time;

	private Integer isPraise;
	private List<PostsImg> imgList;

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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getTagsMain_id() {
		return tagsMain_id;
	}

	public void setTagsMain_id(Integer tagsMain_id) {
		this.tagsMain_id = tagsMain_id;
	}

	public String getTagsMain() {
		return tagsMain;
	}

	public void setTagsMain(String tagsMain) {
		this.tagsMain = tagsMain;
	}

	public String getTagsSub() {
		return tagsSub;
	}

	public void setTagsSub(String tagsSub) {
		this.tagsSub = tagsSub;
	}

	public String getTagsMajor() {
		return tagsMajor;
	}

	public void setTagsMajor(String tagsMajor) {
		this.tagsMajor = tagsMajor;
	}

	public String getTagsCareer() {
		return tagsCareer;
	}

	public void setTagsCareer(String tagsCareer) {
		this.tagsCareer = tagsCareer;
	}

	public String getTagsArea() {
		return tagsArea;
	}

	public void setTagsArea(String tagsArea) {
		this.tagsArea = tagsArea;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
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

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public Integer getReplyCnt() {
		return replyCnt;
	}

	public void setReplyCnt(Integer replyCnt) {
		this.replyCnt = replyCnt;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Integer getIsPraise() {
		return isPraise;
	}

	public void setIsPraise(Integer isPraise) {
		this.isPraise = isPraise;
	}

	public List<PostsImg> getImgList() {
		return imgList;
	}

	public void setImgList(List<PostsImg> imgList) {
		this.imgList = imgList;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getOld_level() {
		return old_level;
	}

	public void setOld_level(String old_level) {
		this.old_level = old_level;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
