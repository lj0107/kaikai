package com.qtong.afinance.module.controller.admin;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;
import com.qtong.afinance.module.service.admin.RecentNewsService;

/**
 * 后台管理-公告管理(最新动态)controller
 */
@Controller
@RequestMapping("/admin/recentNews")
public class RecentNewsController {
	@Autowired
	private  RecentNewsService recentNewsService;
	/**
	 * 增加
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insertRecentNews",method= RequestMethod.POST)
	public void insertCoPartner(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		BusinessRecentnews businessRecentnews=JSON.parseObject(reqMess,BusinessRecentnews.class);
		int insertRecentNews = recentNewsService.insertCoPartner(businessRecentnews);
		String resp=null;
		if(insertRecentNews==1) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 通过id删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteById",method= RequestMethod.POST)
	public void deleteById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
		int deleteById=recentNewsService.deleteById(Integer.parseInt(id));	
		String resp=null;
		if(deleteById==1) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteAll",method= RequestMethod.POST)
	public void deleteAll(HttpServletRequest request,HttpServletResponse response){
		String idList=HttpTool.javaProtogenesisGetRequest(request);		 	 
		int deleteAll=recentNewsService.deleteAll(idList);	
		
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write((deleteAll+"").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 *修改1.通过id进行查询
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectCoPById",method= RequestMethod.POST)
	public void selectCoPById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String coParStr=recentNewsService.selectCoPById(Integer.parseInt(id));	
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(coParStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 修改2.
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateRecentNews",method= RequestMethod.POST)
	public void updateRecentNews(HttpServletRequest request,HttpServletResponse response){
		String businessStr=HttpTool.javaProtogenesisGetRequest(request);		 
		BusinessRecentnews businessRecentnews=JSON.parseObject(businessStr,BusinessRecentnews.class);
		int updateRecentNews=recentNewsService.updateRecentNews(businessRecentnews);	
		String resp=null;
		if(updateRecentNews==1) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 修改3.上线状态
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateState",method= RequestMethod.POST)
	public void updateState(HttpServletRequest request,HttpServletResponse response){
		String updOnliSta=HttpTool.javaProtogenesisGetRequest(request);				
		BusinessRecentnews businessRecentnews=JSON.parseObject(updOnliSta,BusinessRecentnews.class);
		int updateState=recentNewsService.updateState(businessRecentnews);	
		String resp=null;
		if(updateState==1) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 修改4.置顶状态
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateStick",method= RequestMethod.POST)
	public void updateStick(HttpServletRequest request,HttpServletResponse response){
		String stickStatus=HttpTool.javaProtogenesisGetRequest(request);		 
		BusinessRecentnews businessRecentnews=JSON.parseObject(stickStatus,BusinessRecentnews.class);
		int updateStick=recentNewsService.updateStick(businessRecentnews);	
		String resp=null;
		if(updateStick==1) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	/**
	 * 根据条件查询
	 * @param selectByTerm 根据条件查询的字符串包含以下数据
	 * @param byKeyword 关键字模糊查询
	 * @param stickStatus 置顶状态
	 * @param onlineStatus 上线状态
	 * @param pageIndex 当前页号
	 * 根据发布时间查询：
	 * @param firstTime 开始时间
	 * @param secondTime 结束时间
	 * @return
	 */
	@RequestMapping(value = "/selectByTermList",method= RequestMethod.POST)
	public void selectByTermList(HttpServletRequest request,HttpServletResponse response){
		String selectByTerm=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String selectByTermList=recentNewsService.selectByTermList(selectByTerm);	
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(selectByTermList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
