package com.qtong.afinance.module.controller.heweishi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
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
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.heweishi.HWSAuthorizationCheckService;


@Controller
@RequestMapping("/admin/hwsAuthorizationCheck")
public class HWSAuthorizationCheckController {

	@Autowired
	private HWSAuthorizationCheckService hwsAuthorizationCheckService;
	
	/**
	 * 查询和卫士授权核验列表
	 * @param request
	 */
	@RequestMapping(value = "/getAllAuthorizationCheck",method= RequestMethod.POST)
	public void getAllAuthorizationCheck(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		JSONObject json = JSONObject.parseObject(reqMess);
		String authorizationChecks = hwsAuthorizationCheckService.getAuthorizationChecks(json);
        try {
			response.getOutputStream().write(authorizationChecks.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 查询和卫士授权核验-手机号列表
	 * @param request
	 */
	@RequestMapping(value = "/getAllMobile",method= RequestMethod.POST)
	public void getAllMobile(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		JSONObject json = JSONObject.parseObject(reqMess);
		String allMobilell = hwsAuthorizationCheckService.getAllMobilell(json);
        try {
			response.getOutputStream().write(allMobilell.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询和卫士授权核验-详情
	 * @param request
	 */
	@RequestMapping(value = "/getAuthorizationCheck",method= RequestMethod.POST)
	public void getAuthorizationCheck(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);	
		String detiles = hwsAuthorizationCheckService.getAuthorizationCheck(id);
		try {
			response.getOutputStream().write(detiles.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询和卫士授权核验-详情-导出
	 * @param request
	 */
	@RequestMapping(value = "/exportAuthorizationCheck",method= RequestMethod.POST)
	@ResponseBody
	public String exportAuthorizationCheck(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String lbcmpId=(String) map.get("lbcmpId");
		String customerName=(String) map.get("customerName");
		
		List<Map<String, String>> result = hwsAuthorizationCheckService.exportAuthorizationCheck(lbcmpId,customerName);
		return JSON.toJSONString(result);
	}
	
	
	
	/**
	 * 导出授权核验列表
	 * @param request
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportAllAuthorizationCheck",method= RequestMethod.POST)
	@ResponseBody
	public String ExportAllAuthorizationCheck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String productOrderId=(String) map.get("productOrderId");
		String customerName=(String) map.get("customerName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String minCounts=(String) map.get("minCounts");
		String maxCounts=(String) map.get("maxCounts");
		ExportExcel export = hwsAuthorizationCheckService.exportAllAuthorizationCheck(productOrderId, customerName, startTime, endTime, minCounts, maxCounts);
				
		String result=JSON.toJSONString(export);
		return result;
	}
	/**
	 * 导出授权核验
	 * @param request
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value = "/exportAllMobile",method= RequestMethod.POST)
	public String exportAllMobile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String mobile=(String) map.get("mobile");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String proOrdId=(String) map.get("proOrdId");
		ExportExcel export = hwsAuthorizationCheckService.exportAllMobile(proOrdId, mobile, startTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
	}
}
