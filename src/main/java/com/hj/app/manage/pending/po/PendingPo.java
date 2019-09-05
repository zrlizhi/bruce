package com.hj.app.manage.pending.po;

public class PendingPo{

	private String id;  //ID
	private String sname;  //审核名称
	private String openid; //openid
	private int state;  //状态 0 正常 1 待审核 2 审核通过 3审核未通过
	private String url; //URL
	private String addDate;  //添加日期
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	
	
	
	
}
