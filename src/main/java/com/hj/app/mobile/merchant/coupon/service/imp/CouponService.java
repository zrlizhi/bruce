package com.hj.app.mobile.merchant.coupon.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.hj.app.mobile.merchant.coupon.model.CouponModel;
import com.hj.app.mobile.merchant.coupon.model.CouponRecordModel;
import com.hj.app.mobile.merchant.coupon.po.CouponPo;
import com.hj.app.mobile.merchant.coupon.po.CouponRecordPo;
import com.hj.app.mobile.merchant.coupon.service.ICouponService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class CouponService implements ICouponService {

	@Override
	public ResultData add(CouponPo po) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == po.getOpenid() || 0 == po.getOpenid().length() || null == po.getContent()
				|| 0 == po.getContent().length()) { // 校验参数

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}

		try {

			String id = UUID.randomUUID().toString(); // 生成ID

			if (saveHandle(po, id)) { // 判断是否保存成功！

				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				resultData.setResultData(id);

				return resultData;

			}

			resultData.setCode(-1);
			resultData.setMsg("请求失败！");

			return resultData;

		} catch (Exception e) {

			resultData.setCode(-1);
			resultData.setMsg("数据异常！");

			return resultData;

		}

	}

	private boolean saveHandle(CouponPo po, String id) { // 处理保存操作

		return CouponModel.dao.set("id", id).set("content", po.getContent()).set("startDate", po.getStartDate())
				.set("endDate", po.getEndDate()).set("addDate", DateUtil.getTimeStamp())
				.set("coupon_type", po.getCoupon_type()).set("state", 0).set("ccounts", po.getCcounts())
				.set("openid", po.getOpenid()).set("moneys", po.getMoneys()).set("description", po.getDescription()).save();

	}

	@Override
	public ResultData queryInfoById(String id) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length()) {

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}

		resultData.setCode(0);
		resultData.setMsg("请求成功！");
		resultData.setResultData(Db.findFirst("select a.id,a.moneys,a.openid,a.content,DATE_FORMAT(a.startDate,'%Y/%c/%d') as startDate,DATE_FORMAT(a.endDate,'%Y/%c/%d') as endDate,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate,a.coupon_type,a.state,a.ccounts,a.description  from coupon a where a.id = '" + id + "'"));
		return resultData;
	}

	@Override
	public ResultData queryListForShop(String openid) {
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();

		if (null == openid || 0 == openid.length()) {

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}

		resultData.setCode(0);
		resultData.setMsg("请求成功！");
		resultData.setResultData(Db.find("select a.id,a.moneys,a.openid,a.content,DATE_FORMAT(a.startDate,'%Y/%c/%d') as startDate,DATE_FORMAT(a.endDate,'%Y/%c/%d') as endDate,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate,a.coupon_type,a.state,a.ccounts,a.description from coupon a where a.openid = '" + openid + "'"));
		return resultData;
	}

	@Override
	public ResultData del(String id) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length()) {

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}

		CouponModel dao = CouponModel.dao.findFirst("select * from coupon a where a.id = '" + id + "'");
		if (dao.delete()) {
			resultData.setCode(0);
			resultData.setMsg("刪除成功！");

			return resultData;
		}

		resultData.setCode(-1);
		resultData.setMsg("刪除失敗！");

		return resultData;
	}

	@Override
	public ResultData editForCounts(String id) {
		// TODO Auto-generated method stub
		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length()) {

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}
		
		CouponModel dao = CouponModel.dao.findFirst("select * from coupon a where a.id = '" + id + "'");
		
		if(dao.getInt("ccounts") > 0 ){
			
			if( dao.set("ccounts", dao.getInt("ccounts")-1).update() ){
				resultData.setCode(0);
				resultData.setMsg("领取成功！");
				return resultData;
			}
			
			resultData.setCode(0);
			resultData.setMsg("优惠券领取失败！");
			return resultData;
			
		}
		
		resultData.setCode(-1);
		resultData.setMsg("优惠券已被领完！");

		return resultData;
	}

	@Override
	public ResultData editForState(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	synchronized public ResultData getCoupon(String couponid, String openId) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();

		if (null == couponid || 0 == couponid.length() || null == openId || 0 == openId.length() ) {

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}
		
		CouponModel couponInfo = CouponModel.dao.findFirst(" select a.id,a.moneys,a.openid,a.content,DATE_FORMAT(a.endDate,'%Y-%c-%d %h:%i:%s') as endDate,a.coupon_type,a.state,a.ccounts,a.description from coupon a where a.id = '"+couponid+"'");
		
		if( 0 != couponInfo.getInt("state")){  //判断优惠券是否可以领
			
			resultData.setCode(-1);
			resultData.setMsg("优惠券已被领完或已过期！");

			return resultData;
		}
		
		if( DateUtil.getCompreTime(couponInfo.getStr("endDate")) < 0 ){  //判断优惠券是否已过期
			couponInfo.set("state", 2).update();
			resultData.setCode(-1);
			resultData.setMsg("优惠券已过期！");
			return resultData;
		}
		
		if( 1 == couponInfo.getInt("coupon_type") && 0 == couponInfo.getInt("ccounts") ){  //判断优惠券是否已被领完
			
			couponInfo.set("state", 1).update();
			resultData.setCode(-1);
			resultData.setMsg("优惠券已被领完！");

			return resultData;
		}
		
		CouponRecordModel couponRecordInfo = CouponRecordModel.dao.findFirst(" select * from coupon_record a where a.couponid = '"+couponid+"' and a.openid = '"+openId+"'");
		if( null != couponRecordInfo ){
			
			resultData.setCode(-1);
			resultData.setMsg("已经领取！");

			return resultData;
			
		}
		
		if(handleCouponRecord(couponid,openId)){  //判断优惠券是否领取成功
			
			if(1 == couponInfo.getInt("coupon_type")){
				couponInfo.set("ccounts", couponInfo.getInt("ccounts")-1).update();
			}
			
			resultData.setCode(0);
			resultData.setMsg("领取成功！");

			return resultData;

		}
		
		resultData.setCode(-1);
		resultData.setMsg("领取失败！");

		return resultData;
	}
	
	private boolean handleCouponRecord(String couponid, String openId){  //领取优惠券操作
		
		return CouponRecordModel.dao.set("id", UUID.randomUUID().toString()).set("openid", openId)
				.set("couponid", couponid)
				.set("addDate", DateUtil.getTimeStamp())
				.set("state", 0)
				.save();
	}

	@Override
	public ResultData getCouponRecords(String openId) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();

		if ( null == openId || 0 == openId.length() ) {

			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;

		}
		
		resultData.setCode(0);
		resultData.setMsg("请求成功！");
		
		List<CouponRecordPo> couponRecords = new ArrayList<CouponRecordPo>();
		List<Record> list = Db.find("SELECT COUNT(b.openid) AS counts,a.couponid,b.openid FROM coupon_record a,coupon b where a.couponid = b.id and a.state= 0 and a.openid='"+openId+"' GROUP BY b.openid ORDER BY a.addDate DESC");
		for(Record recordInfo : list){
			
			Record shopInfo = Db.findFirst("select * from shop a where a.id='"+recordInfo.getStr("openid")+"'");
			
			CouponRecordPo couponRecordPo = new CouponRecordPo();
			
			couponRecordPo.setOpenId(shopInfo.getStr("id"));
			couponRecordPo.setImgUrl(shopInfo.getStr("shop_img"));
			couponRecordPo.setShopName(shopInfo.getStr("shop_name"));
			couponRecordPo.setContactTel(shopInfo.getStr("contact_tel"));
			couponRecordPo.setCoupons(Db.find("select a.id,a.moneys,a.openid,a.content,DATE_FORMAT(a.startDate,'%Y/%c/%d') as startDate,DATE_FORMAT(a.endDate,'%Y/%c/%d') as endDate,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate,a.coupon_type,b.state,a.ccounts,a.description from coupon a,coupon_record b where a.id = b.couponid  and b.openid='"+recordInfo.getStr("openid")+"' "));
			couponRecords.add(couponRecordPo);
		}
		
		resultData.setResultData(couponRecords);

		return resultData;
	}

	@Override
	public ResultData useCoupon(String content) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if(null == content || 0 == content.length()){
			
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}
		
		content = content.startsWith("coupon_") ? content.replace("coupon_", "") : content;
		
		String strs[] = content.split(",");
		
		if( 2 != strs.length ){
			
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");

			return resultData;
		}
		
		CouponRecordModel info = CouponRecordModel.dao.findFirst("select * from coupon_record a where a.couponid = '"+strs[1]+"' and a.openid = '"+strs[0]+"'");
		
		if( null == info ){
			
			resultData.setCode(-1);
			resultData.setMsg("信息不存在！");

			return resultData;
		}
		
		if( -1 == info.getInt("state")){
			
			resultData.setCode(-1);
			resultData.setMsg("不能重复使用！");

			return resultData;
		}
		
		info.set("state", -1);
		if(info.update()){
			
			resultData.setCode(0);
			resultData.setMsg("使用成功！");

			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("请求失败！");

		return resultData;
	}
	

}
