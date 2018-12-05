package com.qtong.afinance.module.service.heweishi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.heweishi.HWSWhiteListDao;
import com.qtong.afinance.module.pojo.heweishi.WhiteList;
import com.qtong.afinance.module.pojo.heweishi.WhiteListMobile;

/**
 * 和卫士-白名单service
 *
 */
@Service
@Transactional
public class HWSWhiteListService {

	@Autowired
	private HWSWhiteListDao hwsWhiteListDao;
	
	
	/**
	 * 白名单列表
	 * @param pageIndex 当前页
	 * @param customerNumber 客户编码
	 * @param customerName 客户名称
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String queryAll(String pageIndex,String customerNumber,String customerName,String startTime,String endTime) {
		
		PageData pageData=new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		
		pageData =  hwsWhiteListDao.queryAll(pageData, customerNumber, customerName, startTime, endTime);
		
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	
	}
	
	/**
	 * 和卫士-手机号列表 
	 * @param pageIndex 当前页
	 * @param account 帐号
	 * @param mobile 手机号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String queryAllMobile(String pageIndex,String account,String mobile,String startTime,String endTime) {
		PageData pageData=new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		pageData = hwsWhiteListDao.queryAllMobile(pageData, account, mobile, startTime, endTime);
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	}

	
	/**
	 * 导出所有白名单
	 * @param customerNumber
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportQueryAll(String customerNumber, String customerName, String startTime,
			String endTime) {
		
		List<Map<String,Object>> result=new ArrayList<>();
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","客户编号","客户名称","白名单手机号码数目(个)","更新时间"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//根据条件查询所有白名单
		List<WhiteList> exportQueryAll = hwsWhiteListDao.exportQueryAll(customerNumber, customerName, startTime, endTime);
		int index=1;
		for (WhiteList whiteList : exportQueryAll) {
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = whiteList.getCustomerNumber();
			objs[2] = whiteList.getCustomerName();
			int counts = 0;
			if(whiteList.getCounts()!=null) 
				counts = Integer.parseInt(whiteList.getCounts());
			objs[3] = counts;
			objs[4] = DateUtil.toStr(whiteList.getRequestTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			dataList.add(objs);
			
			index++;
		}
		
		return new ExportExcel("白名单", rowsName, dataList);
		
		
	}
	
	
	/**
	 * 导出所有白名单手机号
	 * @param account
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportWhiteListMobile(String account, String mobile, String startTime, String endTime) {
		
		List<Map<String,Object>> result=new ArrayList<>();
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","用户手机号","更新时间"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//根据条件查询所有白名单手机号
		List<WhiteListMobile> exportWhiteListMobile = hwsWhiteListDao.exportWhiteListMobile(account, mobile, startTime, endTime);
		int index=1;
		for (WhiteListMobile whiteListMobile : exportWhiteListMobile) {
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = whiteListMobile.getMobile();
			objs[2] = DateUtil.toStr(whiteListMobile.getInsertTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			dataList.add(objs);

			index++;
		}
		
		return new ExportExcel("白名单手机号", rowsName, dataList);
		
		
	}
	
}
