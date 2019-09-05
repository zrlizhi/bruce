package com.hj.app.mobile.merchant.sellerresinfo.service;

import java.util.List;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.mobile.merchant.sellerresinfo.model.SellerResInfoModel;
import com.jfinal.plugin.activerecord.Record;

public interface ISellerResInfoService {

	public List<Record> queryOrdersForUser(int pageNum,int pageSize,String openId,String status);
	
	public ResultPo saveSellerResInfo(SellerResInfoModel model);
}
