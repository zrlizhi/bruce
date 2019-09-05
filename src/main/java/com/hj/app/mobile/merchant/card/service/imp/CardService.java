package com.hj.app.mobile.merchant.card.service.imp;

import com.hj.app.mobile.merchant.card.model.CardModel;
import com.hj.app.mobile.merchant.card.po.CardPo;
import com.hj.app.mobile.merchant.card.service.ICardService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.ImageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class CardService implements ICardService {

	
	@Override
	public ResultData saveOrUpdateCard(CardPo cardPo) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		if( null == cardPo.getOpenid() || 0 == cardPo.getOpenid().length() ){  //校验参数
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		Record cardInfo = Db.findFirst("select * from mer_card a where a.id = '"+cardPo.getOpenid()+"'");  //校验是否存在重复信息
		if( null != cardInfo ){
			return updateCardHandle(resultData,cardPo);
		}
		
		return saveCardHandle(resultData,cardPo);
		
	}
	
	private ResultData saveCardHandle(ResultData resultData,CardPo cardPo){  //保存信息
		
		CardModel cardModel = CardModel.dao;   //设置保存参数
		 
		cardModel.set("id", cardPo.getOpenid());
		cardModel.set("slogan", cardPo.getSlogan());
		cardModel.set("contact_name", cardPo.getContact_name());
		cardModel.set("contact_tel", cardPo.getContact_tel());
		cardModel.set("mer_name", cardPo.getMer_name());
		cardModel.set("mer_addr", cardPo.getMer_addr());
		cardModel.set("mer_goods", cardPo.getMer_goods());
		cardModel.set("addDate", DateUtil.getTimeStamp());
		
		try{
			if(cardModel.save()){   //校验是否保存成功
				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
		}catch(Exception e){
			resultData.setCode(-2);
			resultData.setMsg("数据异常！");
			return resultData;
		}
	}
	
	private ResultData updateCardHandle(ResultData resultData,CardPo cardPo){   //编辑信息
		
		CardModel cardModel = CardModel.dao.findById(cardPo.getOpenid());   //设置保存参数 
		
		cardModel.set("slogan", cardPo.getSlogan());
		cardModel.set("contact_name", cardPo.getContact_name());
		cardModel.set("contact_tel", cardPo.getContact_tel());
		cardModel.set("mer_name", cardPo.getMer_name());
		cardModel.set("mer_addr", cardPo.getMer_addr());
		cardModel.set("mer_goods", cardPo.getMer_goods());
		
		try {
			if(cardModel.update()){
				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultData.setCode(-2);
			resultData.setMsg("数据异常！");
			return resultData;
		}
	}

	@Override
	public ResultData queryCardInfoById(String openid) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == openid || 0 == openid.length() ){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		Record cardInfo = Db.findFirst("select * from mer_card a where a.id = '"+openid+"'"); 
		if( null == cardInfo ){
			resultData.setCode(-1);
			resultData.setMsg("信息不存在！");
			return resultData;
		}
		
		resultData.setCode(0);
		resultData.setMsg("查询成功！");
		resultData.setResultData(cardInfo);
		
		return resultData;
	}

	@Override
	public ResultData sendCard(String openid,String path) {
		// TODO Auto-generated method stub
        ResultData resultData = new ResultData();
		
		if( null == openid || 0 == openid.length() ){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		Record cardInfo = Db.findFirst("select * from mer_card a where a.id = '"+openid+"'"); 
		if( null == cardInfo ){
			resultData.setCode(-1);
			resultData.setMsg("信息不存在！");
			return resultData;
		}
		
		resultData.setCode(0);
		resultData.setMsg("查询成功！");
		resultData.setResultData(ImageUtil.htmlCompressImage(path, cardInfo));
		
		return resultData;
	}


}
