package com.hj.app.manage.users.controller;

import com.hj.app.manage.users.po.ManageUsersPo;
import com.hj.app.manage.users.service.IManageUsersService;
import com.hj.app.manage.users.service.imp.ManageUsersService;
import com.jfinal.core.Controller;

public class ManageUsersController extends Controller {

	private IManageUsersService userService = new ManageUsersService(); 
	
	public void add(){
		
		ManageUsersPo po = new ManageUsersPo();
		po.setUsername(getPara("userName"));
		po.setPassword(getPara("password"));
		
		renderJson(userService.add(po));
	}
	
	public void login(){
		
		renderJson(userService.login(getPara("userName"),getPara("password")));
	}
	
	public void list(){
		
		renderJson(userService.list());
	}
	
	public void del(){
		
		renderJson(userService.del(getPara("id")));
	}
	
	public void disable(){
		
		renderJson(userService.disable(getPara("id")));
	}
}
