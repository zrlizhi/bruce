package com.hj.app.wx.service;

import java.util.Map;

import com.hj.app.model.ResultData;

public interface IWxService {

	public String getAccessToken();
	
	public ResultData sendUniformMessage(Map<String,Object> info);
}
