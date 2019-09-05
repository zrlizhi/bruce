package com.hj.app.mobile.users.service.impl;

import com.hj.app.mobile.merchant.card.service.ICardService;
import com.hj.app.mobile.merchant.card.service.imp.CardService;
import com.hj.app.mobile.users.model.MUserModel;
import com.hj.app.mobile.users.po.UserLoginResponsePo;
import com.hj.app.mobile.users.po.UserPo;
import com.hj.app.mobile.users.service.IMUserService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author xuhj 手机端用户管理
 *
 */
public class MUserService implements IMUserService {

	private ICardService cardService = new CardService(); //商户明信片
	
	/**
	 * 根据openid查询用户信息
	 * 
	 * @param openId
	 *            用户opneid
	 */
	@Override
	public ResultData queryUserInfoByOpenId(String openId) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();// 初始化请求结果

		if (null == openId) {

			Record userInfo = Db.findFirst("select * from app_user a where a.openid = '"+openId+"'");
			if (null == userInfo) {// 是否有用户信息

				resultData.setCode(-10);
				resultData.setMsg("未注册");

				return resultData;

			} else {

				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				resultData.setResultData(userInfo);

				return resultData;
			}

		} else {

			resultData.setCode(-11);
			resultData.setMsg("参数错误");

			return resultData;
		}

	}

	/**
	 * 保存用户信息
	 * 
	 * @param params
	 * 用户信息
	 */
	@Override
	public ResultData saveUsers(UserPo params) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();// 初始化请求结果

		if (null == params.getOpenid() || 0 == params.getOpenid().length()  || null == params.getUsername() || 0 == params.getUsername().length()
				|| null == params.getUser_icon() || 0 == params.getUser_icon().length()) {// 验证参数

			resultData.setCode(-11);
			resultData.setMsg("参数错误");

			return resultData;

		}

		try {

			UserLoginResponsePo userResponsePo = new UserLoginResponsePo();
			
			Record cardInfo = Db.findFirst("select a.id,a.slogan,a.contact_name,a.contact_tel,a.mer_name,a.mer_addr,a.mer_goods as variety "
					+ " from mer_card a where a.id = '"+params.getOpenid()+"'");  //是否有明信片
			
			userResponsePo.setIsCard( null == cardInfo ? -1 : 0);
		
			if( null != cardInfo){
				userResponsePo.setCardInfo(cardInfo);
			}
				
			Record shopInfo = Db.findFirst("select * from shop a where a.id = '"+params.getOpenid()+"'");  //判断店铺信息是否存在
			userResponsePo.setIsShopMsg( null == shopInfo ? -1 : 0);
			
			Record userInfo = Db
					.findFirst("select a.id,a.username,a.user_icon from app_user a where a.id = '"+params.getOpenid()+"'");  //判断用户信息是否存在
			
			if (null != userInfo) {  //判断用户信息是否存在

				userResponsePo.setUserInfo(params);
				
				resultData.setCode(0);
				resultData.setMsg("请求成功");
				
				resultData.setResultData(userResponsePo);

				return resultData;
			}

			if(handleSave(params)){  //用户信息是否保存成功
				
				userResponsePo.setUserInfo(params);
				
				resultData.setCode(0);
				resultData.setMsg("请求成功");
				resultData.setResultData(userResponsePo);
				return resultData;
			}

			resultData.setCode(-1);
			resultData.setMsg("请求失败");
			return resultData;
			

		} catch (Exception e) {
			// TODO: handle exception
            e.printStackTrace();
			resultData.setCode(-2);
			resultData.setMsg("数据异常");
			return resultData;
		}

	}
	
	private boolean handleSave(UserPo params){
		
		return MUserModel.dao.// 初始化用户MODEL
		set("id", params.getOpenid())// 用户OPENID
		.set("username", params.getUsername())// 用户名
		.set("gender", params.getGender())// 性别
		.set("user_icon", params.getUser_icon())// 头像
		.set("city", params.getCity())// 用户所在城市
		.set("province", params.getProvince())// 用户所在省份
		.set("addDate", DateUtil.getTimeStamp())
		.save();
	}

	@Override
	public UserPo getUserInfo(String openId) {
		// TODO Auto-generated method stub
		return null;
	}

}
