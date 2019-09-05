package com.hj.app.manage.users.service.imp;

import java.util.UUID;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.manage.users.model.ManageUsersModel;
import com.hj.app.manage.users.po.ManageUsersPo;
import com.hj.app.manage.users.service.IManageUsersService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.MD5Util;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ManageUsersService implements IManageUsersService {

	@Override
	public ResultData add(ManageUsersPo po) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == po.getUsername() || 0 == po.getUsername().length() || null == po.getPassword()
				|| 0 == po.getPassword().length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("请输入用户名或密码！");
			return resultData;
		}

		Record userInfo = Db.findFirst("select * from users a where a.username = '" + po.getUsername() + "'");
		if (null != userInfo) {

			resultData.setCode(-1);
			resultData.setMsg("用户名已经存在！");
			return resultData;
		}

		if (saveHandle(po)) {

			resultData.setCode(0);
			resultData.setMsg("添加成功！");
			return resultData;
		}

		resultData.setCode(-1);
		resultData.setMsg("添加失败！");
		return resultData;
	}

	private boolean saveHandle(ManageUsersPo po) {

		return ManageUsersModel.dao.set("id", UUID.randomUUID().toString()).set("username", po.getUsername())
				.set("password", MD5Util.encrypt(po.getPassword())).set("locked", 0)
				.set("addDate", DateUtil.getTimeStamp()).save();
	}

	@Override
	public ResultData login(String userName, String password) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == userName || 0 == userName.length() || null == password || 0 == password.length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("请输入用户名或密码！");
			return resultData;
		}

		Record userInfo = Db.findFirst(" select * from users a where a.username = '" + userName + "' and a.password = '"
				+ MD5Util.encrypt(password) + "'");
		if (null == userInfo) {
			resultData.setCode(-1);
			resultData.setMsg("用户名或密码错误！");
			return resultData;
		}

		if (-1 == userInfo.getInt("locked")) {
			resultData.setCode(-1);
			resultData.setMsg("账号已被锁，请联系管理员！");
			return resultData;
		}

		resultData.setCode(0);
		resultData.setMsg("登录成功！");
		resultData.setResultData(userName);
		return resultData;
	}

	@Override
	public ResultPo list() {
		// TODO Auto-generated method stub

		ResultPo po = new ResultPo();

		try {

			po.setCode(0);
			po.setMsg("请求成功");
			po.setData(Db.find(
					"select a.id,a.username,if(a.locked = 0,'正常','禁用') as locked,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate from users a order by a.addDate desc "));
			return po;

		} catch (Exception e) {

			po.setCode(-1);
			po.setMsg("数据异常！");

			return po;
		}

	}

	@Override
	public ResultData disable(String id) {
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		ManageUsersModel userInfo = ManageUsersModel.dao.findFirst("select * from users a where a.id = '"+id+"'");
		if( null == userInfo ){
			resultData.setCode(-1);
			resultData.setMsg("用户信息不存在！");
			return resultData;
		}
		
		userInfo.set("locked", 0 == userInfo.getInt("locked") ? -1 : 0);
		
		if(userInfo.update()){
			resultData.setCode(0);
			resultData.setMsg("修改成功！");
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("修改失败！");
		return resultData;
	}

	@Override
	public ResultData del(String id) {
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		ManageUsersModel userInfo = ManageUsersModel.dao.findFirst("select * from users a where a.id = '"+id+"'");
		if( null == userInfo ){
			resultData.setCode(-1);
			resultData.setMsg("用户信息不存在！");
			return resultData;
		}
		
		if(userInfo.delete()){
			resultData.setCode(0);
			resultData.setMsg("删除成功！");
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("删除失败！");
		return resultData;
	}

}
