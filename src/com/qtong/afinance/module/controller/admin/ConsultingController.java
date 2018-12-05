package com.qtong.afinance.module.controller.admin;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.admin.ConsultingService;

/**
 * 后台管理-项目咨询controller
 */
@Controller("adminConsulting")
@RequestMapping("/admin/consulting")
public class ConsultingController {
	
	@Autowired
	private ConsultingService consultingService;
	
	/**
	 * 根据id查询项目咨询详情
	 * @param request
	 */
	@RequestMapping(value = "/getConsultingById",method= RequestMethod.POST)
	public void selectCoPById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);	
		String Consulting = consultingService.queryById(Integer.parseInt(id));
        try {
			response.getOutputStream().write(Consulting.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 根据id修改处理状态
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updteConsultIngById",method= RequestMethod.POST)
	public void updateRecentNews(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String id = (String) map.get("id");
		String result = (String) map.get("result");
		
		int count = consultingService.updateState(Integer.parseInt(id),result);	 
		
		String resp=null;
		if(count==1) {
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
	 * 根据条件分页查询项目咨询列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getConsultIngListByTerms",method= RequestMethod.POST)
	public void selectByTermList(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");
		String state=(String) map.get("state");
		String product=(String) map.get("product");
		String job=(String) map.get("job");
		String companyName=(String) map.get("companyName");
		String name=(String) map.get("name");
		
		String queryByTerms = consultingService.queryByPage(pageIndex, state, product, job, companyName, name);
        try {
			response.getOutputStream().write(queryByTerms.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 批量删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteAllConsulting",method= RequestMethod.POST)
	public void deleteAll(HttpServletRequest request,HttpServletResponse response){
		String idList=HttpTool.javaProtogenesisGetRequest(request);	
		int deleteAll=consultingService.deleteAll(idList);	
        try {
			response.getOutputStream().write(Integer.toString(deleteAll).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
