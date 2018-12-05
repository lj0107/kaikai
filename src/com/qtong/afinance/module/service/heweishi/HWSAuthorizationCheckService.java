package com.qtong.afinance.module.service.heweishi;

import java.util.ArrayList;
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
import com.qtong.afinance.module.dao.heweishi.HWSAuthorizationCheckDao;
import com.qtong.afinance.module.pojo.bigdata.AuthorizationCheck;
import com.qtong.afinance.module.pojo.heweishi.LbCityAuthorization;
import com.qtong.afinance.module.pojo.heweishi.LbCityOrder;

/**
 * 乾坤大数据-授权核验-授权核验列表Service层
 *
 */
@Service
@Transactional
public class HWSAuthorizationCheckService {

	@Autowired
	private HWSAuthorizationCheckDao hwsAuthorizationCheckDao;

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
    	
		
		PageData queryAllCustomerOrder = hwsAuthorizationCheckDao.queryAllAuthorizationCheck(pageData, productOrderId, 
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
    	Object mobileJson = json.get("mobile");//获取手机号
    	String mobile = null;//初始化mobile
    	if(mobileJson!=null && !"".equals(mobileJson)) {//若起手机号不为空
    		mobile = mobileJson.toString();//赋值给mobile
    	}
    	Object proIdJson = json.get("product_order_id");//获取手机号
    	String proId = null;//初始化mobile
    	if(proIdJson!=null && !"".equals(proIdJson)) {//若起手机号不为空
    		proId = proIdJson.toString();//赋值给mobile
    	}
    	
    	Object startTimeJson = json.get("startTime");//获取起始时间
    	String startTime = null;//初始化startTime
    	if(startTimeJson!=null && !"".equals(startTimeJson)) {//若起始时间不为空
    		startTime = startTimeJson.toString();//赋值给startTime
    	}
    	
    	
    	Object endTimeJson = json.get("endTime");//获取结束时间
    	String endTime = null;//初始化endTime
    	if(endTimeJson!=null && !"".equals(endTimeJson)) {//若结束时间不为空
    		endTime = endTimeJson.toString();//赋值给endTime
    	}
    	
    	PageData queryAllMobile = hwsAuthorizationCheckDao.queryAllMobile(pageData, proId,mobile, startTime, endTime);
    	JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    	String jsonString = JSON.toJSONString(queryAllMobile,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}

	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
	public String getAuthorizationCheck(String id) {
		List<LbCityAuthorization> authorizationChecks = hwsAuthorizationCheckDao.getAuthorizationCheck(id);
		LbCityAuthorization lbCityAuthorization = new LbCityAuthorization();
		
		String cityCodes = "[";
		for (LbCityAuthorization lbCityAuthorization2 : authorizationChecks) {
			lbCityAuthorization = lbCityAuthorization2;
			cityCodes += "{\"citycode\":\""+lbCityAuthorization2.getCityCodes()+"\"},";
		}
		String substring = cityCodes.substring(0, cityCodes.length()-1);
		substring += "]";
		
		lbCityAuthorization.setCityCodes(substring);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(lbCityAuthorization, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	/**
	 * 导出详情
	 * @param id
	 * @return
	 */
	public List<Map<String, String>> exportAuthorizationCheck(String id,String customerName) {
		List<Map<String, String>> result=new ArrayList<>();
		
		List<LbCityAuthorization> authorizationChecks = hwsAuthorizationCheckDao.getAuthorizationCheck(id);
		LbCityAuthorization lbCityAuthorization = new LbCityAuthorization();
		
		String cityCodes = "[";
		for (LbCityAuthorization lbCityAuthorization2 : authorizationChecks) {
			lbCityAuthorization = lbCityAuthorization2;
			cityCodes += "{\"citycode\":\""+lbCityAuthorization2.getCityCodes()+"\"},";
		}
		String substring = cityCodes.substring(0, cityCodes.length()-1);
		substring += "]";
		
		lbCityAuthorization.setCityCodes(substring);
		
		Map<String, String> map=null;
		map=new HashMap<>();
		map.put("key", "客户名称");
		map.put("value", customerName);
		result.add(map);
		map=new HashMap<>();
		map.put("key", "请求参数");
		map.put("value", "");
		result.add(map);
		map=new HashMap<>();
		map.put("key", "功能标识");
		map.put("value", lbCityAuthorization.getActionCode());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "请求时间");
		map.put("value", DateUtil.toStr(lbCityAuthorization.getRequestTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		result.add(map);
		map=new HashMap<>();
		map.put("key", "业务名称");
		map.put("value", lbCityAuthorization.getUserName());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "密码	");
		map.put("value", lbCityAuthorization.getPwd());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "请求流水号");
		map.put("value", lbCityAuthorization.getRequestRefId());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "手机号");
		map.put("value", lbCityAuthorization.getMobile());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "城市区号集合");
		map.put("value", lbCityAuthorization.getCityCodes());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "需要发送的短信内容");
		map.put("value", lbCityAuthorization.getMsgContent());
		result.add(map);
		map=new HashMap<>();
		map.put("key", "返回参数");
		map.put("value", "");
		result.add(map);
		map=new HashMap<>();
		map.put("key", "返回时间");
		map.put("value", DateUtil.toStr(lbCityAuthorization.getResponseTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
		result.add(map);
		map=new HashMap<>();
		map.put("key", "比对结果");
		map.put("value", lbCityAuthorization.getResult());
		result.add(map);
		map=new HashMap<>();
		if(lbCityAuthorization.getResult().equals("成功")){
			map.put("key", "定位的城市编码");
			map.put("value", lbCityAuthorization.getCityCode());
			result.add(map);
		}else {
			map.put("key", "错误信息");
			map.put("value", lbCityAuthorization.getErrorDesc());
			result.add(map);
			
		}
		map=new HashMap<>();
		map.put("key", "发送短信返回码");
		map.put("value", lbCityAuthorization.getMsgSendCode());
		result.add(map);
			
		return result;
		
		
	}

	/**
	 * 导出和卫士授权核验
	 * @param productOrderId
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @param minCounts
	 * @param maxCounts
	 * @return
	 */
	public ExportExcel exportAllAuthorizationCheck(String productOrderId,String customerName, String startTime, String endTime, String minCounts, String maxCounts) {
    	//导出数据录入
		String[] rowsName = new String[]{"序号","订单号","下单时间","产品名称","客户名称","请求次数"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//根据条件查询所有授权核验
		List<AuthorizationCheck> exportExcel = hwsAuthorizationCheckDao.exportExcel(productOrderId, customerName, startTime, endTime, minCounts, maxCounts);
				
		int index=1;
		for (AuthorizationCheck authorizationCheck : exportExcel) {
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = authorizationCheck.getProductOrderId();
			objs[2] = DateUtil.toStr(authorizationCheck.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			objs[3] = authorizationCheck.getProductName();
			objs[4] = authorizationCheck.getCustomerName();
			objs[5] = authorizationCheck.getSelCounts();
			
			dataList.add(objs);
			
			index++;
		}
		
		return new ExportExcel("和位士-授权核验", rowsName, dataList);
	}

	

	/**
	 * 导出和位士授权核验列表
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportAllMobile(String proId,String mobile,String startTime,String endTime) {
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","用户手机号","标签名称","请求时间"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//根据条件查询所有授权核验
		List<LbCityOrder> exportAllMobile = hwsAuthorizationCheckDao.exportAllMobile(proId, mobile, startTime, endTime);
				
		int index=1;
		for (LbCityOrder lbCityOrder : exportAllMobile) {
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = lbCityOrder.getMobile();
			objs[2] = lbCityOrder.getProductName();
			objs[3] = DateUtil.toStr(lbCityOrder.getRequestTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			
			dataList.add(objs);
			
			index++;
		}
		
		return new ExportExcel("和位士-授权核验列表", rowsName, dataList);
	}
	

	
	
}
