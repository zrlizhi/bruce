package com.hj.app.mobile.merchant.shop.service.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hj.app.manage.pending.po.PendingPo;
import com.hj.app.manage.pending.service.IPendingService;
import com.hj.app.manage.pending.service.imp.PendingService;
import com.hj.app.mobile.merchant.produce.service.IProduceService;
import com.hj.app.mobile.merchant.produce.service.imp.ProduceService;
import com.hj.app.mobile.merchant.shop.model.ShopModel;
import com.hj.app.mobile.merchant.shop.po.ShopInfoForManage;
import com.hj.app.mobile.merchant.shop.po.ShopInfoPo;
import com.hj.app.mobile.merchant.shop.po.ShopManagePo;
import com.hj.app.mobile.merchant.shop.po.ShopPo;
import com.hj.app.mobile.merchant.shop.service.IShopService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ShopService implements IShopService {

	private IProduceService produceService = new ProduceService();  //产品
	private IPendingService pendingService = new PendingService(); //待审核
	
	@Override
	public ResultData saveOrUpdateShopMsg(ShopPo shopPo) {  //添加店铺基本信息
		// TODO Auto-generated method stub
		
		ResultData result = new ResultData();
		
		if( null == shopPo.getId() || 0 == shopPo.getId().length()){
			result.setCode(-1);
			result.setMsg("参数错误！");
			return result;
		}
		
		Record shopInfo = Db.findFirst("select * from shop a where a.id = '"+shopPo.getId()+"'");  //校验是否存在重复信息
		
		if( null != shopInfo ){
			return updateShopHandle(result,shopPo);
		}
		
		
		return saveShopHandle(result,shopPo);
	}
	
	private ResultData saveShopHandle(ResultData result,ShopPo shopPo){  //保存店铺基本信息
		
        ShopModel shopModel = ShopModel.dao;
		
		shopModel.set("id", shopPo.getId());
		shopModel.set("shopnum", DateUtil.dateStr());
		shopModel.set("shop_img", shopPo.getShop_img());
		shopModel.set("shop_name", shopPo.getShop_name());
		shopModel.set("contact_name", shopPo.getContact_name());
		shopModel.set("contact_tel", shopPo.getContact_tel());
		shopModel.set("shop_addr", shopPo.getShop_addr());
		shopModel.set("variety", shopPo.getVariety());
		shopModel.set("shop_introduce", shopPo.getShop_introduce());
		shopModel.set("license", shopPo.getLicense());
		shopModel.set("goods_ids", shopPo.getGoods_ids());
		shopModel.set("views", 0);
		shopModel.set("collects", 0);
		shopModel.set("longitude", shopPo.getLongitude());
		shopModel.set("latitude", shopPo.getLatitude());
		shopModel.set("addDate", DateUtil.getTimeStamp());
		
		try {
			if(shopModel.save() && addPending(shopPo)){  //判断保存是否成功
				result.setCode(0);
				result.setMsg("请求成功！");
				return result;
			}
			result.setCode(-1);
			result.setMsg("请求失败！");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setCode(-1);
			result.setMsg("数据异常！");
			return result;
		}
		
	}
	
	private boolean addPending(ShopPo shopPo){
		
		PendingPo po = new PendingPo();
		po.setSname(shopPo.getShop_name()+",店铺信息审核");
		po.setOpenid(shopPo.getId());
		po.setState(1);
		
		return pendingService.add(po);
	}
	
    private ResultData updateShopHandle(ResultData result,ShopPo shopPo){  //编辑店铺基本信息
		
        ShopModel shopModel = ShopModel.dao.findFirst("select * from shop a where a.id='"+shopPo.getId()+"'");
		
		shopModel.set("shop_img", shopPo.getShop_img());
		shopModel.set("shop_name", shopPo.getShop_name());
		shopModel.set("contact_name", shopPo.getContact_name());
		shopModel.set("contact_tel", shopPo.getContact_tel());
		shopModel.set("shop_addr", shopPo.getShop_addr());
		shopModel.set("variety", shopPo.getVariety());
		shopModel.set("shop_introduce", shopPo.getShop_introduce());
		shopModel.set("license", shopPo.getLicense());
		shopModel.set("longitude", shopPo.getLongitude());
		shopModel.set("latitude", shopPo.getLatitude());
		shopModel.set("goods_ids", shopPo.getGoods_ids());
		
		try {
			if(shopModel.update()){
				result.setCode(0);
				result.setMsg("请求成功！");
				return result;
			}
			result.setCode(-1);
			result.setMsg("请求失败！");
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result.setCode(-1);
			result.setMsg("数据异常！");
			return result;
		}
		
	}

	@Override
	public ResultData queryShopInfo(String openId) {
		// TODO Auto-generated method stub
		
		ResultData result = new ResultData();
		
		if( null == openId || 0 == openId.length()){  //校验参数
			result.setCode(-1);
			result.setMsg("参数错误！");
			return result;
		}
		
		Record shopInfo = Db.findFirst("select a.shop_img,a.shop_name,a.contact_name,a.contact_tel,a.shop_addr,a.variety,a.shop_introduce"
				+ ",a.license,a.longitude,a.latitude,a.state from shop a where a.id = '"+openId+"'");  //店铺详情
		if( null == shopInfo ){
			result.setCode(-1);
			result.setMsg("信息找不到！");
			return result;
		}
		
		Map<String,Object> info = new HashMap<String,Object>();
		
		info.put("shopInfo", shopInfo);
		info.put("produces", produceService.produceListForShop(openId));
		
		result.setCode(0);
		result.setMsg("查询成功！");
		result.setResultData(info);
		return result;
	}

	@Override
	public List<Record> appHomeList() {
		// TODO Auto-generated method stub
		return Db.find("select a.id,a.shop_img from shop a where a.state = 0 order by a.collects desc limit 10 "); 
	}

	@Override
	public ResultData searchShop(String content) {
		// TODO Auto-generated method stub
		
		ResultData result = new ResultData();
		
		if( null == content || 0 == content.length() || "undefine".equals(content)){  //校验参数
			
			result.setCode(-1);
			result.setMsg("参数错误！");
			return result;
			
		}
		
		List<Record> list = Db.find("select a.id,a.shop_img as imgUrl,a.shop_name,a.variety,a.views,a.contact_tel,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate from shop a where a.state = 0 and a.shop_name like '%"+content+"%'");
		if( null == list || 0 == list.size()){
			result.setCode(-2);
			result.setMsg("无数据！");
			return result;
		}
		
		for(Record info : list){
			
			Record activeInfo = Db.findFirst(" select count(*) as counts from actives a where a.openid='"+info.getStr("id")+"'");  //统计动态
			info.set("actives", activeInfo.getLong("counts"));
			
			Record couponInfo = Db.findFirst(" select count(*) as counts from coupon a where a.openid='"+info.getStr("id")+"'");  //统计优惠券
			info.set("coupons", couponInfo.getLong("counts"));
		}
		result.setCode(0);
		result.setMsg("请求成功！");
		result.setResultData(list);
		return result;
	}

	@Override
	public ResultData discountShopList(int pageNum) {
		// TODO Auto-generated method stub
		
         PageUtil pageUtil=new PageUtil(pageNum,Constants.PAGE_SIZE);
		
         String sql ="SELECT a.id,a.shop_img AS imgUrl,a.shop_name AS shopName,a.views,DATE_FORMAT(a.addDate,'%Y/%c/%d') AS ADDDATE,"
         		+ "IFNULL(b.accounts,0) AS coupons FROM ( SELECT a.* FROM shop a,coupon b WHERE a.state = 0 and a.id=b.openid GROUP BY a.id) a  LEFT JOIN "
         		+ "( SELECT b.openid,SUM(b.ccounts) AS accounts FROM coupon b WHERE b.state=0 GROUP BY b.openid) b "
         		+ "on a.id = b.openid ORDER BY a.addDate desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage();
         
         ResultData resultData = new ResultData();
         
		try{
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(Db.find(sql));
			return resultData;
			
		}catch(Exception e){
			
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
		
	}
	
	@Override
	public ResultData hotShopList(int pageNum) {
		// TODO Auto-generated method stub
		
         PageUtil pageUtil=new PageUtil(pageNum,Constants.PAGE_SIZE);
		
         String sql ="select a.*,IFNULL(b.accounts,0) as actives from (select a.id,a.shop_img as imgUrl,a.shop_name as shopName,a.views,"
         		+ "DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate "
         		+ "from shop a where a.state = 0) a LEFT JOIN ( select b.openid,count(b.openid) as accounts "
         		+ "from actives b GROUP BY b.openid) b on a.id = b.openid "
         		+ "ORDER BY a.views desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage();
         
         ResultData resultData = new ResultData();
         
		try{
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(Db.find(sql));
			return resultData;
			
		}catch(Exception e){
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
		
	}

	@Override
	public ResultData search(String content) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == content || 0 == content.length()){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		try{
			
			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(Db.find("select a.shop_name as content from shop a where a.state = 0 and a.shop_name like '%"+content+"%'"));
			return resultData;
			
		}catch(Exception e){
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
		
	}

	@Override
	public ShopInfoPo queryShopInfoForOther(String openId) {
		// TODO Auto-generated method stub
		
		ShopInfoPo po = new ShopInfoPo();
		
		if( null == openId || 0 == openId.length() ){
			
			po.setCode(-1);
			po.setMsg("参数错误！");
			
			return po;
		}
		
		ShopModel shopInfo = ShopModel.dao.findFirst("select a.id,a.shop_img,a.shop_name,a.shop_introduce,a.license,a.shop_addr,a.contact_tel,a.contact_name,a.variety,a.longitude,a.latitude,a.views,a.state from shop a where a.id='"+openId+"'");
		if( null == shopInfo ){  //查询店铺详情
			po.setCode(-1);
			po.setMsg("无法找到商户信息！");
			return po;
		}
		
		po.setCode(0);
		po.setMsg("请求成功！");
		
		shopInfo.set("views", shopInfo.getInt("views")+1).update();
		
		po.setShopInfo(shopInfo.toRecord()); //商户信息
		po.setProduces(Db.find("select * from produce a where a.openid = '"+openId+"' order by a.addDate desc"));  //产品信息
		
		po.setCoupons(Db.find("select a.id,a.moneys,a.openid,a.content,DATE_FORMAT(a.startDate,'%Y/%c/%d') as startDate,DATE_FORMAT(a.endDate,'%Y/%c/%d') as endDate,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate,a.coupon_type,a.state,a.ccounts,a.description from coupon a where a.openid = '"+openId+"' order by a.addDate desc "));  //优惠券信息
		
		
		po.setActiveInfo(Db.findFirst("select * from actives a where a.openid = '"+openId+"' order by a.addDate desc limit 1 ")); //动态信息
		
		
		return po;
	}

	@Override
	public ResultData collectShop(String shopId, String openId) {
		// TODO Auto-generated method stub
		
        ResultData resultData = new ResultData();
		
		if( null == shopId || 0 == shopId.length() || null == openId || 0 == openId.length() ){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		return null;
	}

	@Override
	public ShopManagePo shopListForManage(int pageNum,int limit,String shopName) {
		// TODO Auto-generated method stub
		
		ShopManagePo po = new ShopManagePo();
		
		try{
			
			PageUtil pageUtil=new PageUtil(pageNum,limit); 
			
			String sql = " select a.id,a.shopnum,a.shop_name,a.contact_name,a.contact_tel,a.shop_addr,a.variety,"
					+ "a.views,a.collects,DATE_FORMAT(a.addDate,'%Y-%c-%d') as addDate from shop a where a.state = 0 and 1=1 ";
			
			if( null != shopName && 0 != shopName.length() ){
				
				sql += " and a.shop_name like '%"+shopName+"%' ";
			}
			
			sql +=" order by a.addDate desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage();
			
			Record shopInfo = Db.findFirst(" select count(*) as counts from shop a where a.state = 0 ");
			
			po.setCode(0);
			po.setCount(shopInfo.getInt("counts"));
			po.setMsg("请求成功！");
			po.setData(Db.find(sql));
			return po;
			
		}catch(Exception e){
			po.setCode(-1);
			po.setMsg("数据异常");
			return po;
		}
		
	}

	@Override
	public ShopInfoForManage queryShopInfoForManage(String openId) {
		// TODO Auto-generated method stub
		
		ShopInfoForManage  info = new ShopInfoForManage();
		
		Record shopInfo = Db.findFirst("select a.shop_img,a.shop_name,a.contact_name,a.contact_tel,a.shop_addr,a.variety,a.shop_introduce"
				+ ",a.license,a.longitude,a.latitude,DATE_FORMAT(a.addDate,'%Y-%c-%d') as addDate,a.views,a.collects,a.shopnum from shop a where a.id = '"+openId+"'");  //店铺详情
		
		info.setShopInfo(shopInfo);
		info.setProduces(produceService.produceListForShop(openId));
		
		return info;
	}

	@Override
	public ResultData checkShop(String id) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		if( null == id || 0 == id.length() ){
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}
		
		Record info = Db.findFirst("select * from shop a where a.id = '"+id+"' ");
		if( null == info ){
			resultData.setCode(0);
			resultData.setMsg("无店铺信息！");
			return resultData;
		}
		
		if( 1 == info.getInt("state") ){
			resultData.setCode(1);
			resultData.setMsg("店铺信息正在审核，请耐心等待！");
			return resultData;
		}
		if( 2 == info.getInt("state") ){
			resultData.setCode(2);
			resultData.setMsg("店铺信息审核，未通过，请重新编辑信息！");
			return resultData;
		}
		resultData.setCode(info.getInt("state"));
		resultData.setMsg("");
		return resultData;
	}
	

}
