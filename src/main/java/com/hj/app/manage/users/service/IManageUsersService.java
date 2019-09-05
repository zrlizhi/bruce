package com.hj.app.manage.users.service;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.manage.users.po.ManageUsersPo;
import com.hj.app.model.ResultData;

public interface IManageUsersService {

	public ResultData add(ManageUsersPo po);
	
	public ResultData login(String userName,String password);
	
	public ResultPo list();
	
	public ResultData disable(String id);
	
	public ResultData del(String id);
}
