package com.hj.app.mobile.merchant.actives.po;

import com.hj.app.model.ResultData;

/**
 * 
 * @author huajian
 * 动态po
 */
public class ActivesForManagePo extends ResultData{

	private int count;
	private Object data;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	
}
