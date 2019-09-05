package com.hj.app.manage.news.service;

import com.hj.app.manage.news.po.NewsForManagePo;
import com.hj.app.manage.news.po.NewsPo;
import com.hj.app.model.ResultData;

public interface INewsService {

	public ResultData add(NewsPo po,String path,String imgPath);
	
	public ResultData list(int pageNum);
	
	public NewsForManagePo listForManage(int pageNum,int limit,String shopName);
	
	public ResultData del(String id);
	
	public ResultData configCounts(String id);
}
