package com.qtong.afinance.module.controller.statistics;

import java.util.Date;
import java.util.List;
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
import com.qtong.afinance.module.pojo.product.AdminOrder;
import com.qtong.afinance.module.service.portal.AdimOrderService;
import com.qtong.afinance.module.service.statistics.OrderStatsService;
/**
 * 业务统计Controller
 *
 */
@Controller
@RequestMapping("/stats")
public class OrderStatsController {
	@Autowired
	private OrderStatsService statsService;
	@Autowired
	private AdimOrderService orderService;
	
	/**
	 * 获取统计表相关信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getTable")
	@ResponseBody
	public List<Map<String,Object>> getTable(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String proName=(String) map.get("proName");
		
		//默认开始时间为订购生效时间，结束时间为当前时间或者订购结束时间
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		Date beginTime=null;
		Date endTime=null;  
		if(beginTimeStr==null||beginTimeStr.equals("")){
			beginTime=order.getEffTime();
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM_DD);
		}
		if(endTimeStr==null||endTimeStr.equals("")){
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM_DD));
		}
		
		
		return statsService.getTable(customerNumber, productOrderId, beginTime, endTime,proName);
	}
	
	
	
	/**
	 * 获取统计表相关信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/exportTable")
	@ResponseBody
	public String exportTable(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String proName=(String) map.get("proName");
		
		//默认开始时间为订购生效时间，结束时间为当前时间或者订购结束时间
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		Date beginTime=null;
		Date endTime=null;  
		if(beginTimeStr==null||beginTimeStr.equals("")){
			beginTime=order.getEffTime();
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM_DD);
		}
		if(endTimeStr==null||endTimeStr.equals("")){
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM_DD));
		}
		
		
		
		ExportExcel export = statsService.exportTable(customerNumber, productOrderId, beginTime, endTime,proName);
		String result=JSON.toJSONString(export);
		return result;
		
	}
	
	
	
	
	/**
	 * 乾坤大数据-业务统计-饼状图数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCartForBigDataPie")
	@ResponseBody
	public Map<String, Object> getCartForBigDataPie(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		
		//对时间进行处理 如果flag为month，默认当天向前一个月；如果flag为week，默认当前时间向前一星期；
		Date beginTime=null;
		Date endTime=null;
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getBeforeMonth(DateUtil.toDate(DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD));
			endTime=new Date();
		}else if (flag!=null&&flag.equals("week")) {
			beginTime=DateUtil.getBeforeWeek(DateUtil.toDate(DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD));
			endTime=new Date();
		}else if (flag!=null&&flag.equals("all")) {
			
			beginTime=order.getEffTime();
			
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM_DD);
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM_DD));
			
		}
		
		 return statsService.getCartForBigDataPie(customerNumber,productOrderId, beginTime, endTime);
	}
	
	
	
	/**
	 * 乾坤大数据-业务统计-折线图数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCartForBigDataLine")
	@ResponseBody
	public Map<String, Object> getCartForBigDataLine(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		
		//对时间进行处理 如果flag为month，默认当天向前一个月；如果flag为week，默认当前时间向前一星期；
		Date beginTime=null;
		Date endTime=null;
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getBeforeMonth(new Date());
			endTime=new Date();
		}else if (flag!=null&&flag.equals("week")) {
			beginTime=DateUtil.getBeforeWeek(new Date());
			endTime=new Date();
		}else if (flag!=null&&flag.equals("all")) {
			
			beginTime=order.getEffTime();
			
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY__MM__DD);
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY__MM__DD));
			
		}
		
		 return statsService.getCartForBigDataLine(customerNumber,productOrderId, beginTime, endTime);
	}
	
	
	/**
	 * 乾坤大数据-业务统计-折线图数据-导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportCartForBigDataLine")
	@ResponseBody
	public String exportCartForBigDataLine(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String effTimeStr=(String) map.get("effTime");
		String productNameStr=(String) map.get("productName");
		
		ExportExcel export = statsService.exportCartForBigDataLine(productNameStr,effTimeStr,customerNumber,productOrderId, DateUtil.toDate(beginTimeStr, DateUtil.YYYY__MM__DD), DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY__MM__DD)));
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	
	
	/**
	 * 和位士-业务统计-统计图
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getCartForAndGuards")
	@ResponseBody
	public Map<String, Object> getCartForAndGuards(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		
		//对时间进行处理 如果flag为month，默认当天向前一个月；如果flag为week，默认当前时间向前一星期；
		Date beginTime=null;
		Date endTime=null;
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getBeforeMonth(new Date());
			endTime=new Date();
		}else if (flag!=null&&flag.equals("week")) {
			beginTime=DateUtil.getBeforeWeek(new Date());
			endTime=new Date();
		}else if (flag!=null&&flag.equals("all")) {
			
			beginTime=order.getEffTime();
			
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY__MM__DD);
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY__MM__DD));
			
		}
		
		 return statsService.getCartForAndGuards(customerNumber,productOrderId, beginTime, endTime);
	}
	
	
	
	
	
	/**
	 * 和位士-业务统计-统计表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getTableForAndGuards")
	@ResponseBody
	public String getTableForAndGuards(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		Integer pageIndex=(Integer) map.get("pageIndex");
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		Integer timeOrder=(Integer) map.get("timeOrder");
		Integer countOrder=(Integer) map.get("countOrder");
		
		
		//默认开始时间为订购生效时间，结束时间为当前时间或者订购结束时间
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		Date beginTime=null;
		Date endTime=null;  
		if(beginTimeStr==null||beginTimeStr.equals("")){
			beginTime=order.getEffTime();
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY__MM__DD);
		}
		if(endTimeStr==null||endTimeStr.equals("")){
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY__MM__DD));
		}
		
		return statsService.getTableForAndGuards(pageIndex, customerNumber, productOrderId, beginTime, endTime, timeOrder,countOrder);
		
	}
	
	/**
	 * 和位士-业务统计-统计表-导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportTableForAndGuards")
	@ResponseBody
	public String exportTableForAndGuards(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String effTime=(String) map.get("effTime");
		
		
		//默认开始时间为订购生效时间，结束时间为当前时间或者订购结束时间
		AdminOrder order = orderService.getOrderByProductOrderId(productOrderId);
		Date beginTime=null;
		Date endTime=null;  
		if(beginTimeStr==null||beginTimeStr.equals("")){
			beginTime=order.getEffTime();
		}else {
			beginTime=DateUtil.toDate(beginTimeStr, DateUtil.YYYY__MM__DD);
		}
		if(endTimeStr==null||endTimeStr.equals("")){
			if(order.getExpTime()!=null&&!order.getExpTime().toString().equals("0000-00-00 00:00:00")){
				endTime = order.getExpTime();
			}else{
				endTime=new Date();
			}
		}else {
			endTime=DateUtil.getMaxDayDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY__MM__DD));
		}
		
		ExportExcel export = statsService.exportTableForAndGuards(customerNumber,productOrderId, beginTime, endTime,effTime);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	
}
