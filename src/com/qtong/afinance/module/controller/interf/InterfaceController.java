package com.qtong.afinance.module.controller.interf;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.interf.Interface;
import com.qtong.afinance.module.pojo.interf.Product;
import com.qtong.afinance.module.service.interf.InterfaceService;

@Controller
@RequestMapping("/interface")
public class InterfaceController {
	
	@Autowired
	private InterfaceService interfaceService;
	
	/**
	 * 增加产品
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/addProduct",method= RequestMethod.POST)
	public void insertProduct(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Product addProduct=JSON.parseObject(reqMess,Product.class);
		int insertRecentNews = interfaceService.insertProduct(addProduct);
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 产品管理列表
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectProduct",method= RequestMethod.POST)
	public void selectProduct(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Product selectProduct=JSON.parseObject(reqMess,Product.class);
		JSONObject parseObject = JSON.parseObject(reqMess);
		String pageIndex = parseObject.get("pageIndex").toString();
	    String sProduct = interfaceService.selectProduct(pageIndex,selectProduct);
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(sProduct.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteProductById")
	public void deleteProductById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
	    int byid = interfaceService.deleteProductById(id);
		String resp=null;
		if(byid==0) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更改
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateProduct",method= RequestMethod.POST)
	public void updateProduct(HttpServletRequest request,HttpServletResponse response){	
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Product updateProduct=JSON.parseObject(reqMess,Product.class);
		int insertRecentNews = interfaceService.updateProduct(updateProduct);
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectProductByid")
	@ResponseBody
	public String selectProductByid(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
	    return interfaceService.selectProductByid(id);
	}
	
	
	/**
	 * 增加
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/addInterf",method= RequestMethod.POST)
	public void insertCoPartner(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Interface addInterface=JSON.parseObject(reqMess,Interface.class);
		int insertRecentNews = interfaceService.insertCoPartner(addInterface);
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 服务编码相同时，和金融分配账号不能重复
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/distinctAfinCount",method= RequestMethod.POST)
	public void distinctAfinCount(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Interface addInterface=JSON.parseObject(reqMess,Interface.class);
		int insertRecentNews = interfaceService.distinctAfinCount(addInterface);
		String resp=null;
		if(insertRecentNews==0) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 查询当前客户订购的所有产品信息
	 */
	@RequestMapping(value = "/queryProducts")
	@ResponseBody
	public PageData queryProducts(HttpServletRequest request,HttpServletResponse response){
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		Integer pageIndex=(Integer) map.get("pageIndex");
		Integer state=(Integer) map.get("state");
		String productName1=(String) map.get("productName1");
		String productName2=(String) map.get("productName2");
		String productName3=(String) map.get("productName3");
		String productEn=(String) map.get("productEn");
		String customerNumber=(String) map.get("customerNumber");
		String serviceCode=(String) map.get("serviceCode");
		
		return interfaceService.queryProducts(pageIndex, state, productName1, productName2, productName3, productEn, customerNumber,serviceCode);
		
	}
	/**
	 * 保存绑定服务编码
	 */
	@RequestMapping(value = "/saveServiceCode")
	@ResponseBody
	public ResultObject saveServiceCode(HttpServletRequest request,HttpServletResponse response){
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String serviceCode=(String) map.get("serviceCode");
		String[] productCodes=(String[]) map.get("productCodes");
		String[] unProductCodes=(String[]) map.get("unProductCodes");
		String customerNumber=(String) map.get("customerNumber");
		
		
		return interfaceService.saveServiceCode(customerNumber,serviceCode,productCodes,unProductCodes);
		
	}
	
	/**
	 * 接口管理列表和条件查询
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectInterf",method= RequestMethod.POST)
	public void selectInterf(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Interface addInterface=JSON.parseObject(reqMess,Interface.class);
		JSONObject parseObject = JSON.parseObject(reqMess);
		String pageIndex = parseObject.get("pageIndex").toString();
	    String selectInterf = interfaceService.selectInterf(pageIndex,addInterface);
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(selectInterf.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteById")
	public void deleteInterFById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
	    int byid = interfaceService.deleteInterFById(id);
		String resp=null;
		if(byid==0) {
			resp="success";
		}else{
			resp="erro";
		}
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更改
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateInterf",method= RequestMethod.POST)
	public void updateInterf(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Interface updateInterface=JSON.parseObject(reqMess,Interface.class);
		int insertRecentNews = interfaceService.updateInterf(updateInterface);
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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectByid")
	@ResponseBody
	public String selectByid(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
	    return interfaceService.selectByid(id);
	}
	
	
	/**
	 * 通过id查询接口详情
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/queryDetailById")
	@ResponseBody
	public Interface queryDetailById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
	    return interfaceService.queryDetailById(id);
	}
	
	
	
	
}

