package com.hj.app.mobile.merchant.produce.controller;

import java.util.List;

import com.hj.app.mobile.merchant.produce.po.ProducePo;
import com.hj.app.mobile.merchant.produce.service.IProduceService;
import com.hj.app.mobile.merchant.produce.service.imp.ProduceService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

public class ProduceController extends Controller{

	private IProduceService produceService = new ProduceService();
	
	public void saveOrUpdateProduce(){  //编辑或添加产品信息
		
		ProducePo producePo = new ProducePo();
		
		producePo.setId(getPara("produceId"));
		producePo.setOpenid(getPara("openId"));
		producePo.setPro_name(getPara("pro_name"));
		producePo.setLevel(getPara("level"));
		producePo.setPlace(getPara("place"));
		producePo.setSpec(getPara("spec"));
		producePo.setClassify(getPara("variety"));
		producePo.setPro_img(getPara("pro_img"));
		
		renderJson(produceService.saveOrUpdateProduce(producePo));
	}
	
	public void deleteProduce(){   //删除产品信息
		
		renderJson(produceService.deleteProduce(getPara("id"),getPara("openId")));
	}
	
	public void appHomeList(){  //首页最新产品列表
		
		ResultData resultData = new ResultData();
		
		try{
			
			if( null == getPara("pageNum") || 0 == getPara("pageNum").length() ){  //校验参数
				resultData.setCode(-1);
				resultData.setMsg("参数错误！");
				renderJson(resultData);
				return;
			}
			
			List<Record> list = produceService.appHomeList(getParaToInt("pageNum") , Constants.PAGE_SIZE);
			if( list == null || 0 == list.size() ){  //校验返回的数据
				resultData.setCode(0);
				resultData.setMsg("无数据！");
				resultData.setResultData(list);
				renderJson(resultData);
				return;
			}
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(list);
			renderJson(resultData);
			return;
			
		}catch(Exception e){
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			renderJson(resultData);
			return;
		}
		
		
	}
	
	public void produceInfoByID(){  //产品详情
		
		renderJson(produceService.produceInfoByID(getPara("id"),getPara("openId")));
	}
	
	public void queryProduceInfoForShop(){  //带商家信息的产品详情
		
		renderJson(produceService.queryProduceInfoForShop(getPara("id"),getPara("openId")));
	}
	
	public void queryManageList(){
		
		renderJson(produceService.queryManageList(getParaToInt("page"),getParaToInt("limit"),getPara("content")));
	}
	
   public void search(){
		
		renderJson(produceService.search(getPara("content")));
	}
}
