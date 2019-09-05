package com.hj.app.wx.service.imp;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.wx.service.IWxService;
import com.jfinal.kit.HttpKit;

public class WxService implements IWxService {

	@Override
	public String getAccessToken() {
		// TODO Auto-generated method stub
		
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constants.APP_ID+"&secret="+Constants.APPSECRET;
		JSONObject jsobject = JSON.parseObject(HttpKit.get(url));
		
		return jsobject.getString("access_token");
	}

	private String getAccessTokenHandle(){
		
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+Constants.APP_ID+"&secret="+Constants.APPSECRET;
		JSONObject jsobject = JSON.parseObject(HttpKit.get(url));
		
		return jsobject.getString("access_token");
	}
	@Override
	public ResultData sendUniformMessage(Map<String,Object> info) {
		// TODO Auto-generated method stub
		
		Map<String,Object> parmas = new HashMap<String,Object>();
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("value", DateUtil.dateStrForWxMsg());
		parmas.put("keyword1", values);
		
		Map<String,Object> values2 = new HashMap<String,Object>();
		values2.put("value", info.get("title"));
		parmas.put("keyword2", values2);
		
		Map<String,Object> values3 = new HashMap<String,Object>();
		values3.put("value", "福人企划部");
		parmas.put("keyword3", values3);
		
		Map<String,Object> paramInfo = new HashMap<String,Object>();
		paramInfo.put("touser", info.get("openId"));
		paramInfo.put("template_id", "UMMcYGxmlSiNNaOYCsqYTbgtEiiJUvVfWoiCjr0lD8Y");
		paramInfo.put("page", "pages/news/newsDetail/newsDetail?url="+info.get("htmlUrl"));
		paramInfo.put("form_id", "3fa5ad19f2e6cae6904fd80dc96783ff");
		paramInfo.put("emphasis_keyword", "keyword2.DATA");
		paramInfo.put("data", parmas);
		
		System.out.println(HttpKit.post("https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token="+getAccessTokenHandle(), JSON.toJSONString(paramInfo)));
		return null;
	}
	

}
