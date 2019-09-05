package com.hj.app.mobile.merchant.collects.service;

import com.hj.app.mobile.merchant.collects.po.CollectPo;
import com.hj.app.model.ResultData;

public interface ICollectService {

	ResultData add(CollectPo po);  //添加收藏
	
	ResultData list(String openId);  //查询我的收藏
	
	ResultData del(String openId,String shopId); //删除我的收藏
}
