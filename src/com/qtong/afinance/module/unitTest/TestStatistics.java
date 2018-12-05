package com.qtong.afinance.module.unitTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.product.AdminProductDao;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.product.AdminProduct;
import com.qtong.afinance.module.pojo.statistics.OrderStats;
import com.qtong.afinance.module.pojo.statistics.UrlGuardStats;
import com.qtong.afinance.module.quartz.StatsQuartz;
import com.qtong.afinance.module.service.portal.AdimOrderService;
import com.qtong.afinance.module.service.portal.AdminOrderSubService;
import com.qtong.afinance.module.service.record.RecordService;
import com.qtong.afinance.module.service.statistics.OrderStatsService;
import com.qtong.afinance.module.service.statistics.UrlGuardStatsService;
import com.qtong.afinance.module.service.urlGuard.AfinUrlGuardService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:WebContent/WEB-INF/applicationContext.xml","file:WebContent/WEB-INF/applicationContext-redis.xml"})
public class TestStatistics {
	@Autowired
	private AdimOrderService orderService;
	@Autowired
	private AdminOrderSubService orderSubService;
	@Autowired
	private RecordService detailRecordService;
	@Autowired
	private OrderStatsService statsService;
	@Autowired
	private UrlGuardStatsService urlGuardStatsService;
	@Autowired
	private AdminProductDao productDao;
	@Autowired
	private AfinUrlGuardService afinUrlGuardService;
	
	@Autowired
	private StatsQuartz statsQuartz;
	
	@Test
	public void getorder(){
		List<AdminProduct> adminProductList = productDao.getAdminProductList("数据标签");
		System.out.println(JSON.toJSONString(adminProductList));
		
	}
	@Test
	public void geto(){
		String string = DigestUtils.md5DigestAsHex("!qaz2wsx".getBytes());
		System.out.println(JSON.toJSONString(string));
		
	}


	public static ArrayList<Map.Entry<String,Integer>> sortMap(Map map){
	    List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
	    Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
	        public int compare(Map.Entry<String, Integer> obj1 , Map.Entry<String, Integer> obj2) {
	            return obj2.getValue() - obj1.getValue();
	        }
	    });
	     return (ArrayList<Entry<String, Integer>>) entries;
   }


	
	@Test
	public void initSt(){
		System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
	}
	
	
	
	//循环生成近7个天前到现在的统计数据（0点）【乾坤大数据】
	
	@Test
	public void initStatistics(){
		
		Date newDate=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD);
		Date oldDate=DateUtil.getDifferDay(newDate, -30);
		
		int max=999;
		int min=0;
		Random random = new Random();
		
		
		//添加order表数据
/*		AdminOrder order=new AdminOrder();
		order.setProductOrderId("423879");
		order.setCustomerNumber("E1002016122610000137");
		order.setCustomerName("物联卡客户4");
		order.setProductName("数据标签");
		order.setEffTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));
		order.setExpTime(Timestamp.valueOf(DateUtil.toStr(newDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));
		order.setBillEffTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));
		order.setState(1);
		order.setProductRatio(1);
		orderService.insert(order);*/
		
		//添加orderSub表数据
/*		AdminOrderSub sub=null;
		sub=new AdminOrderSub();
		sub.setProductOrderId("155");
		sub.setProductOrderNumber("133");
		sub.setProCode("A100014");
		sub.setProName("标签A10014");
		sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setState(0);
		sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setRatio("5:5");
		sub.setReserveRatio("5:5");
		orderSubService.insert(sub);
		sub=new AdminOrderSub();
		sub.setProductOrderId("155");
		sub.setProductOrderNumber("133");
		sub.setProCode("A10015");
		sub.setProName("标签A10015");
		sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setState(0);
		sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setRatio("5:5");
		sub.setReserveRatio("5:5");
		orderSubService.insert(sub);
		sub=new AdminOrderSub();
		sub.setProductOrderId("155");
		sub.setProductOrderNumber("133");
		sub.setProCode("A10017");
		sub.setProName("标签A10017");
		sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setState(0);
		sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setRatio("5:5");
		sub.setReserveRatio("5:5");
		orderSubService.insert(sub);
		sub=new AdminOrderSub();
		sub.setProductOrderId("155");
		sub.setProductOrderNumber("133");
		sub.setProCode("A10018");
		sub.setProName("标签A10018");
		sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setState(0);
		sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setRatio("5:5");
		sub.setReserveRatio("5:5");
		orderSubService.insert(sub);
		sub=new AdminOrderSub();
		sub.setProductOrderId("155");
		sub.setProductOrderNumber("133");
		sub.setProCode("A10019");
		sub.setProName("标签A10019");
		sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setState(0);
		sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setRatio("5:5");
		sub.setReserveRatio("5:5");
		orderSubService.insert(sub);*/
		
		
		
		
		
		
/*		for (int i = 0; i <8 ; i++) {
			AdminOrderSub sub=new AdminOrderSub();
			sub.setSubOrderNum("9"+i);
			sub.setProductOrderId("423879");
			sub.setProductOrderNumber("423879");
			sub.setProCode("D1234"+i);
			sub.setProName("标签D"+i);
			sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
			sub.setState(0);
			sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
			
			sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
			sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
			sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
			sub.setRatio("5:5");
			sub.setReserveRatio("5:5");
			
			orderSubService.insert(sub);
		}*/
		
		
		//添加OrderStats表中数据
		
		
		
		while (oldDate.getTime()<=newDate.getTime()) {
			
			OrderStats stats=null;
			
			stats=new OrderStats();
			stats.setCustomerNumber("E1002016122610000137");//客户编码
			stats.setProductOrderId("155");//产品包订购关系Id
			stats.setProductName("标签A10014");//产品名称
			stats.setProductCode("A10014");//产品编码
			stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
			stats.setCount(random.nextInt(max)%(max-min+1) + min);
			//stats.setFee(random.nextInt(max)%(max-min+1) + min);
			statsService.insert(stats);
			stats=new OrderStats();
			stats.setCustomerNumber("E1002016122610000137");//客户编码
			stats.setProductOrderId("155");//产品包订购关系Id
			stats.setProductName("标签A10015");//产品名称
			stats.setProductCode("A10015");//产品编码
			stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
			stats.setCount(random.nextInt(max)%(max-min+1) + min);
			//stats.setFee(random.nextInt(max)%(max-min+1) + min);
			statsService.insert(stats);
			stats=new OrderStats();
			stats.setCustomerNumber("E1002016122610000137");//客户编码
			stats.setProductOrderId("155");//产品包订购关系Id
			stats.setProductName("标签A10017");//产品名称
			stats.setProductCode("A10017");//产品编码
			stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
			stats.setCount(random.nextInt(max)%(max-min+1) + min);
			//stats.setFee(random.nextInt(max)%(max-min+1) + min);
			statsService.insert(stats);
			stats=new OrderStats();
			stats.setCustomerNumber("E1002016122610000137");//客户编码
			stats.setProductOrderId("155");//产品包订购关系Id
			stats.setProductName("标签A10018");//产品名称
			stats.setProductCode("A10018");//产品编码
			stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
			stats.setCount(random.nextInt(max)%(max-min+1) + min);
			//stats.setFee(random.nextInt(max)%(max-min+1) + min);
			statsService.insert(stats);
			stats=new OrderStats();
			stats.setCustomerNumber("E1002016122610000137");//客户编码
			stats.setProductOrderId("155");//产品包订购关系Id
			stats.setProductName("标签A10019");//产品名称
			stats.setProductCode("A10019");//产品编码
			stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
			stats.setCount(random.nextInt(max)%(max-min+1) + min);
			//stats.setFee(random.nextInt(max)%(max-min+1) + min);
			statsService.insert(stats);
			oldDate=DateUtil.getLaterDay(oldDate);
		}
		
		
		
/*		while (oldDate.getTime()<=newDate.getTime()) {
			
			
			
			
			
			for (int i = 0; i <8 ; i++) {
				OrderStats stats=new OrderStats();
				stats.setCustomerNumber("E1002016122610000137");//客户编码
				stats.setProductOrderId("152");//产品包订购关系Id
				stats.setProductName("标签D"+i);//产品名称
				stats.setProductCode("D1234"+i);//产品编码
				stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
				stats.setCount(random.nextInt(max)%(max-min+1) + min);
				stats.setFee(random.nextInt(max)%(max-min+1) + min);
				statsService.insert(stats);
			}
			
			
			oldDate=DateUtil.getLaterDay(oldDate);
		}*/
		
	}
	
	//循环生成4个月前到现在的统计数据（0点）【和卫士】
	
	@Test
	public void initAndStatistics(){
		
		Date newDate=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD);
		Date oldDate=DateUtil.getDifferDay(newDate, -(4*30));
		
		int max=999;
		int min=0;
		Random random = new Random();
		
		while (oldDate.getTime()<=newDate.getTime()) {
			
			OrderStats stats=new OrderStats();
			stats.setCustomerNumber("E1002016122610000137");//客户编码
			stats.setProductOrderId("153");//产品包订购关系Id
			stats.setProductName("和位士");//产品名称
			stats.setProductCode("C10001");//产品编码
			stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));//生成时间
			stats.setCount(random.nextInt(max)%(max-min+1) + min);
			//stats.setFee(random.nextInt(max)%(max-min+1) + min);
			statsService.insert(stats);
			
			oldDate=DateUtil.getLaterDay(oldDate);
		}
		
		//生成对应产品信息
		
/*		AdminOrderSub sub=new AdminOrderSub();
		sub.setSubOrderNum("333");
		sub.setProductOrderId("333");
		sub.setProductOrderNumber("333");
		sub.setProCode("C12345");
		sub.setProName("和位士");
		sub.setEffTime(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate("2017-8-24", DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		sub.setState(0);
		sub.setUpdateTime(Timestamp.valueOf(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)));
		
		sub.setPrice((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setDiscount((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setSum((long) (random.nextInt(max)%(max-min+1) + min));
		sub.setRatio("3%");
		sub.setReserveRatio("40%");
		
		orderSubService.insert(sub);*/
	}
	
	//循环生成4个月前到现在的统计数据（0点）【网址卫士】
	
	@Test
	public void initUrlStatistics(){
		
		Date newDate=DateUtil.toDate(DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD), DateUtil.YYYY_MM_DD);
		Date oldDate=DateUtil.getDifferDay(newDate, -(4*30));
		
		int max=999;
		int min=0;
		Random random = new Random();
		
		while (oldDate.getTime()<=newDate.getTime()) {
			
			List<String> list=new ArrayList<>();
			list.add("101");//发现恶意网址
			list.add("104");//确认恶意网址
			list.add("106");//拦截恶意网址
			for (String string : list) {
				UrlGuardStats stats=new UrlGuardStats();
				stats.setCount(random.nextInt(max)%(max-min+1) + min);
				stats.setCustomerNumber("E1002016122610000137");
				stats.setRecordTime(Timestamp.valueOf(DateUtil.toStr(oldDate, DateUtil.YYYY_MM_DD_HH_MM_SS)));
				stats.setState(string);
				
				urlGuardStatsService.insert(stats);
			}
			
			
			oldDate=DateUtil.getLaterDay(oldDate);
		}
		
	}
	/**
	 * 测试推送网址添加
	 */
	@Test
	public void saveUrl(){
		StringBuffer str =new StringBuffer();
		str.append("[{'url':'http://baidu6.com','counterfeitDomain':'http://baidu.com','snapshot':'http://baidu.com','customerNo':'E1002016122610000137','customerName':'物联卡客户5','checkTime':'2017年11月14日 14:30:37','interceptCount':100,'invalidTime':'2017年11月14日 14:30:46','siteState':'正常','serverIp':'http://baidu.com','serverLocation':'http://baidu.com','registerPerson':'张','registerMail':'123@qq.com','registerPhone':'13246679968','partnerNo':'7','partnerName':'奇虎360','pushTime':'2017年11月14日 14:31:09','state':'201','optTime':'2017年11月21日 09:47:44',"
				+ "'optId':'25','optName':'李元芳'}");
				//+ "'optId':'"+optId+"','optName':'"+optName+"'}");
		for(int i=7;i<=179;i++) {
			str.append(",{'url':'http://baidu"+i+".com','counterfeitDomain':'http://baidu"+i+".com','snapshot':'http://baidu"+i+".com',"
					+ "'customerNo':'E1002016122610000137','customerName':'物联卡客户5"+i+"','checkTime':'2017年11月14日 14:27:27','interceptCount':"+i*10+","
					+ "'invalidTime':'2017年11月14日 14:28:11','siteState':'正常','serverIp':'http://baidu"+i+".com','serverLocation':'北京"+i+"','registerPerson':'张"+i+"',"
					+ "'registerMail':'123@qq.com','registerPhone':'13246679968','partnerNo':'7','partnerName':'奇虎360','pushTime':'2017年11月14日 14:29:10',"
					+ "'state':'201','opt_time':'2017年11月21日 09:45:42',"
					+ "'optId':'25','optName':'李元芳'}");
					//+ "'optId':'"+optId+"','optName':'"+optName+"'}");
		}
		str.append("]");
		int i = afinUrlGuardService.saveUrl(str.toString());
		if(i > 0) {
			System.out.println("推送网址判断成功！！！");
		} else {
			System.out.println("推送网址判断失败！！！");
		}
		
		
	}
}
