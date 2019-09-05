package com.hj.app.manage.varietys.service.imp;

import org.apache.commons.lang3.StringUtils;

import com.hj.app.manage.varietys.service.IVarietysDetailService;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class VarietysDetailService implements IVarietysDetailService{

	@Override
	public Record queryVarDetailById(String varId) {
		
		if(StringUtils.isBlank(varId)) {
			varId = "-1";
		}
		
		return Db.findFirst("select * from varietys_detail t where t.varid ="+Integer.parseInt(varId));
	}

	
}
