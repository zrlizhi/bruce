package com.hj.app.index;

import com.jfinal.core.Controller;

public class IndexController extends Controller {

	public void index(){
		
		renderText("8e0896bc67831ec059d54985732b94b5");
	}
	
	
	public void index2(){
			
		
		render("/front/index.html");
	}
	

}
