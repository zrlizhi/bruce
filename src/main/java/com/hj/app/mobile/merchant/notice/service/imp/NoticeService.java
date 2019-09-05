package com.hj.app.mobile.merchant.notice.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.hj.app.mobile.merchant.notice.model.NoticeModel;
import com.hj.app.mobile.merchant.notice.po.NoticePo;
import com.hj.app.mobile.merchant.notice.service.INoticeService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.PageUtil;
import com.hj.app.utils.RandomUtil;
import com.hj.app.wx.service.IWxService;
import com.hj.app.wx.service.imp.WxService;
import com.jfinal.plugin.activerecord.Db;

public class NoticeService implements INoticeService {

	private IWxService wxService = new WxService();
	
	@Override
	public ResultData add(NoticePo po,String uploadPath,String imgPath) {
		// TODO Auto-generated method stub
		
		ResultData data = new ResultData();
		
		if(null == po.getOpenid() || 0 == po.getOpenid().length() || null == po.getContent() || 0 == po.getContent().length()){
			
			data.setCode(-1);
			data.setMsg("参数错误");
			
			return data;
		}
		
		
        String filePath = createHtmlFile(uploadPath,po);
		
		if( null == filePath ){
			
			data.setCode(-1);
			data.setMsg("请求失败!");
			
			return data;
		}
		
		po.setHtmlUrl(filePath);
		
		if(addHandle(po)){
			data.setCode(0);
			data.setMsg("发送成功！");
			sendWxMsg(po);
			return data;
		}
		data.setCode(-1);
		data.setMsg("发送失败");
		
		return data;
	}
	
	private void sendWxMsg(NoticePo po){
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("htmlUrl", po.getHtmlUrl());
		params.put("openId", po.getOpenid());
		params.put("title", po.getTitle());
		wxService.sendUniformMessage(params);
	}
	private boolean addHandle(NoticePo po){
		
        NoticeModel info = NoticeModel.dao;
		
		info.set("id", UUID.randomUUID().toString());
		info.set("openid", po.getOpenid());
		info.set("content", po.getTitle());
		info.set("noticeType", po.getNoticeType());
		info.set("htmlUrl", po.getHtmlUrl());
		info.set("isAll", po.getIsAll());
		info.set("addDate", DateUtil.getTimeStamp());
		
		return info.save();
	}

	@Override
	public ResultData list(String openId,int pageNum) {
		// TODO Auto-generated method stub
		
		ResultData data = new ResultData();
		
		try{
			
			if( null == openId || 0 == openId.length()){
				data.setCode(-1);
				data.setMsg("参数错误");
				
				return data;
			}
			
			PageUtil pageUtil = new PageUtil(pageNum,Constants.PAGE_SIZE);
			
			data.setCode(0);
			data.setMsg("发送成功！");
			data.setResultData(Db.find("select a.id,a.content,DATE_FORMAT(a.addDate,'%Y-%c-%d') as addDate,a.noticeType as state,a.htmlUrl from notices a where a.openid = '"+openId+"' or a.isAll = 1 order by a.addDate desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage()));
			return data;
			
		}catch(Exception e){
			
			data.setCode(-1);
			data.setMsg("数据异常");
			
			return data;
		}
		
	}

	@Override
	public ResultData del(String id) {
		// TODO Auto-generated method stub
		ResultData data = new ResultData();
		if( null == id || 0 == id.length()){
			data.setCode(-1);
			data.setMsg("参数错误");
			
			return data;
		}
		
		NoticeModel info = NoticeModel.dao.findFirst("select * from notices a where a.id = '"+id+"'");
		if( null == info ){
			data.setCode(-1);
			data.setMsg("信息不存在");
			
			return data;
		}
		
		if(info.delete()){
			data.setCode(0);
			data.setMsg("删除成功");
			
			return data;
		}
		data.setCode(-1);
		data.setMsg("删除失败");
		
		return data;
	}
	
    private String createHtmlFile(String path,NoticePo po){
		
		try{
			
			String fileName = DateUtil.dateStr() + RandomUtil.getRandomCodeStr()+".html";
			path += "/"+fileName;
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream out=new FileOutputStream(path);
			
            PrintStream p=new PrintStream(out);
            
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html><html><head><meta charset='utf-8' />");
            sb.append("<meta name='viewport' content='width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no'>");
            sb.append("<title></title>");
            sb.append("<style>p{width:100%;}img{width:100%;}</style>");
            sb.append("</head><body>");
            sb.append(po.getContent());
            sb.append("</body></html>");
            
            p.write(sb.toString().getBytes("utf-8"));
            
            out.close();    
            
            return fileName;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

}
