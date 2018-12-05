package com.qtong.afinance.module.controller.admin;

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
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.admin.AdminRole;
import com.qtong.afinance.module.service.admin.AdminResoucesService;
import com.qtong.afinance.module.service.admin.AdminRoleService;

/**
 * 角色controller
 */
@Controller
@RequestMapping("/admin/role")
public class AdminRoleController {
	
	@Autowired
	private AdminResoucesService resService;
	
	@Autowired
	private AdminRoleService roleService;
	
	
	
	/**
	 * 
	 * 查用户权限为首页块状菜单展示提供数据（此处展示模块数目最多6个。如果不够6个，顺序排列，后面模块不展示，点击功能入口，跳到对应功能模块的第一个最低级菜单页。）
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getRes4Show",method= RequestMethod.POST)
	public void getRes4Show(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
		String result= resService.getRes4Show(id);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查用户资源权限
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getUserRes",method= RequestMethod.POST)
	public void getUserRes(HttpServletRequest request,HttpServletResponse response){
		String userId=HttpTool.javaProtogenesisGetRequest(request);
		String result= resService.getUserRes(userId);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查所有资源权限
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selAllRes",method= RequestMethod.POST)
	public void selAllRes(HttpServletRequest request,HttpServletResponse response){
		String result= resService.selAll();
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查角色下所有res
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selResByRole",method= RequestMethod.POST)
	public void selResByRole(HttpServletRequest request,HttpServletResponse response){
		String roleId=HttpTool.javaProtogenesisGetRequest(request);
		String result= resService.selResByRole(roleId);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 查角色资源权限
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRoleRes",method= RequestMethod.POST)
	public void getRoleRes(HttpServletRequest request,HttpServletResponse response){
		String roleId=HttpTool.javaProtogenesisGetRequest(request);
		String result= resService.getRoleRes(roleId);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 查角色资源权限
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getResByRoleId",method= RequestMethod.POST)
	public void getResByRoleId(HttpServletRequest request,HttpServletResponse response){
		String roleId=HttpTool.javaProtogenesisGetRequest(request);
		
		String result= resService.getResByRoleId(roleId);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 分页查询
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/pageQuery",method= RequestMethod.POST)
	public void pageQuery(HttpServletRequest request,HttpServletResponse response){	
		String pageIndex=HttpTool.javaProtogenesisGetRequest(request);
		String result= roleService.pageQuery(pageIndex);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insert",method= RequestMethod.POST)
	@ResponseBody
	public ResultObject insertRole(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		String userId=(String) map.get("userId");
		AdminRole role=new AdminRole();
		role.setName((String) map.get("name"));
		role.setDescription((String) map.get("desc"));
		
		return roleService.insert(userId,role);
	}
	
	/**
	 * 修改角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResultObject update(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		
		Map map=JSON.parseObject(reqMess,Map.class);
		AdminRole role=new AdminRole();
		role.setId(Integer.valueOf((String) map.get("id")));
		role.setName((String) map.get("name"));
		role.setDescription((String) map.get("desc"));
		
		return roleService.update(role);
	}
	
	/**
	 * 删除角色
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delete")
	public void delete(HttpServletRequest request,HttpServletResponse response){
		String id=HttpTool.javaProtogenesisGetRequest(request);
		
		String result= roleService.delete(id);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID查角色信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selById",method= RequestMethod.POST)
	public void selById(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String result= roleService.selById(reqMess);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
			
	/**
	 * 根据roleId查已分配和未分配该角色的user集合
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/allotUserToRole",method= RequestMethod.POST)
	public void allotUserToRole(HttpServletRequest request,HttpServletResponse response){
		String roleId=HttpTool.javaProtogenesisGetRequest(request);
		String result= roleService.allotUserToRole(roleId);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 角色绑定user
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addUserRole",method= RequestMethod.POST)
	public void addUserRole(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String result= roleService.addUserRole(reqMess);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存 角色-权限关系
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addRoleRes",method= RequestMethod.POST)
	public void addRoleRes(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String result= roleService.addRoleRes(reqMess);
		try {
			response.getOutputStream().write(result.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
