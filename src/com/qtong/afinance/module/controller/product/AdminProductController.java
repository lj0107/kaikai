package com.qtong.afinance.module.controller.product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.product.AdminProductService;

/**
 * 产品controller
 *
 */
@Controller
@RequestMapping("/product/adminProduct")
public class AdminProductController {
	@Autowired
	private AdminProductService adminProductService;

	/**
	 * 产品管理查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getAdminProductList")
	public void getAdminProductList(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		String product = adminProductService.getAdminProductList(str);
		try {
			response.getOutputStream().write(product.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询产品名字
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getCnNameByAdminProductCount")
	public void getCnNameByAdminProductCount(HttpServletRequest request, HttpServletResponse response) {
		String cn_name = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProductService.getCnNameByAdminProductCount(cn_name);
		System.out.println("产品查询======" + i);
		String str = "";
		if (i > 0) {
			str = "yes";
		} else {
			str = "no";
		}
		try {
			response.getOutputStream().write(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询一级类
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getTypeNameByAdminProType")
	public void getTypeNameByAdminProType(HttpServletRequest request, HttpServletResponse response) {
		String str = adminProductService.getTypeNameByAdminProType();
		try {
			response.getOutputStream().write(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询二级类
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getTypeName")
	public void getTypeName(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		String str = adminProductService.getTypeName(jsonstr);
		try {
			response.getOutputStream().write(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询来源
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getPartners")
	public void getPartners(HttpServletRequest request, HttpServletResponse response) {
		String str = adminProductService.getPartners();
		try {
			response.getOutputStream().write(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addAdminProduct")
	public void addAdminProduct(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProductService.addAdminProduct(jsonstr);
		String str = "";
		if (i > 0) {
			str = "yes";
		} else {
			str = "no";
		}
		try {
			response.getOutputStream().write(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateAdminProduct")
	public void updateAdminProduct(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProductService.updateAdminProduct(jsonstr);
		String str = "";
		if (i > 0) {
			str = "yes";
		} else {
			str = "no";
		}
		try {
			response.getOutputStream().write(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据code获取详细信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getAdminProductInfo")
	public void getAdminProductInfo(HttpServletRequest request, HttpServletResponse response) {
		String code = HttpTool.javaProtogenesisGetRequest(request);
		String product = adminProductService.getAdminProductInfo(code);
		try {
			response.getOutputStream().write(product.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据code删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "delAdminProduct")
	public void delAdminProduct(HttpServletRequest request, HttpServletResponse response) {
		String code = HttpTool.javaProtogenesisGetRequest(request);
		int i = adminProductService.delAdminProduct(code);
		String reqs = "";
		if (i > 0) {
			reqs = "yes";
		} else {
			reqs = "no";
		}
		try {
			response.getOutputStream().write(reqs.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
