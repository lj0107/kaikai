package com.qtong.afinance.module.controller.bigdata;

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
import com.qtong.afinance.module.service.bigdata.AuthorizationCheckService;


@Controller
@RequestMapping("/admin/authorizationCheck")
public class AuthorizationCheckController {

	@Autowired
	private AuthorizationCheckService authorizationCheckService;
	
	/**
	 * 查询授权核验列表
	 * @param request
	 */
	@RequestMapping(value = "/getAllAuthorizationCheck",method= RequestMethod.POST)
	public void getAllAuthorizationCheck(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		JSONObject json = JSONObject.parseObject(reqMess);
		String authorizationChecks = authorizationCheckService.getAuthorizationChecks(json);
        try {
			response.getOutputStream().write(authorizationChecks.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 查询授权核验-授权码列表
	 * @param request
	 */
	@RequestMapping(value = "/getAllMobile",method= RequestMethod.POST)
	public void getAllMobile(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		JSONObject json = JSONObject.parseObject(reqMess);
		String allMobilell = authorizationCheckService.getAllMobilell(json);
        try {
			response.getOutputStream().write(allMobilell.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查询授权核验-授权码详情
	 * @param request
	 */
	@RequestMapping(value = "/getOneDetail",method= RequestMethod.POST)
	public void getOneDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String oneDetail = authorizationCheckService.queryOne(reqMess);
        try {
			response.getOutputStream().write(oneDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *导出授权核验列表
	 * @param request
	 * @param response
	 * @param authorizationCheck
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel",method= RequestMethod.POST)
	@ResponseBody
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String productOrderId=(String) map.get("productOrderId");
		String customerName=(String) map.get("customerName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String minCounts=(String) map.get("minCounts");
		String maxCounts=(String) map.get("maxCounts");
		ExportExcel export = authorizationCheckService.exportExcel(productOrderId, customerName, startTime, endTime, minCounts, maxCounts);
		String result=JSON.toJSONString(export);
		return result;
	}
	/**
	 * 导出授权核验-授权码列表
	 * @param request
	 * @param response
	 * @param afinTsInteractMess
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcelAll",method= RequestMethod.POST)
	@ResponseBody
	public String exportExcelAll(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String productOrderId=(String) map.get("productOrderId");
		String productName=(String) map.get("productName");
		String customerName=(String) map.get("customerName");
		String mobile=(String) map.get("mobile");
		String authCode=(String) map.get("authCode");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		ExportExcel export = authorizationCheckService.exportExcelAll(productOrderId,productName,customerName, mobile, authCode, startTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	/**
	 * 查询授权核验-授权码详情-导出
	 * @param request
	 */
	@RequestMapping(value = "/exportOneDetail",method= RequestMethod.POST)
	@ResponseBody
	public String exportOneDetail(HttpServletRequest request,HttpServletResponse response){
		
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String requestRefId=(String) map.get("requestRefId");
		String productOrderId=(String) map.get("productOrderId");
		String customerName=(String) map.get("customerName");
		String productName=(String) map.get("productName");
		
		List<Map<String, String>> result = authorizationCheckService.exportOne(requestRefId,productOrderId,customerName,productName);
		return JSON.toJSONString(result);
		
	}
	
}
