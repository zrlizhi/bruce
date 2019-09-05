package com.hj.app.mobile.merchant.collects.service.imp;

import java.util.UUID;

import com.hj.app.mobile.merchant.collects.model.CollectModel;
import com.hj.app.mobile.merchant.collects.po.CollectPo;
import com.hj.app.mobile.merchant.collects.service.ICollectService;
import com.hj.app.mobile.merchant.shop.model.ShopModel;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

public class CollectService implements ICollectService {

	@Override
	@Before(Tx.class)
	public ResultData add(final CollectPo po) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == po.getOpenid() || 0 == po.getOpenid().length() || null == po.getShopid()
				|| 0 == po.getShopid().length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}

		Record collectInfo = Db.findFirst("select * from collect a where a.openid = '" + po.getOpenid()
				+ "' and a.shopid = '" + po.getShopid() + "'");

		if (null != collectInfo) {

			resultData.setCode(-1);
			resultData.setMsg("已经收藏！");

			return resultData;

		}

		// TODO Auto-generated method stub
		if (saveHandle(po)) {

			resultData.setCode(0);
			resultData.setMsg("收藏成功！");
			updateCollects(po);
			return resultData;
		}

		resultData.setCode(-1);
		resultData.setMsg("收藏失败！");

		return resultData;
	}

	private boolean saveHandle(CollectPo po) { // 添加收藏

		return CollectModel.dao.set("id", UUID.randomUUID().toString()).set("openid", po.getOpenid()).set("shopid", po.getShopid())
				.set("addDate", DateUtil.getTimeStamp()).save();
	}

	private void updateCollects(CollectPo po) { // 更新商户收藏数

		ShopModel shopInfo = ShopModel.dao
				.findFirst("select * from shop a where a.id='" + po.getOpenid() + "'");
		
		shopInfo.set("collects", shopInfo.getInt("collects") + 1).update();
	}

	@Override
	public ResultData list(String openId) {  //我的收藏列表
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();

		if ( null == openId || 0 == openId.length() ) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}
		
		resultData.setCode(0);
		resultData.setMsg("请求成功！");
		resultData.setResultData(Db.find("select b.shop_name,b.shop_img,a.shopid,a.openid from collect a,shop b where a.shopid = b.id and a.openid = '"+openId+"'"));

		return resultData;
		
	}

	@Override
	public ResultData del(String openId, String shopId) {  //删除收藏
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();

		if (null == openId || 0 == openId.length() || null == shopId
				|| 0 == shopId.length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}
		
		CollectModel collectInfo = CollectModel.dao
				.findFirst("select * from collect a where a.openid = '" + openId + "' and a.shopid = '" + shopId + "'");
		
		if (null == collectInfo) {  //判断信息是否存在
			
			resultData.setCode(-1);
			resultData.setMsg("无收藏信息！");

			return resultData;
		}
		
		if( collectInfo.delete() ){   //检验是否删除成功
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");

			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("请求失败！");

		return resultData;
	}

}
