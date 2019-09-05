package com.hj.app.mobile.merchant.coupon.po;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class CouponRecordPo{

	private String openId; //商户ID
	private String imgUrl; //商户头像
	private String shopName; //店铺名称
	private String contactTel; //联系电话
	
	private List<Record> coupons; //优惠券列表

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public List<Record> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Record> coupons) {
		this.coupons = coupons;
	}
	
	
}
