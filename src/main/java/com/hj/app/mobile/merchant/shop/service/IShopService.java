package com.hj.app.mobile.merchant.shop.service;

import java.util.List;

import com.hj.app.mobile.merchant.shop.po.ShopInfoForManage;
import com.hj.app.mobile.merchant.shop.po.ShopInfoPo;
import com.hj.app.mobile.merchant.shop.po.ShopManagePo;
import com.hj.app.mobile.merchant.shop.po.ShopPo;
import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public interface IShopService {

	ResultData saveOrUpdateShopMsg(ShopPo shopPo);  //添加或编辑店铺信息
	
	ResultData queryShopInfo(String openId); //店铺信息
	
	List<Record> appHomeList();  //首页热门商家
	
	ResultData searchShop(String content); //首页检索店铺
	
	ResultData discountShopList(int pageNum); //优惠促销商户列表
	
	ResultData hotShopList(int pageNum); //人气商家
	
	ResultData search(String content);  //检索店铺
	
	ShopInfoPo queryShopInfoForOther(String openId);  //店铺详情
	
	ResultData collectShop(String shopId,String openId); // 收藏店铺
	
	ShopManagePo shopListForManage(int pageNum,int limit,String shopName); //店铺列表后台
	
	ShopInfoForManage queryShopInfoForManage(String openId); //后台详情
	
	ResultData checkShop(String id); //校验商户信息
}
