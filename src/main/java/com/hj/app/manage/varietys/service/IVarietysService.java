package com.hj.app.manage.varietys.service;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.manage.varietys.model.VarietysDetailModel;
import com.hj.app.manage.varietys.model.VarietysModel;
import com.hj.app.manage.varietys.po.VarietysPo;
import com.hj.app.model.ResultData;

public interface IVarietysService {

     String queryVarietys();
     
     ResultData saveOrUpdateVarietys(VarietysPo varietysPo);
     
     ResultData deleteVarietys(String id);
     
     ResultData search(String vanme);
     
     ResultData countVars();
     
     ResultData varState(int id);
     
     ResultPo ququeryChildVarsListery(int pageNum,int limit,String parentId);
     
     ResultPo saveOrUpdateVarDetails(VarietysDetailModel model);
     
     ResultPo saveOrUpdateVarietys(VarietysModel model);
     
     ResultPo deleteVarDetails(String varId);
}
