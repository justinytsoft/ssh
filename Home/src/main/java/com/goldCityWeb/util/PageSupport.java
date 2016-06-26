package com.goldCityWeb.util;

import javax.servlet.http.HttpServletRequest;

public class PageSupport {
	public final static String PAGE_SIZE = "pageSize";
	public final static String PAGE_OFFSET = "pageOffset";
	public final static int DEFAULT_PAGE_SIZE = 2;
	public final static int FIRST_PAGE = 0;

	public final static String REQUEST_PAGE_SUPPORT = "page";

	private int pageSize;
	private int pageOffset;
	private int totalRecord;
	private String url;

	@Override
	public String toString() {
		return "PageSupport [pageSize=" + pageSize + ", pageOffset=" + pageOffset + ", totalRecord=" + totalRecord
				+ ", url=" + url + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getCurrentPage() {
		return pageOffset / pageSize + 1;
	}
	
	/**
	 * 获取当前页最大的offset
	 * 
	 * @return
	 */
	public int getCurrentMaxOffset() {
		int maxPageOffset = pageOffset + pageSize;
		return maxPageOffset > totalRecord ? totalRecord : maxPageOffset;
	}
	
	/**
	 * @return 下一页的offset
	 */
	public int getNextPageOffset() {
		return (getCurrentPage()) * pageSize;
	}

	/**
	 * @return 上一页的offset
	 */
	public int getPrevPageOffset() {
		return (getCurrentPage() - 2) * pageSize;
	}

	/**
	 * @return 最后一页的offset
	 */
	public int getLastPageOffset() {
		return getTotalPage() == 0 ? 0 : (getTotalPage() - 1) * pageSize;
	}

	/**
	 * @return 总页数
	 */
	public int getTotalPage() {
		if (totalRecord % pageSize == 0) {
			return totalRecord / pageSize;
		} else {
			return totalRecord / pageSize + 1;
		}
	}
	
	/**
	 * @return 是否有上一页
	 */
	public boolean hasPreviousPage() {
		return getPrevPageOffset() >= 0;
	}
	
	/**
	 * @return 是否有下一页
	 */
	public boolean hasNextPage() {
		return getNextPageOffset() <= getLastPageOffset();
	}
	
	/**
	 * 
	 * @return 是否第一页
	 */
	public boolean isFirst() {
		return PageSupport.FIRST_PAGE == getPageOffset();
	}
	
	/**
	 * @return 是否最后一页
	 */
	public boolean isLast() {
		return getLastPageOffset() == getPageOffset();
	}
	
	public Integer getShowFirstPageNo() {
		int lp = getShowLastPageNo();
		int fp = Math.max(1, this.getCurrentPage() - 2);
		if (lp - fp < 4) {
			fp = lp - 4;
		}
		return fp <= 0 ? 1 : fp;
	}
	
	public Integer getShowLastPageNo() {
		int tcp = this.getCurrentPage() - 2;
		int d = 0;
		if (tcp <= 0) {
			d = Math.abs(tcp) + 1;
		}
		
		Integer lastPN = Math.min(this.getTotalPage(), this.getCurrentPage() + 2 + d);
		lastPN = lastPN == 0 ? 1 : lastPN;
		
		return lastPN;
	}

	/**
	 * 初始化并返回分页PageSupport对象
	 * 
	 * @param request
	 * @return
	 */
	public static PageSupport initPageSupport(HttpServletRequest request) {
		PageSupport pageSupport = new PageSupport();
		String pageSize = request.getParameter(PageSupport.PAGE_SIZE);
		pageSupport.setPageSize(pageSize == null || pageSize.equals("") ? PageSupport.DEFAULT_PAGE_SIZE : Integer.valueOf(pageSize).intValue());
		
		String offset = request.getParameter(PageSupport.PAGE_OFFSET);
		pageSupport.setPageOffset(offset == null || offset.equals("") ? PageSupport.FIRST_PAGE : Integer.valueOf(offset).intValue());

		pageSupport.setUrl(request.getServletPath());
		
		request.setAttribute(PageSupport.REQUEST_PAGE_SUPPORT, pageSupport);
		return pageSupport;
	}

}
