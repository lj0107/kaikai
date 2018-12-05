package com.qtong.afinance.module.service.statistics;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.dao.statistics.UrlGuardStatsDao;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCount;
import com.qtong.afinance.module.pojo.statistics.UrlGuardStats;
/**
 * 网址卫士业务统计
 */
@Transactional
@Service
public class UrlGuardStatsService {
	@Autowired
	private UrlGuardStatsDao urlGuardStatsDao;
	
	@Autowired
	private OrderStatsDao orderStatsDao;
	
	
	public List<Map<String, Object>> selectType(){
		 List<Map<String, Object>> selectCustomerCount = orderStatsDao.selectType();
		return selectCustomerCount;
	}
	/**
	 * 查询客户次数统计信息（按天）
	 * @param orderStatistical
	 * @return
	 */
	public  ArrayList<OrderCustomerCount>  selectCustomerCount(String i,String type_name,Date date,String beginTime,String endTimae){
		 List<Map<String, Object>> selectCustomerCount = orderStatsDao.selectCustomerCount(i,beginTime,endTimae);
		 ArrayList<OrderCustomerCount> arrayList = new ArrayList<OrderCustomerCount>();
		 for (Map<String, Object> map : selectCustomerCount) {
			String customer_number = map.get("customer_number").toString();
			String pro_name = map.get("pro_name").toString();
			OrderCustomerCount orderCustomerCount = new OrderCustomerCount();
			orderCustomerCount.setCustomerNumber(customer_number);
			orderCustomerCount.setProName(pro_name);
			orderCustomerCount.setProductName(type_name);			
			orderCustomerCount.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
			arrayList.add(orderCustomerCount);
		}
		return arrayList;
	}
	/**
	 * 添加新的订购客户次数统计信息（按天）
	 * @param orderStatistical
	 * @return
	 */
	public void insertCustomerCount(List<OrderCustomerCount> list){
			 orderStatsDao.insertCustomerCount(list);
	}
	
	/**
	 * 添加新的统计信息（按天）
	 * @param orderStatistical
	 * @return
	 */
	public int insert(UrlGuardStats stats){
		return urlGuardStatsDao.insert(stats);
	}
	
	
	
	/**
	 * 根据客户编码查询出要展示的统计数据(统计图)-网址卫士
	 * 展示请求次数的趋势图
	 * @return
	 */
	public Map<String, Object> getCart(String customerNumber,Date beginTime,Date endTime){
		Map<String, Object> result=new HashMap<String, Object>(); 
		
		
		//1、前台展示类型集合
		List<String> list=new ArrayList<>();
		list.add("101");//发现恶意网址
		list.add("104");//确认恶意网址
		list.add("106");//拦截恶意网址
		
		
		
		
		
		//2、自定义数据类型
		List<String> date=new ArrayList<String>();//坐标横轴日期数据
		List<Map<String, Object>> lineCart=new ArrayList<>();//折线图最终返回数据 [{"name":"和位士","value":[21,31,22]}]
		
		
		//获取日期数组（横坐标）
		Date date1=beginTime;
		Date date2=endTime;
		while (date1.getTime()<=date2.getTime()) {
			date.add(DateUtil.toStr(date1,DateUtil.MMDD));
			date1=DateUtil.getLaterDay(date1);
		}
		result.put("date", date);
		
		//3、跌代出要统计的数据
		for (String state : list) {
			List<UrlGuardStats> urlGuardStats2 = urlGuardStatsDao.getUrlGuardStats(customerNumber,state, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			
			
			//数据加工，修复
			Date date11=beginTime;
			Date date22=endTime;
			List<UrlGuardStats> urlGuardStats=new ArrayList<>();
			while (date11.getTime()<=date22.getTime()) {
				boolean flag=true;
				for (UrlGuardStats sats : urlGuardStats2) {
					String recordTime=sats.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						urlGuardStats.add(sats);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					UrlGuardStats sats=new UrlGuardStats();
					sats.setCount(0);
					urlGuardStats.add(sats);
				}
				
				date11=DateUtil.getLaterDay(date11);
			}
			
			List<Integer> lists=new ArrayList<>();
			for (UrlGuardStats stats : urlGuardStats) {
				lists.add(stats.getCount());
			}
			

			//对统计数据进行修复处理【当用户查看的时间段，超过统计时间之前，那么之前的请求次数应该都为0】
	    	int gap = date.size()-lists.size();
	    	List<Integer> temp=new ArrayList<Integer>();
	    	for (int j = 0; j < gap; j++) {
	    		temp.add(0);
			}
	    	temp.addAll(lists);
	    	
			
			Map<String,Object> map=new HashMap<>();
			if(state.equals("101")){//发现恶意网址
				map.put("name", "发现恶意网址");
			}else if (state.equals("104")) {//确认恶意网址
				map.put("name", "确认恶意网址");
				
			}else if (state.equals("106")) {//拦截恶意网址
				map.put("name", "拦截恶意网址");
				
			}
			
	    	
	    	map.put("value", lists);
	    	lineCart.add(map);
			
		}
		
		
		//返回最终数据
		result.put("lineCart", lineCart);
		
		return result;
	}


	/**
	 * 获取统计图相关数据-导出
	 * @return
	 */
	public ExportExcel exportCart(String customerNumber, Date beginTime,Date endTime) {
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","类型","日期","网址数量"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//1、前台展示类型集合
		List<String> list=new ArrayList<>();
		list.add("101");//发现恶意网址
		list.add("104");//确认恶意网址
		list.add("106");//拦截恶意网址
		
		
		//3、跌代出要统计的数据
		for (String state : list) {
			List<UrlGuardStats> urlGuardStats2 = urlGuardStatsDao.getUrlGuardStats(customerNumber,state, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			
			//数据加工，修复
			Date date11=beginTime;
			Date date22=endTime;
			List<UrlGuardStats> urlGuardStats=new ArrayList<>();
			while (date11.getTime()<=date22.getTime()) {
				boolean flag=true;
				for (UrlGuardStats sats : urlGuardStats2) {
					String recordTime=sats.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						urlGuardStats.add(sats);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					UrlGuardStats sats=new UrlGuardStats();
					sats.setState(state);
					sats.setCount(0);
					sats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS)));  
					urlGuardStats.add(sats);
				}
				
				
				date11=DateUtil.getLaterDay(date11);
			}
			
			for (int i = 0; i < urlGuardStats.size(); i++) {
				UrlGuardStats stats=urlGuardStats.get(i);
				//封装信息
				objs=new Object[rowsName.length];
				objs[0] = i+1;
				
				if(state.equals("101")){//发现恶意网址
					objs[1] = "发现恶意网址";
				}else if (state.equals("104")) {//确认恶意网址
					objs[1] = "确认恶意网址";
				}else if (state.equals("106")) {//拦截恶意网址
					objs[1] = "拦截恶意网址";
				}
				
				
				objs[2] = DateUtil.toStr(DateUtil.toDate(stats.getRecordTime().toString().substring(0, 10), DateUtil.YYYY_MM_DD), DateUtil.YYYY__MM__DD);
				objs[3] = stats.getCount();
				dataList.add(objs);
			}
			
		}
		
		
		return new ExportExcel("网址卫士", rowsName, dataList);
	}
}
