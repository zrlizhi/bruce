package com.hj.app.mobile.merchant.sellerresinfo.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.hj.app.manage.po.ResultPo;
import com.hj.app.mobile.merchant.buyerpurinfo.service.imp.BuyerPurInfoService;
import com.hj.app.mobile.merchant.sellerresinfo.model.SellerResInfoModel;
import com.hj.app.mobile.merchant.sellerresinfo.service.ISellerResInfoService;
import com.hj.app.utils.ImageUtil;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class SellerResInfoService implements ISellerResInfoService{

	private static Logger logger  = Logger.getLogger(BuyerPurInfoService.class);
	
	@Override
	public List<Record> queryOrdersForUser(int pageNum, int pageSize, String openId,String status) {
		PageUtil pageUtil = new PageUtil(pageNum, pageSize);
		logger.info("查询采购信息表,status="+openId);
		StringBuffer sql = new StringBuffer(" select t.*,DATE_FORMAT(t.relase_time,'%Y/%c/%d') as relase_time from seller_res_info t where 1=1 ");
		List<Object> paramList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(openId)) {
			sql.append(" and t.openid = ? ");
			paramList.add(openId);
		}
		if(StringUtils.isNotBlank(status)) {
			sql.append(" and t.s_status = ? ");
			paramList.add(status);
		}
		
		
		sql.append(" order by t.relase_time desc ");
		
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
	public ResultPo saveSellerResInfo(SellerResInfoModel model) {
		ResultPo po = new ResultPo();
		po.setCode(1);
		po.setMsg("提交成功");
		
		try {
			model.save();
		} catch (Exception e) {
			po.setCode(-1);
			po.setMsg("提交失败,请重新提交");
			logger.error("【订单提交供货信息保存失败】",e);
		}
		return po;
	}
}
