package com.hj.app.manage.varietys.controller;

import java.io.File;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.hj.app.manage.po.ResultPo;
import com.hj.app.manage.varietys.model.VarietysDetailModel;
import com.hj.app.manage.varietys.model.VarietysModel;
import com.hj.app.manage.varietys.po.VarietysPo;
import com.hj.app.manage.varietys.service.IVarietysService;
import com.hj.app.manage.varietys.service.imp.VarietysService;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.RandomUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class VarietysController extends Controller {

	IVarietysService var = new VarietysService();
	
	public void queryVarietys(){  //查询所有经营品种
		
		renderJson(JSON.parseArray(var.queryVarietys()));
	}
	
	public void addOrUpdateVar(){  //编辑或修改经营品种
		
		
        String uploadPath = getRequest().getRealPath("/upload");   //图片路径
		
		UploadFile files = getFile();
		
		String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr();   //图片名称
		
		File fileInfo = new File(uploadPath + "/" +DateUtil.yearStr());   //按月创建图片文件夹
		
		if( !fileInfo.mkdir() ){    //判断文件夹是否存在，不存在创建
			
			fileInfo.mkdir();
		}
		
		final String filePath = uploadPath+"/"+DateUtil.yearStr()+"/"+path+".png";
		files.getFile().renameTo(new File(filePath));   //重命名图片
		
		VarietysPo varietysPo = new VarietysPo();
		
		varietysPo.setImgUrl(DateUtil.yearStr()+"/"+path+".png");
		varietysPo.setAuthor(getPara("author"));

		varietysPo.setVname(getPara("vname"));
		var.saveOrUpdateVarietys(varietysPo);
		render("/manage/varietys.html");
	}
	
	public void deleteVarietys(){   //删除经营品种
		
		renderJson(var.deleteVarietys(getPara("id")));
	}
	
	public void search(){  //查询经营品种
		
		renderJson(var.search(getPara("content")));
	}
	
	public void countVars(){
		
		renderJson(var.countVars());
	}
	
	public void varState(){
		
		renderJson(var.varState(getParaToInt("id")));
	}
	
	public void queryChildVarsList() {
		
		renderJson(var.ququeryChildVarsListery(getParaToInt("page"), getParaToInt("limit"), getPara("parentId")));
	}
	
	
	public void addOrupdateVarDetail() {
		String varId = getPara("varietys_detail.varid");
		String parentId = getPara("parentId");
		VarietysDetailModel model = getModel(VarietysDetailModel.class,"varietys_detail");
		ResultPo po = new ResultPo();
		if(StringUtils.isBlank(varId)) {
			VarietysModel varM = new VarietysModel();
			varM.set("vname", model.get("brand_name"));
			varM.set("imgUrl", model.get("imgUrl"));
			varM.set("author", getPara("author"));
			varM.set("state", 0);
			varM.set("parentid", parentId);
			varM.set("var_level", "002");
			varM.set("vname", model.get("brand_name"));
			varM.set("addDate", new Date());
			po = var.saveOrUpdateVarietys(varM);
			if(po.getCode()==0) {
				model.set("addDate", new Date());
				model.set("varid", po.getData());
				model.set("author",getPara("author"));
				var.saveOrUpdateVarDetails(model);
			}
		}else {
			model.set("updateDate", new Date());
			model.set("author",getPara("author"));
			po = var.saveOrUpdateVarDetails(model);
			if(po.getCode()==0) {
				VarietysModel varM = new VarietysModel();
				varM.set("vname", model.get("brand_name"));
				varM.set("imgUrl", model.get("imgUrl"));
				varM.set("author", getPara("author"));
				varM.set("state", 0);
				varM.set("parentid", parentId);
				varM.set("var_level", "002");
				varM.set("vname", model.get("brand_name"));
				varM.set("id", model.get("varid"));
				po = var.saveOrUpdateVarietys(varM);
			}

		}
		
		
		
		renderJson(po);
	}
	
	public void deleteVarDetails() {
		
		renderJson(var.deleteVarDetails(getPara("varId")));
	}
}
