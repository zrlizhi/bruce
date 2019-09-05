package com.hj.app.mobile.users.controller;

import com.hj.app.mobile.users.po.UserPo;
import com.hj.app.mobile.users.service.IMUserService;
import com.hj.app.mobile.users.service.impl.MUserService;
import com.jfinal.core.Controller;

public class MUserController extends Controller {

	
	private IMUserService userService = new MUserService();//用户业务处理
	
	/**
	 * 用户登录
	 */
	public void userLoginByWx(){
		
		UserPo userInfo = new UserPo();
		
		userInfo.setOpenid(getPara("openid"));  //获取用户openid
		userInfo.setUsername(getPara("username")); //用户微信昵称
		userInfo.setGender(getPara("gender"));  //性别
		userInfo.setUser_icon(getPara("user_icon")); //用户头像
		userInfo.setCity(getPara("city"));  //用户所在城市
		userInfo.setProvince(getPara("province")); //用户所在的省份
		
	
		renderJson(userService.saveUsers(userInfo));
	}
}
