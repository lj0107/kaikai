package com.qtong.afinance.module.service.bigdata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.bigdata.AuthorizationCheckDao;
import com.qtong.afinance.module.pojo.bigdata.AfinTsInteractMess;
import com.qtong.afinance.module.pojo.bigdata.AuthorizationCheck;
import com.qtong.afinance.module.pojo.bigdata.DetailRecord;
/**
 * 乾坤大数据-授权核验-授权核验列表Service层
 *
 */
@Service
@Transactional
public class AuthorizationCheckService {

	@Autowired
	private AuthorizationCheckDao authorizationCheckDao;

	/**
	 * 查询授权核验-授权核验列表
	 * @param pageData 分页参数
	 * @param orderNumber 订单编号
	 * @param customerName 客户名称
	 * @param startTime 查询下单时间的起始时间
	 * @param endTime 查询下单时间的结束时间
	 * @param minCounts 查询最小次数
	 * @param maxCounts 查询最大次数
	 * @param countsType 根据查询次数排序类型
	 * @param timeType 根据时间的排序类型
	 * @return
	 */
	public String getAuthorizationChecks(JSONObject json) {
		
		PageData pageData = new PageData();
    	
    	Object pageIndex = json.get("pageIndex");//获取当前页
    	if (pageIndex!=null && !"".equals(pageIndex)) {
			pageData.setPageIndex(Integer.parseInt(pageIndex.toString()));
		}
		
    	Object productOrderIdJson = json.get("productOrderId");//获取订单号
    	String productOrderId = null;//初始化orderNumber
    	if(productOrderIdJson!=null && !"".equals(productOrderIdJson)) {//若订单号不为空
    		productOrderId = productOrderIdJson.toString();//赋值给orderNumber
    	}
    	
    	Object customerNameJson = json.get("customerName");//获取客户名称
    	String customerName = null;//初始化customerName
    	if(customerNameJson!=null && !"".equals(customerNameJson)) {//若所客户名称不为空
    		customerName = customerNameJson.toString();//赋值给customerName
    	}
    	
    	Object startTimeJson = json.get("startTime");//获取起始时间
    	String startTime = null;//初始化startTime
    	if(startTimeJson!=null && !"".equals(startTimeJson)) {//若起始时间不为空
    		startTime = startTimeJson.toString();//赋值给startTime
    	}
    	
    	
    	Object endTimeJson = json.get("endTime");//获取结束时间
    	String endTime = null;//初始化endTime
    	if(endTimeJson!=null && !"".equals(endTimeJson)) {//若结束时间不为空
    		endTime = endTimeJson.toString()+" 23:59:59";//赋值给endTime
    	}
    	
    	
    	Object minCountsJson = json.get("minCounts");//获取最小次数
    	String minCounts = null;//初始化minCounts
    	if(minCountsJson!=null && !"".equals(minCountsJson)) {//若最小次数不为空
    		minCounts = minCountsJson.toString();//赋值给minCounts
    	}
    	
    	Object maxCountsJson = json.get("maxCounts");//获取最大次数
    	String maxCounts = null;//初始化maxCounts
    	if(maxCountsJson!=null && !"".equals(maxCountsJson)) {//若最大次数不为空
    		maxCounts = maxCountsJson.toString();//赋值给maxCounts
    	}
    	
    	Object countsTypeJson = json.get("countsType");//获取countsType
    	String countsType = "desc";//初始化countsType
    	if(countsTypeJson!=null && !"".equals(countsTypeJson)) {//若countsType不为空
    		countsType = countsTypeJson.toString();//赋值给countsType
    	}
    	
    	Object timeTypeJson = json.get("timeType");//获取timeType
    	String timeType = null;//初始化timeType
    	if(timeTypeJson!=null && !"".equals(timeTypeJson)) {//若timeType不为空
    		timeType = timeTypeJson.toString();//赋值给timeType
    	}
    	
		
		PageData queryAllCustomerOrder = authorizationCheckDao.queryAllAuthorizationCheck(pageData, productOrderId, 
				customerName, startTime, endTime, minCounts, maxCounts, countsType, timeType);
	
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(queryAllCustomerOrder, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	
	/**
	 * 获取授权核验列表	
	 * @param json
	 * @return
	 */
	public String getAllMobilell(JSONObject json) {
		
		PageData pageData = new PageData();
    	
    	Object pageIndex = json.get("pageIndex");//获取当前页
    	if (pageIndex!=null && !"".equals(pageIndex)) {
			pageData.setPageIndex(Integer.parseInt(pageIndex.toString()));
		}
		
    	Object productOrderIdJson = json.get("productOrderId");//获取订单号
    	String productOrderId = null;//初始化orderNumber
    	if(productOrderIdJson!=null && !"".equals(productOrderIdJson)) {//若订单号不为空
    		productOrderId = productOrderIdJson.toString();//赋值给orderNumber
    	}
    	
    	Object mobileJson = json.get("mobile");//获取手机号
    	String mobile = null;//初始化mobile
    	if(mobileJson!=null && !"".equals(mobileJson)) {//若起手机号不为空
    		mobile = mobileJson.toString();//赋值给mobile
    	}
    	
    	Object authCodeJson = json.get("authCode");//获取授权码
    	String authCode = null;//初始化authCode
    	if(authCodeJson!=null && !"".equals(authCodeJson)) {//若授权码不为空
    		authCode = authCodeJson.toString();//赋值给authCode
    	}
    	
    	Object startTimeJson = json.get("startTime");//获取起始时间
    	String startTime = null;//初始化startTime
    	if(startTimeJson!=null && !"".equals(startTimeJson)) {//若起始时间不为空
    		startTime = startTimeJson.toString();//赋值给startTime
    	}
    	
    	
    	Object endTimeJson = json.get("endTime");//获取结束时间
    	String endTime = null;//初始化endTime
    	Date maxDayDateTime = DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date()));//获取昨天最大时间
    	if(endTimeJson!=null && !"".equals(endTimeJson) ) {//若结束时间不为空
    		Date endDate = DateUtil.toDate(endTimeJson.toString(),DateUtil.YYYY__MM__DD_HH_MM_SS);
    		if(endDate.getTime() < maxDayDateTime.getTime()) {//如果查询时间小于昨天最大时间
    			endTime = endTimeJson.toString();//赋值给endTime
    		}else {
    			endTime=DateUtil.toStr(maxDayDateTime,DateUtil.YYYY__MM__DD_HH_MM_SS);//大于昨天时间  把昨天最大时间赋值给查询结束时间
    		}
    	}else {
    		endTime=DateUtil.toStr(maxDayDateTime,DateUtil.YYYY__MM__DD_HH_MM_SS);
    	}
    	
    	PageData queryAllMobile = authorizationCheckDao.queryAllMobile(pageData, productOrderId, mobile, authCode, startTime, endTime);
    	
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(queryAllMobile, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	
	/**
	 * 根据产品订购关系id产看详情
	 * 
	 */
	public String queryOne(String requestRefId) {
		List<DetailRecord> detailRecords = authorizationCheckDao.queryOne(requestRefId);
		
		DetailRecord detailRecord = new DetailRecord();
		
		String productNameStr="";
		String usermarkValueStr="";
		for (DetailRecord detailRecord1 : detailRecords) {//遍历list 拼接Boss产品名称
			
			detailRecord = detailRecord1;
			
			String productName = detailRecord1.getProductName();
			productNameStr+=productName+"<br/>";
			
			usermarkValueStr+=detailRecord1.getUsermarkValue()+"<br/>";
		}
		
		detailRecord.setProductName(productNameStr);
		detailRecord.setUsermarkValue(usermarkValueStr);
		
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(detailRecord, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	/**
	 * 根据产品订购关系id导出详情
	 * 
	 */
	public List<Map<String, String>> exportOne(String requestRefId,String productOrderId,String customerName,String productName) {
		List<Map<String, String>> result=new ArrayList<>();
		
		List<DetailRecord> detailRecords = authorizationCheckDao.queryOne(requestRefId);
		
		DetailRecord detailRecord = new DetailRecord();
		
		String productNameStr="";
		String usermarkValueStr="";
		for (DetailRecord detailRecord1 : detailRecords) {//遍历list 拼接Boss产品名称
			
			detailRecord = detailRecord1;
			
			String name = detailRecord1.getProductName();
			productNameStr+=name+";";
			
			usermarkValueStr+=detailRecord1.getUsermarkValue()+";";
			
			
			
		}
		
		detailRecord.setProductName(productNameStr);
		detailRecord.setUsermarkValue(usermarkValueStr);
		
		Map<String, String> map=null;
		map=new HashMap<>();
		map.put("key", "订单信息");
		map.put("value", "");
		result.add(map);
		map=new HashMap<>();
		map.put("key", "订单号");
		map.put("value", productOrderId);
		result.add(map);
		map=new HashMap<>();
		map.put("key", "客户名称");
		map.put("value", customerName);
		result.add(map);
		map=new HashMap<>();
		map.put("key", "产品名称");
		map.put("value", productName);
		result.add(map);
		
		map=new HashMap<>();
		map.put("key", "请求参数");
		map.put("value", "");
		result.add(map);
		map=new HashMap<>();
		map.put("key", "数字签名");
		map.put("value", detailRecord.getSignature());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "请求时间");
		map.put("value", DateUtil.toStr(detailRecord.getRequestTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
		result.add(map);
		//map=new HashMap<>();
		//map.put("key", "用户手机号码");
		//map.put("value", detailRecord.getMobile());
		//result.add(map);
		map=new HashMap<>();
		map.put("key", "验证码");
		map.put("value", detailRecord.getAuthCode());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "请求标签");
		map.put("value", detailRecord.getProductName());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "返回参数");
		map.put("value", "");
		result.add(map);
		map=new HashMap<>();
		map.put("key", "返回时间");
		map.put("value", DateUtil.toStr(detailRecord.getResponseTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
		result.add(map);
		map=new HashMap<>();
		map.put("key", "返回标签");
		map.put("value", detailRecord.getUsermarkValue());
		result.add(map);
		
		
		return result;
	}
	
	
	
	/**
	 * 导出授权核验
	 * @param json
	 * @return
	 */
	public ExportExcel exportExcel(String productOrderId ,String customerName,String startTime ,String endTime,String minCounts,String maxCounts) {
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","订单号","下单时间","客户名称","产品名称","请求次数"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//根据条件查询所有授权核验
		List<AuthorizationCheck> queryAllCustomerOrder = authorizationCheckDao.exportExcel(productOrderId, 
				customerName, startTime, endTime, minCounts, maxCounts);
		int index=1;
		for (AuthorizationCheck authorizationCheck : queryAllCustomerOrder) {
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = authorizationCheck.getProductOrderId();
			objs[2] = DateUtil.toStr(authorizationCheck.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			objs[3] = authorizationCheck.getCustomerName();
			objs[4] = authorizationCheck.getProductName();
			objs[5] = authorizationCheck.getSelCounts();
			dataList.add(objs);
			
			index++;
		}
		
		return new ExportExcel("授权核验", rowsName, dataList);
	}

	/**
	 * 导出授权核验-授权码
	 * @param json
	 * @return
	 */
	public ExportExcel exportExcelAll(String productOrderId,String productName,String customerName, String mobile, String authCode,
			String startTime, String endTime) {
		//导出数据录入
		String[] rowsName = new String[]{"序号","授权码","请求时间"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		
		
		
		Date maxDayDateTime = DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date()));//获取昨天最大时间
    	if(endTime!=null && !"".equals(endTime) ) {//若结束时间不为空
    		Date endDate = DateUtil.toDate(endTime.toString(),DateUtil.YYYY__MM__DD_HH_MM_SS);
    		if(endDate.getTime() < maxDayDateTime.getTime()) {//如果查询时间小于昨天最大时间
    			endTime = endTime.toString();//赋值给endTime
    		}else {
    			endTime=DateUtil.toStr(maxDayDateTime,DateUtil.YYYY__MM__DD_HH_MM_SS);//大于昨天时间  把昨天最大时间赋值给查询结束时间
    		}
    	}else {
    		endTime=DateUtil.toStr(maxDayDateTime,DateUtil.YYYY__MM__DD_HH_MM_SS);
    	}
		
		
		//根据条件查询所有授权核验
		List<AfinTsInteractMess> exportExcelAll = authorizationCheckDao.exportExcelAll(productOrderId, mobile, authCode, startTime, endTime);
				
		int index=1;
		for (AfinTsInteractMess afinTsInteractMess : exportExcelAll) {
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			//objs[1] = afinTsInteractMess.getMobile();
			objs[1] = afinTsInteractMess.getAuthCode();
			objs[2] = DateUtil.toStr(afinTsInteractMess.getRequestTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			
			dataList.add(objs);
			
			index++;
		}
		
		String string = "订单号："+productOrderId+"   客户名称："+customerName+"   产品名称："+productName;
		
		return new ExportExcel(string,"授权核验列表", rowsName, dataList);
	}

}
