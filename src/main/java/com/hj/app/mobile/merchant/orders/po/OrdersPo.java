package com.hj.app.mobile.merchant.orders.po;

import java.io.Serializable;
import java.util.Date;

import com.jfinal.plugin.activerecord.Model;

public class OrdersPo extends Model<OrdersPo> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id; //id
	private String orders_id;//订单号
	private String status;//订单状态
	private String status_name;//订单名称
	private Date addDate;//创建时间
	
	public OrdersPo() {
		
	}
	

	public OrdersPo(String id, String orders_id, String status, String status_name, Date addDate) {
		super();
		this.id = id;
		this.orders_id = orders_id;
		this.status = status;
		this.status_name = status_name;
		this.addDate = addDate;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(String orders_id) {
		this.orders_id = orders_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}


	public Date getAddDate() {
		return addDate;
	}


	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}


	@Override
	public String toString() {
		return "OrdersPo [id=" + id + ", orders_id=" + orders_id + ", status=" + status + ", status_name=" + status_name
				+ ", addDate=" + addDate + "]";
	}


	
	
}
