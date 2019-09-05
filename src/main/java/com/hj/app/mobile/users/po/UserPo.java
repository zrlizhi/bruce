package com.hj.app.mobile.users.po;

import java.io.Serializable;

import com.jfinal.plugin.activerecord.Record;

public class UserPo implements Serializable {

	/**
	 * @author huajian
	 * @serialField
	 */
	private static final long serialVersionUID = 1L;
	
	private String openid;    //获取用户openid
	private String username;  //用户微信昵称
	private String gender;    //性别
	private String user_icon; //用户头像
	private String city;      //用户所在城市
	private String province;  //用户所在的省份
	private String addDate;   //添加日期
	
	private int isCard; //是否有明信片
	private int isShopMsg;//是否有商品信息
	
	private Record cardInfo;//明信片信息
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUser_icon() {
		return user_icon;
	}
	public void setUser_icon(String user_icon) {
		this.user_icon = user_icon;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAddDate() {
		return addDate;
	}
	public void setAddDate(String addDate) {
		this.addDate = addDate;
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
