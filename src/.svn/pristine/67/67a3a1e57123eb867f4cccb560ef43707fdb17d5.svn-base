package com.hj.app.common.error;

import com.jfinal.core.Controller;

public class ErrorController extends Controller{

	//跳转到登录页面
	public void login(){
		
		setAttr("msg", "重新登录");
		render("/error/error");
	}
	
	//跳转到需要权限页面
	public void needPermission(){
		
		setAttr("msg", "暂无权限");
		render("/error/error");
	}
}
