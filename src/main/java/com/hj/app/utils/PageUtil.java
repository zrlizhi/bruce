package com.hj.app.utils;

public class PageUtil {

	private int currentPage;
	private int pageSize;
	
	public PageUtil(int currentPage,int pageSize) {
		super();
		// TODO Auto-generated constructor stub
		this.currentPage=currentPage;
		this.pageSize=pageSize;
	}

	public int pageNum(){
		return (currentPage-1)*pageSize;
	}
	
	public int nextPage(){
		return pageSize;
	}
}
