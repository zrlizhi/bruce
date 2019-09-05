package com.hj.app.manage.pending.controller;

import com.hj.app.manage.pending.service.IPendingService;
import com.hj.app.manage.pending.service.imp.PendingService;
import com.jfinal.core.Controller;

public class PendingController extends Controller {

	private IPendingService pendingService = new PendingService();
	
	public void update(){
		
		renderJson(pendingService.update(getPara("id"), getParaToInt("state"),getPara("content")));
	}
	
	public void list(){
		
		renderJson(pendingService.list());
	}
}
