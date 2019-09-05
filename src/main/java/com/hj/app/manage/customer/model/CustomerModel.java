package com.hj.app.manage.customer.model;

import com.jfinal.plugin.activerecord.Model;

public class CustomerModel extends Model<CustomerModel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final CustomerModel dao = new CustomerModel().dao();
	
	

}
