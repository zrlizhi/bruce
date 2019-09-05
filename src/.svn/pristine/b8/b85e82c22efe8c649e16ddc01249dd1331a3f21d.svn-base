package com.hj.app.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomUtil {

	//生成短信验证码
	public static String getRandomCodeStr(){
		String randStr=String.valueOf(new Random().nextInt(99999));
		if(randStr.length()<5){
			return getRandomCodeStr();
		}else{
			return randStr;
		}
	}
	
	//红包随机金额
	public static String getRandomCodeStr(float average){
		float randValue=new Random().nextFloat();
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");
		if(randValue>0.1 && randValue<average){
			return decimalFormat.format(randValue);
		}else{
			return getRandomCodeStr(average);
		}
	}
	
	//随机坐标
	public static Map<String,String> getCoordinate(String latitude,String longitude){
		
		Map<String,String> map=new HashMap<String,String>();
		
		String strs[]=latitude.split("\\.");
		String strs2[]=longitude.split("\\.");		
		if(strs[1].length()>3){
			map.put("latitude", strs[0]+"."+strs[1].substring(0, 3)+getRandomCodeStr());
		}else{
			map.put("latitude", strs[0]+"."+strs[1]+getRandomCodeStr());
		}
		if(strs2[1].length()>3){
			map.put("longitude", strs2[0]+"."+strs2[1].substring(0, 3)+getRandomCodeStr());
		}else{
			map.put("longitude", strs2[0]+"."+strs2[1]+getRandomCodeStr());
		}

		return map;
	}
	
	public static String billStr(String str){
    	String dateStr=String.valueOf(new Date().getTime());
    	return str+dateStr;
    }
	
	public static String getDoubleFortmat(Double d){
		
		NumberFormat ddf1=NumberFormat.getNumberInstance() ;
		ddf1.setMaximumFractionDigits(2); 
		return ddf1.format(d);
	}
	
	public static Map<String,Object> shangMoneys(int state){
		
		String str=randStr();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("number", str);
		if(state==0){
			int one=str.charAt(str.length()-1);
			int two=str.charAt(str.length()-2);
			int moneys=one+two;
			if(moneys%2==0){
				map.put("state", 2);
				return map;
			}else{
				map.put("state", 1);
				return map;
			}
		}else{
			map.put("state", 0);
			return map;
		}
		
	}
    public static Map<String,Object> getLunckyNum(String str2){
		
    	Map<String,Object> map=new HashMap<String,Object>();
    	String str1=randStr();
    	//String str1="1585457664";
		char arry2[]=str2.toCharArray();
		List<String> list=new ArrayList<>();
		int a=0;
		for(int i=arry2.length-1;i>=0;i--){
			a++;
			if(arry2[i]==str1.charAt(str1.length()-a)){
				
				list.add(String.valueOf(arry2[i]));
			}else{
				a=0;
				break;
			}
		}
		String number="";
		if(list.size()==0){
			number="0";
		}else{
			for(int i=list.size()-1;i>=0;i--){
				
				number+=list.get(i);
				
				
			}
		}
		
		if(Integer.parseInt(number)>100){
			
			String numberstr=number.substring(number.length()-2,number.length());
			map.put("moneys", numberstr);
			
		}else{
			map.put("moneys", number);
		}

		map.put("number", str1);
		return map;
	}
	
	
    public static String randStr(){
    	
    	Random random=new Random();
    	String randStr=String.valueOf((random.nextInt()*random.nextInt()));
    	
    	if(randStr.length()<10 || Integer.parseInt(randStr)<0){
    		return randStr();
    	}else{
    		return randStr;
    	}

    }
    
    //领取金额的随机数
    public static String randFloat(float Max,float Min){
    	String str="";
        for (int i = 0; i < 10; i++) {  
            BigDecimal db = new BigDecimal(Math.random() * (Max - Min) + Min);  
            str=db.setScale(2, BigDecimal.ROUND_HALF_UP)// 保留30位小数并四舍五入  
                    .toString();
           
        } 
        
        return str;
    }
	
}
