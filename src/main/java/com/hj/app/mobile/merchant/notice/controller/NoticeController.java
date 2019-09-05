package com.hj.app.mobile.merchant.notice.controller;

import java.io.File;

import com.hj.app.manage.news.po.NewsImgPo;
import com.hj.app.manage.news.po.NewsUploadImgPo;
import com.hj.app.mobile.merchant.notice.po.NoticePo;
import com.hj.app.mobile.merchant.notice.service.INoticeService;
import com.hj.app.mobile.merchant.notice.service.imp.NoticeService;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.RandomUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class NoticeController extends Controller {

	private INoticeService noticeService = new NoticeService();
	
    public void addImg(){
		
		NewsUploadImgPo po = new NewsUploadImgPo();
		
		try{
			
			@SuppressWarnings("deprecation")
			String uploadPath = getRequest().getRealPath("/upload");   //图片路径
			
			UploadFile files = getFile();
			
			String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr();   //图片名称
			
			File fileInfo = new File(uploadPath + "/" +DateUtil.yearStr());   //按月创建图片文件夹
			
			if( !fileInfo.mkdir() ){    //判断文件夹是否存在，不存在创建
				
				fileInfo.mkdir();
			}
			
			final String filePath = uploadPath+"/"+DateUtil.yearStr()+"/"+path+".png";
			files.getFile().renameTo(new File(filePath));   //重命名图片
			
			po.setCode(0);
			po.setMsg("添加成功！");
			
			NewsImgPo imgPo = new NewsImgPo(); //图片信息
			
			imgPo.setSrc("/bruce/upload/"+DateUtil.yearStr()+"/"+path+".png");
			imgPo.setTitle("通知");
			
			po.setData(imgPo);
			
			renderJson(po);
			
		}catch(Exception e){
			
			po.setCode(-1);
			po.setMsg("添加失败！");
			renderJson(po);
		}
        
	}
	
	@SuppressWarnings("deprecation")
	public void add(){
		
		String uploadPath = getRequest().getRealPath("/html");   //图片路径
		String imgPath = getRequest().getRealPath("/upload");
		
		NoticePo po = new NoticePo();
		po.setTitle(getPara("title"));
		po.setContent(getPara("content"));
		po.setOpenid(getPara("openId"));

		po.setIsAll(getParaToInt("isAll"));
		po.setNoticeType(getParaToInt("noticeType"));
		renderJson(noticeService.add(po,uploadPath,imgPath));
	}
	
	public void list(){
		renderJson(noticeService.list(getPara("openId"), getParaToInt("pageNum")));
	}
	
	public void del(){
		renderJson(noticeService.del(getPara("id")));
	}
}
