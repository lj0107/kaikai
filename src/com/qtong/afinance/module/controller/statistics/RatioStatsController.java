package com.qtong.afinance.module.controller.statistics;

import java.util.Date;
import java.util.HashMap;
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
import com.qtong.afinance.module.service.statistics.RatioStatsService;


@Controller
@RequestMapping("/ratioStats")
public class RatioStatsController {

	@Autowired
	private RatioStatsService ratioStatsService;
	
			
	/**
	 * 饼图
	 * @return
	 */
	@RequestMapping("/ratioPie")
	@ResponseBody
	public Map ratioPie(HttpServletRequest request,HttpServletResponse response){
		Map mapresult =new HashMap();
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);		
		String customer=(String) map.get("customer");
		String productType=(String) map.get("productType");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");				
		//处理时间
		Date beginTime=null;
		Date endTime=null;		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-2);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-11);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else {
			beginTime=DateUtil.getInitMonth(DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM));
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
			
		}
		
		return ratioStatsService.ratioPie(customer, productType, beginTime, endTime);

	}
	
	@RequestMapping("/exportPie")
	@ResponseBody
	public String exportPie(HttpServletRequest request,HttpServletResponse response){
		Map mapresult =new HashMap();
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);		
		String customer=(String) map.get("customer");
		String productType=(String) map.get("productType");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");				
		//处理时间
		Date beginTime=null;
		Date endTime=null;		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-2);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-11);
			endTime=DateUtil.getMaxMonthDateTime(new Date());
		}else {
			beginTime=DateUtil.getInitMonth(DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM));
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
			
		}
		
		ExportExcel export = ratioStatsService.exportPie(customer, productType, beginTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	/**
	 * 查1级产品类别
	 * @return
	 */
	@RequestMapping("/selProductTypeLev1")
	@ResponseBody
	public String selProductTypeLev1(){
		List list = ratioStatsService.selProductTypeLev1();
		String json = JSON.toJSONString(list);
		return json;
	}
	
	/**
	 * 各业务合作伙伴分成-柱状图
	 * @return
	 */
	@RequestMapping("/getRationByPartner")
	@ResponseBody
	public Map<String, Object> getRationByPartner(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String partnerId=(String) map.get("partnerId");
		String customerNumber=(String) map.get("customerNumber");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		
		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-2);
			endTime=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD),DateUtil.YYYY_MM_DD);
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-11);
			endTime=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD),DateUtil.YYYY_MM_DD);
		}else {
			beginTime=DateUtil.getInitMonth(DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM));
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
			
		}
		
		
		 return ratioStatsService.getPartnerDivide(partnerId, customerNumber, beginTime, endTime);
	}
	
	
	
	/**
	 * 各业务合作伙伴分成-柱状图-导出
	 * @return
	 */
	@RequestMapping("/exportRationByPartner")
	@ResponseBody
	public String exportRationByPartner(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String partnerId=(String) map.get("partnerId");
		String customerNumber=(String) map.get("customerNumber");
		String beginTimeStr=(String) map.get("beginTime");
		String endTimeStr=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		//处理时间
		Date beginTime=null;
		Date endTime=null;
		
		
		//对时间进行处理 如果flag为month，默认为近三个月；如果flag为year，默认经一年；
		if(flag!=null&&flag.equals("month")){
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-2);
			endTime=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD),DateUtil.YYYY_MM_DD);
		}else if (flag!=null&&flag.equals("year")) {
			beginTime=DateUtil.getPerFirstDayOfMonth(DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD),-11);
			endTime=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD),DateUtil.YYYY_MM_DD);
		}else {
			beginTime=DateUtil.getInitMonth(DateUtil.toDate(beginTimeStr, DateUtil.YYYY_MM));
			endTime=DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTimeStr, DateUtil.YYYY_MM));
			
		}
		
		ExportExcel export = ratioStatsService.exportPartnerDivide(partnerId, customerNumber, beginTime, endTime);
		String result=JSON.toJSONString(export);
		return result;
	}
	
}
