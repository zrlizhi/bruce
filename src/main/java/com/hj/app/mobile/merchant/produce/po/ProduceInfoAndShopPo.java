package com.hj.app.mobile.merchant.produce.po;

import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public class ProduceInfoAndShopPo extends ResultData{

	private Record shopInfo; //店铺详情
	
	private Record produceInfo; //产品详情

	public Record getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(Record shopInfo) {
		this.shopInfo = shopInfo;
	}

	public Record getProduceInfo() {
		return produceInfo;
	}

	public void setProduceInfo(Record produceInfo) {
		this.produceInfo = produceInfo;
	}
	
	
	
}
