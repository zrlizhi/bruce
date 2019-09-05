package com.hj.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by huajian on 2015/11/6.
 */
public class DateUtil {
    //格式化当前日期
    public static String dateStr(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(new Date());
    }
    public static String dateStrForWxMsg(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        return formatter.format(new Date());
    }
    public static String yearStr(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        return formatter.format(new Date());
    }
    public static String timeStr(){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(new Date());
    }
    public static String parseDate2(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年");
        Date date=null;
        try {
            date=sdf.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(date);
    }
    public static String parseDate(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=sdf.parse(strDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(date);
    }

    public static String convertWeekByDate() {
          SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
          Calendar cal = Calendar.getInstance();
          cal.setTime(new Date());
           //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
          int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
          if(1 == dayWeek) {
              cal.add(Calendar.DAY_OF_MONTH, -1);
          }
           cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
           int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
           cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
           cal.add(Calendar.DATE, 5);
           String imptimeEnd = sdf.format(cal.getTime());
           //System.out.println("所在周星期日的日期："+imptimeEnd);
        return imptimeEnd;
    }
    public static String getWeekDateStr(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date=null;
        try {
            date=sdf.parse(strDate);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }
    public static String getWeekDateStr2(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date=null;
        try {
            date=sdf.parse(strDate);
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 7);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sdf.format(calendar.getTime());
    }

    public static int getIntervalDays(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long intervalMilli=0;
        Date date=null;
        try {
            date=sdf.parse(strDate);
            intervalMilli=date.getTime()-new Date().getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }
    
    public static long getCompreTime(String strDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=null;
        try {
            date=sdf.parse(strDate);
            return date.getTime()-new Date().getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static int getAllTimeDays(String startDate,String endDate){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long intervalMilli=0;
        Date sDate=null;
        Date eDate=null;
        try {
            sDate=sdf.parse(startDate);
            eDate=sdf.parse(endDate);
            intervalMilli=eDate.getTime()-sDate.getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }
    public static String getTimeStamp(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    public static String getTimeStamps(){
    	
        String dateStr=String.valueOf(new Date().getTime());
    	return dateStr.substring(0, 10);
    }
    
    public static long countMin(String dateStr){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		try {
		   java.util.Date now = df.parse(getTimeStamp());
		   java.util.Date date=df.parse(dateStr);   
		   long l=now.getTime()-date.getTime();   
		   long day=l/(24*60*60*1000);   
		   long hour=(l/(60*60*1000)-day*24);   
		   long min=((l/(60*1000))-day*24*60-hour*60);   
		   
		   return min;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return 0;
    }
    
    public static void main(String[] args) {
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
    	System.out.println(formatter.format(new Date()));
        
	}
}
