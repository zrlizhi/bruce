package com.hj.app.manage.pending.service.imp;

import java.util.List;
import java.util.UUID;

import com.hj.app.manage.pending.model.PendingModel;
import com.hj.app.manage.pending.po.PendingForManagePo;
import com.hj.app.manage.pending.po.PendingPo;
import com.hj.app.manage.pending.service.IPendingService;
import com.hj.app.mobile.merchant.notice.model.NoticeModel;
import com.hj.app.mobile.merchant.shop.model.ShopModel;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class PendingService implements IPendingService {
	
	@Override
	public boolean add(PendingPo po) {
		// TODO Auto-generated method stub
		if( null == po.getSname() || 0 == po.getSname().length() ){
			return false;
		}
		
		return addHandle(po);
	}
	
	private boolean addHandle(PendingPo po){
		
		return PendingModel.dao.set("id", UUID.randomUUID().toString())
				.set("addDate", DateUtil.getTimeStamp())
				.set("sname", po.getSname())
				.set("openid", po.getOpenid())
				.set("state", po.getState())
				.set("url", po.getUrl())
				.save();
		  
	}

    private boolean addNoticeHandle(PendingModel po,String content){
		
        NoticeModel info = NoticeModel.dao;
		
		info.set("id", UUID.randomUUID().toString());
		info.set("openid", po.getStr("openid"));
		info.set("content", content);
		info.set("noticeType", 0);
		info.set("htmlUrl", "");
		info.set("isAll", 0);
		info.set("addDate", DateUtil.getTimeStamp());
		
		return info.save();
	}
	@Override
	public PendingForManagePo list() {
		// TODO Auto-generated method stub
		
		PendingForManagePo po = new PendingForManagePo();
		
		try{
			List<Record> list = Db.find("select a.id,a.sname,a.url,a.openid,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate from pending a where a.state = 1 order by a.addDate desc " );
			po.setCode(0);
			po.setMsg("请求成功！");
			po.setCount(Db.findFirst("select count(*) as counts from pending a where a.state = 1").getInt("counts"));
			po.setData(list);
			return po;
			
		}catch(Exception e){
			
			po.setCode(-1);
			po.setMsg("数据异常！");
			return po;
		}

	}

	@Override
	public ResultData update(String id, int state,String content) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == id || 0 == id.length() ){
			
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		PendingModel pendingInfo = PendingModel.dao.findFirst(" select * from pending a where a.id = '"+id+"' ");
		
		if( null == pendingInfo ){
			resultData.setCode(-1);
			resultData.setMsg("信息不存在！");
			return resultData;
		}
		
		ShopModel shopInfo = ShopModel.dao.findFirst(" select * from shop a where a.id = '"+pendingInfo.getStr("openid")+"' ");
		
		if( null == shopInfo ){
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
		}
		
		
		
		pendingInfo.set("state", state);
		shopInfo.set("state", state);
		
		if(pendingInfo.update() && shopInfo.update()){
			addNoticeHandle(pendingInfo,content);
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("请求失败！");
		return resultData;
	}

}
