package com.hj.app.manage.pending.service;

import com.hj.app.manage.pending.po.PendingForManagePo;
import com.hj.app.manage.pending.po.PendingPo;
import com.hj.app.model.ResultData;

public interface IPendingService {

	public boolean add(PendingPo po);
	
	public PendingForManagePo list();
	
	public ResultData update(String id,int state,String content);
}
