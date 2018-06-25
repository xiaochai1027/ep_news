package com.cfc.util.model;

public class PageModel {
	private Integer pageSize;
	private Integer pageNum;
	private Integer start;

	private final int MAX_SIZE = 50000;
	private final int MIN_SIZE = 1;
	private final int DEFAULT_SIZE = 20;

	private final int MIN_NUM = 1;
	
	public Integer getPageSize() {
		if (pageSize == null) {
			return DEFAULT_SIZE;
		} else if (pageSize < MIN_SIZE) {
			return MIN_SIZE;
		} else if (pageSize > MAX_SIZE) {
			return MAX_SIZE;
		}
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		if (pageNum == null || pageNum < MIN_NUM) {
			return MIN_NUM;
		}
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getStart() {
	    if (start != null) {
	        return start;
	    }
	    
		return (getPageNum() - 1) * getPageSize();
	}
	
	public void setStart(int start) {
	    this.start = start;
	}
	
	public int getCount() {
		return getPageSize();
	}
}
