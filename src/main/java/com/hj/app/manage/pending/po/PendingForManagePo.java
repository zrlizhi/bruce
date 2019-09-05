package com.hj.app.manage.pending.po;

import java.util.List;

import com.hj.app.model.ResultData;
import com.jfinal.plugin.activerecord.Record;

public class PendingForManagePo extends ResultData{

	private int count;
	private List<Record> data;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<Record> getData() {
		return data;
	}
	public void setData(List<Record> data) {
		this.data = data;
	}
	
	
}
