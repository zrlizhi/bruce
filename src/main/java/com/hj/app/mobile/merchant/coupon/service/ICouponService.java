package com.hj.app.mobile.merchant.coupon.service;

import com.hj.app.mobile.merchant.coupon.po.CouponPo;
import com.hj.app.model.ResultData;

public interface ICouponService {

	public ResultData add(CouponPo po);  //添加优惠券
	
	public ResultData queryInfoById(String id);  //查询优惠券详情
	
	public ResultData queryListForShop(String openid);  //查询商户优惠券
	
	public ResultData del(String id);  //删除优惠券
	
	public ResultData editForCounts(String id); //编辑优惠券个数
	
	public ResultData editForState(String id); //编辑优惠券状态
	
	public ResultData getCoupon(String couponid,String openId); //领取优惠券
	
	public ResultData getCouponRecords(String openId); //领取优惠券列表
	
	public ResultData useCoupon(String content);
}
