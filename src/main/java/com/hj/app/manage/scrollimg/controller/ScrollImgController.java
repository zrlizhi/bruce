package com.hj.app.manage.scrollimg.controller;

import java.io.File;

import com.hj.app.manage.scrollimg.po.ScrollImgPo;
import com.hj.app.manage.scrollimg.service.IScrollImgService;
import com.hj.app.manage.scrollimg.service.imp.ScrollImgService;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.RandomUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class ScrollImgController extends Controller {

	private IScrollImgService scrollImgService = new ScrollImgService(); 
	
	@SuppressWarnings("deprecation")
	public void add(){  //添加
		
		String uploadPath = getRequest().getRealPath("/upload");   //图片路径
		
		UploadFile files = getFile();
		
		String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr();   //图片名称
		
		File fileInfo = new File(uploadPath + "/" +DateUtil.yearStr());   //按月创建图片文件夹
		
		if( !fileInfo.mkdir() ){    //判断文件夹是否存在，不存在创建
			
			fileInfo.mkdir();
		}
		
		final String filePath = uploadPath+"/"+DateUtil.yearStr()+"/"+path+".png";
		files.getFile().renameTo(new File(filePath));   //重命名图片
		
		ScrollImgPo scrollImgPo = new ScrollImgPo();
		
		scrollImgPo.setImgUrl(DateUtil.yearStr()+"/"+path+".png");
		scrollImgPo.setAuthor(getPara("author"));
		scrollImgPo.setState(0);
		
		scrollImgService.addScrollImg(scrollImgPo);
		
		render("/manage/home_scroll_imgs_list.html");
		
	}
	
    @SuppressWarnings("deprecation")
	public void del(){  //删除
		
    	String uploadPath = getRequest().getRealPath("/upload/");   //图片路径

		renderJson(scrollImgService.delScrollImg(getPara("id"),uploadPath+"/"+getPara("imgUrl")));
	}
    
    public void queryList(){  //查询列表
		
		renderJson(scrollImgService.queryScrollImgs());
	}
    
    public void config(){  //修改状态
    	
    	renderJson(scrollImgService.configState(getPara("id")));
    }
}
