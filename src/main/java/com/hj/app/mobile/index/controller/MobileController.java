package com.hj.app.mobile.index.controller;

import com.hj.app.mobile.index.service.IMobileService;
import com.hj.app.mobile.index.service.imp.MobileService;
import com.hj.app.mobile.merchant.produce.service.IProduceService;
import com.hj.app.mobile.merchant.produce.service.imp.ProduceService;
import com.hj.app.mobile.merchant.shop.service.IShopService;
import com.hj.app.mobile.merchant.shop.service.imp.ShopService;
import com.jfinal.core.Controller;

public class MobileController extends Controller{

	IMobileService mobileService = new MobileService();
	IShopService shopService = new ShopService(); //商户信息
	IProduceService produceService = new ProduceService(); //产品
	
	public void list(){  //首页
		
		renderJson(mobileService.list());
	}
	
	public void search(){  //搜索
		
		renderJson( 0 == getParaToInt("stype") ? shopService.searchShop(getPara("content")) : produceService.searchProduce(getPara("content"),0));
	}
	
	public void queryProduceForVarietys(){ //首页根据品种查找产品
		
		renderJson(produceService.searchProduce(getPara("content"),getParaToInt("pageNum")));
	}
}
