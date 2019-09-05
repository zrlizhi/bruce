package com.hj.app.manage.homevar.service;

import java.util.List;

import com.hj.app.manage.homevar.po.HomeVarietyPo;
import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public interface IHomeVarietyService {

	ResultData save(HomeVarietyPo po);  //添加种类
	
	ResultData del(String id,String imgUrl); //删除种类
	
	ResultData list(); // 查询所有的种类
	
	List<Record> appHomeList(); //首页品种列表
}
