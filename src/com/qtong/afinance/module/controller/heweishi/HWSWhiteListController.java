package com.qtong.afinance.module.controller.heweishi;

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
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.heweishi.WhiteList;
import com.qtong.afinance.module.pojo.heweishi.WhiteListMobile;
import com.qtong.afinance.module.service.heweishi.HWSWhiteListService;


@Controller
@RequestMapping("/admin/hwsWhiteLists")
public class HWSWhiteListController {

	@Autowired
	private HWSWhiteListService hwsWhiteListService;
	
	/**
	 *和卫士-白名单列表
	 * @param request
	 */
	@RequestMapping(value = "/getAllWhiteList",method= RequestMethod.POST)
	public void getAllWhiteList(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");
		String customerNumber=(String) map.get("customerNumber");
		String customerName=(String) map.get("customerName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String whiteList = hwsWhiteListService.queryAll(pageIndex, customerNumber, customerName, startTime, endTime);
        try {
			response.getOutputStream().write(whiteList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 和卫士-手机号列表
	 * @param request
	 */
	@RequestMapping(value = "/getAllWhiteListMobile",method= RequestMethod.POST)
	public void getAllMobile(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");
		String account=(String) map.get("account");
		String mobile=(String) map.get("mobile");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String whiteList = hwsWhiteListService.queryAllMobile(pageIndex, account, mobile, startTime, endTime);
        try {
			response.getOutputStream().write(whiteList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出-和卫士白名单
	 * @param request
	 * @param response
	 * @param whiteList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportExcel",method= RequestMethod.POST)
	@ResponseBody
	public String exportExcel(HttpServletRequest request,HttpServletResponse response,WhiteList whiteList) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String customerNumber=(String) map.get("customerNumber");
		String customerName=(String) map.get("customerName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		ExportExcel export = hwsWhiteListService.exportQueryAll(customerNumber, customerName, startTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	
	/**
	 * 导出和卫士-手机号列表
	 * @param request
	 * @throws Exception 
	 */
	@RequestMapping(value = "/exportWhiteListMobile",method= RequestMethod.POST)
	@ResponseBody
	public String exportWhiteListMobile(HttpServletRequest request,HttpServletResponse response,WhiteListMobile whiteListMobile) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String account=(String) map.get("account");
		String mobile=(String) map.get("mobile");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		ExportExcel export = hwsWhiteListService.exportWhiteListMobile(account, mobile, startTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	
	
}
