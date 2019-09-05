package com.hj.app.mobile.merchant.buyerpurinfo.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.mobile.merchant.buyerpurinfo.model.BuyerPurInfoModel;
import com.hj.app.mobile.merchant.buyerpurinfo.service.IBuyerPurInfoService;
import com.hj.app.mobile.merchant.buyerpurinfo.service.imp.BuyerPurInfoService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.RandomUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

public class BuyerPurInfoController extends Controller{

	private static Logger logger  = Logger.getLogger(BuyerPurInfoController.class);
	
	private IBuyerPurInfoService buyerPurInfoService = new BuyerPurInfoService();  
	
	public void queryAllOrdersList() {
		
		ResultData resultData = new ResultData();
		try {
			if (StringUtils.isBlank(getPara("pageNum"))) {
				resultData.setCode(-1);
				resultData.setMsg("查询参数有误");
				renderJson(resultData);
				return;
			}
			List<Record> list = buyerPurInfoService.queryAllOrdersList(getParaToInt("pageNum"), Constants.PAGE_SIZE,
					getPara("orderStatus"),"");
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
	
	public void queryAllOrdersListByUser() {
		
		ResultData resultData = new ResultData();
		try {
			if (StringUtils.isBlank(getPara("pageNum"))||StringUtils.isBlank(getPara("openId"))) {
				resultData.setCode(-1);
				resultData.setMsg("查询参数有误");
				renderJson(resultData);
				return;
			}
			List<Record> list = buyerPurInfoService.queryAllOrdersList(getParaToInt("pageNum"), Constants.PAGE_SIZE,
					getPara("orderStatus"),getPara("openId"));
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
	
	
	@SuppressWarnings("deprecation")
	public void releaseBuyInfoImg() {
		ResultPo po = new ResultPo();
		try {
			String uploadPath = getRequest().getRealPath("/upload"); //图片路径
			String folder = DateUtil.yearStr();
			UploadFile file = getFile();
			String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr(); //图片名称
			File fileInfo = new File(uploadPath + "/" + folder); //按月创建图片文件夹
			if (!fileInfo.mkdir()) { //判断文件夹是否存在，不存在创建

				fileInfo.mkdir();
			}
			final String filePath = uploadPath + File.separator + folder +  File.separator + path + ".png";
			file.getFile().renameTo(new File(filePath)); //重命名图片
			po.setData(folder + "/" + path + ".png");
		} catch (Exception e) {
			po.setCode(-1);
			po.setMsg("文件上传失败");
			logger.error("采购信息发布,文件上传失败",e);
		}
		po.setCode(1);
		po.setMsg("文件上传成功");
		
		renderJson(po);
	}
	
	public void  releaseBuyInfo() {
		BuyerPurInfoModel model = new BuyerPurInfoModel();
		model.set("procuct_name", getPara("procuct_name"));//品名
		model.set("inten_price", getParaToInt("inten_price"));//意向价格
		model.set("num", getParaToInt("num"));//数量
		model.set("phone", getPara("phone"));//联系电话
		model.set("procuct_length", getParaToInt("procuct_length"));//长度
		model.set("product_level", getParaToInt("product_level"));//等级
		model.set("product_thickness", getParaToInt("product_thickness"));//宽度
		model.set("openid", getPara("openId"));//openid
		model.set("relase_time", new Date());//发布时间     
		model.set("b_status", "0");
		if(StringUtils.isNoneBlank(getPara("imgSrc"))) {
			model.set("picture_path",getPara("imgSrc").substring(1));//getPara("picture_path")
		}
		
//		model.save();
		
		renderJson(buyerPurInfoService.saveBuyerPurInfo(model));
		
	}
	
	
	/*private List<Record> listConcatList(List<Record> list,int num,List<Record> newList){
		List<Record> tempList = new ArrayList<Record>();
		Record r = new Record();
		copyList(list, tempList);
		newList.addAll(tempList);
		if(newList.size()>num) {
			return newList;
		}else {
			return listConcatList(list, num ,newList);
		}
	}
	
	private void copyList(List<Record> oldList,List<Record> newList) {
		Record[] recArray = new Record[oldList.size()];
		oldList.toArray(recArray);
		Collections.addAll(newList, recArray);
		Collections.copy(newList, oldList);
	}*/
}
