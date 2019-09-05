package com.hj.app.mobile.merchant.coupon.po;

import com.hj.app.model.ResultData;

public class CouponPo extends ResultData { // 优惠券

	private String id; // ID
	private String content; // 内容
	private String startDate; // 开始日期
	private String endDate; // 结束日期
	private String addDate; // 添加日期
	private int coupon_type;// 优惠券类型
	private int state;// 优惠券状态
	private int ccounts;// 优惠券个数
	private String openid;// OPENID
	private String moneys; //金额
	private String description;//描述

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAddDate() {
		return addDate;
	}

	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}

	public int getCoupon_type() {
		return coupon_type;
	}

	public void setCoupon_type(int coupon_type) {
		this.coupon_type = coupon_type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCcounts() {
		return ccounts;
	}

	public void setCcounts(int ccounts) {
		this.ccounts = ccounts;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getMoneys() {
		return moneys;
	}

	public void setMoneys(String moneys) {
		this.moneys = moneys;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}
