package com.hj.app.mobile.merchant.produce.service.imp;

import java.util.List;

import com.hj.app.mobile.merchant.produce.model.ProduceModel;
import com.hj.app.mobile.merchant.produce.po.ProduceInfoAndShopPo;
import com.hj.app.mobile.merchant.produce.po.ProduceManagePo;
import com.hj.app.mobile.merchant.produce.po.ProducePo;
import com.hj.app.mobile.merchant.produce.service.IProduceService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ProduceService implements IProduceService {

	@Override
	public ResultData saveOrUpdateProduce(ProducePo producePo) { // 添加或编辑产品信息
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == producePo.getOpenid() || 0 == producePo.getOpenid().length() || null == producePo.getPro_name()
				|| 0 == producePo.getPro_name().length()) { // 验证参数
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}

		if (null == producePo.getId() || 0 == producePo.getId().length()) { // 判断是否重复提交

			return saveProduceHandle(resultData, producePo); // 添加产品

		}

		Record shopInfo = Db.findFirst("select * from produce a where a.id = '" + producePo.getId() + "' and a.openid='"
				+ producePo.getOpenid() + "'"); // 校验是否存在重复信息
		if (null != shopInfo) {
			return updateProduceHandle(resultData, producePo); // 编辑产品
		}
		resultData.setCode(-1);
		resultData.setMsg("信息错误！");
		return resultData;
	}

	private ResultData saveProduceHandle(ResultData resultData, ProducePo producePo) { // 保存产品信息

		ProduceModel produceModel = ProduceModel.dao;

		String idStr = DateUtil.dateStr(); // 生成产品ID
		produceModel.set("id", idStr);
		produceModel.set("openid", producePo.getOpenid());
		produceModel.set("pro_name", producePo.getPro_name());
		produceModel.set("level", producePo.getLevel());
		produceModel.set("place", producePo.getPlace());
		produceModel.set("spec", producePo.getSpec());
		produceModel.set("classify", producePo.getClassify());
		produceModel.set("pro_img", producePo.getPro_img());
		produceModel.set("addDate", DateUtil.getTimeStamp());
		produceModel.set("views", "0");

		try {
			if (produceModel.save()) {
				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				resultData.setResultData(idStr);
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}

	}

	private ResultData updateProduceHandle(ResultData resultData, ProducePo producePo) { // 编辑产品信息

		ProduceModel produceModel = ProduceModel.dao.findFirst("select * from produce a where a.id = '"
				+ producePo.getId() + "' and a.openid='" + producePo.getOpenid() + "'"); // 查询产品信息
		produceModel.set("pro_name", producePo.getPro_name());
		produceModel.set("level", producePo.getLevel());
		produceModel.set("place", producePo.getPlace());
		produceModel.set("spec", producePo.getSpec());
		produceModel.set("classify", producePo.getClassify());
		produceModel.set("pro_img", producePo.getPro_img());

		try {
			if (produceModel.update()) {
				resultData.setCode(0);
				resultData.setMsg("请求成功！");
				resultData.setResultData(produceModel.getStr("id"));
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("请求失败！");
			return resultData;
		} catch (Exception e) {
			// TODO: handle exception
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}

	}

	@Override
	public ResultData deleteProduce(String id, String openid) { // 删除产品信息
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length() || null == openid || 0 == openid.length()) { // 检验参数
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}

		ProduceModel produceModel = ProduceModel.dao
				.findFirst("select * from produce a where a.id = '" + id + "' and a.openid='" + openid + "'"); // 查询产品信息
		if (null == produceModel) {
			resultData.setCode(-1);
			resultData.setMsg("信息不存在！");
			return resultData;
		}

		try {
			if (produceModel.delete()) {
				resultData.setCode(0);
				resultData.setMsg("删除成功！");
				return resultData;
			}
			resultData.setCode(-1);
			resultData.setMsg("删除失败！");
			return resultData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
	}

	@Override
	public List<Record> appHomeList(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub

		PageUtil pageUtil = new PageUtil(pageNumber, pageSize);

		String sql = "select a.id,a.openid,a.pro_name,b.shop_name,b.contact_tel,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate,SPLIT_STR(a.pro_img,',',1) as pro_img,"
				+ "a.views from produce a,shop b "
				+ "where a.openid = b.id and b.state = 0 order by a.addDate desc limit " + pageUtil.pageNum() + ","
				+ pageUtil.nextPage() + " ";
		return Db.find(sql);
	}

	@Override
	public ResultData produceInfoByID(String id, String openid) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == id || 0 == id.length() || null == openid || 0 == openid.length()) {
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}

		Record produceInfo = Db
				.findFirst("select * from produce a where a.id = '" + id + "' and a.openid='" + openid + "'"); // 产品详情

		if (null == produceInfo) {
			resultData.setCode(-1);
			resultData.setMsg("无产品信息！");
			return resultData;
		}

		resultData.setCode(0);
		resultData.setMsg("请求成功！");
		resultData.setResultData(produceInfo);

		return resultData;
	}

	@Override
	public List<Record> produceListForShop(String openId) {
		// TODO Auto-generated method stub
		return Db.find("select * from produce a where a.openid='" + openId + "' order by a.addDate desc ");
	}

	@Override
	public ResultData searchProduce(String content, int pageNum) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		if (null == content || 0 == content.length()) {
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}

		String sql = "select a.id,a.openid,SPLIT_STR(a.pro_img,',',1) as imgUrl,a.pro_name,a.classify as variety,a.views,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate,b.shop_name,b.contact_tel from produce a,shop b where a.openid = b.id and b.state = 0 and a.pro_name like '%"
				+ content + "%'";

		if (pageNum > 0) { // 是否需要分页显示

			PageUtil pageUtil = new PageUtil(pageNum, Constants.PAGE_SIZE);
			sql += " limit " + pageUtil.pageNum() + "," + pageUtil.nextPage();
		}

		List<Record> list = Db.find(sql);
		if (null == list || 0 == list.size()) {
			resultData.setCode(0);
			resultData.setMsg("无数据！");
			resultData.setResultData(list);
			return resultData;
		}

		for (Record info : list) {

			Record activeInfo = Db.findFirst(
					" select count(*) as counts from actives a where a.openid='" + info.getStr("openid") + "'"); // 统计动态
			info.set("actives", activeInfo.getLong("counts"));

			Record couponInfo = Db.findFirst(
					" select count(*) as counts from coupon a where a.openid='" + info.getStr("openid") + "'"); // 统计优惠券
			info.set("coupons", couponInfo.getLong("counts"));
		}

		resultData.setCode(0);
		resultData.setMsg("请求成功！");
		resultData.setResultData(list);

		return resultData;
	}

	@Override
	public ProduceInfoAndShopPo queryProduceInfoForShop(String id, String openId) { // 带商家的店铺详情
		// TODO Auto-generated method stub

		ProduceInfoAndShopPo po = new ProduceInfoAndShopPo();

		if (null == id || 0 == id.length() || null == openId || 0 == openId.length()) { // 校验参数
			po.setCode(-1);
			po.setMsg("参数错误！");
			return po;
		}

		Record shopInfo = Db.findFirst(
				"select a.id,a.shop_img,a.shop_name,a.shop_addr,a.contact_tel from shop a where a.id='" + openId + "'");
		if (null == shopInfo) {
			po.setCode(-1);
			po.setMsg("无法找到商户信息！");
			return po;
		}

		ProduceModel produceInfo = ProduceModel.dao
				.findFirst("select * from produce a where a.id = '" + id + "' and a.openid='" + openId + "'"); // 产品详情

		if (null == produceInfo) {
			po.setCode(-1);
			po.setMsg("无产品信息！");
			return po;
		}

		produceInfo.set("views", produceInfo.getInt("views") + 1).update();
		po.setCode(0);
		po.setMsg("请求成功！");
		po.setShopInfo(shopInfo);
		po.setProduceInfo(produceInfo.toRecord());

		return po;
	}

	@Override
	public ProduceManagePo queryManageList(int pageNum, int limit, String content) {
		// TODO Auto-generated method stub

		PageUtil pageUtil = new PageUtil(pageNum, limit);

		ProduceManagePo po = new ProduceManagePo();

		po.setCode(0);
		po.setMsg("请求成功！");

		po.setCount(Db.findFirst("select count(*) as counts from produce a ").getInt("counts"));

		String sql = "select a.*,b.shop_name,b.contact_name,b.contact_tel from produce a,shop b where a.openid = b.id and 1 = 1 ";

		if (null != content && 0 != content.length()) {

			sql += " and a.pro_name like '%" + content + "%' ";
		}

		sql += " order by a.addDate desc limit " + pageUtil.pageNum() + "," + pageUtil.nextPage();

		po.setData(Db.find(sql));

		return po;
	}

	@Override
	public ResultData search(String content) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();

		if (null == content || 0 == content.length()) {
			resultData.setCode(-1);
			resultData.setMsg("参数错误！");
			return resultData;
		}

		try {

			resultData.setCode(0);
			resultData.setMsg("请求成功！");
			resultData.setResultData(
					Db.find("select a.pro_name as content from produce a where a.pro_name like '%" + content + "%'"));
			return resultData;

		} catch (Exception e) {
			resultData.setCode(-1);
			resultData.setMsg("数据异常！");
			return resultData;
		}
	}

}
