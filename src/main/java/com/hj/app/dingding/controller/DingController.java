package com.hj.app.dingding.controller;

import com.hj.app.dingding.po.SignIn;
import com.hj.app.dingding.service.IDingService;
import com.hj.app.dingding.service.imp.DingService;
import com.jfinal.core.Controller;

public class DingController extends Controller {

	private final IDingService dingService = new DingService();
	
	public void jumpHtml(){
		
		render("/ding/excel.html");
	}
	
	public void dingAuthor(){

		try {
			
			renderJson(dingService.dingAuthor(getPara("code")));  //钉钉授权签到
	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	public void addSignMsg(){  //添加数据
		
		SignIn sign = new SignIn();
		sign.setNickName(getPara("nickName"));
		sign.setGroupName(getPara("groupName"));
		sign.setHost(getPara("host"));
		sign.setSignMsg(getPara("signMsg"));
		sign.setDesc(getPara("desc"));
		renderJson(dingService.addSignMsg(sign));
	}
	
	public void importExcel(){  //导入Excel数据
		
		try {
			renderJson(dingService.handleExcelToDb(getFile()));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
}
