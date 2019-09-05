package com.hj.app.mobile.merchant.notice.service;

import com.hj.app.mobile.merchant.notice.po.NoticePo;
import com.hj.app.model.ResultData;

public interface INoticeService {

	ResultData add(NoticePo po,String uploadPath,String imgPath);
	
	ResultData list(String openId, int pageNum);
	
	ResultData del(String id);
	
}
