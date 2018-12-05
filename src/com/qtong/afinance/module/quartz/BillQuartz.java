package com.qtong.afinance.module.quartz;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SnowflakeIdWorker;
import com.qtong.afinance.module.dao.portal.AdminOrderDao;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCount;
import com.qtong.afinance.module.service.bill.PortalBillService;
import com.qtong.afinance.module.service.portal.AdimOrderService;
import com.qtong.afinance.module.service.statistics.OrderStatsService;

@Component
public class BillQuartz {
	
	@Autowired
	private PortalBillService billService;
	@Autowired
	private AdimOrderService adimOrderService;
	@Autowired
	private OrderStatsService orderStatsService;
	
	//@Scheduled(cron = "0 */1 * * * ?")//
	//@Scheduled(cron = "0 59 23 L * ?")//每月最后一天的最后一分钟执行
	//@Scheduled(cron = "0 0 0 1 * ?")//每月第一天的零点执行
	@Scheduled(cron = "0 10 0 1 * ?")//每月第一天的零点10分执行
	public void execute() {
		
		try{
			
			Date dateNew = new Date();
			
			
			//时间格式化为YYYY-MM
			String dateStr = DateUtil.toStr(DateUtil.getBeforeDay(dateNew), DateUtil.YYYY_MM);
			
			
			/******************************统计乾坤大数据和和卫士的账单***************************************************/
			List<Bill> orderStatsByMonth = orderStatsService.getOrderStatsByMonth(dateStr);
			List<Bill> bills = new ArrayList<Bill>();
			

			
			if(orderStatsByMonth!=null) {
				
				SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);//生成主键
				
				for (Bill bill : orderStatsByMonth) {
					
					long code = idWorker.nextId();
					String id = String.valueOf(code);//生成id
					
					bill.setBillNumber(id);
					
					Timestamp ts = new Timestamp(System.currentTimeMillis());   
			        try {   
			            ts = Timestamp.valueOf(DateUtil.toStr(DateUtil.getBeforeDay(dateNew),DateUtil.YYYY_MM_DD_HH_MM_SS));   
			        } catch (Exception e) {   
			            e.printStackTrace();   
			        }  
					bill.setBillTime(ts);
					
					bills.add(bill);
				}
			}
			
			
			
			/**************************************统计网址卫士账单***********************************************************/
			//时间格式化为YYYY-MM-dd HH:mm:ss
			String dateString = DateUtil.toStr(dateNew, DateUtil.YYYY_MM_DD_HH_MM_SS);
					
			//查询出所有网址卫士的信息
			List<Map<String, Object>> wwsOrder = orderStatsService.getWWSOrder(dateString);
			SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);//生成主键
			DecimalFormat df=new DecimalFormat("0.00");
			for (Map<String, Object> map : wwsOrder) {
				
				//订单生效时间
				String effTime = map.get("eff_time").toString();
				//订单结束时间
				String expTime = map.get("exp_time").toString();
				
				Date effTime1 = DateUtil.toDate(effTime,DateUtil.YYYY_MM_DD_HH_MM_SS);
				Date expTime1 = DateUtil.toDate(expTime,DateUtil.YYYY_MM_DD_HH_MM_SS);
				
				//获取时间差
				if( dateNew.getTime() > effTime1.getTime() && dateNew.getTime() < expTime1.getTime() ){//判断时间是否在订单时间内
					
					
					boolean beforeMonth = DateUtil.isBeforeMonth(effTime1, dateNew);//判断订单时间是否是统计时间的前一个月
					
					boolean isOneMonth =  DateUtil.isBetweenOneMonth(expTime1, dateNew);//判断统计时间的月是否是该订单第n（n>1）年的出账单月（订单失效时间的后一个月）
					
					if(beforeMonth || isOneMonth) {
						int year = DateUtil.getYear(effTime1, expTime1);//订购的年数
						
						Bill bill = new Bill();
						
						double fee = Double.parseDouble(map.get("price").toString());//费用
						double discount = Double.parseDouble(map.get("discount").toString());//折扣
						double sum = Double.parseDouble(map.get("sum").toString());//实收
						
//						double fee1 = fee*discount/100/year;//每年的费用
						double fee1 = sum/year;//每年的费用
						
						String format = df.format(fee1);
						
						long code = idWorker.nextId();
						String id = String.valueOf(code);//生成id
						bill.setBillNumber(id);
						
						
						Timestamp ts = new Timestamp(System.currentTimeMillis());   
				        try {   
				            ts = Timestamp.valueOf(DateUtil.toStr(DateUtil.getBeforeDay(dateNew),DateUtil.YYYY_MM_DD_HH_MM_SS));   
				        } catch (Exception e) {   
				            e.printStackTrace();   
				        }  
						bill.setBillTime(ts);
					
						bill.setCustomerName(map.get("customer_name").toString());
						bill.setCustomerNumber(map.get("customer_number").toString());
						bill.setFee(Double.parseDouble(format));
						bill.setOrderTime(Timestamp.valueOf(map.get("eff_time").toString()));
						bill.setProductName(map.get("product_name").toString());
						bill.setProductOrderId(map.get("product_order_id").toString());
						
						bills.add(bill);
					}
				}
			
			}
			
			billService.insertBills(bills);
			
			
		
			
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		/**************************************客户信息统计***********************************************************/
		try {
			String beginTime = DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			String dateStr = DateUtil.toStr(DateUtil.getBeforeMonth(new Date()), DateUtil.YYYY_MM);
			List<OrderCustomerCount> selectCumCount = adimOrderService.selectCumCount(dateStr,beginTime);
			adimOrderService.insertCustomerCountNo(selectCumCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
