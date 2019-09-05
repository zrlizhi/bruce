package com.hj.app.dingding.service;

import com.hj.app.dingding.po.SignIn;
import com.hj.app.model.ResultData;
import com.jfinal.upload.UploadFile;

public interface IDingService {

	public ResultData dingAuthor(String code);
	
	public ResultData addSignMsg(SignIn sign);
	
	public ResultData handleExcelToDb(UploadFile file);
}
