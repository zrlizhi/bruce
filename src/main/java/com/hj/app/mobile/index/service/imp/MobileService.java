package com.hj.app.mobile.index.service.imp;

import com.hj.app.manage.homevar.service.IHomeVarietyService;
import com.hj.app.manage.homevar.service.imp.HomeVarietyService;
import com.hj.app.manage.scrollimg.service.IScrollImgService;
import com.hj.app.manage.scrollimg.service.imp.ScrollImgService;
import com.hj.app.mobile.index.po.MobileResponsePo;
import com.hj.app.mobile.index.service.IMobileService;
import com.hj.app.mobile.merchant.actives.service.IActivesService;
import com.hj.app.mobile.merchant.actives.service.imp.ActivesService;
import com.hj.app.mobile.merchant.buyerpurinfo.service.IBuyerPurInfoService;
import com.hj.app.mobile.merchant.buyerpurinfo.service.imp.BuyerPurInfoService;
import com.hj.app.mobile.merchant.produce.service.IProduceService;
import com.hj.app.mobile.merchant.produce.service.imp.ProduceService;
import com.hj.app.mobile.merchant.shop.service.IShopService;
import com.hj.app.mobile.merchant.shop.service.imp.ShopService;
import com.hj.app.utils.Constants;

public class MobileService implements IMobileService {

	private IScrollImgService scrollImgService = new ScrollImgService();  //滚动图片
	private IActivesService activesService = new ActivesService();  //动态
	private IHomeVarietyService homeVarietyService = new HomeVarietyService(); //品种
	private IShopService shopService = new ShopService(); // 商户
	private IProduceService produceService = new ProduceService(); //产品
	private IBuyerPurInfoService BuyerPurInfoService = new BuyerPurInfoService();
	
	@Override
	public MobileResponsePo list() {
		// TODO Auto-generated method stub
		
		MobileResponsePo responsPo = new MobileResponsePo();
		
		try {
			
			responsPo.setScroImgs(scrollImgService.appHomeList()); //滚动图片
			responsPo.setActives(activesService.appHomeList());  //动态
			responsPo.setVarietys(homeVarietyService.appHomeList());//品种
			responsPo.setHots(shopService.appHomeList());  // 商户
			responsPo.setProduces(produceService.appHomeList(1,Constants.PAGE_SIZE));  //最新产品
			responsPo.setOrders(BuyerPurInfoService.queryAllOrdersList(1, Constants.PAGE_SIZE, "0",""));
			
			responsPo.setCode(0);
			responsPo.setMsg("请求成功！");
			
			return responsPo;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responsPo.setCode(-1);
			responsPo.setMsg("数据异常！");
			return responsPo;
		}
		
	}
	
	

}
