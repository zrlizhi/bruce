package com.hj.app.mobile.merchant.actives.po;

import java.util.List;

import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author huajian
 * 动态po
 */
public class ActivesForShopPo extends ResultData{

	private List<Record> actives;
	
	private Record shopInfo;

	public List<Record> getActives() {
		return actives;
	}

	public void setActives(List<Record> actives) {
		this.actives = actives;
	}

	public Record getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(Record shopInfo) {
		this.shopInfo = shopInfo;
	}
	
	
	
}
