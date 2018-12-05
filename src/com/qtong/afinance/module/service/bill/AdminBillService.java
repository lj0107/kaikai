package com.qtong.afinance.module.service.bill;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.bill.BillDao;
import com.qtong.afinance.module.dao.portal.AdminOrderSubDao;
import com.qtong.afinance.module.dao.portal.BossCustomerDao;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.statistics.OrderStats;

/**
 * 账单service
 *
 */
@Service
@Transactional
public class AdminBillService {
	
	@Autowired
	private BillDao billDao;
	@Autowired
	private AdminOrderSubDao orderSubDao;
	@Autowired 
	private OrderStatsDao statsDao;
	@Autowired
	private  IJedisClient jedisClient;
	@Autowired
	private  BossCustomerDao bossCustomerDao;
	
	
	/**
	 * 修改order_relation表中的产品中文名称和英文名
	 * @param ProCode 产品编码
	 * @param ProName 产品中文名
	 * @param ProEn 产品英文名
	 */
	public void upProEnAndProName(String ProCode,String ProName,String ProEn) {
	    Map<String, Object> redisMap1 =new HashMap<String, Object>();
		Map<String, String> hgetAll = jedisClient.hgetAll("order_relation");
		Set<String> keys = hgetAll.keySet( );    
		if(keys != null) { 
		Iterator<String> iterator = keys.iterator( );     
		while(iterator.hasNext( )) { //获取value   
		    Boolean flag=false;
			ArrayList<Object> arrayList = new ArrayList<Object>();
		    Object key = iterator.next( );   
		    String string = hgetAll.get(key); 
		    Map map = JSON.parseObject(string, Map.class);
		    List<?> list = (List) map.get("list");//获取list
		    String customer_name = (String) map.get("customer_name");//获取customer_name
		    for (Object object : list) {
		    	Map<String, String> listM = JSON.parseObject(object.toString(), Map.class);
			    if(listM.get("product_code").equals(ProCode)) {
			    	listM.put("product_name", ProName);//产品中文名
			    	listM.put("product_en", ProEn);//产品英文名
			    	flag=true;
			    }
			    	arrayList.add(listM);//更改后的数据封装list
			    } 
		    if(flag) {
		    	redisMap1.put("customer_name", customer_name);//数据封装map
		  	    redisMap1.put("list", arrayList);
		  		jedisClient.hset("order_relation",key.toString(), JSON.toJSONString(redisMap1));
		    }
		  	}
		} 
		
	}
	/**
	 * 后台管理-账单-账单list
	 * @param pageIndex
	 * @param billNumber 账单号
	 * @param customerNumber 客户名称
	 * @param productName 产品名称
	 * @param startTime  起始时间
	 * @param endTime 结束时间
	 * @param countsType 
	 * @param timeType 
	 * @return 
	 */
	public String getAllBills(String pageIndex,String billNumber, String customerName,String productName,
			String startTime,String endTime,String flag, String timeType, String countsType) {

		
		PageData pageData = new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		

		if(endTime!=null && !"".equals(endTime)) {
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTime,DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		}
		
		
		PageData queryBills = billDao.queryBills(pageData,"",billNumber,"", customerName, productName, startTime, endTime,flag,timeType,countsType);
		
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(queryBills, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
		
		
	}
	
	
	/**
	 * 导出后台账单
	 * @param json
	 * @return
	 */
	public ExportExcel exportExcel(String billNumber, String customerName,String productName,
			String startTime,String endTime,String flag) {
		//导出数据录入
		String[] rowsName = null;
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		if(endTime!=null && !"".equals(endTime)) {
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTime,DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		}
		List<Bill> exportBills = new ArrayList<>();

		
		exportBills = billDao.exportBills(billNumber,"", "", customerName, productName, startTime, endTime, flag);
		
		int index=1;
		DecimalFormat df=new DecimalFormat("0.00");
		Map<String,Integer> rowNameMap = new HashMap<String,Integer>();//产品名称map
		Map<String,String> map = new HashMap<String,String>();//产品名称map
		
		
		for (Bill bill : exportBills) {//封装表头
			//获取订购产品名称
			List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(bill.getProductOrderId());
			for(AdminOrderSub product:products){
				
				String proCode = product.getProCode();//产品编码
				String proname = product.getProName();//产品名称
				map.put(proCode, proname);
			}
		}
		
		rowsName = new String[map.keySet().size()*2+7];
		rowsName[0] = "序号";
		rowsName[1] = "账单号";
		rowsName[2] = "账单时间";
		rowsName[3] = "客户名称";
		rowsName[4] = "产品名称";
		int length = 5;
		for (String key : map.keySet()) {
			rowsName[length] = "标签名称";
			rowNameMap.put(key, length);//将产品名称放入map中   key为产品编码 值为下标
			length += 1;
			rowsName[length] = "查询次数";
			length++;
		}
		rowsName[length] = "标签总请求数";
		
		rowsName[length+1] = "金额";
		
        //封装数据		
		for (Bill bill : exportBills) {
			int count = 0;//总请求数
			
			
			String time = "";
			if(bill.getProductName().equals("网址卫士")) {
				time = DateUtil.toStr(bill.getBillTime(), DateUtil.YYYY);
			}else {
				time = DateUtil.toStr(bill.getBillTime(), DateUtil.YYYY_MM);
			}
			
			//获取订购产品名称
			List<AdminOrderSub> products = orderSubDao.getProductByProductOrderId(bill.getProductOrderId());
			
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = bill.getBillNumber();
			objs[2] = time;
			objs[3] = bill.getCustomerName();
			objs[4] = bill.getProductName();
			
			
			
			for(AdminOrderSub product:products){
				
					String proCode = product.getProCode();//产品编码
					//String productName1 = product.getProName();//产品名称
					
					Set<String> keySet = rowNameMap.keySet();
					
					for (String procode : keySet) {
						
						
						if(proCode.equals(procode)) {//产品名称存在
							Integer index2 = rowNameMap.get(procode);
							
							//获取统计信息【当月账单】
							List<OrderStats> orderStats2 = statsDao.getOrderStatsByProductOrderId( bill.getProductOrderId(), proCode, DateUtil.toStr(DateUtil.getMinDayDateTime(DateUtil.getInitMonth(bill.getBillTime())), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(DateUtil.getMaxMonthDateTime(bill.getBillTime()), DateUtil.YYYY_MM_DD_HH_MM_SS));
							int sumCount=0;
							for (OrderStats orderStats : orderStats2) {
								sumCount+=orderStats.getCount();
							}
							
							objs[index2] = map.get(procode);//标签名称
							objs[index2+1] = sumCount;//标签请求次数
							
							count += sumCount;
							
						}else {
							Integer index2 = rowNameMap.get(procode);
							Object object = objs[index2+1];
							if(object!=null) {
								int value = Integer.parseInt(object.toString());
								if(value>0){
									objs[index2] =  map.get(procode);//标签名称
									objs[index2+1] = value;
								}
							}else {
								objs[index2] =  map.get(procode);//标签名称
								objs[index2+1] = 0;
							}
							
						}
						
					}
					
				
			}
			
			objs[length] = count;
			objs[length+1] = df.format(bill.getFee());
			
			
			
			dataList.add(objs);
			
			index++;
			
		}
		
		return new ExportExcel("账单", rowsName, dataList);
	}
	
	
	
	
	
	
	
	

	
}
