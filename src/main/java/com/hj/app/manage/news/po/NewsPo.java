package com.hj.app.manage.news.po;

public class NewsPo {

	private String id; //ID
	private String title; //标题
	private String content; //内容
	private String html_url;//htmlURL
	private int views; //浏览数
	private String author;// 发布者
	private String addDate; //日期
	private String thum_url;//
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHtml_url() {
		return html_url;
	}
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	public String getThum_url() {
		return thum_url;
	}
	public void setThum_url(String thum_url) {
		this.thum_url = thum_url;
	}
	
	
}
