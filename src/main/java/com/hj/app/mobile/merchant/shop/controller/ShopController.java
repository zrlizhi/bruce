package com.hj.app.mobile.merchant.shop.controller;

import com.hj.app.mobile.merchant.shop.po.ShopPo;
import com.hj.app.mobile.merchant.shop.service.IShopService;
import com.hj.app.mobile.merchant.shop.service.imp.ShopService;
import com.jfinal.core.Controller;

/**
 * 店铺信息
 * 
 * @author huajian
 *
 */
public class ShopController extends Controller {

	private IShopService shopService = new ShopService();

	public void saveOrUpdateShopMsg() { //添加或编辑店铺信息

		ShopPo shopPo = new ShopPo();
		shopPo.setId(getPara("openId"));
		shopPo.setShop_img(getPara("shop_img"));
		shopPo.setShop_name(getPara("shop_name"));
		shopPo.setContact_name(getPara("contact_name"));
		shopPo.setContact_tel(getPara("contact_tel"));
		shopPo.setShop_addr(getPara("shop_addr"));
		shopPo.setVariety(getPara("variety"));
		shopPo.setShop_introduce(getPara("shop_introduce"));
		shopPo.setLicense(getPara("license"));
		shopPo.setGoods_ids(getPara("goods_ids"));
		shopPo.setLongitude(getPara("longitude"));
		shopPo.setLatitude(getPara("latitude"));

		renderJson(shopService.saveOrUpdateShopMsg(shopPo));
	}

	public void queryShopInfo() {  //店铺信息

		renderJson(shopService.queryShopInfo(getPara("openId")));
	}

	public void discountShopList() {  //优惠促销商家列表

		renderJson(shopService.discountShopList(getParaToInt("pageNum")));
	}

	public void hotShopList() {  //人气商家

		renderJson(shopService.hotShopList(getParaToInt("pageNum")));
	}
	
	public void search() {

		renderJson(shopService.search(getPara("content")));
	}
	
	public void queryShopInfoForOther() {  //店铺信息

		renderJson(shopService.queryShopInfoForOther(getPara("openId")));
	}
	
	public void shopListForManage(){
		
		renderJson(shopService.shopListForManage(getParaToInt("page"),getParaToInt("limit"),getPara("shopName")));
	}
	
	public void queryShopInfoForManage(){
		
		setAttr("state", getParaToInt("state"));
		setAttr("pid", getPara("pid"));
		setAttr("info", shopService.queryShopInfoForManage(getPara("openId")));
		render("/manage/shopInfo.html");
	}
	
	public void checkShop(){  //检验店铺信息审核
		
		renderJson(shopService.checkShop(getPara("openId")));
	}
}
