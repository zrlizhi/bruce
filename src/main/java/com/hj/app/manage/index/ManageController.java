package com.hj.app.manage.index;

import java.util.HashMap;
import java.util.Map;


import com.hj.app.manage.varietys.service.IVarietysDetailService;
import com.hj.app.manage.varietys.service.imp.VarietysDetailService;
import com.hj.app.mobile.merchant.actives.service.IActivesService;
import com.hj.app.mobile.merchant.actives.service.imp.ActivesService;
import com.hj.app.utils.ImageUtil;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class ManageController extends Controller {

	private IActivesService activesService = new ActivesService(); //动态
	private IVarietysDetailService varietysDetailService = new VarietysDetailService();
	
	public void index() { // 后台首页

		render("/manage/index.html");
	}
	
	public void login() { // 后台登录

		render("/manage/login.html");
	}
	
	public void manage() { // 后台右侧首页内容

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("users", Db.findFirst("select count(*) as counts from app_user a").getInt("counts"));
		map.put("shops",Db.findFirst("select count(*) as counts from shop a").getInt("counts"));
		map.put("actives", Db.findFirst("select count(*) as counts from actives a").getInt("counts"));
		map.put("news", Db.findFirst("select count(*) as counts from news a").getInt("counts"));
		map.put("hots", Db.find("select a.shop_name,a.contact_name,a.contact_tel,a.views,a.collects from shop a where a.state = 0 order by a.views desc limit 10 "));
		map.put("pros", Db.find("select a.pro_name,a.level,a.place,a.views,a.spec,a.classify from produce a order by a.views desc limit 10 "));
		setAttr("info", map);
		render("/manage/manage.html");
	}
	
	public void varietys() { // 后台经营品种

		render("/manage/varietysNews.html");
	}

	public void scrollImgs() { // APP首页滚动图片列表页

		render("/manage/home_scroll_imgs_list.html");
	}

	public void scrollImgsAdd() { // 发布滚动图片页面

		render("/manage/home_scroll_imgs_add.html");
	}

	public void homeVarList() { // 首页品种列表页面

		render("/manage/home_var_list.html");
	}

	public void homeVarAdd() { // 发布首页品种页面

		render("/manage/home_var_add.html");
	}
	
	public void addNews() { // 添加新闻页面

		render("/manage/addNews.html");
	}
	public void newsList() { // 新闻列表页面

		render("/manage/newsList.html");
	}
	
	public void shops() { // 商户列表页面

		render("/manage/shops.html");
	}
	
	public void produces() { // 产品列表页面

		render("/manage/produces.html");
	}
	
	public void activesList() { // 动态列表页面

		render("/manage/activesList.html");
	}
	
	public void activesInfo() { // 动态详情页面

		setAttr("activeInfo", activesService.activeInfo(getPara("id")));
		render("/manage/activesInfo.html");
	}
	
	public void usersList() { // 用户列表页面

		render("/manage/usersList.html");
	}
	
	public void addNotice() { // 添加通知

		setAttr("openId",getPara("openId"));
		setAttr("shopName",getPara("shopName"));
		render("/manage/addNotice.html");
	}
	
	public void pending(){
		
		render("/manage/pendings.html");
	}
	
	public void orders() { // 后台首页

		render("/manage/orders.html");
	}
	
	public void customer() { // 后台首页

		render("/manage/customer.html");
	}
	
	public void varietysChildEdit() {
		setAttr("varId", getPara("varId"));
		render("/manage/varietys/varietys_edit.html");
	}
	
	public void varietysChildDetailEdit() {
		Record varDetail = varietysDetailService.queryVarDetailById(getPara("varId"));
		String imgUrl = null;
		if(varDetail==null) {
			imgUrl = ImageUtil.VAR_IMG_DEFAULT_SRC;
		}else {
			imgUrl = varDetail.get("imgUrl");
		}
		setAttr("varDetail", varDetail==null?new Record():varDetail);
		setAttr("varId",getPara("varId"));
		setAttr("parentId", getPara("parentId"));
		setAttr("imgUrl", imgUrl);
		render("/manage/varietys/varietys_edit_detail.html");
	}
}
