package com.hj.app.manage.homevar.po;

/**
 * 
 * @author huajian
 * 首页种类
 *
 */
public class HomeVarietyPo {

	private int id;  //ID
	private String varName;  //种类名称
	private String imgUrl;  //图片
	private String author;  // 发布人
	private String addDate;  // 添加日期
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	
	
}
