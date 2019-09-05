package com.hj.app.mobile.merchant.card.service;

import com.hj.app.mobile.merchant.card.po.CardPo;
import com.hj.app.model.ResultData;

/**
 * 
 * @author huajian
 *
 */
public interface ICardService {

	ResultData saveOrUpdateCard(CardPo cardPo);  //保存明信片信息
	
	ResultData queryCardInfoById(String openid);  //查询当前明细片信息
	
	ResultData sendCard(String openid,String path);
	
}
