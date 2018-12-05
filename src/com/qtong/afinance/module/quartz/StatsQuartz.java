package com.qtong.afinance.module.quartz;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.pojo.product.AdminOrder;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCount;
import com.qtong.afinance.module.pojo.statistics.OrderStats;
import com.qtong.afinance.module.pojo.statistics.RequestCounts;
import com.qtong.afinance.module.pojo.statistics.UrlGuardStats;
import com.qtong.afinance.module.service.admin.CustomerService;
import com.qtong.afinance.module.service.portal.AdimOrderService;
import com.qtong.afinance.module.service.portal.AdminOrderSubService;
import com.qtong.afinance.module.service.record.RecordService;
import com.qtong.afinance.module.service.statistics.OrderStatsService;
import com.qtong.afinance.module.service.statistics.UrlGuardStatsService;
import com.qtong.afinance.module.service.urlGuard.AfinUrlGuardService;

/**
 * 产品使用信息统计【每天0点执行】
 *
 */
@Component
public class StatsQuartz {
	@Autowired
	private RecordService recordService;
	@Autowired
	private AdimOrderService orderService;
	@Autowired
	private AdminOrderSubService orderSubService;
	@Autowired
	private OrderStatsService statsService;
	@Autowired
	private UrlGuardStatsService urlGuardStatsService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AfinUrlGuardService urlGuardService;
	
	@Scheduled(cron = "0 0 0 * * ?")//每天0点执行
    public void execute(){
		
		
		//#########################乾坤大数据、和位士业务统计###########################
		
		try {
			//1、查询出所有订单信息
			List<AdminOrder> orders = orderService.getOrders();
			
			//2、根据订单号分别解析出产品（标签信息）
			for (AdminOrder order : orders) {
				String productOrderId=order.getProductOrderId();
				String customerNumber = order.getCustomerNumber();
				List<AdminOrderSub> list = orderSubService.getProductByProductOrderId(productOrderId);
				for (AdminOrderSub product : list) {
					//String proCode = product.getProCode();
					String orderId = product.getSubOrderNum();
					
					Map<String, Object> map = recordService.getStatsCondition(orderId);
					
					Long count =(Long)map.get("count");
					Object  feeStr = map.get("fee");
					/*double fee = 0;
					if(null != feeStr) 
						fee= Integer.valueOf(feeStr.toString()); */
					//int count = recordService.getCountByCondition(orderId);
					//Double fee=recordService.getFeeByCondition(orderId);
					
					if(count!=0){
						OrderStats orderStatistical=new OrderStats();
						orderStatistical.setProductOrderId(productOrderId);
						orderStatistical.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
						orderStatistical.setCount(count.intValue());
						orderStatistical.setProductCode(product.getProCode());
						orderStatistical.setProductName(product.getProName());
						orderStatistical.setCustomerNumber(customerNumber);
						BigDecimal df = new BigDecimal(Double.parseDouble(map.get("fee").toString())/1000.0);
						double doubleValue = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						orderStatistical.setFee(doubleValue);
						statsService.insert(orderStatistical);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//#########################乾坤大数据-请求次数###########################
		try {
			List<Map<String,Object>> datamarkCount = recordService.getDatamarkCount();
			List<RequestCounts> addList = new ArrayList<RequestCounts>();
			if(datamarkCount.size()>0) {
				for (Map<String, Object> map : datamarkCount) {
					int count = Integer.parseInt(map.get("counts").toString());
					if(count>0) {
						RequestCounts requestCounts = new RequestCounts();
						requestCounts.setProductOrderId((String)map.get("product_package_ord_id"));
						requestCounts.setCount(count);
						requestCounts.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
						addList.add(requestCounts);
					}
				}
				
			}
			recordService.saveRequestCounts(addList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		//#########################网址卫士业务统计###########################
		
		
		try {
			//1、查询出所有客户信息(可用的状态为0)
			List<BossCustomer> customers = customerService.getCustomersWeiShi();
			
			//2、根据客户信息分别统计每个客户的网址卫士使用信息
			for (BossCustomer customer : customers) {
				String customerNumber = customer.getCustomerNumber();
				UrlGuardStats stats=new UrlGuardStats();
				//设置中的条数来源于这里：网址卫士操作日志表 afin_urlguard_process 网址卫士信息主表 afin_urlguard
				//发现恶意网址
				int a=urlGuardService.getCountByCondition(customerNumber, "101");
				if(a!=0){
					stats.setCustomerNumber(customerNumber);
					stats.setCount(a);//网址卫士操作日志表 afin_urlguard_process 网址卫士信息主表 afin_urlguard》》》发现恶意网址
					stats.setState("101");
					stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
					urlGuardStatsService.insert(stats);
				}
				//确认恶意网址
				int b=urlGuardService.getCountByCondition(customerNumber, "104");
				if(b!=0){
					stats.setCustomerNumber(customerNumber);
					stats.setCount(b);//》》》确认恶意网址
					stats.setState("104");
					stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
					urlGuardStatsService.insert(stats);
				}
				//拦截恶意网址
				int c=urlGuardService.getCountByCondition(customerNumber, "106");
				if(c!=0){
					stats.setCustomerNumber(customerNumber);
					stats.setCount(c);//》》》拦截恶意网址
					stats.setState("106");
					stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
					urlGuardStatsService.insert(stats);
				}
				//提交误报网址
				int d=urlGuardService.getCountByCondition(customerNumber, "201");
				if(d!=0){
					stats.setCustomerNumber(customerNumber);
					stats.setCount(d);//》》》提交误报网址
					stats.setState("201");
					stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
					urlGuardStatsService.insert(stats);
				}
				//确认误报网址
				int e=urlGuardService.getCountByCondition(customerNumber, "203");
				if(e!=0){
					stats.setCustomerNumber(customerNumber);
					stats.setCount(e);//》》》确认误报网址
					stats.setState("203");
					stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
					urlGuardStatsService.insert(stats);
				}
				//解除误报网址
				int f=urlGuardService.getCountByCondition(customerNumber, "205");
				if(f!=0){
					stats.setCustomerNumber(customerNumber);
					stats.setCount(f);//》》》接触误报网址
					stats.setState("205");
					stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.getMaxDayDateTime(DateUtil.getBeforeDay(new Date())), DateUtil.YYYY_MM_DD_HH_MM_SS)));
					urlGuardStatsService.insert(stats);
				}
				
				//最后将统计出来的数据插入到这里afin_urlguard_stats>>>网址卫士统计表
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//#########################网址卫士业务统计-客户次数###########################
		
		try {
			//获取所有的一级产品包
			List<Map<String, Object>> selectType = urlGuardStatsService.selectType();
			for (Map<String, Object> map : selectType) {
				String string = map.get("package_code").toString();//产品包编码
				String type_name = map.get("type_name").toString();//产品包别名
				Date date = new Date();
				String beginTime = DateUtil.toStr(DateUtil.getBeforeDay(new Date()), DateUtil.YYYY_MM_DD_HH_MM_SS);//getBeforeDay获取当前时间的前一天
				String endTimae = DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
				//统计每天的订购产品的客户及其所订购的产品（比如今天有两个客户订购不同的产品或者订购相同产品等）
				ArrayList<OrderCustomerCount> selectCustomerCount = urlGuardStatsService.selectCustomerCount(string,type_name,date,beginTime, endTimae);
				//添加到统计表：afin_order_customer_count（暂且称为订单统计表）
				urlGuardStatsService.insertCustomerCount(selectCustomerCount);//添加新的订购客户次数统计信息（按天）
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	   
	}
	

}

