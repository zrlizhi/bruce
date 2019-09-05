package com.hj.app.mobile.merchant.collects.controller;

import com.hj.app.mobile.merchant.collects.po.CollectPo;
import com.hj.app.mobile.merchant.collects.service.ICollectService;
import com.hj.app.mobile.merchant.collects.service.imp.CollectService;
import com.jfinal.core.Controller;

public class CollectController extends Controller {

	private ICollectService collectService = new CollectService();
	
	public void add(){  //添加收藏
		
		CollectPo po = new CollectPo();
		po.setOpenid(getPara("openId"));
		po.setShopid(getPara("shopId"));
		
		renderJson(collectService.add(po));
	}
	
	public void list(){  //我的收藏列表
		
		renderJson(collectService.list(getPara("openId")));
	}
	 
	public void del(){  //删除收藏
		
		renderJson(collectService.del(getPara("openId"), getPara("shopId")));
	}
}
