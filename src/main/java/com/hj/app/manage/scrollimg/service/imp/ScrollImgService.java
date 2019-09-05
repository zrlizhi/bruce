package com.hj.app.manage.scrollimg.service.imp;

import java.util.List;

import com.hj.app.manage.scrollimg.model.ScrollImgModel;
import com.hj.app.manage.scrollimg.po.ScrollImgPo;
import com.hj.app.manage.scrollimg.service.IScrollImgService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.FileUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ScrollImgService implements IScrollImgService {

	@Override
	public ResultData addScrollImg(ScrollImgPo scrollImgPo) { // 添加滚动图片
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == scrollImgPo.getImgUrl() || 0 == scrollImgPo.getImgUrl().length() || null == scrollImgPo.getAuthor()
				|| 0 == scrollImgPo.getAuthor().length()) { // 检验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}

		try {

			if (addHandle(scrollImgPo)) { // 判断是否保存成功
				resultData.setCode(0);
				resultData.setMsg("请求成功！");

				return resultData;
			}

			resultData.setCode(-1);
			resultData.setMsg("请求失败！");

			return resultData;

		} catch (Exception e) {

			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}
	}

	private boolean addHandle(ScrollImgPo scrollImgPo) { // 添加操作
       
		return ScrollImgModel.dao.set("id", null).set("imgUrl", scrollImgPo.getImgUrl()).set("author", scrollImgPo.getAuthor())
				.set("addDate", DateUtil.getTimeStamp()).set("state", scrollImgPo.getState()).save();
	}

	@Override
	public ResultData delScrollImg(String id,String imgUrl) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if(null == id || 0 == id.length() ){  //校验参数
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}
		
		try{
			
			if(ScrollImgModel.dao.deleteById(id)){  //判断是否删除成功
				FileUtil.delFile(imgUrl);
				resultData.setCode(0);
				resultData.setMsg("删除成功！");

				return resultData;
			}
			
			resultData.setCode(-1);
			resultData.setMsg("删除失败！");

			return resultData;
			
		}catch(Exception e){
			
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");

			return resultData;
		}
		
	}

	@Override
	public ResultData queryScrollImgs() {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		try{
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
            resultData.setResultData(Db.find("select * from home_scroll_img a "));
			return resultData;
			
		}catch(Exception e){
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");

			return resultData;
		}
	}

	@Override
	public ResultData configState(String id) {  //修改状态
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		try{
			
			if( null == id || 0 == id.length() ){  //检验参数
				
				resultData.setCode(-1);
				resultData.setMsg("参数错误！");

				return resultData;
			}
			
			ScrollImgModel info = ScrollImgModel.dao.findFirst("select * from home_scroll_img a where a.id="+id);   //更具ID查询信息
			
			if( null == info ){  //判断信息是否存在
				
				resultData.setCode(-1);
				resultData.setMsg("信息不存在！");

				return resultData;
			}
			
			if(info.set("state", 0 == info.getInt("state") ? 1 : 0 ).update()){   //判断是否更新成功!
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
	public List<Record> appHomeList() {
		// TODO Auto-generated method stub
		return Db.find(" select a.imgUrl from home_scroll_img a where a.state=1 ");
	}

}
