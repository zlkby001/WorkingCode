package com.java1234.util;

public class GridPageBean {
	private String start = "0";
	private String limit = "30";
	private String total = "0";
	
	public GridPageBean(String limit){
		this.limit = limit;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	
}
