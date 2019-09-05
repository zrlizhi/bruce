package com.hj.app.manage.news.service.imp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hj.app.manage.news.model.NewsModel;
import com.hj.app.manage.news.po.NewsForManagePo;
import com.hj.app.manage.news.po.NewsPo;
import com.hj.app.manage.news.service.INewsService;
import com.hj.app.model.ResultData;
import com.hj.app.utils.Constants;
import com.hj.app.utils.DateUtil;
import com.hj.app.utils.PageUtil;
import com.hj.app.utils.RandomUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class NewsService implements INewsService {

	@Override
	public ResultData add(NewsPo po,String path,String imgPath) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == po.getTitle() || 0 == po.getTitle().length() || null == po.getContent() || 0 == po.getContent().length() ){  //校验参数
			
			resultData.setCode(-1);
			resultData.setMsg("参数错误!");
			
			return resultData;
		}
		
		String filePath = createHtmlFile(path,po);
		
		if( null == filePath ){
			
			resultData.setCode(-1);
			resultData.setMsg("请求失败!");
			
			return resultData;
		}
		
		po.setThum_url(getThumImg(po.getContent(),imgPath));
		po.setHtml_url(filePath);
		
		if(saveHandle(po)){ 
			
			resultData.setCode(0);
			resultData.setMsg("请求成功!");
			
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("请求失败!");
		
		return resultData;
	}
	
	private String createHtmlFile(String path,NewsPo po){
		
		try{
			
			String fileName = DateUtil.dateStr() + RandomUtil.getRandomCodeStr()+".html";
			path += "/"+fileName;
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			
			FileOutputStream out=new FileOutputStream(path);
			
            PrintStream p=new PrintStream(out);
            
            StringBuffer sb = new StringBuffer();
            sb.append("<!DOCTYPE html><html><head><meta charset='utf-8' />");
            sb.append("<meta name='viewport' content='width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0, user-scalable=no'>");
            sb.append("<title></title>");
            sb.append("<style>p{width:100%;}img{width:100%;}</style>");
            sb.append("</head><body>");
            sb.append(po.getContent());
            sb.append("</body></html>");
            
            p.write(sb.toString().getBytes("utf-8"));
            
            out.close();    
            
            return fileName;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	private String getThumImg(String content,String imgPath){
		
		String imgUlr = "";
		
		Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
		Matcher m_img = p_img.matcher(content);
		boolean result_img = m_img.find();
		if (result_img) {
			while (result_img) {
				String str_img = m_img.group(2);
				//开始匹配<img />标签中的src
				Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
				Matcher m_src = p_src.matcher(str_img);
				if (m_src.find()) {
					imgUlr = m_src.group(3);
				    return imgUlr;
				}
			
			}
		}
		
		return null;
	
	}
	
	private boolean saveHandle(NewsPo po){  //保存操作
		
		return NewsModel.dao.set("id", UUID.randomUUID().toString())
				.set("title", po.getTitle())
				.set("views", 0)
				.set("html_url", po.getHtml_url())
				.set("author", po.getAuthor())
				.set("addDate", DateUtil.getTimeStamp())
				.set("thum_url", po.getThum_url())
				.save();
	}

	@Override
	public ResultData list(int pageNum) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();

		try{
			
			String sql = "";
			
			PageUtil page = new PageUtil( 0 == pageNum ? 1 : pageNum,Constants.PAGE_SIZE);
			
			sql = "select a.id,a.title,a.views,a.html_url,a.thum_url,DATE_FORMAT(a.addDate,'%Y/%c/%d') as showDate from news a order by a.addDate desc limit "+page.pageNum()+","+page.nextPage();
			
			List<Record> list = Db.find(sql);
			
			if( null == list || 0 == list.size()){
				
				resultData.setCode(0);
				resultData.setMsg("暂无数据!");
				resultData.setResultData(list);
				return resultData;
			}
			
			resultData.setCode(0);
			resultData.setMsg("请求成功!");
			resultData.setResultData(list);
			return resultData;
			
		}catch(Exception e ){
			
			resultData.setCode(-1);
			resultData.setMsg("数据异常!");
			
			return resultData;
		}
		
		
		
	}

	@Override
	public NewsForManagePo listForManage(int pageNum, int limit, String title) {
		// TODO Auto-generated method stub
		
		NewsForManagePo po = new NewsForManagePo();
		
        try{
			
			PageUtil pageUtil=new PageUtil(pageNum,limit); 
			
			String sql = " select a.id,a.title,a.views,a.author,a.html_url"
					+ ",DATE_FORMAT(a.addDate,'%Y-%c-%d') as addDate from news a ";
			
			if( null != title && 0 != title.length() ){
				
				sql += " where a.title like '%"+title+"%' ";
			}
			
			sql +=" order by a.addDate desc limit "+pageUtil.pageNum()+","+pageUtil.nextPage();
			
			Record shopInfo = Db.findFirst(" select count(*) as counts from news a ");
			
			po.setCode(0);
			po.setCount(shopInfo.getInt("counts"));
			po.setMsg("请求成功！");
			po.setData(Db.find(sql));
			return po;
			
		}catch(Exception e){
			po.setCode(-1);
			po.setMsg("数据异常");
			return po;
		}
	}

	@Override
	public ResultData del(String id) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( null == id || 0 == id.length() ){
			
			resultData.setCode(-1);
			resultData.setMsg("参数错误!");
			
			return resultData;
		}
		
		NewsModel newsInfo = NewsModel.dao.findFirst(" select * from news a where a.id = '"+id+"' ");
		if(null == newsInfo){
			resultData.setCode(-1);
			resultData.setMsg("信息不存在!");
			
			return resultData;
		}
		
		if(newsInfo.delete()){
			
			resultData.setCode(0);
			resultData.setMsg("删除成功!");
			
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("删除失败!");
		
		return resultData;
	}

	@Override
	public ResultData configCounts(String id) {
		// TODO Auto-generated method stub
		
		ResultData resultData = new ResultData();
		
		if( id == null || 0 == id.length()){
			
			resultData.setCode(-1);
			resultData.setMsg("参数错误!");
			
			return resultData;
		}
		
		NewsModel newsInfo = NewsModel.dao.findFirst(" select * from news a where a.id = '"+id+"' ");
		
		if( null == newsInfo ){
			resultData.setCode(-1);
			resultData.setMsg("信息不存在!");
			
			return resultData;
		}
		
		newsInfo.set("views", newsInfo.getInt("views")+1);
		if(newsInfo.update()){
			
			resultData.setCode(0);
			resultData.setMsg("成功!");
			
			return resultData;
		}
		
		resultData.setCode(-1);
		resultData.setMsg("失败!");
		
		return resultData;
	}

}
