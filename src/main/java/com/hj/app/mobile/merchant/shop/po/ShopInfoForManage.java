package com.hj.app.mobile.merchant.shop.po;

import java.io.Serializable;
import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class ShopInfoForManage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Record shopInfo;
	
	private List<Record> produces;

	public Record getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(Record shopInfo) {
		this.shopInfo = shopInfo;
	}

	public List<Record> getProduces() {
		return produces;
	}

	public void setProduces(List<Record> produces) {
		this.produces = produces;
	}
	
	
	
	
	
	
	
}
