package com.qtong.afinance.module.controller.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.product.AdminBossService;
/**
 * boss产品controlller
 *
 */
@Controller
@RequestMapping("/product/adminBoss")
public class AdminBossProController {
	@Autowired
	private AdminBossService adminBossService;
	/**
	 * BOSS查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getAdminBossProList")
	public void getAdminBossProList(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		String boss = adminBossService.getAdminBossProList(str);
		try {
			response.getOutputStream().write(boss.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * BOSS修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateBoss")
	public void updateBoss(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminBossService.updateBoss(str);
		String res="";
		if(i>0){
			res="yes";
		}else{
			res="no";
		}
		try {
			response.getOutputStream().write(res.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取消关联
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateQxgl")
	public void updateQxgl(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminBossService.updateQxgl(str);
		String res="";
		if(i>0){
			res="yes";
		}else{
			res="no";
		}
		try {
			response.getOutputStream().write(res.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询产品
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getProduct")
	public void getProduct(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		String product = adminBossService.getProduct(str);
		try {
			response.getOutputStream().write(product.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
