package com.hj.app.manage.customer.controller;

import com.hj.app.manage.customer.service.ICustomerService;
import com.hj.app.manage.customer.service.imp.CustomerService;
import com.jfinal.core.Controller;

public class CustomerController extends Controller{

	private ICustomerService customerService= new CustomerService();
	
	public void customerListForManage() {
		renderJson(customerService.customerListForManage(getParaToInt("page"),getParaToInt("limit"),getPara("shopName")));
	}
}
