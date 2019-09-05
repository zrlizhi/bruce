package com.hj.app.mobile.merchant.sellerresinfo.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hj.app.mobile.merchant.buyerpurinfo.controller.BuyerPurInfoController;
import com.hj.app.mobile.merchant.buyerpurinfo.model.BuyerPurInfoModel;
import com.hj.app.mobile.merchant.sellerresinfo.model.SellerResInfoModel;
import com.hj.app.mobile.merchant.sellerresinfo.service.ISellerResInfoService;
import com.hj.app.mobile.merchant.sellerresinfo.service.imp.SellerResInfoService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class SellerResInfoController extends Controller{
	private static Logger logger  = Logger.getLogger(BuyerPurInfoController.class);
	
	private  ISellerResInfoService sellerResInfoService = new SellerResInfoService();
	
	public void queryAllOrdersList() {
		
		ResultData resultData = new ResultData();
		try {
			if (StringUtils.isBlank(getPara("pageNum"))||StringUtils.isBlank(getPara("openId"))) {
				resultData.setCode(-1);
				resultData.setMsg("查询参数有误");
				renderJson(resultData);
				return;
			}
			
			List<Record> list = sellerResInfoService.queryOrdersForUser(getParaToInt("pageNum"), Constants.PAGE_SIZE,
					getPara("openId"),getPara("orderStatus"));
			if (list == null || 0 == list.size()) { //校验返回的数据
				resultData.setCode(0);
				resultData.setMsg("无数据！");
				resultData.setResultData(list);
				renderJson(resultData);
				return;
			}
//			List<Record> newList = new ArrayList<Record>();
//			newList = listConcatList(list, multipleTtemsNum ,newList);
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(list);
			renderJson(resultData);
			return;
		} catch (Exception e) {
			logger.error("",e);
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			renderJson(resultData);
			return;
		}
	}
	
	public void  releaseSellInfo() {
		SellerResInfoModel model = new SellerResInfoModel();
		model.set("procuct_name", getPara("procuct_name"));//品名
		model.set("inten_price", getParaToInt("inten_price"));//意向价格
		model.set("num", getParaToInt("num"));//数量
		model.set("phone", getPara("phone"));//联系电话
		model.set("procuct_length", getParaToInt("procuct_length"));//长度
		model.set("product_level", getParaToInt("product_level"));//等级
		model.set("product_thickness", getParaToInt("product_thickness"));//宽度
		model.set("openid", getPara("openId"));//openid
		model.set("relase_time", new Date());//发布时间     
		model.set("s_status", "0");
		if(StringUtils.isNoneBlank(getPara("imgSrc"))) {
			model.set("picture_path",getPara("imgSrc").substring(1));//getPara("picture_path")
		}
		
		
		renderJson(sellerResInfoService.saveSellerResInfo(model));
		
	}
}
