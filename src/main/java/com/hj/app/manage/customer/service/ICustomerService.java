package com.hj.app.manage.customer.service;

import com.hj.app.manage.po.ResultPo;

public interface ICustomerService {

	public ResultPo customerListForManage(int page,int limit,String context);
}
