package com.hj.app.manage.customer.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import com.hj.app.manage.customer.service.ICustomerService;
import com.hj.app.manage.po.ResultPo;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class CustomerService implements ICustomerService{

    private static Logger logger  = Logger.getLogger(CustomerService.class); 
	
	@Override
	public ResultPo customerListForManage(int pageNum, int limit, String context) {
		ResultPo po = new ResultPo();
		try {
			PageUtil pageUtil=new PageUtil(pageNum,limit); 
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id,a.ename,a.cname,tel,postion,address,customer_type,status,add_date,update_date from customer a where 1=1");
			List<Object> params = new ArrayList<>();
			if(StringUtils.isNotBlank(context)) {
				sql.append(" and a.ename like ? ");
				params.add("%"+context+"%");
			}
			Record shopInfo = Db.findFirst(" select count(*) as counts from customer a ");
			
			sql.append(" order by a.add_date desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage());
			
			po.setCode(0);
			po.setCount(shopInfo.getInt("counts"));
			po.setMsg("请求成功！");
			po.setData(Db.find(sql.toString(), params.toArray()));
			return po;
		} catch (Exception e) {
			logger.error("[注册企业信息查询失败]",e);
			po.setCode(0);
			po.setMsg("数据异常");
			return po;
		}
	}

	
}
