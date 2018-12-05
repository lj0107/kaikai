package com.qtong.afinance.module.controller.partners;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.partners.AfinPartner;
import com.qtong.afinance.module.service.partners.AfinPartnerService;

@Controller
@RequestMapping("/partners")
public class AfinPartnerController {
	
	@Autowired
	private AfinPartnerService partnersService;
	
	/**
	 * 查询后台管理-合作伙伴信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectPartners",method= RequestMethod.POST)
	public void selectPartners(HttpServletRequest request,HttpServletResponse response){
		String pageIndex=HttpTool.javaProtogenesisGetRequest(request);	
		String queryAllByPage = partnersService.queryAllByPage(Integer.parseInt(pageIndex));
        try {
			response.getOutputStream().write(queryAllByPage.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	/**
	 * 通过id查看是否可以删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deletePartnersById",method= RequestMethod.POST)
	public void deletePartnersById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
		int deleteById=partnersService.deletePartnersById(Integer.parseInt(id));	
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
	 * 通过id删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deletePartnersById2",method= RequestMethod.POST)
	public void deletePartnersById2(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
		int deleteById=partnersService.deletePartnersById2(Integer.parseInt(id));	
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
	 * 增加
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/insertPartners",method= RequestMethod.POST)
	public void insertPartners(HttpServletRequest request,HttpServletResponse response){
		String json=HttpTool.javaProtogenesisGetRequest(request);
		AfinPartner businessPartners = JSON.parseObject(json,AfinPartner.class);
		int insertPartners = partnersService.insertPartners(businessPartners);
		String resp=null;
		if(insertPartners==1) {
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
	 *修改.通过id进行查询
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectPartnersById",method= RequestMethod.POST)
	public void selectPartnersById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String partnersStr=partnersService.selectPartnersById(Integer.parseInt(id));	
		System.out.println(partnersStr);
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(partnersStr.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 修改2.合作伙伴
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updatPartners",method= RequestMethod.POST)
	public void updatePartners(HttpServletRequest request,HttpServletResponse response){
		String businessStr=HttpTool.javaProtogenesisGetRequest(request);		 
		AfinPartner businessPartners=JSON.parseObject(businessStr,AfinPartner.class);
		int updatePartners=partnersService.updatePartners(businessPartners);	
		String resp=null;
		if(updatePartners==1) {
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
	 * 后台管理-合作伙伴-验证名称是否重复
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectPartnersName",method = RequestMethod.POST)
	@ResponseBody
	public void selectPartnersName(HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, Exception{
		String name = HttpTool.javaProtogenesisGetRequest(request);
		int partnerName = partnersService.selectPartnersName(name);
		String resp = "";
		if(partnerName==0){
			resp=("success");
		}else{
			resp=("erro");
		}
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
	 *6. 网址卫士-所属客户名称
	 * @return customerNumber 客户编码
	 * @return customerName 客户名称
	 */
	@RequestMapping(value = "/getSiteGuardInterface",method= RequestMethod.POST)
	@ResponseBody
	public void getSiteGuardInterface(HttpServletRequest request,HttpServletResponse response){
		String selectByTermList=partnersService.getSiteGuardInterface();	
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
	
	/**
	 * 所有合作伙伴 下拉框接口
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllPartnerInterface",method= RequestMethod.POST)
	@ResponseBody
	public void getAllPartnerInterface(HttpServletRequest request,HttpServletResponse response){
		String allPartner = partnersService.getAllPartnerInterface();	
		//将response返回
        try {
			response.getOutputStream().write(allPartner.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
