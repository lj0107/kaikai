package com.qtong.afinance.module.controller.admin;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.service.admin.CustomerService;

/**
 * 客户管理controller
 */
@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
	
	@Autowired
	private  CustomerService customerService;
	
	/**
	 * 1.查看详情-通过客户编码（唯一）
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/selectCusByNumber",method= RequestMethod.POST)
	@ResponseBody
	public void selectCusByNumber(HttpServletRequest request,HttpServletResponse response){
		String customerNumber=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String coParStr=customerService.selectCusByNumber(customerNumber);	
		//4、将response请求报文返回给行业客户  
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
	/**
	 * 2.修改状态(正常或禁用)
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateStatus",method= RequestMethod.POST)
	@ResponseBody
	public void updateStatus(HttpServletRequest request,HttpServletResponse response){
		String status=HttpTool.javaProtogenesisGetRequest(request);		 
		BossCustomer bossCustomer=JSON.parseObject(status,BossCustomer.class);
		 System.out.println("增加登陆名称："+bossCustomer.getStatus());
		int updateStatus=customerService.updateStatus(bossCustomer);	
		String resp=null;
		if(updateStatus==1) {
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
	 *3. 根据条件查询
	 * @param customerNumber 客户编码
	 * @param customerName 客户名称
	 * @param loginName 登陆名称
	 * @param pageIndex 当前页号
	 */
	@RequestMapping(value = "/selectCusLimit",method= RequestMethod.POST)
	@ResponseBody
	public void selectCusByTermList(HttpServletRequest request,HttpServletResponse response){
		String selectByTerm=HttpTool.javaProtogenesisGetRequest(request);		 	 
		String selectByTermList=customerService.selectCusByTermList(selectByTerm);	
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
	 *4. 创建账号
	 * @param customerNumber 客户编码
	 * @param loginName 登陆名称
	 * @param loginPwd 登陆密码
	 */
	@RequestMapping(value = "/insertCus",method= RequestMethod.POST)
	@ResponseBody
	public void insertCus(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		BossCustomer bossCustomer=JSON.parseObject(reqMess,BossCustomer.class);
		int insertCus = customerService.insertCus(bossCustomer);
		String resp=null;
		if(insertCus==1) {
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
	 * 更改负责人
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updatePic",method= RequestMethod.POST)
	@ResponseBody
	public void updatePic(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		BossCustomer bossCustomer=JSON.parseObject(reqMess,BossCustomer.class);
		int updatePic = customerService.updatePic(bossCustomer);
		String resp=null;
		if(updatePic==1) {
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
	 * 5. 验证用户名是否重复
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertCusName",method= RequestMethod.POST)
	@ResponseBody
	public void insertCusName(HttpServletRequest request,HttpServletResponse response){
		String loginName=HttpTool.javaProtogenesisGetRequest(request);
		String reqs = customerService.getUserName(loginName);
		String resp="";
		if(reqs.equals("null")){
			resp=("success");
		}else{			
			resp=("erro");
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
	 *6. 网址卫士-所有客户名称
	 * @return customerNumber 客户编码
	 * @return customerName 客户名称
	 */
	@RequestMapping(value = "/selectCusList",method= RequestMethod.POST)
	@ResponseBody
	public void selectCusList(HttpServletRequest request,HttpServletResponse response){
		String selectByTermList=customerService.selectCusList();	
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
	 * 查询所有客户
	 * @return
	 */
	@RequestMapping("/getCustomers")
	@ResponseBody
	public List<BossCustomer> getCustomers(){
		return customerService.getCustomers();
	}
}
