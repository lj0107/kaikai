package com.qtong.afinance.module.controller.myProfile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.service.myProfile.MyProfileService;

@Controller
@RequestMapping("/myProfile")
public class MyProfileController {
	
	@Autowired
	private MyProfileService myProfileService;
	
	
	/**
	 * 客户中心—我的资料-修改登录密码-查询旧密码
	 * 1:与原密码一致  2:与原密码不一致
	 * @param request
	 * @param afinAdminUser
	 * @return
	 * @throws Exception 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@RequestMapping(value = "/getUserPassword",method = RequestMethod.POST)
	public void getUserPassword(HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, Exception{
		String jsonStr = HttpTool.javaProtogenesisGetRequest(request);
		BossCustomer advert = new BossCustomer();
		
		JSONObject parseObject = JSON.parseObject(jsonStr);
		advert.setCustomerNumber(parseObject.get("customerNumber").toString());
		advert.setLoginPwd(parseObject.get("password").toString());
		
		String loginPwd = myProfileService.getUserPassword(advert);
		System.out.println(loginPwd);
		
		//加密密码
		String md5 = DigestUtils.md5DigestAsHex(advert.getLoginPwd().getBytes());
		System.out.println(" 加密后"+md5);
		String resp = null;
		if(md5.equals(loginPwd)){
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
	 * 客户中心-我的资料-修改登录密码
	 * @param response
	 * @param request
	 * @param afinAdminUser
	 * @return
	 */
	@RequestMapping(value = "/updateMyProfilePassword",method = RequestMethod.POST)
	@ResponseBody
	public void updateMyProfilePassword(HttpServletResponse response,HttpServletRequest request){
		String str = HttpTool.javaProtogenesisGetRequest(request);
		
		BossCustomer advert = new BossCustomer();
		
		JSONObject parseObject = JSON.parseObject(str);
		advert.setCustomerNumber(parseObject.get("customerNumber").toString());
		advert.setLoginPwd(parseObject.get("loginPwd").toString());
		//加密密码
		String loginPwd = DigestUtils.md5DigestAsHex(advert.getLoginPwd().getBytes());
		advert.setLoginPwd(loginPwd);
		System.out.println("加密后的密码："+advert.getLoginPwd());
		advert.setUpdpwdTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		int count = myProfileService.updateMyProfilePassword(advert);
		System.out.println(count);
		String resp=null;
		if(count==1) {
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
	 * 客户中心-我的资料-基础资料
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getMyProfileList",method = RequestMethod.POST)
	@ResponseBody
	public void getMyProfileList(HttpServletRequest request,HttpServletResponse response){
		String customerNumber=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String coParStr=myProfileService.getMyProfileList(customerNumber);
		 try {
				response.getOutputStream().write(coParStr.getBytes("utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}

