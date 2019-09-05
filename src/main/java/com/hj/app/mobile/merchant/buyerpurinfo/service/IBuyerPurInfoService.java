package com.hj.app.mobile.merchant.buyerpurinfo.service;

import java.util.List;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.mobile.merchant.buyerpurinfo.model.BuyerPurInfoModel;
import com.jfinal.plugin.activerecord.Record;

public interface IBuyerPurInfoService {

	public List<Record> queryAllOrdersList(int pageNum,int pageSize,String ordersStatus,String openId);
	
	public ResultPo saveBuyerPurInfo(BuyerPurInfoModel model);
}
