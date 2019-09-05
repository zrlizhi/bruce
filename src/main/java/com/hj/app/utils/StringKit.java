package com.hj.app.utils;

import org.apache.commons.lang3.StringUtils;

public class StringKit {

	public String[] convertStrToArray(String str,String sp){  
        String[] strArray = null;  
        if( str.contains("_thum") ){
        	str = str.replace("_thum", "");
        }
        strArray = str.split(sp);  
        return strArray;  
    }
	
	public static boolean isblankOrUndefine(String str) {
		if(StringUtils.isBlank(str)||"undefine".equals(str)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotblankOrUndefine(String str) {
		return !isblankOrUndefine(str);
	}
}
