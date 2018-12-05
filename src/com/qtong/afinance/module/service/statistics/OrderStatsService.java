package com.qtong.afinance.module.service.statistics;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.portal.AdminOrderDao;
import com.qtong.afinance.module.dao.portal.AdminOrderSubDao;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.product.AdminOrder;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.statistics.OrderStats;
/**
 * 业务统计Service
 *
 */
@Transactional
@Service
public class OrderStatsService {
	
	@Autowired 
	private OrderStatsDao statsDao;
	@Autowired
	private AdminOrderDao orderDao;
	@Autowired
	private AdminOrderSubDao orderSubDao;
	
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据(统计表)
	 * @return
	 */
	public Map<String, Object> getBill(String productOrderId,Date billTime){
		
		Map<String, Object> result=new HashMap<>();
		
		DecimalFormat df=new DecimalFormat("0.00");
		
		//假设可以根据账单号，查出订单号，我们这里查出的订单号为，333（和位士）
		
		List<Map<String,Object>> dataTemp=new ArrayList<>();
		List<List<OrderStats>> listTemp=new ArrayList<>();
		List<String> tableHead=new ArrayList<>();
		List<Integer> sumCountList=new ArrayList<>();
		
		tableHead.add("日");
		
		//根据产品订购Id获取所有产品信息（标签）
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proname = product.getProName();//产品名称
			
			//获取统计信息【当月账单】
			List<OrderStats> orderStats2 = statsDao.getOrderStatsByProductOrderId( productOrderId, proCode, DateUtil.toStr(DateUtil.getInitMonth(billTime), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(DateUtil.getMaxMonthDateTime(billTime), DateUtil.YYYY_MM_DD_HH_MM_SS));
			//数据加工
			
			
			//获取日期数组（横坐标）,数据修复
			Date date1=DateUtil.getInitMonth(billTime);
			Date date2=DateUtil.getMaxMonthDateTime(billTime);
			List<OrderStats> orderStats=new ArrayList<>();
			while (date1.getTime()<=date2.getTime()) {
				boolean flag=true;
				for (OrderStats order : orderStats2) {
					String recordTime=order.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date1, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						orderStats.add(order);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					OrderStats order=new OrderStats();
					order.setCount(0);
					order.setFee(0.00);
					orderStats.add(order);
				}
				
				date1=DateUtil.getLaterDay(date1);
			}
			
			
			
			//计算总使用次数
			int sumCount=0;
			for (OrderStats stats : orderStats) {
				sumCount+=stats.getCount();
			}
			
			//封装表头信息
			tableHead.add(proname+"请求（次）");
			
			listTemp.add(orderStats);
			sumCountList.add(sumCount);
			
			
		}
		//加上请求总计和金额
		tableHead.add("标签总请求（次）");
		tableHead.add("标签总金额（元）");
		
		
		
		//初始化日期大小
		for (int i = 0; i <= listTemp.get(0).size(); i++) {
			Map<String, Object> map=new HashMap<>();
			List<Integer> list=new ArrayList<>();
			if(i<listTemp.get(0).size()){
				map.put("date", i+1);
				map.put("countList", list);
				dataTemp.add(map);
			}else {//加上总计栏
				map.put("date", "合计");
				map.put("countList", list);
				dataTemp.add(map);
			}
		}
		
		
		

		//实例化表格信息
		for (int i = 0; i < dataTemp.size();i++){
			if(i<(dataTemp.size()-1)){//放基本数据
				//一排一排放日期数据
				List<Object> list = (List<Object>) dataTemp.get(i).get("countList");
				int allCount=0;
				Double allFee=0.00;
				for (int j = 0; j < listTemp.size()+2; j++) {//放标签、总请求数、总金额数
					if(j==0){//初始化计数器
						allCount=0;
						allFee=0.00;
					}
					
					if(j<listTemp.size()){//放标签
						Integer count = listTemp.get(j).get(i).getCount();
						list.add(count);
						allCount+=count;
						allFee+=listTemp.get(j).get(i).getFee();
					}else if (j==listTemp.size()) {//放总请求数
						list.add(allCount);
					}else if (j==(listTemp.size()+1)) {//放总金额
						list.add(df.format(allFee));
						
					}
					
				}
				
				dataTemp.get(i).put("countList", list);
			}else {//放总计栏
				List<Object> list = (List<Object>) dataTemp.get(i).get("countList");
				
				//先总计标签部分，把总请求次数和总金额也计算出来
				int allCount=0;
				Double allFee=0.00;
				for (int j = 0; j < listTemp.size(); j++) {
					int count=0;
					for (OrderStats stats : listTemp.get(j)) {
						count+=stats.getCount();
						allFee+=stats.getFee();
					}
					list.add(count);
					allCount+=count;
				}
				
				//放总请求次数
				
				list.add(allCount);
				list.add(df.format(allFee));
				
				
				dataTemp.get(i).put("countList", list);
			}
			
		}
		
		
		
		result.put("tableHead", tableHead);
		result.put("tableBody", dataTemp);
		
		
		return result;
	}
	
	
	
	
	
	/**  
	 * 控制台-账单-查看账单-导出
	 * @author SY
	 */
	public ExportExcel exportBill(String billNumber,String productName,String productOrderId, Date billTime) {
		
		//假设可以根据账单号，查出订单号，我们这里查出的订单号为，333（和位士）
		List<Map<String,Object>> dataTemp=new ArrayList<>();
		List<List<OrderStats>> listTemp=new ArrayList<>();
		List<String> tableHead=new ArrayList<>();
		List<Integer> sumCountList=new ArrayList<>();
		
		tableHead.add("日");
		DecimalFormat df=new DecimalFormat("0.00");
		//根据产品订购Id获取所有产品信息（标签）
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proname = product.getProName();//产品名称
			
			//获取统计信息【当月账单】
			List<OrderStats> orderStats2 = statsDao.getOrderStatsByProductOrderId( productOrderId, proCode, DateUtil.toStr(DateUtil.getInitMonth(billTime), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(DateUtil.getMaxMonthDateTime(billTime), DateUtil.YYYY_MM_DD_HH_MM_SS));
			//数据加工
			
			
			//获取日期数组（横坐标）,数据修复
			Date date1=DateUtil.getInitMonth(billTime);
			Date date2=DateUtil.getMaxMonthDateTime(billTime);
			List<OrderStats> orderStats=new ArrayList<>();
			while (date1.getTime()<=date2.getTime()) {
				boolean flag=true;
				for (OrderStats order : orderStats2) {
					String recordTime=order.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date1, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						orderStats.add(order);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					OrderStats order=new OrderStats();
					order.setCount(0);
					order.setFee(0.00);
					orderStats.add(order);
				}
				
				date1=DateUtil.getLaterDay(date1);
			}
			
			
			
			//计算总使用次数
			int sumCount=0;
			for (OrderStats stats : orderStats) {
				sumCount+=stats.getCount();
			}
			
			//封装表头信息
			tableHead.add(proname+"请求（次）");
			
			listTemp.add(orderStats);
			sumCountList.add(sumCount);
			
			
		}
		//加上请求总计和金额
		tableHead.add("标签总请求（次）");
		tableHead.add("标签总金额（元）");
		
		
		
		//初始化日期大小
		for (int i = 0; i <= listTemp.get(0).size(); i++) {
			Map<String, Object> map=new HashMap<>();
			List<Integer> list=new ArrayList<>();
			if(i<listTemp.get(0).size()){
				map.put("date", i+1);
				map.put("countList", list);
				dataTemp.add(map);
			}else {//加上总计栏
				map.put("date", "合计");
				map.put("countList", list);
				dataTemp.add(map);
			}
		}
		
		
		

		//实例化表格信息
		for (int i = 0; i < dataTemp.size();i++){
			if(i<(dataTemp.size()-1)){//放基本数据
				//一排一排放日期数据
				List<Object> list = (List<Object>) dataTemp.get(i).get("countList");
				int allCount=0;
				Double allFee=0.00;
				for (int j = 0; j < listTemp.size()+2; j++) {//放标签、总请求数、总金额数
					if(j==0){//初始化计数器
						allCount=0;
						allFee=0.00;
					}
					
					if(j<listTemp.size()){//放标签
						Integer count = listTemp.get(j).get(i).getCount();
						list.add(count);
						allCount+=count;
						allFee+=listTemp.get(j).get(i).getFee();
					}else if (j==listTemp.size()) {//放总请求数
						list.add(allCount);
					}else if (j==(listTemp.size()+1)) {//放总金额
						list.add(allFee);
						
					}
					
				}
				
				dataTemp.get(i).put("countList", list);
			}else {//放总计栏
				List<Object> list = (List<Object>) dataTemp.get(i).get("countList");
				
				//先总计标签部分，把总请求次数和总金额也计算出来
				int allCount=0;
				Double allFee=0.00;
				for (int j = 0; j < listTemp.size(); j++) {
					int count=0;
					for (OrderStats stats : listTemp.get(j)) {
						count+=stats.getCount();
						allFee+=stats.getFee();
					}
					list.add(count);
					allCount+=count;
				}
				
				//放总请求次数
				
				list.add(allCount);
				list.add(allFee);
				
				
				dataTemp.get(i).put("countList", list);
			}
			
		}
		
		
		
		//数据导出
		String[] rowsName=new String[tableHead.size()];
		tableHead.toArray(rowsName);
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		
		for (int j = 0; j < dataTemp.size(); j++) {
			Map<String, Object> map = dataTemp.get(j);
			//封装信息
			objs = new Object[rowsName.length];
			
			for (int k = 0; k < rowsName.length; k++) {
				if(k==0){
					objs[0] = map.get("date");
				}else {
					List<Object> list = (List<Object>) map.get("countList");
					if(k==(rowsName.length-1)){
						objs[k]=df.format(list.get(k-1));
					}else {
						objs[k]=list.get(k-1);
					}
					
				}
			}
			
			dataList.add(objs);
			
		}
		
		return new ExportExcel("账单		账单号："+billNumber+"   订单号："+productOrderId+"   产品名称："+productName,"账单", rowsName, dataList);
	}
	
	
	
	/**  
	 * 后台-账单-查看账单-导出
	 * @author SY
	 */
	public ExportExcel exportAdminBill(String productOrderId,String billNumber,String billTimeStr, Date billTime,String productName,String customerName) {
		
		
		List<Map<String,Object>> dataTemp=new ArrayList<>();
		List<List<OrderStats>> listTemp=new ArrayList<>();
		List<String> tableHead=new ArrayList<>();
		List<Integer> sumCountList=new ArrayList<>();
		
		tableHead.add("日");
		DecimalFormat df=new DecimalFormat("0.00");
		//根据产品订购Id获取所有产品信息（标签）
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proname = product.getProName();//产品名称
			
			//获取统计信息【当月账单】
			List<OrderStats> orderStats2 = statsDao.getOrderStatsByProductOrderId( productOrderId, proCode, DateUtil.toStr(DateUtil.getInitMonth(billTime), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(DateUtil.getMaxMonthDateTime(billTime), DateUtil.YYYY_MM_DD_HH_MM_SS));
			//数据加工
			
			
			//获取日期数组（横坐标）,数据修复
			Date date1=DateUtil.getInitMonth(billTime);
			Date date2=DateUtil.getMaxMonthDateTime(billTime);
			List<OrderStats> orderStats=new ArrayList<>();
			while (date1.getTime()<=date2.getTime()) {
				boolean flag=true;
				for (OrderStats order : orderStats2) {
					String recordTime=order.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date1, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						orderStats.add(order);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					OrderStats order=new OrderStats();
					order.setCount(0);
					order.setFee(0.00);
					orderStats.add(order);
				}
				
				date1=DateUtil.getLaterDay(date1);
			}
			
			
			
			//计算总使用次数
			int sumCount=0;
			for (OrderStats stats : orderStats) {
				sumCount+=stats.getCount();
			}
			
			//封装表头信息
			tableHead.add(proname+"请求（次）");
			
			listTemp.add(orderStats);
			sumCountList.add(sumCount);
			
			
		}
		//加上请求总计和金额
		tableHead.add("标签总请求（次）");
		tableHead.add("标签总金额（元）");
		
		
		
		//初始化日期大小
		for (int i = 0; i <= listTemp.get(0).size(); i++) {
			Map<String, Object> map=new HashMap<>();
			List<Integer> list=new ArrayList<>();
			if(i<listTemp.get(0).size()){
				map.put("date", i+1);
				map.put("countList", list);
				dataTemp.add(map);
			}else {//加上总计栏
				map.put("date", "合计");
				map.put("countList", list);
				dataTemp.add(map);
			}
		}
		
		
		

		//实例化表格信息
		for (int i = 0; i < dataTemp.size();i++){
			if(i<(dataTemp.size()-1)){//放基本数据
				//一排一排放日期数据
				List<Object> list = (List<Object>) dataTemp.get(i).get("countList");
				int allCount=0;
				Double allFee=0.00;
				for (int j = 0; j < listTemp.size()+2; j++) {//放标签、总请求数、总金额数
					if(j==0){//初始化计数器
						allCount=0;
						allFee=0.0;
					}
					
					if(j<listTemp.size()){//放标签
						Integer count = listTemp.get(j).get(i).getCount();
						list.add(count);
						allCount+=count;
						allFee+=listTemp.get(j).get(i).getFee();
					}else if (j==listTemp.size()) {//放总请求数
						list.add(allCount);
					}else if (j==(listTemp.size()+1)) {//放总金额
						list.add(allFee);
						
					}
					
				}
				
				dataTemp.get(i).put("countList", list);
			}else {//放总计栏
				List<Object> list = (List<Object>) dataTemp.get(i).get("countList");
				
				//先总计标签部分，把总请求次数和总金额也计算出来
				int allCount=0;
				Double allFee=0.0;
				for (int j = 0; j < listTemp.size(); j++) {
					int count=0;
					for (OrderStats stats : listTemp.get(j)) {
						count+=stats.getCount();
						allFee+=stats.getFee();
					}
					list.add(count);
					allCount+=count;
				}
				
				//放总请求次数
				
				list.add(allCount);
				list.add(allFee);
				
				
				dataTemp.get(i).put("countList", list);
			}
			
		}
		
		
		
		//数据导出
		String[] rowsName=new String[tableHead.size()];
		tableHead.toArray(rowsName);
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		
		for (int j = 0; j < dataTemp.size(); j++) {
			Map<String, Object> map = dataTemp.get(j);
			//封装信息
			objs = new Object[rowsName.length];
			
			for (int k = 0; k < rowsName.length; k++) {
				if(k==0){
					objs[0] = map.get("date");
				}else {
					List<Object> list = (List<Object>) map.get("countList");
					if(k==(rowsName.length-1)){
						objs[k]=df.format(list.get(k-1));
					}else {
						objs[k]=list.get(k-1);
					}
				}
			}
			
			
			dataList.add(objs);
			
		}
		
		return new ExportExcel("账单   账单号："+billNumber+"   账单时间："+billTimeStr+"   产品名称："+productName+"    客户名称："+customerName,"账单", rowsName, dataList);
	}
		
	/**
	 * 添加新的统计信息（按天）
	 * @param orderStatistical
	 * @return
	 */
	public int insert(OrderStats orderStatistical){
		return statsDao.insert(orderStatistical);
	}
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据【乾坤大数据，统计表】
	 * @return
	 */
	public List<Map<String,Object>> getTable(String customerNumber,String productOrderId,Date beginTime,Date endTime,String proName){
		List<Map<String,Object>> result=new ArrayList<>();
		
		//根据产品订购Id获取所有产品信息（标签）
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId,proName);
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proname = product.getProName();//产品名称
			//获取所有统计信息
			List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, productOrderId, proCode, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
		
			int count=0;
			for (OrderStats stats : orderStats) {
				count+=stats.getCount();
			}
			if(count!=0) {
				
				//封装信息
				Map<String, Object> map=new HashMap<>();
				map.put("name", proname);
				map.put("count", count);
				map.put("beginTime", DateUtil.toStr(beginTime, DateUtil.YYYY__MM__DD));
				map.put("endTime", DateUtil.toStr(endTime, DateUtil.YYYY__MM__DD));
				result.add(map);
			}
		}
		
		//根据Count排序
        Collections.sort(result, new Comparator<Map<String, Object>>() {  

			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int i = (Integer)o2.get("count") - (Integer)o1.get("count");  
                return i;  
			} 
        });  
		
		return result;
	}
	
	
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据【乾坤大数据-饼状图】
	 * 展示排行前六的标签（若前六标签有0次数，也归为其它标签）和其它标签，显示占比和次数
	 * @return
	 */
	public Map<String, Object> getCartForBigDataPie(String customerNumber,String productOrderId,Date beginTime,Date endTime){
		
		Map<String, Object> result=new HashMap<String, Object>();		
		
		//1、获取所有标签集合
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		
		
		//2、自定义数据类型
		//2.1	饼状图数据类型
		List<Map<String, Object>> pieCart=new ArrayList<>();//饼状图最终返回数据	[{"name":"标签一","value":24},{...}]
		Map<String, Integer> pieTempMap=new TreeMap<String, Integer>();//饼状图Map临时数据，临时存储所有标签对应的请求次数（为了比较标签的使用次数）[{"标签一":24},{...}]

		
		
		//3、根据客户编码和订单号、产品编码查询统计信息【时间段内】查询统计信息
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proName = product.getProName();//产品名称
			List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, productOrderId, proCode, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			
			
			
			//将数据标签统计信息分别放到临时数据中，待后面处理
			List<Integer> list=new ArrayList<>();
			int sum=0;
			for (OrderStats stats : orderStats) {
				list.add(stats.getCount());
				sum+=stats.getCount();
			}
			pieTempMap.put(proName, sum);
		}
		
		
		
		
		//4、拼接返回数据
		
		//4.1	拼接饼状图返回数据
		//4.1.1	对Map进行排序（从大到小），
	    ArrayList<Map.Entry<String,Integer>> entries= sortMap(pieTempMap);
	    //4.1.2	拼接排序前6个数据
	    int pieflag=0;	    
		if(pieTempMap.size()<6){//如果数据不超过6个
		    for( int i=0;i<pieTempMap.size();i++){
			  	  if(entries.get(i).getValue()>0){
			  		  Map<String, Object> map=new HashMap<>();
			  		  map.put("value", entries.get(i).getValue());
			  		  map.put("name", entries.get(i).getKey());
			  		  pieCart.add(map);
			  		  pieflag=i;
			  	  }else{
			  		  break;
			  	  }
			}
		}else {//如果数据超过六个
		    for( int i=0;i<6;i++){
			  	  if(entries.get(i).getValue()>0){
			  		  Map<String, Object> map=new HashMap<>();
			  		  map.put("value", entries.get(i).getValue());
			  		  map.put("name", entries.get(i).getKey());
			  		  pieCart.add(map);
			  		  pieflag=i;
			  	  }else{
			  		  break;
			  	  }
			}
		}
	    
	    
	    
	    
	    
	    
	    //4.1.3	拼接‘其它标签’    说明：如果前6个都有为0的，那么‘其它标签’肯定为0
	    if(pieflag>5){
	  	  int sum=0;
	      for( int i=6;i<pieTempMap.size();i++){
	      	  sum+=entries.get(i).getValue();
	      }
	      
	      if(sum!=0){
	    	  Map<String, Object> map=new HashMap<>();
	    	  map.put("value", sum);
	    	  map.put("name", "其它标签");
	    	  pieCart.add(map);
	      }
	    }
	    
	    //4.1.4	返回最终数据
        result.put("pieCart", pieCart);
		
		return result;
	}
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据【乾坤大数据-折线图】
	 * 展示排行前3（时段平均值）和其它标签的趋势图，鼠标悬浮到具体节点时，显示具体请求次数
	 * @return
	 */
	public Map<String, Object> getCartForBigDataLine(String customerNumber,String productOrderId,Date beginTime,Date endTime){
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		//1、获取所有标签集合
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		
		
		//2、自定义数据类型
		Map<String, Integer> pieTempMap=new TreeMap<String, Integer>();//饼状图Map临时数据，临时存储所有标签对应的请求次数（为了比较标签的使用次数）[{"标签一":24},{...}]
		//2.2	折线图数据类型
		List<String> date=new ArrayList<String>();//坐标横轴日期数据
		List<String> date_2=new ArrayList<String>();//坐标横轴日期数据
		List<Map<String, Object>> lineCart=new ArrayList<>();//折线图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]
		List<Map<String, Object>> lineCart_2=new ArrayList<>();//折线图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]
		Map<String, List<Integer>> lineTempMap=new HashMap<>();//折线图Map临时数据,（为了比较标签的使用次数） [{"标签1":[21,31,22]},{...}]
		
		//获取日期数组（横坐标）
		Date date1=beginTime;
		Date date2=endTime;
		while (date1.getTime()<=date2.getTime()) {
			date.add(DateUtil.toStr(date1,DateUtil.MMDD));
			date_2.add(DateUtil.toStr(date1, DateUtil.YYYY_MM_DD));
			date1=DateUtil.getLaterDay(date1);
		}
		result.put("date", date);
		result.put("date2", date_2);
		
		//3、根据客户编码和订单号、产品编码查询统计信息【时间段内】查询统计信息
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proName = product.getProName();//产品名称
			List<OrderStats> orderStats2 = statsDao.getOrderStats(customerNumber, productOrderId, proCode, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			
			//数据加工，修复
			Date date11=beginTime;
			Date date22=endTime;
			List<OrderStats> orderStats=new ArrayList<>();
			while (date11.getTime()<=date22.getTime()) {
				boolean flag=true;
				for (OrderStats order : orderStats2) {
					String recordTime=order.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						orderStats.add(order);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					OrderStats order=new OrderStats();
					order.setCount(0);
					order.setFee(0.00);
					orderStats.add(order);
				}
				
				date11=DateUtil.getLaterDay(date11);
			}
			
			
			//将数据标签统计信息分别放到临时数据中，待后面处理
			List<Integer> list=new ArrayList<>();
			int sum=0;
			for (OrderStats stats : orderStats) {
				list.add(stats.getCount());
				sum+=stats.getCount();
			}
			pieTempMap.put(proName, sum);
			lineTempMap.put(proName, list);
		}
		
		
		
		
		//4、拼接返回数据
		
		//4.1	拼接饼状图返回数据
		//4.1.1	对Map进行排序（从大到小），
	    ArrayList<Map.Entry<String,Integer>> entries= sortMap(pieTempMap);        
        
        //4.2	拼接折线图返回数据
	    //4.2.1	对统计数据进行修复处理【当用户查看的时间段，超过统计时间之前，那么之前的请求次数应该都为0】
	    for(int i=0;i<lineTempMap.size();i++){
	    	List<Integer> list = lineTempMap.get(entries.get(i).getKey());
	    	int gap = date.size()-list.size();
	    	List<Integer> temp=new ArrayList<Integer>();
	    	for (int j = 0; j < gap; j++) {
	    		temp.add(0);
			}
	    	temp.addAll(list);
	    	lineTempMap.put(entries.get(i).getKey(), temp);
	    }
	    
	    //4.2.2	解析出前三个标签，并将其从临时数据中移除（为什么用entries？排序）
	    
	    if(entries.size()<=3){
	    	for( int i=0;i<entries.size();i++){
	    		List<Integer> list = lineTempMap.get(entries.get(i).getKey());
	    		Map<String,Object> map=new HashMap<>();
	    		map.put("name", entries.get(i).getKey());
	    		map.put("value", list);
	    		lineCart.add(map);
	    		lineCart_2.add(map);
	    		lineTempMap.remove(entries.get(i).getKey());
	    	}
	    }else {
	    	for( int i=0;i<3;i++){
	    		List<Integer> list = lineTempMap.get(entries.get(i).getKey());
	    		Map<String,Object> map=new HashMap<>();
	    		map.put("name", entries.get(i).getKey());
	    		map.put("value", list);
	    		lineCart.add(map);
	    		lineCart_2.add(map);
	    		lineTempMap.remove(entries.get(i).getKey());
	    	}
			
		}
	    
	    
	    //4.2.2	取‘其它标签’
	    List<Integer> lineTempList=new ArrayList<Integer>();//把剩下的所有数据合并到该List中
	    //初始化该数组
	    for (int i = 0; i < date.size(); i++) {
			lineTempList.add(0);
		}
	    
		//Iterator迭代
		Iterator it=lineTempMap.keySet().iterator();
		while(it.hasNext()){
			String key=it.next().toString();
			List<Integer> list = lineTempMap.get(key);
			
			Map<String,Object> map=new HashMap<>();
	    	map.put("name", key);
	    	map.put("value", list);
	    	lineCart_2.add(map);
			
			for (int i = 0; i < list.size(); i++) {
				
				
				lineTempList.set(i, lineTempList.get(i)+list.get(i));
			}
		}
    	Map<String,Object> map=new HashMap<>();
    	map.put("name", "其它标签");
    	map.put("value", lineTempList);
    	lineCart.add(map);
    	
    	//4.2.3	返回最终数据
		result.put("lineCart", lineCart);
		result.put("lineCart2", lineCart_2);
		
		return result;
	}
	
	
	
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据(统计图)-和位士
	 * 展示请求次数的趋势图
	 * @return
	 */
	public Map<String, Object> getCartForAndGuards(String customerNumber,String productOrderId,Date beginTime,Date endTime){
		Map<String, Object> result=new HashMap<String, Object>(); 
		
		
		//1、获取所有标签集合
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		
		
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
		
		//3、根据客户编码和订单号、产品编码查询统计信息【时间段内】查询统计信息
		for (AdminOrderSub product : products) {//其实就一个数据（和位士）
			String proCode = product.getProCode();//产品编码
			String proName = product.getProName();//产品名称
			List<OrderStats> orderStats2 = statsDao.getOrderStats(customerNumber, productOrderId, proCode, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			
			
			//数据加工，修复
			Date date11=beginTime;
			Date date22=endTime;
			List<OrderStats> orderStats=new ArrayList<>();
			while (date11.getTime()<=date22.getTime()) {
				boolean flag=true;
				for (OrderStats order : orderStats2) {
					String recordTime=order.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						orderStats.add(order);
						flag=false;
						break;
					}
					
				}
				
				if(flag){
					OrderStats order=new OrderStats();
					order.setCount(0);
					order.setFee(0.00);
					orderStats.add(order);
				}
				
				date11=DateUtil.getLaterDay(date11);
			}
			
			
			
			
			
			
			List<Integer> list=new ArrayList<>();
			for (OrderStats stats : orderStats) {
				list.add(stats.getCount());
			}
			
			
			
			
			//对统计数据进行修复处理【当用户查看的时间段，超过统计时间之前，那么之前的请求次数应该都为0】
	    	int gap = date.size()-list.size();
	    	List<Integer> temp=new ArrayList<Integer>();
	    	for (int j = 0; j < gap; j++) {
	    		temp.add(0);
			}
	    	temp.addAll(list);
	    	
			
			Map<String,Object> map=new HashMap<>();
	    	map.put("name", proName);
	    	map.put("value", list);
	    	lineCart.add(map);
			
		}
		
		
		//返回最终数据
		result.put("lineCart", lineCart);
		
		return result;
	}
	
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据【和位士，统计表】
	 * 说明：type 0 按日期从小到大正序排序；type 1 按日期从大到小倒序排序；type 2 按次数从大到小倒序排序；type 3 按次数从小到大正序排序；
	 * @return
	 */
	public String getTableForAndGuards(int pageIndex,String customerNumber,String productOrderId,Date beginTime,Date endTime,int timeOrder,int countOrder){
		
		PageData pageData = statsDao.getListByType(pageIndex,customerNumber,productOrderId,DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS),timeOrder,countOrder);
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd";
		//List<BusinessRecentnews> selectByKeyword = (List<BusinessRecentnews>) pageData.getLst();
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
		
	}
	
	
	private static ArrayList<Map.Entry<String,Integer>> sortMap(Map map){
	    List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
	    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
	        public int compare(Map.Entry<String, Integer> obj1 , Map.Entry<String, Integer> obj2) {
	            return obj2.getValue() - obj1.getValue();
	        }
	    });
	     return (ArrayList<Entry<String, Integer>>) entries;
   }




	/**
	 * 导出数据【乾坤大数据，统计表】
	 * @param customerNumber
	 * @param productOrderId
	 * @param beginTime
	 * @param endTime
	 * @param proName
	 * @return
	 */
	public ExportExcel exportTable(String customerNumber,String productOrderId, Date beginTime, Date endTime, String proName) {

		
		//导出数据录入
		String[] rowsName = new String[]{"序号","标签名称","开始时间","结束时间","请求次数"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		
		//根据产品订购Id获取所有产品信息（标签）
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId,proName);
		int index=1;
		for (AdminOrderSub product : products) {
			
			String proCode = product.getProCode();//产品编码
			String proname = product.getProName();//产品名称
			//获取所有统计信息
			List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, productOrderId, proCode, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
		
			int count=0;
			for (OrderStats stats : orderStats) {
				count+=stats.getCount();
			}
			
			if(count!=0) {
				
				//封装信息
				objs = new Object[rowsName.length];
				objs[0] = index;
				objs[1] = proname;
				objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM__DD);
				objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM__DD);
				objs[4] = count;
				dataList.add(objs);
				
				index++;
			}
		}
		//获取产品订单
		AdminOrder order = orderDao.getOrderByProductOrderId(productOrderId);
		
		
		//列表头需包含订单号、订购生效时间和产品名称
		return new ExportExcel("订单号："+productOrderId+"   下单时间："+order.getEffTime().toString().substring(0, 19)+"   产品名称："+order.getProductName(),"乾坤大数据", rowsName, dataList);
	}


	/**
	 * 乾坤大数据-业务统计-折线图数据-导出
	 * @param request
	 * @param response
	 * @return
	 */
public ExportExcel exportCartForBigDataLine(String productNameStr,String effTimeStr,String customerNumber,String productOrderId,Date beginTime, Date endTime) {
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","类型","日期","请求次数"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		
		//1、获取所有标签集合
		List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(productOrderId);
		
		
		//3、根据客户编码和订单号、产品编码查询统计信息【时间段内】查询统计信息
		for (AdminOrderSub product : products) {
			String proCode = product.getProCode();//产品编码
			String proName=product.getProName();//产品名称
			List<OrderStats> orderStats2 = statsDao.getOrderStats(customerNumber, productOrderId, proCode, DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			
			//数据加工，修复
			Date date11=beginTime;
			Date date22=endTime;
			List<OrderStats> orderStats=new ArrayList<>();
			while (date11.getTime()<=date22.getTime()) {
				boolean flag=true;
				for (OrderStats order : orderStats2) {
					String recordTime=order.getRecordTime().toString().substring(0, 10);
					if((DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS).substring(0, 10)).equals(recordTime)){//如果当前时间有统计信息
						orderStats.add(order);
						flag=false;
						break;
					}
				}
				
				if(flag){
					OrderStats order=new OrderStats();
					order.setCount(0);
					order.setRecordTime(Timestamp.valueOf(DateUtil.toStr(date11, DateUtil.YYYY_MM_DD_HH_MM_SS)));
					order.setFee(0.00);
					order.setProductName(proName);
					orderStats.add(order);
				}
				
				date11=DateUtil.getLaterDay(date11);
			}
			
			for (int i = 0; i < orderStats.size(); i++) {
				OrderStats stats=orderStats.get(i);
				//封装信息
				objs=new Object[rowsName.length];
				objs[0] = i+1;
				objs[1] = stats.getProductName();
				objs[2] = DateUtil.toStr(DateUtil.toDate(stats.getRecordTime().toString().substring(0, 10), DateUtil.YYYY_MM_DD), DateUtil.YYYY__MM__DD);
				objs[3] = stats.getCount();
				dataList.add(objs);
			}
		}
		String excleName="订单号："+productOrderId+"，下单时间："+effTimeStr+"，产品标签："+productNameStr;
		return new ExportExcel(excleName,"乾坤大数据", rowsName, dataList);
	}




	/**
	 * 和位士-业务统计-统计表-导出
	 * @return
	 */
	public ExportExcel exportTableForAndGuards(String customerNumber,String productOrderId, Date beginTime, Date endTime,String effTime) {
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","类型","日期","请求次数"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		
		List<OrderStats> statsList= statsDao.getList(customerNumber,productOrderId,DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
		
		//序号	标签名称	日期	请求次数
		for (int i = 0; i < statsList.size(); i++) {
			OrderStats stats = statsList.get(i);
			
			//封装信息
			objs=new Object[rowsName.length];
			objs[0] = i+1;
			objs[1] = stats.getProductName();
			objs[2] = DateUtil.toStr(DateUtil.toDate(stats.getRecordTime().toString().substring(0, 10), DateUtil.YYYY_MM_DD), DateUtil.YYYY__MM__DD);
			objs[3] = stats.getCount();
			dataList.add(objs);
		}
		
		return new ExportExcel("订单号："+productOrderId+"   下单时间："+effTime+"   产品名称：和位士","和位士", rowsName, dataList);
	}

	/**
	 * 查询每个月的订单信息
	 * @param dateStr
	 * @return
	 */
	public List<Bill>  getOrderStatsByMonth(String dateStr){
		List<Bill> orderStatsByMonth = statsDao.getOrderStatsByMonth(dateStr);
		return orderStatsByMonth;
	}
	/**
	 * 查询每个月网址卫士的订单信息
	 * @param dateStr
	 * @return
	 */
	public List<Map<String,Object>>  getWWSOrder(String dateStr){
		List<Map<String, Object>> allWWSOrder = statsDao.getAllWWSOrder(dateStr);
		return allWWSOrder;
	}
	

	
}
