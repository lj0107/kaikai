package com.qtong.afinance.module.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.admin.WaitManageService;
/**
 * 用户代办
 *
 */
@Controller
@RequestMapping("/waitManage")
public class WaitManageController {
	@Autowired
	private WaitManageService waitManageService;
	
	@RequestMapping("/waitManageTable")
	@ResponseBody
	public List<Map<String,Object>> waitManageTable(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String userId=(String) map.get("userId");
		
		return waitManageService.waitManageTable(userId);
	}
	
	@RequestMapping("/waitManageCount")
	@ResponseBody
	public Integer waitManageCount(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String userId=(String) map.get("userId");
		
		return waitManageService.waitManageCount(userId);
	}
	
}
