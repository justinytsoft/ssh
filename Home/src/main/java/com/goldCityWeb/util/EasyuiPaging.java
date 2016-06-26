package com.goldCityWeb.util;

import java.util.ArrayList;
import java.util.List;

/**
 * easyui的datagrid使用
 * @author dreamtec
 *
 */
public class EasyuiPaging<T> {

	private Integer total = 0; //总共多少条记录
	private List<T> rows = new ArrayList<T>(); //返回的数据集合
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
}
