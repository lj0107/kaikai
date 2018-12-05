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
import com.qtong.afinance.module.service.statistics.AdminStatsService;

/**
 * 后台统计ServiceController
 *
 */
@Controller
@RequestMapping("/adminStats")
public class AdminStatsController {

	@Autowired
	private AdminStatsService statsService;
	
	
	
	/**
	 * 后台统计-业务金额-产品金额
	 * @param productName 产品名称
	 * @param customerNumber 客户编码
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@RequestMapping("/getCartForFeeByProduct")
	@ResponseBody
	public Map<String, Object> getCartForFeeByProduct(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productName=(String) map.get("productName");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(beginTimeStr!=null&&!beginTimeStr.equals("")){//时间优先
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
		}else if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}
		
		return statsService.getCartForFeeByProduct(productName, customerNumber, beginTime, endTime);
		
	}
	
	
	
	
	/**
	 * 后台统计-业务金额-产品金额-导出
	 * @param productName 产品名称
	 * @param customerNumber 客户编码
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	@RequestMapping("/exportCartForFeeByProduct")
	@ResponseBody
	public String exportCartForFeeByProduct(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productName=(String) map.get("productName");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(beginTimeStr!=null&&!beginTimeStr.equals("")){//时间优先
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
		}else if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}
		
		ExportExcel export = statsService.exportCartForFeeByProduct(customerNumber,productName ,beginTime, endTime);
		String result=JSON.toJSONString(export);
		 return result;
		
	}
	
	
	
	
	/**
	 * 后台统计-业务金额
	 * @return
	 */
	@RequestMapping("/getCartForFee")
	@ResponseBody
	public Map<String, Object> getCartForFee(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
			
		}
		
		 return statsService.getCartForFee(customerNumber, beginTime, endTime);
	}
	
	
	
	/**
	 * 后台统计-业务金额-导出
	 * @return
	 */
	@RequestMapping("/exportCartForFee")
	@ResponseBody
	public String exportCartForFee(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		if(beginTimeStr!=null&&!beginTimeStr.equals("")&&endTimeStr!=null&&!endTimeStr.equals("")){
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
		}else{
			//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
			if(flag!=null&&flag.equals("month")){
				beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
				endTime=DateUtil.getMaxMonthDateTime(new Date());
			}else if (flag!=null&&flag.equals("year")) {
				beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
				endTime=DateUtil.getMaxMonthDateTime(new Date());
			}
		}
		
		ExportExcel export = statsService.exportCartForFee(customerNumber, beginTime, endTime);
		String result=JSON.toJSONString(export);
		 return result;
	}
	
	
	
	/**
	 * 后台统计-客户次数
	 * @return
	 */
	@RequestMapping("/getOrderState")
	@ResponseBody
	public Map<String, Object> getOrderState(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
			
		}
		 return statsService.getOrderState(beginTime, endTime);
	}
	/**
	 * 后台统计-客户次数-导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/excelOrderState")
	@ResponseBody
	public String excelOrderState(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		if(beginTimeStr!=null&&!beginTimeStr.equals("")&&endTimeStr!=null&&!endTimeStr.equals("")){
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
		}else{
			//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
			if(flag!=null&&flag.equals("month")){
				beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
				endTime=DateUtil.getMaxMonthDateTime(new Date());
			}else if (flag!=null&&flag.equals("year")) {
				beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
				endTime=DateUtil.getMaxMonthDateTime(new Date());
			}
		}
		
		ExportExcel export = statsService.excelOrderState(beginTime, endTime);
		String result=JSON.toJSONString(export);
		 return result;
	}

	
	
	
	/**
	 * 后台统计-客户次数-产品客户次数
	 * @param productName 产品名称
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */               
	@RequestMapping("/getOrderSubState")
	@ResponseBody
	public Map<String, Object> getOrderSubState(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String productName=(String) map.get("productName");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//处理时间
				Date beginTime=null;
				Date endTime=null;
				
				//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
				if(beginTimeStr!=null&&!beginTimeStr.equals("")){//时间优先
					beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
					endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
				}else if(flag!=null&&flag.equals("month")){
					beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
					endTime=DateUtil.getMaxMonthDateTime(new Date());
				}else if (flag!=null&&flag.equals("year")) {
					beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
					endTime=DateUtil.getMaxMonthDateTime(new Date());
				}
				
		
		
		return statsService.getCartForCountByProduct(productName, beginTime, endTime);
		
	}
	/**
	 * 后台统计-客户次数-产品客户次数-导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/excelOrderSubState")
	@ResponseBody
	public String excelOrderSubState(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String productName=(String) map.get("productName");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		//处理时间
				Date beginTime=null;
				Date endTime=null;
				
				//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
				if(beginTimeStr!=null&&!beginTimeStr.equals("")){//时间优先
					beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM);
					endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
				}else if(flag!=null&&flag.equals("month")){
					beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-2);
					endTime=DateUtil.getMaxMonthDateTime(new Date());
				}else if (flag!=null&&flag.equals("year")) {
					beginTime=DateUtil.getPerFirstDayOfMonth(new Date(),-11);
					endTime=DateUtil.getMaxMonthDateTime(new Date());
				}
		
		ExportExcel export = statsService.excelOrderSubState(productName ,beginTime, endTime);
		String result=JSON.toJSONString(export);
		 return result;
		
	}
}
