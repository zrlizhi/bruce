package com.hj.app.mobile.merchant.buyerpurinfo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.mobile.merchant.buyerpurinfo.model.BuyerPurInfoModel;
import com.hj.app.mobile.merchant.buyerpurinfo.service.IBuyerPurInfoService;
import com.hj.app.utils.ImageUtil;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class BuyerPurInfoService implements IBuyerPurInfoService{

	private static Logger logger  = Logger.getLogger(BuyerPurInfoService.class);
	

	@Override
	public List<Record> queryAllOrdersList(int pageNum,int pageSize,String ordersStatus,String openId) {
		PageUtil pageUtil = new PageUtil(pageNum, pageSize);
		logger.info("查询采购信息表,status="+ordersStatus);
		StringBuffer sql = new StringBuffer(" select t.*,DATE_FORMAT(t.relase_time,'%Y/%c/%d') as relase_time from buyer_pur_info t where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(ordersStatus)) {
			sql.append(" and t.b_status=? ");	
			paramList.add(ordersStatus);
		}
		if(StringUtils.isNotBlank(openId)) {
			sql.append(" and t.openid=? ");	
			paramList.add(openId);
		}
		
		sql.append(" order by t.relase_time desc");
		
		sql.append(" limit "+pageUtil.pageNum()+","+pageUtil.nextPage());
		List<Record> list = Db.find(sql.toString(), paramList.toArray());
		imgSrcSetDefault(list);
		return list;
	}

	private void imgSrcSetDefault(List<Record> list) {
		if(list!=null&&list.size()==0) {
			return ;
		}
		for(Record r :list) {
			if(StringUtils.isEmpty((CharSequence) r.get("picture_path"))) {
				r.set("picture_path", ImageUtil.IMG_DEFAULT_SRC);
			}else {
				String imgSrc = r.getStr("picture_path");
				if(imgSrc.indexOf(",")>0) {
					r.set("picture_path", imgSrc.substring(0,imgSrc.indexOf(",")));	//列表只展示第一张图片
				}
				
			}
		}
		
	}

	@Override
	public ResultPo saveBuyerPurInfo(BuyerPurInfoModel model) {
		ResultPo po = new ResultPo();
		po.setCode(1);
		po.setMsg("提交成功");
		
		try {
			model.save();
		} catch (Exception e) {
			po.setCode(-1);
			po.setMsg("提交失败,请重新提交");
			logger.error("【采购信息保存失败】",e);
		}
		return po;
	}

}
