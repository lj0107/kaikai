package com.qtong.afinance.module.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.admin.AdminUser;
import com.qtong.afinance.module.service.admin.AdminUserService;

/**
 * 后台管理-管理员管理
 */
@Controller
@RequestMapping("/admin/administratorManage")
public class AdministratorManageController {
	
	@Autowired
	private AdminUserService adminUserService;
	
	
	/**
	 * 根据条件分页查询管理员列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAdministratorManageList",method= RequestMethod.POST)
	public void selectByTermList(HttpServletRequest request,HttpServletResponse response){
		String selectByTerm=HttpTool.javaProtogenesisGetRequest(request);
		JSONObject json = JSONObject.parseObject(selectByTerm);
		String queryByPage = adminUserService.queryByPage(json);
        try {
			response.getOutputStream().write(queryByPage.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 添加管理员
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveAdministratorManage",method= RequestMethod.POST)
	public void saveAdministratorManage(HttpServletRequest request,HttpServletResponse response) {
		String json=HttpTool.javaProtogenesisGetRequest(request);
		AdminUser u = JSONObject.parseObject(json,AdminUser.class);
		int insertAdminUser = adminUserService.insertAdminUser(u);
		String resp=null;
		if(insertAdminUser==1) {
			resp="success";
		}else{
			resp="erro";
		}
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据id state修改管理员状态
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateAdministratorState",method= RequestMethod.POST)
	public void updateAdministratorState(HttpServletRequest request,HttpServletResponse response){
		String json=HttpTool.javaProtogenesisGetRequest(request);	
		int count = adminUserService.updateState(json);	 
		String resp=null;
		if(count==1) {
			resp="success";
		}else{
			resp="erro";
		}
        try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id 修改管理员
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updateAdminUser",method= RequestMethod.POST)
	public void saveAdministrator(HttpServletRequest request,HttpServletResponse response){
		String json=HttpTool.javaProtogenesisGetRequest(request);	
		AdminUser u = JSONObject.parseObject(json,AdminUser.class);
		int count = adminUserService.updateAdminUser(u);	 
		try {
			response.getOutputStream().write(Integer.toString(count).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据id 修改管理员
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/update",method= RequestMethod.POST)
	public void update(HttpServletRequest request,HttpServletResponse response){
		String json=HttpTool.javaProtogenesisGetRequest(request);
		AdminUser u = JSONObject.parseObject(json,AdminUser.class);
		int count = adminUserService.update(u);
		try {
			response.getOutputStream().write(Integer.toString(count).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据id 修改密码
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/updatePassword",method= RequestMethod.POST)
	public void updatePassword(HttpServletRequest request,HttpServletResponse response){
		String json=HttpTool.javaProtogenesisGetRequest(request);
		AdminUser u = JSONObject.parseObject(json,AdminUser.class);
		int count = adminUserService.updatePassword(u);
		try {
			response.getOutputStream().write(Integer.toString(count).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据id删除管理员
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/deleteAdministratorById",method= RequestMethod.POST)
	public void deleteAdministratorById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);	
		int count = adminUserService.deleteAdminUser(id);	 
		String resp=null;
		if(count==1) {
			resp="1";
		}else{
			resp="0";
		}
		try {
			response.getOutputStream().write(resp.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据id查看管理员详情
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/getAdministratorById",method= RequestMethod.POST)
	public void getAdministratorById(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);	
		String selAdminUserById = adminUserService.selAdminUserById(id);	 
		try {
			response.getOutputStream().write(selAdminUserById.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据姓名查看管理员
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/getAdministratorByName",method= RequestMethod.POST)
	public void getAdministratorByName(HttpServletRequest request,HttpServletResponse response){
		String name=HttpTool.javaProtogenesisGetRequest(request);	
		String queryByName = adminUserService.queryByName(name);	 
		try {
			response.getOutputStream().write(queryByName.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据手机号查看管理员详情
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/getAdministratorByMobile",method= RequestMethod.POST)
	public void getAdministratorByMobile(HttpServletRequest request,HttpServletResponse response){
		String mobile=HttpTool.javaProtogenesisGetRequest(request);	
		String queryByMobile = adminUserService.queryByMobile(mobile);	 	 
		try {
			response.getOutputStream().write(queryByMobile.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
