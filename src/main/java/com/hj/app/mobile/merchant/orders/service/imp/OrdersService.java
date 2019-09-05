package com.hj.app.mobile.merchant.orders.service.imp;

import com.hj.app.mobile.merchant.orders.model.OrdersModel;
import com.hj.app.mobile.merchant.orders.po.OrdersManagePo;
import com.hj.app.mobile.merchant.orders.service.IOrdersService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class OrdersService implements IOrdersService{

	/**
	 * 列表查询
	 * */
	@Override
	public OrdersManagePo queryOrdersList(int pageNum, int limit, String content) {
		
		PageUtil pageUtil = new PageUtil(pageNum, limit);

		OrdersManagePo po = new OrdersManagePo();

		po.setCode(0);
		po.setMsg("请求成功！");

		po.setCount(Db.findFirst("select count(*) as counts from orders a ").getInt("counts"));

		String sql = "select a.* from orders a where  1 = 1 ";

		if (null != content && 0 != content.length()) {

			sql += " and a.orders_id like '%" + content + "%' ";
		}

		sql += " order by a.addDate desc limit " + pageUtil.pageNum() + "," + pageUtil.nextPage();

		po.setData(Db.find(sql));

		return po;
	}

	@Override
	public Record queryOrdersInfo(String ordersId) {
		// TODO Auto-generated method stub
		return Db.findFirst(
				" select a.* from orders a where a.orders_id = '" + ordersId + "'");
	}

	@Override
	public ResultData updateOrders(OrdersModel po) {
		
		
		return null;
	}

	
}
