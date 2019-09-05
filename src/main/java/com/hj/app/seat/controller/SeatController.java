package com.hj.app.seat.controller;

import com.hj.app.model.ResultData;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class SeatController extends Controller{

	public void search(){
		
		ResultData resultData = new ResultData();
		
		String content = getPara("content");
		
		if(null == content || 0 == content.length()){
			
			resultData.setCode(-1);
			resultData.setMsg("请输入姓名");
			
			renderJson(resultData);
			return;
		}
		
		Record info = Db.findFirst("select * from seats a where a.sname='"+content+"'");
		
		if(null == info){
			
			resultData.setCode(-2);
			resultData.setMsg("未找到信息，请联系晚会负责人");
			
			renderJson(resultData);
			return;
		}
		
		resultData.setCode(0);
		resultData.setMsg("查询成功");
		resultData.setResultData(info);
		
		renderJson(resultData);
		return;
	}
	
	public void page(){
		
		render("/seat/index.html");
	}
}
