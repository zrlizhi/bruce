package com.hj.app.manage.varietys.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.manage.varietys.model.VarietysDetailModel;
import com.hj.app.manage.varietys.model.VarietysModel;
import com.hj.app.manage.varietys.po.VarietysPo;
import com.hj.app.manage.varietys.service.IVarietysService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.PageUtil;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class VarietysService implements IVarietysService {

	@Override
	public String queryVarietys() {
		// TODO Auto-generated method stub

		return JsonKit.toJson(Db.find("select * from varietys a order by a.state desc "));
	}

	@Override
	public ResultData saveOrUpdateVarietys(VarietysPo varietysPo) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == varietysPo.getVname() || 0 == varietysPo.getVname().length()){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		try{
			VarietysModel varietysModel = VarietysModel.dao;

			varietysModel.set("id", null);
			varietysModel.set("vname", varietysPo.getVname());
			varietysModel.set("imgUrl", varietysPo.getImgUrl());
			varietysModel.set("author", varietysPo.getAuthor());
			varietysModel.set("addDate", DateUtil.getTimeStamp());
			
			if(varietysModel.save()){
				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
		}catch(Exception e){
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
	}

	@Override
	public ResultData deleteVarietys(String id) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		if(null == id || 0 == id.length()){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		try {
			
			if(VarietysModel.dao.deleteById(id)){
				resultData.setCode(0);
				resultData.setMsg("删除成功！");
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("删除失败！");
			return resultData;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
	}

	@Override
	public ResultData search(String vname) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == vname || 0 == vname.length()){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		try{
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(Db.find("select a.vname as content from varietys a where a.vname like '%"+vname+"%'"));
			return resultData;
			
		}catch(Exception e){
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
	}

	@Override
	public ResultData countVars() {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		try{			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(Db.find("SELECT COUNT(a.id) AS counts,a.vname FROM varietys a,produce b WHERE FIND_IN_SET(a.vname,b.classify) GROUP BY a.id "));
			return resultData;
		}catch(Exception e){
			
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
	}

	@Override
	public ResultData varState(int id) {
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();
		VarietysModel info = VarietysModel.dao.findFirst("select * from varietys a where a.id ="+id);
		if(null == info ){
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
			
		}
		
		info.set("state", 0 == info.getInt("state") ? 1 : 0);
		
		if(info.update()){
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			return resultData;
		}
		resultData.setCode(0);
		resultData.setMsg("请求失败！");
		return resultData;
		
	}

	@Override
	public ResultPo ququeryChildVarsListery(int pageNum,int limit,String parentId) {
		ResultPo po = new ResultPo();
		PageUtil pageUtil = new PageUtil(pageNum, limit);
		
		if(StringUtils.isBlank(parentId)) {
			po.setCode(-1);
			po.setMsg("参数错误");
			return po;
		}
		try{
			List<String> param = new ArrayList<String>();
			param.add(parentId);
			StringBuffer sql = new StringBuffer(" select * from varietys a where a.parentid = ? ");
			
			sql.append(" order by a.addDate desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage());
			
			Record count = Db.findFirst("select count(*) as counts from varietys a where a.parentid = ? ",param.toArray());
			po.setCode(0);
			po.setMsg("请求成功");
			po.setCount(count.getInt("counts"));
			po.setData(Db.find(sql.toString(),param.toArray()));
			
		}catch (Exception e) {
			po.setCode(-1);
			po.setMsg("请求失败");
		}
		
		return po;
	}

	@Override
	public ResultPo saveOrUpdateVarDetails(VarietysDetailModel model) {
		
		ResultPo ResultPo = new ResultPo();
		
		if( StringUtils.isBlank((CharSequence) model.get("brand_name")) ||model.get("varid")==null){
			ResultPo.setCode(-1);
			ResultPo.setMsg("参数错误！");
			return ResultPo;
		}
		
		
		try{
			Record count = Db.findFirst("select count(*) as counts from varietys_detail where varid =  "+model.getInt("varid"));
			if(count.getInt("counts")==0) {
				model.save();
			}else {
				model.update();
			}
			model.update();
			ResultPo.setCode(0);
			ResultPo.setMsg("请求成功！");
			return ResultPo;
		}catch(Exception e){
			e.printStackTrace();
			ResultPo.setCode(-1);
			ResultPo.setMsg("数据异常！");
			return ResultPo;
		}
	}

	@Override
	public ResultPo saveOrUpdateVarietys(VarietysModel model) {
		// TODO Auto-generated method stub
		ResultPo po = new ResultPo();
		try {
			Record count = Db.findFirst("select count(*) as counts from varietys where id =  "+model.getInt("id"));
			if(count.getInt("counts")==0) {
				if(model.save()) {
					po.setCode(0);
					po.setMsg("请求成功");
					po.setData(model.getInt("id"));
					return po;
				}
			}else {
				if(model.update()) {
					po.setCode(0);
					po.setMsg("请求成功");
					po.setData(model.getInt("id"));
					return po;
				}
			}
			
			po.setCode(-1);
			po.setMsg("请求失败");
			return po;
		}catch (Exception e) {
			po.setCode(-1);
			po.setMsg("请求失败");
			return po;
		}
	}

	@Override
	public ResultPo deleteVarDetails(String varId) {

		ResultPo resultPo = new ResultPo();
		if(null == varId || 0 == varId.length()){
			resultPo.setCode(-1);
			resultPo.setMsg("参数错误！");
			return resultPo;
		}
		
		try {
			VarietysDetailModel varDeatil = new VarietysDetailModel();
			varDeatil.set("varid", varId);
			if(varDeatil.delete()){
				resultPo.setCode(0);
				resultPo.setMsg("删除成功！");
			}
			if(resultPo.getCode()==0) {
				VarietysModel var = new VarietysModel();
				var.set("id", varId);
				if(var.deleteById(varId)) {
					resultPo.setCode(0);
					resultPo.setMsg("删除成功！");
					return resultPo;
				}
			}
			resultPo.setCode(-1);
			resultPo.setMsg("删除失败！");
			return resultPo;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			resultPo.setCode(-1);
			resultPo.setMsg("数据异常！");
			return resultPo;
		}
		
	}

}
