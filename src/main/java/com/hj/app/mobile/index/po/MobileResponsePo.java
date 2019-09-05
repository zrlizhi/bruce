package com.hj.app.mobile.index.po;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;

public class MobileResponsePo {

	private int code;  //状态码
	private String msg;  //错误信息
	
	private List<Record> scroImgs; //首页滚动图片
	
	private List<Record> actives; //首页动态
	
	private List<Record> varietys; //首页品种
	
	private List<Record> hots; //热门商家
	
	private List<Record> produces; //最新产品
	
	private List<Record> orders; //订单

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<Record> getScroImgs() {
		return scroImgs;
	}

	public void setScroImgs(List<Record> scroImgs) {
		this.scroImgs = scroImgs;
	}

	public List<Record> getActives() {
		return actives;
	}

	public void setActives(List<Record> actives) {
		this.actives = actives;
	}

	public List<Record> getVarietys() {
		return varietys;
	}

	public void setVarietys(List<Record> varietys) {
		this.varietys = varietys;
	}

	public List<Record> getHots() {
		return hots;
	}

	public void setHots(List<Record> hots) {
		this.hots = hots;
	}

	public List<Record> getProduces() {
		return produces;
	}

	public void setProduces(List<Record> produces) {
		this.produces = produces;
	}

	public List<Record> getOrders() {
		return orders;
	}

	public void setOrders(List<Record> orders) {
		this.orders = orders;
	}
	
	
}
