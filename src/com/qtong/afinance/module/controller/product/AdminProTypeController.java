package com.qtong.afinance.module.controller.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.product.AdminProTypeService;

/**
 * 产品类别管理
 *
 */
@Controller
@RequestMapping("/product/adminProType")
public class AdminProTypeController {

	@Autowired
	private AdminProTypeService adminProTypeService;
	/**
	 * 产品品类别管理查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getAdminProTypeList", method = RequestMethod.POST)
	public void advertUpdate(HttpServletRequest request, HttpServletResponse response) {
		String pageIndex = HttpTool.javaProtogenesisGetRequest(request);
		String advert = adminProTypeService.getAdminProTypeList(pageIndex);
		try {
			response.getOutputStream().write(advert.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询总数
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getAdminProTypeCount", method = RequestMethod.POST)
	public void getAdminProTypeCount(HttpServletRequest request, HttpServletResponse response) {
		int i = adminProTypeService.getAdminProTypeCount();
		try {
			response.getOutputStream().write(String.valueOf(i).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 产品品类别管理添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addAdminProType", method = RequestMethod.POST)
	public void addAdminProType(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		int i  = adminProTypeService.addAdminProType(jsonstr);
		String reqs = "";
		if(i>0){
			reqs = "yes";
		}else{
			reqs = "no";
		}
		try {
			response.getOutputStream().write(reqs.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 产品类别管理查询name</br>
	 * 判断数据库里是否有重复的name
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getNameByAdminProType", method = RequestMethod.POST)
	public void getNameByAdminProType(HttpServletRequest request, HttpServletResponse response) {
		String name = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProTypeService.getNameByAdminProType(name);
		String reps="";
		if(i>0){
			reps = "yes";
		}else{
			reps = "no";
		}
		try {
			response.getOutputStream().write(reps.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 产品品类别管理判断是否能删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteAdminProType", method = RequestMethod.POST)
	public void deleteAdminProType(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProTypeService.deleteAdminProType(str);
		String resp = "";
		if (i == 1) {
			resp = "yes";
		} else {
			resp = "no";
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
	 * 产品品类别管理删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "deleteAdminProType2", method = RequestMethod.POST)
	public void deleteAdminProType2(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProTypeService.deleteAdminProType2(str);
		String resp = "";
		if (i == 1) {
			resp = "yes";
		} else {
			resp = "no";
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
