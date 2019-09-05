package com.hj.app.mobile.users.po;

import java.io.Serializable;

import com.jfinal.plugin.activerecord.Record;

public class UserLoginResponsePo implements Serializable {

	/**
	 * @author huajian
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	private int isCard; //是否有明信片
	private int isShopMsg;//是否有商品信息
	
	private UserPo userInfo; //用户信息
	private Record cardInfo;//明信片信息
	
	
	public UserPo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserPo userInfo) {
		this.userInfo = userInfo;
	}
	public int getIsCard() {
		return isCard;
	}
	public void setIsCard(int isCard) {
		this.isCard = isCard;
	}
	public int getIsShopMsg() {
		return isShopMsg;
	}
	public void setIsShopMsg(int isShopMsg) {
		this.isShopMsg = isShopMsg;
	}
	public Record getCardInfo() {
		return cardInfo;
	}
	public void setCardInfo(Record cardInfo) {
		this.cardInfo = cardInfo;
	}
	
	
	
}
