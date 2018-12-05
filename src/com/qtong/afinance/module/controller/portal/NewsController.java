package com.qtong.afinance.module.controller.portal;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.ConfigurationInfo;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;
import com.qtong.afinance.module.service.portal.NewsService;


/**
 * 最新动态controller
 */
@Controller
@RequestMapping("/portal/recentNews")
public class NewsController {
	@Autowired
	private  NewsService recentNewsService;
	/**
	 * 门户-最新动态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRecentNewsList",method= RequestMethod.POST)
	public void getRecentNewsList(HttpServletRequest request,HttpServletResponse response) {
		String count=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = recentNewsService.getRecentNewsList(count);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	/**
	 * 首页-最新动态（前四条）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRecentNewsDetails1",method= RequestMethod.POST)
	public void getRecentNewsDetails1(HttpServletRequest request,HttpServletResponse response) {
		String recentNewsList = recentNewsService.getRecentNewsDetails1();
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	/**
	 * 门户-最新动态详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getRecentNewsDetails",method= RequestMethod.POST)
	public void getRecentNewsDetails(HttpServletRequest request,HttpServletResponse response) {
		String id=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = recentNewsService.getRecentNewsDetails(Integer.parseInt(id));
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
}
