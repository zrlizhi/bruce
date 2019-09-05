package com.hj.app.mobile.merchant.shop.po;

import com.hj.app.model.ResultData;

public class DiscountShopPo extends ResultData {  //首页优惠商家

	private String openid;  //openID
	private String imgUrl;  //商户图片
	private String shopName;// 商户名称
	private int views; //浏览数
	private int coupons;//优惠券数
	private String addDate; //日期
	
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
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
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getCoupons() {
		return coupons;
	}
	public void setCoupons(int coupons) {
		this.coupons = coupons;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
	}
	
	
}
