package com.qtong.afinance.module.service.daping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.util.DateUtil;

import com.qtong.afinance.module.dao.daping.DaPingDao;
import com.qtong.afinance.module.pojo.bill.Bill;



@Service
public class DaPingService {
	
	@Autowired
	private DaPingDao daPingDao;
	@Autowired
	private IJedisClient jedisClient;
	
	

	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据【浦发乾坤大数据-折线图】
	 * 
	 * @return
	 */
	public Map<String, Object> getCartForBigDataLine(){
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		//2、自定义数据类型
		//2.2	折线图数据类型
		List<String> date=new ArrayList<String>();//坐标横轴日期数据
		List<Object> lineCart=new ArrayList<>();//折线图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]
		
		int allPfCount=0;//查询总次数
		double doubleValue = 0 ;// 折扣
		
		//3、数据加工，修复
		//3.1 获取折扣
		List<Map<String, Object>> sum2 = daPingDao.getSum();
		for (Map<String, Object> map : sum2) {
			double fee = (double) map.get("fee");
			BigDecimal df = new BigDecimal(fee/1000.0);
		    doubleValue = df.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			break;
		}
		//3.2获取这线图中数据
		List<Bill> orders = daPingDao.getOrders();
		for (Bill bill : orders) {
			double fee = bill.getFee();//每个月的金额
			double d = fee/doubleValue;
			BigDecimal setScale = new BigDecimal(new Double(d).toString()).setScale(0,BigDecimal.ROUND_HALF_UP);
			int count = (int) setScale.doubleValue();
			//System.out.println(setScale.doubleValue());
		    //int count=(int) (fee/doubleValue);//次数
			String month = DateUtil.toStr(bill.getBillTime(), DateUtil.YYYY___MM);//月份
			allPfCount=allPfCount+count;
			date.add(month);
			lineCart.add(count);
		}
		
		//上个月 的次数
		Bill bill = orders.get(orders.size()-1);
		double fee = bill.getFee();//上个月的金额
		double d =  fee/doubleValue;
		BigDecimal setScale = new BigDecimal(new Double(d).toString()).setScale(0,BigDecimal.ROUND_HALF_UP);
		int count = (int) setScale.doubleValue();
		String month = DateUtil.toStr(bill.getBillTime(),DateUtil.MM);
		
		result.put("date", date);//月份
		result.put("lineCart", lineCart);//次数
		result.put("allPfCount", allPfCount);//总次数
		result.put("lastMonthCount", count);//上个月次数
		result.put("lastMonth", month);//上个月
		
		return result;
	}
	
	
	
	
	/**
	 * 根据客户编码、订购关系Id查询出要展示的统计数据【太保-折线图】
	 * 
	 * @return
	 */
	public Map<String, Object> getCartForTaiBaoLine(){
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		//2、自定义数据类型
		//2.2	折线图数据类型
		List<String> date=new ArrayList<String>();//坐标横轴日期数据
		List<Object> lineCart=new ArrayList<>();//折线图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]
		
		int sumCount=0;//今日查询总次数
		
		//3、数据加工，修复
		//3.1 从redis中获取数据
		
		String key = "order_stats";
		String customerNumber =  "E0002018062710040370";//太保集团客户编码
		String productOrderId =  "100000351059";//订购id
		Date newDate = new Date();
		String currentDay = DateUtil.toStr(newDate,DateUtil.YYYYMMDD);//获取当前天
		String currentDayPage = DateUtil.toStr(newDate,DateUtil.MMDD);//获取MM月dd日
		String currentMonthPage = DateUtil.toStr(newDate,DateUtil.MM);//获取MM月
		Date initMonth = DateUtil.getInitMonth(newDate);
		Date currentMonth = DateUtil.getMinDayDateTime(initMonth);
		String field = customerNumber+currentDay;//当天
		
		Date start = DateUtil.getMinDayDateTime(newDate);//开始时间1：00
		int length = DateUtil.getHourNumber(start,newDate);//获取时间之间间隔小时数
		
		String strHour = "";
		
		//今日实时数据
		if(jedisClient.hexists(key, field)) {//存在当天数据
			
			String hget = jedisClient.hget(key, field);//value ====>  Map
			Map<String,Integer> redisMap = JSON.parseObject(hget, Map.class);
			
			Set<String> keySet = redisMap.keySet();
			for (String stringKey : keySet) {
				Integer integer = redisMap.get(stringKey);
				sumCount+=integer;
			}
			
		}
		
		
		
		for(int i=0;i<length;i++) {
			
			start = DateUtil.getLaterHour(start);
			strHour = DateUtil.toStr(DateUtil.getMinHourMinute(start), DateUtil.HHMM);
			
			if(jedisClient.hexists(key, field)) {//存在当天数据
				
				String hget = jedisClient.hget(key, field);//value ====>  Map
				Map<String,Integer> redisMap = JSON.parseObject(hget, Map.class);
				
				if(redisMap.containsKey(strHour)) {
					Integer count = redisMap.get(strHour);//次数
					date.add(strHour);
					lineCart.add(count);
					//sumCount += count;
				}else {
					date.add(strHour);
					lineCart.add(0);
				}
				
				
			}else {
				date.add(strHour);
				lineCart.add(0);
			}
		}
		
		strHour = DateUtil.toStr(DateUtil.getMinHourMinute(newDate), DateUtil.HHMM);
		Date maxDayDateTime = DateUtil.getMaxDayDateTime(newDate);
		//date.add(strHour);
		int l = DateUtil.getHourNumber(newDate,maxDayDateTime);//获取时间之间间隔小时数
		for(int i=0;i<=l;i++) {
			newDate = DateUtil.getLaterHour(newDate);
			strHour = DateUtil.toStr(DateUtil.getMinHourMinute(newDate), DateUtil.HHMM);
			date.add(strHour);
			
		}
		
		//这个月 的总次数
		int currentMonthCount = daPingDao.getCurrentMonthCount(customerNumber,productOrderId,currentMonth);
		int monthCount = currentMonthCount + sumCount;
		result.put("date", date);//小时
		result.put("lineCart", lineCart);//次数
		result.put("sumCount", sumCount);//当天总次数
		result.put("monthCount", monthCount);//总次数
		result.put("currentDayPage", currentDayPage);//MM月dd日
		result.put("currentMonthPage", currentMonthPage);//MM月
		return result;
	}
	
}

