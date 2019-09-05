package com.hj.app.utils;

import java.io.File;

public class FileUtil {

	public static void delFile(String path){  //删除图片
		
		File file = new File(path);
		try{
			if(file.exists()){
				file.delete();
			}
		}catch(Exception e ){
			e.printStackTrace();
		}
	}
}
