package com.hj.app.mobile.merchant.actives.service.imp;

import java.util.List;
import java.util.UUID;

import com.hj.app.mobile.merchant.actives.model.ActivesModel;
import com.hj.app.mobile.merchant.actives.po.ActivesForManagePo;
import com.hj.app.mobile.merchant.actives.po.ActivesForShopPo;
import com.hj.app.mobile.merchant.actives.po.ActivesPo;
import com.hj.app.mobile.merchant.actives.service.IActivesService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.PageUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 
 * @author huajian
 *
 */
public class ActivesService implements IActivesService {

	@Override
	public ResultData addActives(ActivesPo act) {
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		try {

			if (null == act.getOpenid() || 0 == act.getOpenid().length() || null == act.getContent()
					|| 0 == act.getContent().length()) { // 校验参数
				resultData.setCode(-1);
				resultData.setMsg("参数错误");
				return resultData;
			}

			
			if (!saveActivesHandle(act)) { // 保存操作
				resultData.setCode(-1);
				resultData.setMsg("请求失败");
				return resultData;
			}

			resultData.setCode(0);
			resultData.setMsg("请求成功");
			return resultData;

		} catch (Exception e) {
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常!");
			return resultData;
		}

	}

	private boolean saveActivesHandle(ActivesPo act) { // 保存动态

		return ActivesModel.dao.set("id", UUID.randomUUID().toString()).set("openid", act.getOpenid())
				.set("content", act.getContent()).set("img_url", act.getImg_url()).set("views", act.getViews())
				.set("contact_tel", act.getContact_tel()).set("addDate", DateUtil.getTimeStamp()).save();

	}

	private boolean updateActivesHandle(ActivesPo act) { // 编辑动态

		ActivesModel dao = ActivesModel.dao
				.findFirst("select * from actives a where a.openId = '" + act.getOpenid() + "'");

		return dao.set("content", act.getContent()).set("img_url", act.getImg_url())
				.set("contact_tel", act.getContact_tel()).update();
	}

	@Override
	public List<Record> appHomeList() {
		// TODO Auto-generated method stub
		return Db.find(
				"select b.shop_name,a.content from actives a,shop b where a.openid = b.id order by a.addDate desc limit 10 ");
	}

	@Override
	public ResultData list(int pageNum) { // 动态列表
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		try {

			PageUtil page = new PageUtil(pageNum, Constants.PAGE_SIZE);

			String sql = "select a.openid,b.shop_name,b.shop_img,b.shop_addr,a.content,a.img_url,a.views,a.contact_tel,a.id,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate from actives a,shop b where a.openid = b.id order by a.addDate desc limit "
					+ page.pageNum() + "," + page.nextPage();

			List<Record> list = Db.find(sql);

			if (null == list || 0 == list.size()) {
				resultData.setCode(0);
				resultData.setMsg("暂无数据!");
				resultData.setResultData(list);
				return resultData;
			}

			resultData.setCode(0);
			resultData.setMsg("请求成功!");
			resultData.setResultData(list);
			return resultData;

		} catch (Exception e) {
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常!");
			return resultData;
		}

	}

	@Override
	public ResultData listForMy(int pageNum, String openId) { // 我的动态列表
		// TODO Auto-generated method stub

		ResultData resultData = new ResultData();

		try {

			if (null == openId || 0 == openId.length()) {

				resultData.setCode(-1);
				resultData.setMsg("参数错误!");
				return resultData;

			}
			PageUtil page = new PageUtil(pageNum, Constants.PAGE_SIZE);

			String sql = "select a.content,a.img_url,a.views,a.id,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate from actives a where a.openid = '"
					+ openId + "' order by a.addDate desc limit " + page.pageNum() + "," + page.nextPage();

			List<Record> list = Db.find(sql);

			if (null == list || 0 == list.size()) {
				resultData.setCode(-1);
				resultData.setMsg("暂无数据!");
				return resultData;
			}

			resultData.setCode(0);
			resultData.setMsg("请求成功!");
			resultData.setResultData(list);
			return resultData;

		} catch (Exception e) {
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("数据异常!");
			return resultData;
		}

	}

	@Override
	public ActivesForShopPo listForShop(int pageNum, String openId) {
		// TODO Auto-generated method stub
		ActivesForShopPo po = new ActivesForShopPo();

		try {

			if (null == openId || 0 == openId.length()) {

				po.setCode(-1);
				po.setMsg("参数错误!");
				return po;

			}

			Record shopInfo = Db.findFirst(
					"select a.id as openid,a.shop_img,a.shop_name,a.contact_tel,a.shop_addr from shop a where a.id = '"
							+ openId + "'");

			if (null == shopInfo) {

				po.setCode(-1);
				po.setMsg("商户信息不存在!");
				return po;
			}

			po.setShopInfo(shopInfo);

			PageUtil page = new PageUtil(pageNum, Constants.PAGE_SIZE);

			String sql = "select a.openid,a.content,a.img_url,a.views,a.id,DATE_FORMAT(a.addDate,'%Y/%c/%d') as addDate from actives a where a.openid = '"
					+ openId + "' order by a.addDate desc limit " + page.pageNum() + "," + page.nextPage();

			List<Record> list = Db.find(sql);

			if (null == list || 0 == list.size()) {
				po.setCode(-1);
				po.setMsg("暂无数据!");
				return po;
			}

			po.setCode(0);
			po.setMsg("请求成功!");
			po.setActives(list);
			return po;

		} catch (Exception e) {
			e.printStackTrace();
			po.setCode(-1);
			po.setMsg("数据异常!");
			return po;
		}
	}

	@Override
	public ActivesForManagePo listForManage(int pageNum, int limit, String content) {
		// TODO Auto-generated method stub

		ActivesForManagePo po = new ActivesForManagePo();

		try {

			PageUtil pageUtil = new PageUtil(pageNum, limit);

			String sql = " select a.id,a.content,a.views,a.contact_tel,b.shop_name,b.shop_addr"
					+ ",DATE_FORMAT(a.addDate,'%Y-%c-%d') as addDate from actives a,shop b where a.openid = b.id and 1=1 ";

			if (null != content && 0 != content.length()) {

				sql += " and a.content like '%" + content + "%' ";
			}

			sql += " order by a.addDate desc limit " + pageUtil.pageNum() + "," + pageUtil.nextPage();

			Record shopInfo = Db.findFirst(" select count(*) as counts from actives a ");

			po.setCode(0);
			po.setCount(shopInfo.getInt("counts"));
			po.setMsg("请求成功！");
			po.setData(Db.find(sql));
			return po;

		} catch (Exception e) {
			po.setCode(-1);
			po.setMsg("数据异常");
			return po;
		}
	}

	@Override
	public Record activeInfo(String id) {
		// TODO Auto-generated method stub
		return Db.findFirst(
				" select a.id,a.content,a.views,a.img_url,a.contact_tel,DATE_FORMAT(a.addDate,'%Y-%c-%d') as addDate,b.shop_name,b.shop_addr from actives a,shop b where a.openid = b.id and a.id = '"
						+ id + "'");
	}

}
