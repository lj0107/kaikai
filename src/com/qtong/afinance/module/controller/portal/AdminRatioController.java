/**
 * 
 */
package com.qtong.afinance.module.controller.portal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.portal.AdminRatioService;

/**
 * 审批
 *
 */
@Controller
@RequestMapping("/portal/ratio")
public class AdminRatioController {
	
	@Autowired
	private AdminRatioService adminRatioService;
	
	/**
	 * 查询未审批
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRatioList",method= RequestMethod.POST)	
	public void getRatioList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminRatioService.getRatioList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
	/**
	 * 查询未审批
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRatioList1",method= RequestMethod.POST)	
	public void getRatioList1(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminRatioService.getRatioList1(reqMess);
		try {
			response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询未审批详细
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRatioInfo",method= RequestMethod.POST)	
	public void getRatioInfo(HttpServletRequest request,HttpServletResponse response) {
		String id=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminRatioService.getRatioInfo(id);
		try {
			response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 未审通过
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateRatioArgee",method= RequestMethod.POST)	
	public void updateRatioArgee(HttpServletRequest request,HttpServletResponse response) {
		String jsonstr=HttpTool.javaProtogenesisGetRequest(request);
		int i = adminRatioService.updateRatioArgee(jsonstr);
		String resp = "";
		if(i>0){
			resp="yes";
		}else{
			resp="no";
		}
		try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 未审驳回
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateRatioReject",method= RequestMethod.POST)	
	public void updateRatioReject(HttpServletRequest request,HttpServletResponse response) {
		String jsonstr=HttpTool.javaProtogenesisGetRequest(request);
		int i = adminRatioService.updateRatioReject(jsonstr);
		String resp = "";
		if(i>0){
			resp="yes";
		}else{
			resp="no";
		}
		try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
