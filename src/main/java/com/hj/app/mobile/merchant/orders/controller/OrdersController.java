package com.hj.app.mobile.merchant.orders.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hj.app.mobile.merchant.orders.model.OrdersModel;
import com.hj.app.mobile.merchant.orders.po.OrdersManagePo;
import com.hj.app.mobile.merchant.orders.po.UsersPo;
import com.hj.app.mobile.merchant.orders.service.IOrdersService;
import com.hj.app.mobile.merchant.orders.service.imp.OrdersService;
import com.hj.app.model.ResultData;
import com.jfinal.core.Controller;

public class OrdersController extends Controller{
	private IOrdersService ordersService = new OrdersService();
	
	
	public void queryOrdersList() {
		renderJson(ordersService.queryOrdersList(getParaToInt("page"),getParaToInt("limit"),getPara("content")));
	}
	
	public void ordersEdit() {
		setAttr("orders", ordersService.queryOrdersInfo(getPara("orderid")));
		render("/manage/orders/orders_edit.html");
	}
	
	public void ordersTest() {
		setAttr("orders", ordersService.queryOrdersInfo(getPara("orderid")));
		render("/manage/testTableNest.html");
	}
	
	public void updateOrders() {
		OrdersModel po = getModel(OrdersModel.class,"orders");
		po.set("addDate",new Date());
		po.update();
		
		ResultData resultData = new ResultData();
		resultData.setCode(1);
		resultData.setMsg("成功");
		renderJson(resultData);
	}
	
	public void user() {
		OrdersManagePo po = new OrdersManagePo();

		po.setCode(0);
		po.setMsg("请求成功！");
		po.setData(getUses());
		
		renderJson(po);
	}
	
	private List<UsersPo> getUses(){
		List<UsersPo> list= new ArrayList<UsersPo>();
		for(int i= 0;i<10;i++) {
			UsersPo po = new UsersPo();
			po.setId(i);
			po.setCity("上海"+i);
			po.setExperience("积分"+i);
			po.setRight("right"+i+i);
			po.setScore("score"+i);
			po.setSex("sex"+i);
			po.setUsername("张三"+i);
			list.add(po);
		}
		
		return list;
	}
}