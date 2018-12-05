package com.qtong.afinance.module.controller.urlGuard;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.urlGuard.AfinUrlGuardService;

/**
 * 网址卫士controller
 *
 */
@Controller
@RequestMapping("/urlGuard/afinUrlGuard")
public class AfinUrlGuardController {
	@Autowired
	private AfinUrlGuardService afinUrlGuardService;

	/**
	 * 网址卫士通用查询
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAfinUrlGuardList", method = RequestMethod.POST)
	public void getAfinUrlGuardList(HttpServletRequest request, HttpServletResponse response) {
		String reqMess = HttpTool.javaProtogenesisGetRequest(request);
		String afinUrlGuardList = afinUrlGuardService.getAfinUrlGuardList(reqMess);
		try {
			response.getOutputStream().write(afinUrlGuardList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 网址卫士通用导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportWZWSList",method= RequestMethod.POST)
	@ResponseBody
	public String exportWZWSList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String url = (String) parseObject.get("url");
		String snapshot = (String) parseObject.get("snapshot");
		String push_timeStart = (String) parseObject.get("push_timeStart");
		String push_timeEnd = (String) parseObject.get("push_timeEnd");
		String partner_no = (String) parseObject.get("partner_no");
		String opt_timeStart = (String) parseObject.get("opt_timeStart");
		String opt_timeEnd = (String) parseObject.get("opt_timeEnd");
		String customer_no = (String) parseObject.get("customer_no");
		String state = (String) parseObject.get("state");
		String result=afinUrlGuardService.exportWZWSList(url,snapshot,push_timeStart,push_timeEnd,partner_no,opt_timeStart,opt_timeEnd,customer_no,state);
		return result;
	}
	/**
	 * 网址卫士通用导出-恶意网址库
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportEYIList",method= RequestMethod.POST)
	@ResponseBody
	public String exportEYIList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String url = (String) parseObject.get("url");
		String snapshot = (String) parseObject.get("snapshot");
		String push_timeStart = (String) parseObject.get("push_timeStart");
		String push_timeEnd = (String) parseObject.get("push_timeEnd");
		String partner_no = (String) parseObject.get("partner_no");
		String opt_timeStart = (String) parseObject.get("opt_timeStart");
		String opt_timeEnd = (String) parseObject.get("opt_timeEnd");
		String customer_no = (String) parseObject.get("customer_no");
		String state = (String) parseObject.get("state");
		String result=afinUrlGuardService.exportEYIList(url,snapshot,push_timeStart,push_timeEnd,partner_no,opt_timeStart,opt_timeEnd,customer_no,state);
		return result;
	}
	
	/**
	 * 网址卫士通用导出-网址查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportWZCXList",method= RequestMethod.POST)
	@ResponseBody
	public String exportWZCXList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String url = (String) parseObject.get("url");
		String snapshot = (String) parseObject.get("snapshot");
		String push_timeStart = (String) parseObject.get("push_timeStart");
		String push_timeEnd = (String) parseObject.get("push_timeEnd");
		String partner_no = (String) parseObject.get("partner_no");
		String opt_timeStart = (String) parseObject.get("opt_timeStart");
		String opt_timeEnd = (String) parseObject.get("opt_timeEnd");
		String customer_no = (String) parseObject.get("customer_no");
		String state = (String) parseObject.get("state");
		String result=afinUrlGuardService.exportWZCXList(url,snapshot,push_timeStart,push_timeEnd,partner_no,opt_timeStart,opt_timeEnd,customer_no,state);
		return result;
	}
	/**
	 * 下拉框：状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectStateList", method = RequestMethod.POST)
	public void selectStateList(HttpServletRequest request, HttpServletResponse response) {
		String afinUrlGuardList = afinUrlGuardService.selectSateList();
		try {
			response.getOutputStream().write(afinUrlGuardList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 恶意网址库列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectSpiteList", method = RequestMethod.POST)
	public void selectSpiteList(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		String afinUrlGuardList = afinUrlGuardService.selectSpiteList(jsonstr);
		try {
			response.getOutputStream().write(afinUrlGuardList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *  恶意网址库列表-导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportSpiteList",method= RequestMethod.POST)
	@ResponseBody
	public String exportSpiteList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String url = (String) parseObject.get("url");
		String partnerName = (String) parseObject.get("partnerName");
		String customerName = (String) parseObject.get("customerName");
		String snapshot = (String) parseObject.get("snapshot");
		String firstTime = (String) parseObject.get("firstTime");
		String secondTime = (String) parseObject.get("secondTime");
		String result=afinUrlGuardService.exportSpiteList(url,partnerName,snapshot,customerName,firstTime,secondTime);
		return result;
	}
	
	/**
	 * 1.安全网址列表
	 * @return resp 
	 */
	@RequestMapping(value = "/selectSaftyList", method = RequestMethod.POST)
	public void selectSaftyList(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		String afinUrlGuardList = afinUrlGuardService.selectSaftyList(jsonstr);
		try {
			response.getOutputStream().write(afinUrlGuardList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 2.1安全网址库-查看详情
	 * @return resp 
	 */
	@RequestMapping(value = "/selectByUrlList", method = RequestMethod.POST)
	public void selectByUrlList(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		String afinUrlGuardList = afinUrlGuardService.selectByUrlList(jsonstr);
		try {
			response.getOutputStream().write(afinUrlGuardList.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 2.2安全网址库-查看详情-导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exportByUrlList",method= RequestMethod.POST)
	@ResponseBody
	public String exportByUrlList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String url = (String) parseObject.get("url");
		String customerNo = (String) parseObject.get("customerNo");
		String customerName = (String) parseObject.get("customerName");
		String firstTime = (String) parseObject.get("firstTime");
		String secondTime = (String) parseObject.get("secondTime");
		String result=afinUrlGuardService.exportByUrlList(url,customerNo,customerName,firstTime,secondTime);
		return result;
	}
	
	
	
	/**
	 * 3.安全网址-普通删除
	 * @return resp 
	 */
	@RequestMapping(value = "/deleteByUrl", method = RequestMethod.POST)
	public void deleteByUrl(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		int afinUrlGuardList = afinUrlGuardService.deleteByUrl(jsonstr);
		String resp=null;
		if(afinUrlGuardList==1) {
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
	 * 4.安全网址-批量删除
	 * @return resp 
	 */
	@RequestMapping(value = "/deleteList", method = RequestMethod.POST)
	public void deleteList(HttpServletRequest request, HttpServletResponse response) {
		String jsonstr = HttpTool.javaProtogenesisGetRequest(request);
		int afinUrlGuardList = afinUrlGuardService.deleteList(jsonstr);
		
		try {
			response.getOutputStream().write((afinUrlGuardList+"").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 5.安全网址-导入网址
	 * @return resp 
	 */
	@RequestMapping(value = "/insertUrl",method= RequestMethod.POST)
	public void insertUrl(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		int insertRecentNews = afinUrlGuardService.insertUrl(reqMess);
		String resp=null;
		if(insertRecentNews==1) {
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
	 * 根据网址修改状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateStateByUrl", method = RequestMethod.POST)
	public void updateStateByUrl(HttpServletRequest request, HttpServletResponse response) {
		String reqMess = HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		List<String> urls=(List<String>) map.get("urls");
		String lastState=(String) map.get("lastState");
		String currentState=(String) map.get("currentState");
		String remark=(String) map.get("remark");
		String optId=(String) map.get("optId");
		String optName=(String) map.get("optName");
		String optType=(String) map.get("optType");
		int updateState = afinUrlGuardService.updateState(urls, lastState, currentState, remark, optType, optId, optName);
		try {
			response.getOutputStream().write((updateState+"").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据网址和状态修改状态（不通过）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateStateByUrlAndState", method = RequestMethod.POST)
	public void updateStateByUrlAndState(HttpServletRequest request, HttpServletResponse response) {
		String reqMess = HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		List<String> urls=(List<String>) map.get("urls");
		String currentState=(String) map.get("currentState");
		String remark=(String) map.get("remark");
		String optId=(String) map.get("optId");
		String optName=(String) map.get("optName");
		String optType=(String) map.get("optType");
		int updateNState = afinUrlGuardService.updateNState(urls, currentState, remark, optType, optId, optName);
		try {
			response.getOutputStream().write((updateNState+"").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 根据网址和状态修改状态（误报 通过）
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateYStateByUrlAndState", method = RequestMethod.POST)
	public void updateYStateByUrlAndState(HttpServletRequest request, HttpServletResponse response) {
		String reqMess = HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		List<String> urls=(List<String>) map.get("urls");
		String currentState=(String) map.get("currentState");
		String remark=(String) map.get("remark");
		String optId=(String) map.get("optId");
		String optName=(String) map.get("optName");
		String optType=(String) map.get("optType");
		int updateNState = afinUrlGuardService.updateYState(urls, currentState, remark, optType, optId, optName);
		try {
			response.getOutputStream().write((updateNState+"").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 白名单判断
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/saveUrl", method = RequestMethod.POST)
	public void saveUrl(HttpServletRequest request, HttpServletResponse response) {
		String reqMess = HttpTool.javaProtogenesisGetRequest(request);
		int updateNState = afinUrlGuardService.saveUrl(reqMess);
		try {
			response.getOutputStream().write((updateNState+"").getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 网址卫士通用查询Url
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportAfinUrlGuardUrl", method = RequestMethod.POST)
	public void exportAfinUrlGuardUrl(HttpServletRequest request, HttpServletResponse response) {
		String reqMess = HttpTool.javaProtogenesisGetRequest(request);
		String urls = afinUrlGuardService.exportAfinUrlGuardUrl(reqMess);
		try {
			response.getOutputStream().write(urls.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 网址卫士通用查询Url
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllIntercepedUrl", method = RequestMethod.POST)
	public void getAllIntercepedUrl(HttpServletRequest request, HttpServletResponse response) {
		String state = HttpTool.javaProtogenesisGetRequest(request);
		JSONObject queryAllIntercepedUrl =null;
		if(state.equals("")||state==null) {
			 queryAllIntercepedUrl = afinUrlGuardService.querySafteAllIntercepedUrl();
		}else {
			 queryAllIntercepedUrl = afinUrlGuardService.queryAllIntercepedUrl(state);
		}
		try {
			response.getOutputStream().write(JSON.toJSONString(queryAllIntercepedUrl).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
