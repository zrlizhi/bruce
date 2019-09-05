package com.hj.app.mobile.merchant.shop.po;

import java.util.List;

import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public class ShopInfoPo extends ResultData{

	Record shopInfo; //店铺详情
	
	Record activeInfo; //动态详情
	
	List<Record> coupons;//优惠券列表
	
	List<Record> produces; //产品列表

	public Record getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(Record shopInfo) {
		this.shopInfo = shopInfo;
	}

	public Record getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(Record activeInfo) {
		this.activeInfo = activeInfo;
	}

	public List<Record> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Record> coupons) {
		this.coupons = coupons;
	}

	public List<Record> getProduces() {
		return produces;
	}

	public void setProduces(List<Record> produces) {
		this.produces = produces;
	}
	
	
	
	
}
