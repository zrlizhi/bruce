package com.hj.app.manage.homevar.controller;

import java.io.File;

import com.hj.app.manage.homevar.po.HomeVarietyPo;
import com.hj.app.manage.homevar.service.IHomeVarietyService;
import com.hj.app.manage.homevar.service.imp.HomeVarietyService;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.RandomUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class HomeVarietyController extends Controller {

	private IHomeVarietyService homeVarietyService = new HomeVarietyService();
	
	@SuppressWarnings("deprecation")
	public void add(){   //添加品种
		
        String uploadPath = getRequest().getRealPath("/upload");   //图片路径
		
		UploadFile files = getFile();
		
		String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr();   //图片名称
		
		File fileInfo = new File(uploadPath + "/" +DateUtil.yearStr());   //按月创建图片文件夹
		
		if( !fileInfo.mkdir() ){    //判断文件夹是否存在，不存在创建
			
			fileInfo.mkdir();
		}
		
		final String filePath = uploadPath+"/"+DateUtil.yearStr()+"/"+path+".png";
		files.getFile().renameTo(new File(filePath));   //重命名图片
		
		HomeVarietyPo po = new HomeVarietyPo();
		
		po.setImgUrl(DateUtil.yearStr()+"/"+path+".png");
		po.setAuthor(getPara("author"));
		po.setVarName(getPara("vname"));
		
		homeVarietyService.save(po);
		
		render("/manage/home_var_list.html");
	}
	
	@SuppressWarnings("deprecation")
	public void del(){  //删除品种
		
		String uploadPath = getRequest().getRealPath("/upload/");   //图片路径
		renderJson(homeVarietyService.del(getPara("id"), uploadPath+"/"+getPara("imgUrl")));
	}
	
	public void list(){  //查询列表
		
		renderJson(homeVarietyService.list());
	}
}
