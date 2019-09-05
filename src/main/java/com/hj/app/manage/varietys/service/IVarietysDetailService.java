package com.hj.app.manage.varietys.service;

import com.jfinal.plugin.activerecord.Record;

public interface IVarietysDetailService {

	public Record queryVarDetailById(String varId);
}
