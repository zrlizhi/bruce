package com.hj.app.common.file;

import java.io.File;

public class FileUtils {

	public static boolean deleteFile(String path){    //删除文件
		try {
			File file = new File(path);
			if(file.exists()){
				file.delete();
				return true;
			}
			return false;
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
}
