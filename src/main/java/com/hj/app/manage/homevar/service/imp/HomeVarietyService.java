package com.hj.app.manage.homevar.service.imp;

import java.util.List;

import com.hj.app.manage.homevar.model.HomeVarietyModel;
import com.hj.app.manage.homevar.po.HomeVarietyPo;
import com.hj.app.manage.homevar.service.IHomeVarietyService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.FileUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class HomeVarietyService implements IHomeVarietyService {

	@Override
	public ResultData save(HomeVarietyPo po) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if (null == po.getImgUrl() || 0 == po.getImgUrl().length() || null == po.getVarName()
				|| 0 == po.getVarName().length()) { // 校验参数
   
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			
			return resultData;
		}
		
		try{
			
			if(saveHandle(po)){
				
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
	
	private boolean saveHandle(HomeVarietyPo po){  //保存品种
		
		return HomeVarietyModel.dao.set("id", null)
				.set("var_name", po.getVarName())
				.set("imgUrl", po.getImgUrl())
				.set("addDate", DateUtil.getTimeStamp())
				.set("author", po.getAuthor())
				.save();
	}

	@Override
	public ResultData del(String id ,String imgUrl) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == id || 0 == id.length() ){  //校验参数
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			
			return resultData;
		}
		
		HomeVarietyModel info = HomeVarietyModel.dao.findById(id);  //查询详情
		
		if( null == info ){  //判断信息是否存在
			
			resultData.setCode(-1);
			resultData.setMsg("信息不存在！");
			
			return resultData;
		}
		
		if(info.deleteById(id)){   //判断删除是否成功
			
			FileUtil.delFile(imgUrl);
			
			resultData.setCode(0);
			resultData.setMsg("删除成功！");
			
			return resultData;
		}
		
		resultData.setCode(0);
		resultData.setMsg("删除失败！");
		
		return resultData;
	}

	@Override
	public ResultData list() {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		try{
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			
			resultData.setResultData(Db.find("select * from home_varietys a "));
			
			return resultData;
			
		}catch(Exception e){
			
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			
			return resultData;
		}
		
	}

	@Override
	public List<Record> appHomeList() {
		// TODO Auto-generated method stub
		return Db.find("select a.imgUrl,a.id,a.vname as var_name,a.id as var_id from varietys a where a.state=1");
	}

}
