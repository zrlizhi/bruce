package com.hj.app.mobile.merchant.produce.service;

import java.util.List;

import com.hj.app.mobile.merchant.produce.po.ProduceInfoAndShopPo;
import com.hj.app.mobile.merchant.produce.po.ProduceManagePo;
import com.hj.app.mobile.merchant.produce.po.ProducePo;
import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public interface IProduceService {

	ResultData saveOrUpdateProduce(ProducePo producePo);  //保存添加产品
	
	ResultData deleteProduce(String id,String openid);  //删除产品
	
	ResultData produceInfoByID(String id,String openid);  //产品详情
	
	List<Record> produceListForShop(String openid); //店铺产品列表
	
	List<Record> appHomeList(int pageNumber, int pageSize);  //首页最新产品列表
	
	ResultData searchProduce(String content,int pageNum); //搜索产品
	
	ResultData search(String content); //搜索产品
	
	ProduceInfoAndShopPo queryProduceInfoForShop(String id,String openId);  //带商家信息的产品详情
	
	ProduceManagePo queryManageList(int pageNum,int limit,String content);  //后台管理产品列表
}
