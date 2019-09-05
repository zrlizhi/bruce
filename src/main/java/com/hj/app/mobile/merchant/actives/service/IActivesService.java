package com.hj.app.mobile.merchant.actives.service;

import java.util.List;

import com.hj.app.mobile.merchant.actives.po.ActivesForManagePo;
import com.hj.app.mobile.merchant.actives.po.ActivesForShopPo;
import com.hj.app.mobile.merchant.actives.po.ActivesPo;
import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public interface IActivesService {

	public ResultData addActives(ActivesPo act);  //添加动态
	
	public List<Record> appHomeList(); //首页动态列表
	
	public Record activeInfo(String id); //动态详情
	
	public ResultData list(int pageNum); //动态列表
	
	public ResultData listForMy(int pageNum,String openId); //我的动态列表
	
	public ActivesForShopPo listForShop(int pageNum,String openId); //商户动态列表
	
	public ActivesForManagePo listForManage(int pageNum,int limit,String content);
}
