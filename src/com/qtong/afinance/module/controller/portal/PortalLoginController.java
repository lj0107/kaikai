package com.qtong.afinance.module.controller.portal;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.portal.PortalLoginService;
/**
 * 客户验证登录接口
 *
 */
@Controller
@RequestMapping("/portal/user")
public class PortalLoginController {
	@Autowired
	private PortalLoginService loginService;
	
	@RequestMapping("/login")
	@ResponseBody
	public ResultObject login(HttpServletRequest request){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String loginName=(String) map.get("loginName");
		String loginPwd=(String) map.get("loginPwd");
		String key=(String) map.get("key");
		String code=(String) map.get("code");
		return loginService.login(loginName,loginPwd,key,code);
	}
	/*
	 * 生成验证码
	 * */
	@RequestMapping("/getAuthCode")
	@ResponseBody
	public Map<String,String> getAuthCode(HttpServletRequest request){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String type=(String) map.get("type");
		Map<String, String> yzm = loginService.getAuthCode(type);
		return yzm;
	}
	
}
