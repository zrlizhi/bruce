package com.hj.app.common.mapping;

import com.hj.app.dingding.model.SignInModel;
import com.hj.app.manage.customer.model.CustomerModel;
import com.hj.app.manage.homevar.model.HomeVarietyModel;
import com.hj.app.manage.news.model.NewsModel;
import com.hj.app.manage.pending.model.PendingModel;
import com.hj.app.manage.scrollimg.model.ScrollImgModel;
import com.hj.app.manage.users.model.ManageUsersModel;
import com.hj.app.manage.varietys.model.VarietysDetailModel;
import com.hj.app.manage.varietys.model.VarietysModel;
import com.hj.app.mobile.merchant.actives.model.ActivesModel;
import com.hj.app.mobile.merchant.buyerpurinfo.model.BuyerPurInfoModel;
import com.hj.app.mobile.merchant.card.model.CardModel;
import com.hj.app.mobile.merchant.collects.model.CollectModel;
import com.hj.app.mobile.merchant.coupon.model.CouponModel;
import com.hj.app.mobile.merchant.coupon.model.CouponRecordModel;
import com.hj.app.mobile.merchant.notice.model.NoticeModel;
import com.hj.app.mobile.merchant.orders.model.OrdersModel;
import com.hj.app.mobile.merchant.produce.model.ProduceModel;
import com.hj.app.mobile.merchant.sellerresinfo.model.SellerResInfoModel;
import com.hj.app.mobile.merchant.shop.model.ShopModel;
import com.hj.app.mobile.users.model.MUserModel;
import com.hj.app.seat.model.SeatModel;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;

public class BruceMapping extends ActiveRecordPlugin {

	
	public BruceMapping(IDataSourceProvider dataSourceProvider) {
		super(dataSourceProvider);
		// TODO Auto-generated constructor stub
		init();
		addMapping();
		addManageMapping();
	}
	
	//初始化数据源配置
	private void init(){
		//orm映射 配置ActiveRecord插件
		setShowSql(PropKit.getBoolean("devMode"));
		setDialect(new MysqlDialect());
	}

	//添加手机用户映射关系
	private void addMapping(){
		
		addMapping("app_user", MUserModel.class);//手机用户
		addMapping("mer_card", CardModel.class);//明信片
		addMapping("shop", ShopModel.class);//店铺信息
		addMapping("produce", ProduceModel.class);//产品信息
		addMapping("actives",ActivesModel.class); //动态
		addMapping("coupon",CouponModel.class); //优惠券
		addMapping("coupon_record",CouponRecordModel.class); //领取优惠券记录		
		addMapping("collect",CollectModel.class); //收藏
		addMapping("notices",NoticeModel.class); //通知
		addMapping("orders","id",OrdersModel.class);//订单列表
		
		addMapping("buyer_pur_info","id",BuyerPurInfoModel.class);
		addMapping("seller_res_info","id",SellerResInfoModel.class);
		
		
	}
	
	private void addManageMapping(){  
		
		addMapping("varietys",VarietysModel.class); //经营种类
		addMapping("home_scroll_img",ScrollImgModel.class); //首页滚动图片
		addMapping("home_varietys",HomeVarietyModel.class); //首页品种
		addMapping("news",NewsModel.class); //新闻
		addMapping("users",ManageUsersModel.class); //后台管理员
		addMapping("pending",PendingModel.class); //后台待审核管理
		
		addMapping("signin",SignInModel.class); //钉钉签到
		addMapping("seats",SeatModel.class); //年会座位
		addMapping("customer",CustomerModel.class); //年会座位
		
		addMapping("varietys_detail","varid",VarietysDetailModel.class);
		
		
	}
	
}
