package com.hj.app.mobile.merchant.orders.service;

import com.hj.app.mobile.merchant.orders.model.OrdersModel;
import com.hj.app.mobile.merchant.orders.po.OrdersManagePo;
import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public interface IOrdersService {

	public OrdersManagePo queryOrdersList(int pageNum, int limit, String content) ;
	
	public Record queryOrdersInfo(String ordersId); //动态详情
	
	public ResultData updateOrders(OrdersModel po);
}
