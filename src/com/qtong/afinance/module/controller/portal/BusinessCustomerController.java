package com.qtong.afinance.module.controller.portal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.portal.BusinessCustomerService;

/**
 *  门户-控制台首页 -最新动态(当前登录的用户的信息)controller
 */
@Controller
@RequestMapping("/portal/customer")
public class BusinessCustomerController {
	@Autowired
	private  BusinessCustomerService businessCustomerService;
	
	@RequestMapping(value = "/selectLogUser",method= RequestMethod.POST)
	@ResponseBody
	public void selectLogUser(HttpServletRequest request,HttpServletResponse response){
		String logUser=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String selectLogUser=businessCustomerService.selectLogUser(logUser);	
		//4、将response请求报文返回给行业客户  
        try {
			response.getOutputStream().write(selectLogUser.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
