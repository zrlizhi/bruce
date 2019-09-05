package com.hj.app.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hj.app.mobile.merchant.card.po.CardPo;
import com.jfinal.plugin.activerecord.Record;

import gui.ava.html.image.generator.HtmlImageGenerator;

public class ImageUtil {
	
	public final static String IMG_DEFAULT_SRC = "20180829/2018082916570061789.png";
	public final static String VAR_IMG_DEFAULT_SRC="home/2018110114484037042.png";
	
	/** * 图片文件读取 * * @param srcImgPath * @return */  
    private static BufferedImage InputImage(String srcImgPath,OnCompressImageListener compressImageListener) {  
        BufferedImage srcImage = null;  
        try {  
            FileInputStream in = new FileInputStream(srcImgPath);  
            srcImage = javax.imageio.ImageIO.read(in);  
        } catch (IOException e) {  
        	compressImageListener.onFail(e.getMessage());
            e.printStackTrace();  
        }  
        return srcImage;  
    }  
  
    /** 
     * * 将图片按照指定的图片尺寸压缩 * * @param srcImgPath :源图片路径 * @param outImgPath * 
     * :输出的压缩图片的路径 * @param new_w * :压缩后的图片宽 * @param new_h * :压缩后的图片高 
     */  
    public static void compressImage(String srcImgPath, String outImgPath,  
            int new_w, int new_h,OnCompressImageListener compressImageListener) {  

        disposeImage(InputImage(srcImgPath,compressImageListener), outImgPath, new_w, new_h,compressImageListener);  
    }  
  
    /** 
     * * 指定长或者宽的最大值来压缩图片 * * @param srcImgPath * :源图片路径 * @param outImgPath * 
     * :输出的压缩图片的路径 * @param maxLength * :长或者宽的最大值 
     */  
    public static void compressImage(String srcImgPath, String outImgPath,  
            int maxLength,OnCompressImageListener compressImageListener) {  
        // 得到图片  
        BufferedImage src = InputImage(srcImgPath,compressImageListener);  
        if (null != src) {  
            int old_w = src.getWidth();  
            // 得到源图宽  
            int old_h = src.getHeight();  
            // 得到源图长  
            int new_w = 0;  
            // 新图的宽  
            int new_h = 0;  
            // 新图的长  
            // 根据图片尺寸压缩比得到新图的尺寸  
            if (old_w > old_h) {  
                // 图片要缩放的比例  
                new_w = maxLength;  
                new_h = (int) Math.round(old_h * ((float) maxLength / old_w));  
            } else {  
                new_w = (int) Math.round(old_w * ((float) maxLength / old_h));  
                new_h = maxLength;  
            }  
            disposeImage(src, outImgPath, new_w, new_h,compressImageListener);  
        }  
    }  
  
    /** * 处理图片 * * @param src * @param outImgPath * @param new_w * @param new_h */  
    private synchronized static void disposeImage(BufferedImage src,  
            String outImgPath, int new_w, int new_h,OnCompressImageListener compressImageListener) {  
    	try {
    		// 得到图片  
            int old_w = src.getWidth();  
            // 得到源图宽  
            int old_h = src.getHeight();  
            // 得到源图长  
            BufferedImage newImg = null;  
            // 判断输入图片的类型  
            switch (src.getType()) {  
            case 13:  
                // png,gifnewImg = new BufferedImage(new_w, new_h,  
                // BufferedImage.TYPE_4BYTE_ABGR);  
                break;  
            default:  
                newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);  
                break;  
            }  
            Graphics2D g = newImg.createGraphics();  
            // 从原图上取颜色绘制新图  
            g.drawImage(src, 0, 0, old_w, old_h, null);  
            g.dispose();  
            // 根据图片尺寸压缩比得到新图的尺寸  
            newImg.getGraphics().drawImage(  
                    src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,  
                    null);  
            // 调用方法输出图片文件  
            OutImage(outImgPath, newImg,compressImageListener);  
		} catch (Exception e) {
			// TODO: handle exception
			compressImageListener.onFail(e.getMessage());
		}
        
    }  
  
    /** 
     * * 将图片文件输出到指定的路径，并可设定压缩质量 * * @param outImgPath * @param newImg * @param 
     * per 
     */  
    private static void OutImage(String outImgPath, BufferedImage newImg,OnCompressImageListener compressImageListener) {  
        // 判断输出的文件夹路径是否存在，不存在则创建  
        File file = new File(outImgPath);  
        if (!file.getParentFile().exists()) {  
            file.getParentFile().mkdirs();  
        }// 输出到文件流  
        try {  
            ImageIO.write(newImg, outImgPath.substring(outImgPath  
                    .lastIndexOf(".") + 1), new File(outImgPath));  
            compressImageListener.onSucess();
        } catch (FileNotFoundException e) {  
        	compressImageListener.onFail(e.getMessage());
            e.printStackTrace();  
        } catch (IOException e) {  
        	compressImageListener.onFail(e.getMessage());
            e.printStackTrace();  
        }  
    }  
    
    
    public static String htmlCompressImage(String path,Record po){
    	
    	String htmlstr="";
    	htmlstr+="<div style='padding:10px;'><table><tr>";
    	htmlstr+="<td style='width:250px;font-size: 18px;text-align:left;'><p>"+po.getStr("mer_name")+"</p></td>";
    	htmlstr+="<td style='width:0px;text-align:right;'>";
    	htmlstr+="</td></tr></table>";
    	htmlstr+="<p style='margin-top: -30px;height: 10px;width:100%;'><label>联系人："+po.getStr("contact_name")+"</label>&nbsp;&nbsp;&nbsp;&nbsp;<label>电话："+po.getStr("contact_tel")+"</label></p>";
    	htmlstr+="<p style='height: 10px;'>地址："+po.getStr("mer_addr")+"</p>";
    	htmlstr+="<p style='height: 10px;'>主营："+po.getStr("mer_goods")+"</p>";
    	htmlstr+="<p style='height: 10px;'>标语："+po.getStr("slogan")+"</p>";
    	htmlstr+="<table style='margin-top:16px;'><tr>";
    	htmlstr+="<td style='width: 250px;text-align:left;'><p style='font-size: 13px;height: 10px;'>您好！这是我的名片！</p>";
    	htmlstr+="<p style='font-size: 18px;height: 10px;margin-top:5px;'>请惠存</p>";
    	htmlstr+="<p style='font-size: 13px;height: 10px;margin-top:5px;'>扫码或长按进入</p></td>";
    	htmlstr+="<td style='width: 70px;text-align: right;'>";
    	htmlstr+= "<img  src='file:///D:/100.png'/></td>";
    	htmlstr+="</tr></table></div>";
    	
    	HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
    	
    	String pathStr=DateUtil.dateStr() + RandomUtil.getRandomCodeStr()+".png";
    
    	imageGenerator.loadHtml(htmlstr);
    	imageGenerator.getBufferedImage();
    	imageGenerator.saveAsImage(path+"/"+pathStr);
    	imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
    	
    	return pathStr;
    }
    
    public static String htmlCompressImage2(String path,Record po){
    	
    	String htmlstr="";
    	htmlstr+="<div style='padding:10px;'><table><tr>";
    	htmlstr+="<td style='width:250px;font-size: 18px;text-align:left;'><p>5555</p></td>";
    	htmlstr+="<td style='width:0;text-align:right;'>";
    	htmlstr+="<img class='avatar_pp fl' src='file:///F:/20181103155955.jpg' width='70px' height='70px'/></td></tr></table>";
    	htmlstr+="<p style='margin-top: -30px;height: 10px;width:100%;'><label>联系人：dddddddd</label>&nbsp;&nbsp;&nbsp;&nbsp;<label>电话：15221466530</label></p>";
    	htmlstr+="<p style='height: 10px;'>地址：33444</p>";
    	htmlstr+="<p style='height: 10px;'>主营：2111</p>";
    	htmlstr+="<p style='height: 10px;'>标语：</p>";
    	htmlstr+="<table style='margin-top:16px;'><tr>";
    	htmlstr+="<td style='width: 250px;text-align:left;'><p style='font-size: 13px;height: 10px;'>您好！这是我的名片！</p>";
    	htmlstr+="<p style='font-size: 18px;height: 10px;margin-top:5px;'>请惠存</p>";
    	htmlstr+="<p style='font-size: 13px;height: 10px;margin-top:5px;'>扫码或长按进入</p></td>";
    	htmlstr+="<td style='width:70px;text-align: right;'>";
    	htmlstr+= "<img  src='file:///F:/100.png' /></td>";
    	htmlstr+="</tr></table></div>";
    	
    	HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
    	
    	String pathStr=DateUtil.dateStr() + RandomUtil.getRandomCodeStr()+".png";
    
    	imageGenerator.loadHtml(htmlstr);
    	imageGenerator.getBufferedImage();
    	imageGenerator.saveAsImage(path+"/"+pathStr);
    	imageGenerator.saveAsHtmlWithMap("hello-world.html", "hello-world.png");
    	
    	return pathStr;
    }
    
    public interface OnCompressImageListener{
    	
    	void onSucess(); //压缩成功
    	void onFail(String msg); //压缩失败
    }
    
    public static void main(String[] args) {
		
		ImageUtil.htmlCompressImage2("D:/",null);
		
	}
}
