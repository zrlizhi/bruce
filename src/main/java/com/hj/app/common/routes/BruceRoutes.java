package com.hj.app.common.routes;

import com.hj.app.common.error.ErrorController;
import com.hj.app.common.file.FileController;
import com.hj.app.dingding.controller.DingController;
import com.hj.app.index.IndexController;
import com.hj.app.manage.customer.controller.CustomerController;
import com.hj.app.manage.homevar.controller.HomeVarietyController;
import com.hj.app.manage.index.ManageController;
import com.hj.app.manage.msg.controller.MsgController;
import com.hj.app.manage.news.controller.NewsController;
import com.hj.app.manage.pending.controller.PendingController;
import com.hj.app.manage.scrollimg.controller.ScrollImgController;
import com.hj.app.manage.users.controller.ManageUsersController;
import com.hj.app.manage.varietys.controller.VarietysController;
import com.hj.app.mobile.index.controller.MobileController;
import com.hj.app.mobile.merchant.actives.controller.ActivesController;
import com.hj.app.mobile.merchant.buyerpurinfo.controller.BuyerPurInfoController;
import com.hj.app.mobile.merchant.card.controller.CardController;
import com.hj.app.mobile.merchant.collects.controller.CollectController;
import com.hj.app.mobile.merchant.coupon.controller.CouponController;
import com.hj.app.mobile.merchant.notice.controller.NoticeController;
import com.hj.app.mobile.merchant.orders.controller.OrdersController;
import com.hj.app.mobile.merchant.produce.controller.ProduceController;
import com.hj.app.mobile.merchant.sellerresinfo.controller.SellerResInfoController;
import com.hj.app.mobile.merchant.shop.controller.ShopController;
import com.hj.app.mobile.users.controller.MUserController;
import com.hj.app.seat.controller.SeatController;
import com.hj.app.wx.WxController;
import com.jfinal.config.Routes;

public class BruceRoutes {

	public BruceRoutes(Routes me) {

		addRoutes(me);   // 前端
		addManageRoutes(me);  // 管理后台
		addMobileRoutes(me);  // 手机端
	}

	
	private void addRoutes(Routes me) {  // 前端

		me.setBaseViewPath("/views");
		me.add("/", IndexController.class);
		me.add("/file", FileController.class); //文件管理
		
		me.add("/error", ErrorController.class);    // 处理异常情况

	}

	private void addManageRoutes(Routes me) {  // 管理后台

		me.add("/manage", ManageController.class);   // 后台首页
		me.add("/var",VarietysController.class);  //经营品种
		me.add("/scroImg",ScrollImgController.class);  //首页滚动图片
		me.add("/homeVar",HomeVarietyController.class);  //首页品种
		me.add("/news", NewsController.class);  // 新闻信息
		me.add("/users", ManageUsersController.class);  // 后台管理员
		me.add("/msg", MsgController.class);  // 后台消息
		me.add("/pending", PendingController.class);  // 待审核消息
		
		me.add("/customer" ,CustomerController.class); //企业注册信息
		me.add("/orders",OrdersController.class);	//订单列表
	}

	
	private void addMobileRoutes(Routes me) {  // 手机端

		me.add("/app", MobileController.class);    // 微信首页
		me.add("/wx", WxController.class);    // 微信
		me.add("/muser", MUserController.class);    // 手机端用户信息管理
		me.add("/shop", ShopController.class);  // 店铺管理
		me.add("/card", CardController.class);  // 商户明信片管理
		me.add("/pro", ProduceController.class);  // 产品信息
		me.add("/act", ActivesController.class);  // 动态信息
		me.add("/coupon", CouponController.class);  // 优惠券信息
		me.add("/collect", CollectController.class);  // 收藏信息
		me.add("/notice", NoticeController.class);  // 通知信息
		
		me.add("/ding",DingController.class);//钉钉
		me.add("/seat",SeatController.class);//年会座位
		
		me.add("/buyerpurinfo",BuyerPurInfoController.class);//我要买
		me.add("/sellerresinfo",SellerResInfoController.class);//我要卖
		
	}
	
}
