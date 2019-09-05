package com.hj.app.dingding.service.imp;

import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.hj.app.dingding.model.SignInModel;
import com.hj.app.dingding.po.SignIn;
import com.hj.app.dingding.service.IDingService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.POIUtil;
import com.jfinal.kit.HttpKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.taobao.api.ApiException;

public class DingService implements IDingService {

	@Override
	public ResultData dingAuthor(String code) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		try {
			
			String takeStr = getTaken();
			getUserInfo(resultData,getUserId(code,takeStr),takeStr);
		    
			return resultData;
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			resultData.setCode(-1);
			resultData.setMsg("签到失败！");
		}
		
		
		return resultData;
	}

	
	private String getTaken() throws ApiException{
		
		DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey("dingikmjscbjhj7esxhb");
		request.setAppsecret("w_0IaWXOR7i1a2VipkoP_z99h-oD6dLAaB4ggMkjVf1ICIfYONgsMgzPNUZc0N4p");
		request.setHttpMethod("GET");
		OapiGettokenResponse response = client.execute(request);
		
		return response.getAccessToken();
		
	}
	
	private String getUserId(String code,String accessToken) throws ApiException{
		
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		request.setHttpMethod("GET");
		OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
		System.out.println("========="+response.getUserid());
		return response.getUserid();
		
	}
	
	private ResultData getUserInfo(ResultData resultData,String userId,String accessToken) throws ApiException{
		
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(userId);
		request.setHttpMethod("GET");
		OapiUserGetResponse response = client.execute(request, accessToken);
		
		SignInModel signInModel = SignInModel.dao.findFirst("select * from signin a where a.nickName='"+response.getName()+"'");
		
		if( null == signInModel ){
			return null;
		}
		
		if( 1 == signInModel.getInt("state")){
			resultData.setCode(-1); 
			resultData.setMsg("已经签到！");
			return resultData;
		}
		
		signInModel.set("state", 1);
		if(signInModel.update()){
			sendMsg(userId,response.getName());
			resultData.setCode(0); 
			resultData.setMsg("签到成功！");
			return resultData;
		}
		
		resultData.setCode(0); 
		resultData.setMsg("签到失败！");
		return resultData;
		
	}
	
	private void sendMsg(String userId,String userName) throws ApiException{
		
		String jsonStr = HttpKit.get("https://oapi.dingtalk.com/gettoken?corpid=dingikmjscbjhj7esxhb&corpsecret=w_0IaWXOR7i1a2VipkoP_z99h-oD6dLAaB4ggMkjVf1ICIfYONgsMgzPNUZc0N4p");
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		
		DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");

		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setUseridList(userId);
		request.setAgentId(214207562L);
		request.setToAllUser(false);

		OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
		msg.setMsgtype("text");
		msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
		
		SignInModel signInModel = SignInModel.dao.findFirst("select * from signin a where a.nickName='"+userName+"'");
		String str=signInModel.getStr("nickName")+"，您好！\n";
		str+=signInModel.getStr("signMsg")+"\n";
		str+="参加人："+signInModel.getStr("nickName")+"\n";
		str+="所在组别："+signInModel.getStr("groupName")+"\n";
		str+="柱子："+signInModel.getStr("host")+"\n";
		str+=signInModel.getStr("desc");
		
		msg.getText().setContent(str);
		request.setMsg(msg);
		
		OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,jsonObject.getString("access_token"));
		System.out.println(response.getBody());
	}
	
	@Override
	public ResultData addSignMsg(SignIn sign) {
		// TODO Auto-generated method stub
	
		ResultData resultData = new ResultData();
		
		if( null == sign.getNickName() || 0 == sign.getNickName().length()){
			resultData.setCode(-1);
			resultData.setMsg("用户名不能为空！");
			return resultData;
		}
		
		Record info = Db.findFirst("select * from signin a where a.nickName='"+sign.getNickName()+"'");
		
		if( null != info ){
			resultData.setCode(-1);
			resultData.setMsg("用户名已经存在！");
			return resultData;
		}
		
		boolean isSuccess = SignInModel.dao.set("id", UUID.randomUUID().toString())
				.set("nickName", sign.getNickName())
				.set("groupName", sign.getGroupName())
				.set("signMsg", sign.getSignMsg())
				.set("host", sign.getHost())
				.set("desc", sign.getDesc())
				.set("addDate", DateUtil.getTimeStamp())
				.set("state", 0)
				.save();
		
		if(isSuccess){
			resultData.setCode(0);
			resultData.setMsg("添加成功！");
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("添加失败！");
		return resultData;
	}
	
	@Override
	public ResultData handleExcelToDb(UploadFile file){
		
		ResultData resultData = new ResultData();
		
		try {
			
			List<String[]> datas = POIUtil.readExcel(file, 1);
			
			for(int i=0;i<datas.size();i++){
				
				String[] strs = datas.get(i);
				
				SignIn sign = new SignIn();
				sign.setNickName(strs[0]);
				sign.setGroupName(strs[2]);
				sign.setHost(strs[3]);
				sign.setSignMsg(strs[1]);
				sign.setDesc(strs[4]);
				
				resultData = addSignMsg(sign);
				if(0 != resultData.getCode()){
					return resultData;
				}
				
			}
			
			resultData.setCode(0);
			resultData.setMsg("导入成功！");
			
			return resultData;
			
		} catch (Exception e) {
			// TODO: handle exception
			resultData.setCode(-1);
			resultData.setMsg("导入失败！");
			
			return resultData;
		}
		
	}
	
}
