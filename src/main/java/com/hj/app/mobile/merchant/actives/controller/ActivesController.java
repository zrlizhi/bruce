package com.hj.app.mobile.merchant.actives.controller;

import com.hj.app.mobile.merchant.actives.po.ActivesPo;
import com.hj.app.mobile.merchant.actives.service.IActivesService;
import com.hj.app.mobile.merchant.actives.service.imp.ActivesService;
import com.jfinal.core.Controller;

public class ActivesController extends Controller {

	private IActivesService activeService = new ActivesService();
	
	public void addActives(){  //添加动态
		
		ActivesPo activesPo = new ActivesPo();
		activesPo.setOpenid(getPara("openId"));
		activesPo.setContent(getPara("content"));
		activesPo.setImg_url(getPara("imgUrl"));
		activesPo.setContact_tel(getPara("contact_tel"));
		
		renderJson(activeService.addActives(activesPo));
	}
	
	public void list(){  //动态列表
		
		renderJson(activeService.list( 0 == getParaToInt("pageNum") ? 1 : getParaToInt("pageNum") ));
	}
	
    public void listForMy(){  //我的动态列表
		
		renderJson(activeService.listForMy( 0 == getParaToInt("pageNum") ? 1 : getParaToInt("pageNum"),getPara("openId") ));
	}
    
    public void listForShop(){  //商户的动态列表
		
		renderJson(activeService.listForShop( 0 == getParaToInt("pageNum") ? 1 : getParaToInt("pageNum"),getPara("openId") ));
	}
    
    public void listForManage(){  //动态列表管理
    	
    	renderJson(activeService.listForManage(getParaToInt("page"),getParaToInt("limit"),getPara("content")));
    }
    
}
