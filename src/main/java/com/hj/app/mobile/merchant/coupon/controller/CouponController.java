package com.hj.app.mobile.merchant.coupon.controller;

import com.hj.app.mobile.merchant.coupon.po.CouponPo;
import com.hj.app.mobile.merchant.coupon.service.ICouponService;
import com.hj.app.mobile.merchant.coupon.service.imp.CouponService;
import com.jfinal.core.Controller;

public class CouponController extends Controller{

	ICouponService couponService = new CouponService();
	
	public void add(){  //添加优惠券
		
		CouponPo couponPo = new CouponPo();
		couponPo.setCcounts(getParaToInt("ccounts"));
		couponPo.setCoupon_type(getParaToInt("couponType"));
		couponPo.setContent(getPara("content"));
		couponPo.setStartDate(getPara("startDate"));
		couponPo.setEndDate(getPara("endDate"));
		couponPo.setOpenid(getPara("openId"));
		couponPo.setMoneys(getPara("moneys"));
		couponPo.setDescription(getPara("description"));
		couponPo.setState(0);
		
		renderJson(couponService.add(couponPo));
	}
	
	public void del(){  //删除优惠券
		
		renderJson(couponService.del(getPara("id")));
	}
	
	public void queryInfoById(){  //优惠券详情
		
		renderJson(couponService.queryInfoById(getPara("id")));
	}
	
	public void queryListForShop(){  //获取商家优惠券
		
		renderJson(couponService.queryListForShop(getPara("openId")));
	}
	
	public void getCoupon(){  //领取优惠券
		
		renderJson(couponService.getCoupon(getPara("couponid"), getPara("openId")));
	}
	
	public void getCouponRecords(){  //领取优惠券列表
		
		renderJson(couponService.getCouponRecords(getPara("openId")));
	}
	
	public void getScancode(){
		
		renderQrCode("coupon_"+getPara("content"), getParaToInt("width"), getParaToInt("height"));
	}
	
	public void useCoupon(){
		
		renderJson(couponService.useCoupon(getPara("content")));
	}
	
}
