package com.qtong.afinance.module.controller.admin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.admin.BusinessAdvert;
import com.qtong.afinance.module.service.admin.AdvertService;

/**
 * 推荐位controller
 * 
 *
 */
@Controller
@RequestMapping("/admin/advert")
public class AdvertController {

	@Autowired
	private AdvertService advertService;

	/**
	 * 推荐位根据id查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "advertUpdate", method = RequestMethod.POST)
	public void advertUpdate(HttpServletRequest request, HttpServletResponse response) {
		String id = HttpTool.javaProtogenesisGetRequest(request);
		String advert = advertService.advertUpdate(id);
		try {
			response.getOutputStream().write(advert.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询总数
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "getAdvertCount", method = RequestMethod.POST)
	public void getAdvertListDetails(HttpServletRequest request, HttpServletResponse response) {
		String advert_address = HttpTool.javaProtogenesisGetRequest(request);
		int i = advertService.getAdvertCount(advert_address);
		try {
			response.getOutputStream().write(String.valueOf(i).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 推荐位上线或者下线
	 * 
	 * @param id
	 * @param advert_state
	 */
	@RequestMapping(value = "updateAdvertStates", method = RequestMethod.POST)
	public void updateAdvertStates(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		int i = advertService.updateAdvertStates(str);
		String resp = "";
		if (i == 1) {
			resp = "true";
		} else {
			resp = "false";
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
	 * 推荐位删除
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "advertDelete", method = RequestMethod.POST)
	public void advertDelete(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		int i = advertService.advertDelete(str);
		String resp = "";
		if (i == 1) {
			resp = "true";
		} else {
			resp = "false";
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
	 * 推荐位搜索
	 * 
	 * @param id
	 * @param advert_state
	 */
	@RequestMapping(value = "advertFind", method = RequestMethod.POST)
	public void advertFind(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		BusinessAdvert advert = new BusinessAdvert();
		JSONObject parseObject = JSON.parseObject(jsonstr);
		advert.setId(parseObject.get("id").toString());
		advert.setAdvert_address(parseObject.get("advert_address").toString());
		advert.setAdvert_title(parseObject.get("advert_title").toString());
		advert.setCreate_time(parseObject.get("create_time").toString());
		advert.setCustom_time(parseObject.get("custom_time").toString());
		advert.setAdvert_states(parseObject.get("advert_states").toString());
		String adverts = advertService.advertFind(advert);
		try {
			response.getOutputStream().write(adverts.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 推荐位查询广告
	 * 
	 * @param id
	 * @param advert_state
	 */
	@RequestMapping(value = "advertFindState", method = RequestMethod.POST)
	public void advertFindState(HttpServletRequest request, HttpServletResponse response) {
		String advert_address = HttpTool.javaProtogenesisGetRequest(request);
		String adverts = advertService.advertFindState(advert_address);
		try {
			response.getOutputStream().write(adverts.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 推荐位添加
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "addAdvert", method = RequestMethod.POST)
	public void addAdvert(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		BusinessAdvert advert = new BusinessAdvert();
		JSONObject parseObject = JSON.parseObject(str);
		advert.setId(parseObject.getString("id").toString());
		advert.setAdvert_model(parseObject.get("advert_model").toString());
		advert.setAdvert_address(parseObject.get("advert_address").toString());
		advert.setAdvert_size(parseObject.get("advert_size").toString());
		advert.setAdvert_title(parseObject.get("advert_title").toString());
		advert.setAdvert_pic(parseObject.get("advert_pic").toString());
		advert.setAdvert_url(parseObject.get("advert_url").toString());
		advert.setAdvert_sort(parseObject.get("advert_sort").toString());
		advert.setCreate_time(DateUtil.toStr(new java.util.Date()));
		advert.setAdvert_states(parseObject.get("advert_states").toString());
		advert.setCustom_time(parseObject.get("custom_time").toString());
		String resp = "";
		int i = advertService.addAdvert(advert);
		if (i == 1) {
			resp = "true";
		} else {
			resp = "false";
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
	 * 推荐位修改
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "updateAdvert", method = RequestMethod.POST)
	public void updateAdvert(HttpServletRequest request, HttpServletResponse response) {
		String str = HttpTool.javaProtogenesisGetRequest(request);
		BusinessAdvert advert = new BusinessAdvert();
		JSONObject parseObject = JSON.parseObject(str);
		advert.setId(parseObject.getString("id").toString());
		advert.setAdvert_model(parseObject.get("advert_model").toString());
		advert.setAdvert_address(parseObject.get("advert_address").toString());
		advert.setAdvert_size(parseObject.get("advert_size").toString());
		advert.setAdvert_title(parseObject.get("advert_title").toString());
		advert.setAdvert_pic(parseObject.get("advert_pic").toString());
		advert.setAdvert_url(parseObject.get("advert_url").toString());
		advert.setAdvert_sort(parseObject.get("advert_sort").toString());
		advert.setCreate_time(DateUtil.toStr(new java.util.Date()));
		advert.setUpdate_time(DateUtil.toStr(new java.util.Date()));
		advert.setAdvert_states(parseObject.get("advert_states").toString());
		advert.setCustom_time(parseObject.get("custom_time").toString());
		int i = advertService.updateAdvert(advert);
		String resp = "";
		if (i == 1) {
			resp = "true";
		} else {
			resp = "false";
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
	 * 推荐位判断是否够5条
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "advertIfFives", method = RequestMethod.POST)
	public void advertIfFives(HttpServletRequest request, HttpServletResponse response) {
		String advert_address = HttpTool.javaProtogenesisGetRequest(request);
		int i = advertService.advertIfFives(advert_address);
		String reps="";
		reps=String.valueOf(i);
		try {
			response.getOutputStream().write(reps.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
