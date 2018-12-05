package com.qtong.afinance.module.controller.statistics;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.statistics.UrlGuardStatsService;

/**
 * 网址卫士业务统计Controller
 *
 */
@Controller
@RequestMapping("/urlStats")
public class UrlGuardStatsController {
	
	@Autowired
	private UrlGuardStatsService urlGuardStatsService;
	
	/**
	 * 获取统计图相关数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCart")
	@ResponseBody
	public Map<String, Object> getCart(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//对时间进行处理 如果flag为month，默认当天向前一个月；如果flag为week，默认当前时间向前一星期；
		Date beginTime=null;
		Date endTime=null;
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getBeforeMonth(new Date());
			endTime=new Date();
		}else if (flag!=null&&flag.equals("week")) {
			beginTime=DateUtil.getBeforeWeek(new Date());
			endTime=new Date();
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM_DD);
			endTime=DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM_DD);
		}
		
		return urlGuardStatsService.getCart(customerNumber, beginTime, endTime);
		
	}
	
	/**
	 * 获取统计图相关数据-导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportCart")
	@ResponseBody
	public String exportCart(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//对时间进行处理 如果flag为month，默认当天向前一个月；如果flag为week，默认当前时间向前一星期；
		Date beginTime=null;
		Date endTime=null;
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getBeforeMonth(new Date());
			endTime=new Date();
		}else if (flag!=null&&flag.equals("week")) {
			beginTime=DateUtil.getBeforeWeek(new Date());
			endTime=new Date();
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM_DD);
			endTime=DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM_DD);
		}
		
		ExportExcel export = urlGuardStatsService.exportCart(customerNumber, beginTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
		
	}
	
	
	
	
}
