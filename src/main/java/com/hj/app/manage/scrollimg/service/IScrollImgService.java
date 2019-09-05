package com.hj.app.manage.scrollimg.service;

import java.util.List;

import com.hj.app.manage.scrollimg.po.ScrollImgPo;
import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public interface IScrollImgService {

	ResultData addScrollImg(ScrollImgPo scrollImgPo); //滚动图片添加
	
	ResultData delScrollImg(String id,String imgUrl); //滚动图片删除
	
	ResultData queryScrollImgs(); //查询列表
	
	ResultData configState(String id);
	
	List<Record> appHomeList();  //小程序首页列表数据
}
