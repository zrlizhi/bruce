package com.hj.app.mobile.merchant.card.controller;

import com.hj.app.mobile.merchant.card.po.CardPo;
import com.hj.app.mobile.merchant.card.service.ICardService;
import com.hj.app.mobile.merchant.card.service.imp.CardService;
import com.jfinal.core.Controller;

/**
 * 
 * @author huajian
 *
 */
public class CardController extends Controller {

	private ICardService cardService = new CardService();
	
	public void saveOrUpdateMerCard(){   //保存商户明信片信息
		
		CardPo cardPo = new CardPo();
		
		cardPo.setOpenid(getPara("openId"));
		cardPo.setSlogan(getPara("slogan"));
		cardPo.setContact_name(getPara("contactName"));
		cardPo.setContact_tel(getPara("contactTel"));
		cardPo.setMer_name(getPara("merName"));
		cardPo.setMer_addr(getPara("merAddr"));
		cardPo.setMer_goods(getPara("variety"));
		
		renderJson(cardService.saveOrUpdateCard(cardPo));
		
	}
	
	public void getCardInfo(){  //获取明信片信息
		
		renderJson(cardService.queryCardInfoById(getPara("openId")));
	}
	
    @SuppressWarnings("deprecation")
	public void sendCard(){
		
		String uploadPath = getRequest().getRealPath("/card");   //图片路径
		
		renderJson(cardService.sendCard(getPara("openId"), uploadPath));
	}
}
