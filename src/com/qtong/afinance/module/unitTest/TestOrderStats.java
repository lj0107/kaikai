package com.qtong.afinance.module.unitTest;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.pojo.statistics.OrderStats;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"file:WebContent/WEB-INF/applicationContext.xml",
		"file:WebContent/WEB-INF/applicationContext-redis.xml" })
public class TestOrderStats {

	@Autowired
	private OrderStatsDao orderStatsDao;
	
	
	/**
	 * 添加浦发对账单统计数据
	 * @param date
	 * @param authCode
	 * @return
	 * @throws Exception
	 */
	@Test
	public void insertRecord() throws Exception {
		
		
		
		String pro_ord_id="100000166686";//产品订购关系ID
		String product_code="370004";//产品编码
		String product_name="ID与手机号匹配情况标签";//产品名称
		String customer_number="E0002017103110010884";
		
		//测试
//		String pro_ord_id="MSW83100000098801";//产品包订购关系Id
//    	String product_code="MOID0001";//产品编码
//    	String product_name="移动手机号码、ID匹配情况";//产品名称
//    	String customer_number="E1002017122909251120";//客户编码
		
		
		Map<Date, Integer> dataMap = getDataMap();
		
		Set<Date> keys = dataMap.keySet();
		for (Date date2 : keys) {
			
			OrderStats orderStats = new OrderStats();
					
			orderStats.setProductOrderId(pro_ord_id);
			orderStats.setProductCode(product_code);
			orderStats.setProductName(product_name);
			orderStats.setCustomerNumber(customer_number);
			
			Date minDayDateTime = DateUtil.getMinDayDateTime(date2);
			orderStats.setRecordTime( new Timestamp((minDayDateTime).getTime()));
			
			Integer count = dataMap.get(date2);
			orderStats.setCount(count);
			
			
			DecimalFormat df=new DecimalFormat("0.00");
			double fee;
			fee = count * 0.68;
			
			String format = df.format(fee);
			
			
			orderStats.setFee(Double.parseDouble(format));

			orderStatsDao.insert(orderStats);
			
			System.out.println("===>"+orderStats);
		}
		
		
		
		
		
		
		
		
	}
	
	
	
	
	private static Map<Date,Integer> getDataMap() {
		
		
		 Map<Date,Integer> dataMap=new LinkedHashMap<>();
		 
		 
		 
		 dataMap.put(DateUtil.toDate("2018050101"+DateUtil.toStr(new Date()).substring(10)), 137176);
		 dataMap.put(DateUtil.toDate("2018050202"+DateUtil.toStr(new Date()).substring(10)), 173879);
		 dataMap.put(DateUtil.toDate("2018050303"+DateUtil.toStr(new Date()).substring(10)), 124427);
		 dataMap.put(DateUtil.toDate("2018050404"+DateUtil.toStr(new Date()).substring(10)), 147321);
		 dataMap.put(DateUtil.toDate("2018050505"+DateUtil.toStr(new Date()).substring(10)), 209580);
		 dataMap.put(DateUtil.toDate("2018050606"+DateUtil.toStr(new Date()).substring(10)), 201705);
		 dataMap.put(DateUtil.toDate("2018050707"+DateUtil.toStr(new Date()).substring(10)), 159522);
		 dataMap.put(DateUtil.toDate("2018050808"+DateUtil.toStr(new Date()).substring(10)), 141075);
		 dataMap.put(DateUtil.toDate("2018050909"+DateUtil.toStr(new Date()).substring(10)), 162408);
		 dataMap.put(DateUtil.toDate("2018051010"+DateUtil.toStr(new Date()).substring(10)), 158515);
		 dataMap.put(DateUtil.toDate("2018051111"+DateUtil.toStr(new Date()).substring(10)), 121338);
		 dataMap.put(DateUtil.toDate("2018051212"+DateUtil.toStr(new Date()).substring(10)), 137965);
		 dataMap.put(DateUtil.toDate("2018051313"+DateUtil.toStr(new Date()).substring(10)), 163071);
		 dataMap.put(DateUtil.toDate("2018051414"+DateUtil.toStr(new Date()).substring(10)), 140897);
		 dataMap.put(DateUtil.toDate("2018051515"+DateUtil.toStr(new Date()).substring(10)), 83388);
	
		 
//		 dataMap.put(DateUtil.toDate("2018041616"+DateUtil.toStr(new Date()).substring(10)), 82806);
//		 dataMap.put(DateUtil.toDate("2018041717"+DateUtil.toStr(new Date()).substring(10)), 66683);
//		 dataMap.put(DateUtil.toDate("2018041818"+DateUtil.toStr(new Date()).substring(10)), 60014);
//		 dataMap.put(DateUtil.toDate("2018041919"+DateUtil.toStr(new Date()).substring(10)), 79742);
//		 dataMap.put(DateUtil.toDate("2018042020"+DateUtil.toStr(new Date()).substring(10)), 74851);
//		 dataMap.put(DateUtil.toDate("2018042121"+DateUtil.toStr(new Date()).substring(10)), 83636);
//		 dataMap.put(DateUtil.toDate("2018042222"+DateUtil.toStr(new Date()).substring(10)), 81607);
//		 dataMap.put(DateUtil.toDate("2018042323"+DateUtil.toStr(new Date()).substring(10)), 110436);
//		 dataMap.put(DateUtil.toDate("2018042400"+DateUtil.toStr(new Date()).substring(10)), 72606);
//		 dataMap.put(DateUtil.toDate("2018042501"+DateUtil.toStr(new Date()).substring(10)), 69972);
//		 dataMap.put(DateUtil.toDate("2018042602"+DateUtil.toStr(new Date()).substring(10)), 101613);
//		 dataMap.put(DateUtil.toDate("2018042703"+DateUtil.toStr(new Date()).substring(10)), 88013);
//		 dataMap.put(DateUtil.toDate("2018042804"+DateUtil.toStr(new Date()).substring(10)), 89751);
//		 dataMap.put(DateUtil.toDate("2018042905"+DateUtil.toStr(new Date()).substring(10)), 85486);
//		 dataMap.put(DateUtil.toDate("2018043006"+DateUtil.toStr(new Date()).substring(10)), 140965);
		 //------对应excle表18年03月份结束
		 
		 
		 
		 
		return dataMap;
		
	}
	
	
	
	
}
