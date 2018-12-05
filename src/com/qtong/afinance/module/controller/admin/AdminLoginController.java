package com.qtong.afinance.module.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.admin.AdminLoginService;
/**
 * 用户登录接口
 *
 */
@Controller
@RequestMapping("/admin/user")
public class AdminLoginController {
	@Autowired
	private AdminLoginService loginService;
	
	
	
	@RequestMapping("/login")
	@ResponseBody
	public ResultObject login(HttpServletRequest request){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String mobile=(String) map.get("mobile");
		String password=(String) map.get("password");
		String key=(String) map.get("key");
		String code=(String) map.get("code");
		return loginService.login(mobile,password,key,code);
	}
	@RequestMapping("/isAuth")
	@ResponseBody
	public Boolean isAuth(HttpServletRequest request){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Boolean b =  loginService.isAuth(reqMess);
		return b;
	}
	
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
