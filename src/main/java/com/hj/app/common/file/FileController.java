package com.hj.app.common.file;

import java.io.File;

import com.hj.app.manage.po.ResultPo;
import com.hj.app.model.ResultData;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.ImageUtil;
import com.hj.app.utils.ImageUtil.OnCompressImageListener;
import com.hj.app.utils.RandomUtil;
import com.jfinal.core.Controller;
import com.jfinal.upload.UploadFile;

public class FileController extends Controller{

	/**
	 * 上传图片
	 */
	@SuppressWarnings("deprecation")
	synchronized public void uploadImg(){
		
		String imgName = getHeader("imgName");
		String compress = getHeader("compress");    //是否压缩 0 不压缩 1 压缩
		String height = getHeader("height");   //图片压缩高度
		String width = getHeader("width");    //图片压缩宽度
		
		final ResultData resultData = new ResultData();
		
		try{
			
			String uploadPath = getRequest().getRealPath("/upload");   //图片路径
			
			checkImgExist(uploadPath,imgName);  //验证是否存在旧图片，存在删除
			
			UploadFile files = getFile();
			
			String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr();   //图片名称
			
			File fileInfo = new File(uploadPath + "/" +DateUtil.yearStr());   //按月创建图片文件夹
			
			if( !fileInfo.mkdir() ){    //判断文件夹是否存在，不存在创建
				
				fileInfo.mkdir();
			}
			
			final String filePath = uploadPath+"/"+DateUtil.yearStr()+"/"+path+".png";
			files.getFile().renameTo(new File(filePath));   //重命名图片
			
			if( compress.equals("1") ){   //判断是否需要压缩图片
				
				final String thumPath = path+"_thum"+".png";

				ImageUtil.compressImage(filePath,  //生成缩略图，保存
						uploadPath+"/"+DateUtil.yearStr()+"/"+thumPath, 
						Integer.parseInt(width), 
						Integer.parseInt(height),
						new OnCompressImageListener(){
							@Override
							public void onSucess() {
								// TODO Auto-generated method stub
								resultData.setCode(0);
								resultData.setMsg("上传成功");
								resultData.setResultData(DateUtil.yearStr()+"/"+thumPath);
							}

							@Override
							public void onFail(String msg) {
								// TODO Auto-generated method stub
								FileUtils.deleteFile(filePath);  //压缩失败，删除之前上传的文件
								resultData.setCode(-1);
								resultData.setMsg("上传失败");
							}
					
				});   

			}else{
				
				resultData.setCode(0);
				resultData.setMsg("上传成功");
				resultData.setResultData(DateUtil.yearStr()+"/"+path+".png");
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			resultData.setCode(-1);
			resultData.setMsg("上传失败");
		}
		
		renderJson(resultData);
		
	}
	
	private void checkImgExist(String uploadPath,String imgName){
		
		if( null == imgName || 0 == imgName.length()){
			return;
		}
		
		File file = new File(uploadPath+imgName);
		if(file.exists()){
			file.delete();
		}
		return;
	}
	
	public void updaloadImg() {
		ResultPo po = new ResultPo();
		 try {
			String uploadPath = getRequest().getRealPath("/upload"); //图片路径
			UploadFile files = getFile();
			String path = DateUtil.dateStr() + RandomUtil.getRandomCodeStr(); //图片名称
			File fileInfo = new File(uploadPath + "/" + DateUtil.yearStr()); //按月创建图片文件夹
			if (!fileInfo.mkdir()) { //判断文件夹是否存在，不存在创建

				fileInfo.mkdir();
			}
			final String filePath = uploadPath + "/" + DateUtil.yearStr() + "/" + path + ".png";
			files.getFile().renameTo(new File(filePath)); //重命名图片
			po.setCode(0);
			po.setMsg("上传成功");
			po.setData(DateUtil.yearStr()+"/"+path+".png");
		} catch (Exception e) {
			// TODO: handle exception
			po.setCode(-1);
			po.setMsg("上传失败");
		}
		renderJson(po);
	}
}
