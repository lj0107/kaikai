package com.qtong.afinance.module.service.statistics;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.dao.statistics.RatioStatsDao;
import com.qtong.afinance.module.pojo.partners.AfinPartner;
import com.qtong.afinance.module.pojo.product.AdminProduct;

/**
 * 后台统计-合作伙伴分成比例统计
 *
 */
@Transactional
@Service
public class RatioStatsService {
	@Autowired
	private OrderStatsDao statsDao;
	
	@Autowired
	private RatioStatsDao ratioStatsDao;
	
	/**
	 * 后台统计-合作伙伴分成比例 柱状图
	 * @param partnerId 合作伙伴id
	 * @param customerNumber 客户编码
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public Map<String, Object> getPartnerDivide(String partnerName,String customerNumber,Date beginTime,Date endTime){
		
		Map<String, Object> result=new HashMap<String, Object>();
		
		//2、自定义数据类型
		//2.2	折线图数据类型
		List<String> date=new ArrayList<String>();//坐标横轴日期数据
		List<String> date_2=new ArrayList<String>();//坐标横轴日期数据
		List<Double> list1=new ArrayList<Double>();//乾坤大数据
		List<Double> list2=new ArrayList<Double>();//和位士
		List<Double> list3=new ArrayList<Double>();//网址卫士
	
		DecimalFormat df=new DecimalFormat("0.00");
		
		
		List<Map<String, Object>> cart=new ArrayList<>();//统计图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]
		
		//通过合作伙伴name查出所有的产品编码 list
		List<AdminProduct> queyByPartnerId = ratioStatsDao.queyByPartnerName(partnerName);
		
		
		
		//获取日期数组（横坐标）
		Date date1=beginTime;
		Date date2=endTime;
		while(date1.getTime()<=date2.getTime()) {
		
			
			//遍历list 找出每个产品对应的分成比例  以及  分成比例生效的时间
			Double fee1=0.00;//乾坤大数据
			Double fee2=0.00;//和卫士
			Double fee3=0.00;//网址卫士的总价钱
			
			
			
			for (AdminProduct adminProduct : queyByPartnerId) {
				
				//通过产品编码 查出对应的分成比例 以及生效时间
				String productCode = adminProduct.getProductCode();//产品编码
				
				
				
			
				//通过产品编码 查出对应的分成比例 
				List<Map<String, Object>> queryByProductCode = ratioStatsDao.queryByProductCode(productCode,customerNumber);
	
			
				
				if(adminProduct.getCnname().equals("网址卫士")) {
					
					for (Map<String, Object> map : queryByProductCode) {//遍历分成比例list
						
						
						Date eff_time=(Date) map.get("eff_time");//订单开始时间
						Date exp_time=(Date) map.get("exp_time");//订单结束时间
						int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
						if(eff_time.getTime()>DateUtil.getPerFirstDayOfMonth(date1,1).getTime()  || exp_time.getTime()<date1.getTime()){//订单开始时间>统计开始时间  订单时间结束时间<统计时间
							continue;
						}
						double sum = Double.parseDouble(map.get("sum").toString());
						
						Object ratio = map.get("ratio");//取出的分成比例
						if(ratio==null || "".equals(ratio)) {
							continue;
						}
						String[] split = ratio.toString().split(":");
						
						
						double s = Double.valueOf(Double.valueOf(split[1])/(Double.valueOf(split[0])  + Double.valueOf(split[1])));
						
						double money = sum*s/year/12;//单价*折扣*分成比例 /12= 每个月份的钱
						
						fee3 += money; 
					}
					
				}else {
/**********统计和卫士和乾坤大数据的费用****************************************/
				
				
					for (Map<String, Object> map : queryByProductCode) {//遍历分成比例list
					
						Object ratio = map.get("ratio");//取出的分成比例
						//先判断分成比例是否为空 为空时跳出循环继续下一次循环
						if(ratio==null || "".equals(ratio)) {
							continue;
						}else {
							
							//customerNumber = map.get("customer_number").toString();
							
							String[] split = ratio.toString().split(":");
							
							
							
	
							double s = Double.valueOf(Double.valueOf(split[1])/(Double.valueOf(split[0])  + Double.valueOf(split[1])));
							
							//通过产品编码  和开始时间 结束时间 统计费用   （客户编码）
								Map<String, Object> queryCountFee = ratioStatsDao.queryCountFee(productCode, map.get("customer_number").toString(), DateUtil.toStr(DateUtil.getInitMonth( DateUtil.getInitMonth(date1)), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(DateUtil.getMaxMonthDateTime(date1), DateUtil.YYYY_MM_DD_HH_MM_SS));
								String productName = "";
								String feeData = "0";
								if(queryCountFee.get("product_name")!=null) {
									productName = queryCountFee.get("product_name").toString();
								}
								if(queryCountFee.get("fee")!=null) {
									feeData = queryCountFee.get("fee").toString();
								}
									double fee = Double.parseDouble(feeData);
									if(productName.equals("和位士")) {//和位士
										fee2 += fee*s;
									}/*else if(productName.equals("网址卫士")) {
										
									}*/else{//乾坤大数据
										fee1 += fee*s;
									}
								
							}
					
					}
				}	
			
			}
			
			String format1 = df.format(fee1);
			String format2 = df.format(fee2);
			String format3 = df.format(fee3);
			Boolean flag = false;
			int selectTime = statsDao.selectTimeQK(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),customerNumber);
			if(selectTime>0) {
				//list1.add(Double.parseDouble(format1));
				fee1 = Double.parseDouble(format1);
				flag =true;
			}else {
				//list1.add(null);
				fee1 = null;
			}
			int selectTime1 = statsDao.selectTime(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),"和位士",customerNumber);
			if(selectTime1>0) {
				//list2.add(Double.parseDouble(format2));
				fee2 = Double.parseDouble(format2);
				flag =true;
			}else {
				//list2.add(null);
				fee2 = null;
			}
			int selectTime3 = statsDao.selectTime(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),"网址卫士",customerNumber);
			if(selectTime3>0) {
				//list3.add(Double.parseDouble(format3));
				fee3 = Double.parseDouble(format3);
				flag =true;
			}else {
				//list3.add(null);
				fee3 = null;
			}
			//拼接横坐标
			if(flag) {
				date.add(DateUtil.toStr(date1,DateUtil.MM));
				date_2.add(DateUtil.toStr(date1, DateUtil.YYYY__MM));
				
				
				list1.add(fee1);
				list2.add(fee2);
				list3.add(fee3);
				
				
			}
	
				date1=DateUtil.getPerFirstDayOfMonth(date1);
				
				
			}
		
		result.put("date", date);
		result.put("date2", date_2);
		
		//拼接统计数据
		Map<String, Object> map1=new HashMap<>();
		map1.put("name", "乾坤大数据");
		map1.put("value", list1);
		Map<String, Object> map2=new HashMap<>();
		map2.put("name", "和位士");
		map2.put("value", list2);
		Map<String, Object> map3=new HashMap<>();
		map3.put("name", "网址卫士");
		map3.put("value", list3);
		
		cart.add(map1);
		cart.add(map2);
		cart.add(map3);
		
		// 返回开始时间、结束时间
		result.put("beginTime", DateUtil.toStr(beginTime, DateUtil.YYYY__MM));
		result.put("endTime", DateUtil.toStr(endTime, DateUtil.YYYY__MM));
		
		result.put("cart", cart);
		
		return result;
	}
	
	/**
	 * 导出柱状图
	 * @param partnerName
	 * @param customerNumber
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportPartnerDivide(String partnerName,String customerNumber,Date beginTime,Date endTime){
		// 导出数据录入
		String[] rowsName = new String[] { "序号", "产品类别", "月份", "金额(元)" };
		List<Object[]> dataList = new ArrayList<Object[]>();

		Object[] objs = null;

		// 获取日期数组（横坐标）
		Date date1 = beginTime;
		Date date2 = endTime;
		int index = 1;
		
		//通过合作伙伴name查出所有的产品编码 list
		List<AdminProduct> queyByPartnerId = ratioStatsDao.queyByPartnerName(partnerName);
		
		while(date1.getTime()<=date2.getTime()) {
			
		
			
			
			//遍历list 找出每个产品对应的分成比例  以及  分成比例生效的时间
			DecimalFormat df=new DecimalFormat("0.00");
			double fee1 = 0;//乾坤大数据
			double fee2 = 0;//和卫士
			double fee3 = 0;//网址卫士的总价钱
			
			for (AdminProduct adminProduct : queyByPartnerId) {
				
				//通过产品编码 查出对应的分成比例 以及生效时间
				String productCode = adminProduct.getProductCode();//产品编码
				
				
				
			
				//通过产品编码 查出对应的分成比例 
				List<Map<String, Object>> queryByProductCode = ratioStatsDao.queryByProductCode(productCode,customerNumber);
	
			
				
				if(adminProduct.getCnname().equals("网址卫士")) {
					
					for (Map<String, Object> map : queryByProductCode) {//遍历分成比例list
						
						
						Date eff_time=(Date) map.get("eff_time");//订单开始时间
						Date exp_time=(Date) map.get("exp_time");//订单结束时间
						int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
						if(eff_time.getTime()>DateUtil.getPerFirstDayOfMonth(date1,1).getTime()  || exp_time.getTime()<date1.getTime()){//订单开始时间>统计开始时间  订单时间结束时间<统计时间
							continue;
						}
						//double price = Double.parseDouble(map.get("price").toString());
						//double discount = Double.parseDouble(map.get("discount").toString());
						double sum = Double.parseDouble(map.get("sum").toString());//实收
						Object ratio = map.get("ratio");//取出的分成比例
						if(ratio==null || "".equals(ratio)) {
							continue;
						}
						String[] split = ratio.toString().split(":");
						
						
						double s = Double.valueOf(Double.valueOf(split[1])/(Double.valueOf(split[0])  + Double.valueOf(split[1])));
						
						double money = sum*s/year/12;//单价*折扣*分成比例 /12= 每个月份的钱
						
						fee3 += money; 
					}
				}else {
/**********统计和卫士和乾坤大数据的费用****************************************/
				
				
					for (Map<String, Object> map : queryByProductCode) {//遍历分成比例list
						
						Object ratio = map.get("ratio");//取出的分成比例
						//先判断分成比例是否为空 为空时跳出循环继续下一次循环
						if(ratio==null || "".equals(ratio)) {
							continue;
						}else {
							
							
							String[] split = ratio.toString().split(":");
							
							
							
	
							double s = Double.valueOf(Double.valueOf(split[1])/(Double.valueOf(split[0])  + Double.valueOf(split[1])));
							
							//通过产品编码  和开始时间 结束时间 统计费用   （客户编码）
								Map<String, Object> queryCountFee = ratioStatsDao.queryCountFee(productCode, map.get("customer_number").toString(), DateUtil.toStr(DateUtil.getInitMonth( DateUtil.getInitMonth(date1)), DateUtil.YYYY_MM_DD_HH_MM_SS), DateUtil.toStr(DateUtil.getMaxMonthDateTime(date1), DateUtil.YYYY_MM_DD_HH_MM_SS));
								String productName = "";
								String feeData = "0";
								if(queryCountFee.get("product_name")!=null) {
									productName = queryCountFee.get("product_name").toString();
								}
								if(queryCountFee.get("fee")!=null) {
									feeData = queryCountFee.get("fee").toString();
								}
									double fee = Double.parseDouble(feeData);
									if(productName.equals("和位士")) {//和位士
										fee2 += fee*s;
									}/*else if(productName.equals("网址卫士")) {
										
									}*/else{//乾坤大数据
										fee1 += fee*s;
									}
								
							}
					
					}
				}
				
			}
			// 封装信息
			Boolean flag=false;
			int selectTime = statsDao.selectTimeQK(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),customerNumber);
			if(selectTime>0) {
			objs = new Object[rowsName.length];
			// =>乾坤大数据
				objs[0] = index;
				objs[1] = "乾坤大数据";
				objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
				objs[3] = df.format(fee1);
				dataList.add(objs);
				index++;
				flag=true;
			}
			int selectTime1 = statsDao.selectTime(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),"和位士",customerNumber);
			if(selectTime1>0) {
			// =>和位士
				objs = new Object[rowsName.length];
				objs[0] = index;
				objs[1] = "和位士";
				objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
				objs[3] = df.format(fee2);
				dataList.add(objs);
				index++;
				flag=true;
			}
			int selectTime3 = statsDao.selectTime(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),"网址卫士",customerNumber);
			if(selectTime3>0) {
			// =>网址卫士
				objs = new Object[rowsName.length];
				objs[0] = index;
				objs[1] = "网址卫士";
				objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
				objs[3] = df.format(fee3);
				dataList.add(objs);
				index++;
				flag=true;
			}
			if(flag) {
			// =>所有产品
				objs = new Object[rowsName.length];
				objs[0] = index;
				objs[1] = "所有产品";
				objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
				objs[3] = df.format(fee1+fee2+fee3);
				dataList.add(objs);
				index++;
			}
			

			date1 = DateUtil.getPerFirstDayOfMonth(date1);
		}
		

		return new ExportExcel("各合作伙伴分成比例", rowsName, dataList);
	}
	
	
	
	/**
	 * 饼图
	 * @param customerNumber
	 * @param productType
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map ratioPie(String customerNumber,String productType,Date beginTime,Date endTime){	
		List<Map<String, Object>> pieCart=new ArrayList<>();//饼状图
		List<Map<String, Object>> pieCart2=new ArrayList<>();//列表
		Map mapPartner=new HashMap();
		Map mapPartner1=new HashMap();
		DecimalFormat df=new DecimalFormat("0.00");
		List<AfinPartner> partnerList = ratioStatsDao.selPartnerList();//查所有合作伙伴
		for (AfinPartner afinPartner : partnerList) {
			double total=0;//当前合作伙伴金额	
			List<AdminProduct> products=new ArrayList<AdminProduct>();//待关联订单的产品集合
			if(productType==null || "".equals(productType)){//判断是否带产品类别条件
				products=ratioStatsDao.queyByPartnerId(afinPartner.getId().toString());//根据合作伙伴id查它所有产品
			}else{				
				products=ratioStatsDao.queyByPartnerId(afinPartner.getId().toString(),productType);//根据合作伙伴&产品类别查它所有产品										
			}			
			for (AdminProduct adminProduct : products) {
				List<Map<String,Object>> orders=new ArrayList<Map<String,Object>>();
				if("网址卫士".equals(adminProduct.getCnname())){//网址卫士包年计费
					if(customerNumber==null || "".equals(customerNumber)){//判断是否客户编码条件
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),null);//根据产品编码查订单
						for (Map<String, Object> map : orders) {
							Date eff_time=(Date) map.get("eff_time");
							Date exp_time=(Date) map.get("exp_time");
							int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
							SimpleDateFormat sdfYMd = new SimpleDateFormat("yyyy-MM-dd");
							String aa=sdfYMd.format(eff_time);
							String bb=sdfYMd.format(exp_time);
							try {
								eff_time=sdfYMd.parse(aa);
								exp_time=sdfYMd.parse(bb);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							int month =0;
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							//double feeYear=Double.parseDouble(map.get("price").toString())*Double.parseDouble(map.get("discount").toString())/year/100;//包年总价
							double feeYear=Double.parseDouble(map.get("sum").toString())/year;//包年总价
														

							//订单开始时间>统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								month = DateUtil.getMonth(eff_time,exp_time)+1;
							}	
							//订单开始时间<统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								month = DateUtil.getMonth(beginTime,endTime)+1;
							}
							//订单开始时间<统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								if(exp_time.getTime()<beginTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(beginTime,exp_time)+1;
								}															
							}							
							//订单开始时间>统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								if(eff_time.getTime()>endTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(eff_time,endTime)+1;
								}															
							}
							
							double ratioFee=feeYear/12/ratioSum*Integer.valueOf(ratio2[1]);
							double monthFee = Double.valueOf(df.format(ratioFee))*month;
							total=total+monthFee;									
						}
					}else{
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),customerNumber);//根据产品编码,客户编码 查订单
						for (Map<String, Object> map : orders) {
							Date eff_time=(Date) map.get("eff_time");
							Date exp_time=(Date) map.get("exp_time");
							int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
							int month =0;							
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							double feeYear=Double.parseDouble(map.get("sum").toString())/year;//包年总价
							
							//订单开始时间>统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								month = DateUtil.getMonth(eff_time,exp_time)+1;
							}	
							//订单开始时间<统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								month = DateUtil.getMonth(beginTime,endTime)+1;
							}
							//订单开始时间<统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								if(exp_time.getTime()<beginTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(beginTime,exp_time)+1;
								}															
							}							
							//订单开始时间>统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								if(eff_time.getTime()>endTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(eff_time,endTime)+1;
								}															
							}
							
							double ratioFee=feeYear/12/ratioSum*Integer.valueOf(ratio2[1]);
							double monthFee = Double.valueOf(df.format(ratioFee))*month;
							total=total+monthFee;					
						}
					}										
				}else{//乾坤大数据，和位士标签按次计费
					if(customerNumber==null || "".equals(customerNumber)){//判断是否客户编码条件
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),null);//根据产品编码查订单
						for (Map<String, Object> map : orders) {
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							Double fee=ratioStatsDao.
									selFee(map.get("customer_number").toString(),map.get("pro_code").toString(),beginTime,endTime);//查统计表fee
							Double ratioFee=fee/ratioSum*Integer.valueOf(ratio2[1]);
							total=total+ratioFee;
						}
					}else{
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),customerNumber);//根据产品编码查订单
						for (Map<String, Object> map : orders) {
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							Double fee=ratioStatsDao.
									selFee(map.get("customer_number").toString(),map.get("pro_code").toString(),beginTime,endTime);//查统计表fee
							Double ratioFee=fee/ratioSum*Integer.valueOf(ratio2[1]);
							total=total+ratioFee;
						}
					}					
				}								
			}//遍历product结束
			mapPartner.put(afinPartner.getName(), (new Double(total)).intValue());
			mapPartner1.put(afinPartner.getName(), Double.parseDouble(df.format(total)));
		}//遍历partner结束
		
		//4、拼接返回数据		
		//4.1	拼接饼状图返回数据
		//4.1.1	对Map进行排序（从大到小），
		ArrayList<Map.Entry<String,Integer>> entries= sortMap(mapPartner);
	    //4.1.2	拼接排序前6个数据
	    int tempSize=0;
	    if(mapPartner.size()<6){
	    	tempSize=mapPartner.size();
	    }else{
	    	tempSize=6;
	    }
	    int pieflag=0;
	    for( int i=0;i<tempSize;i++){
	  	  if(entries.get(i).getValue()>0){
	  		  Map<String, Object> map=new HashMap<>();
	  		  //map.put("y", entries.get(i).getValue());
	  		  map.put("y", mapPartner1.get(entries.get(i).getKey()));
	  		  map.put("name", entries.get(i).getKey());
	  		  pieCart.add(map);
	  		  pieCart2.add(map);
	  		  pieflag=i;
	  	  }else{
	  		  break;
	  	  }
	    }
	    
	    //4.1.3	拼接‘其它标签’    说明：如果前6个都有为0的，那么‘其它标签’肯定为0
	    if(pieflag==5){
	  	  Double sum=0.00;
	        for( int i=6;i<mapPartner.size();i++){
	      	  sum += (Double)mapPartner1.get(entries.get(i).getKey());
	  		  Map<String, Object> map=new HashMap<>();
	  		  map.put("y", mapPartner1.get(entries.get(i).getKey()));
	  		  map.put("name", entries.get(i).getKey());
	  		  pieCart2.add(map);
	        }
	        
  		  Map<String, Object> map=new HashMap<>();
  		  map.put("y", sum);
  		  map.put("name", "其它标签");
  		  pieCart.add(map);
	    }else {
	    	if(mapPartner.size()<6){
	    		
	    	}else{
	    		Map<String, Object> map=new HashMap<>();
	    		map.put("y", 0);
	    		map.put("name", "其它标签");
	    		pieCart.add(map);
	    	} 		  
	    }
	    Map result=new HashMap();
	    result.put("pieCart", pieCart);
        result.put("pieCart2", pieCart2);
		
        //返回开始时间、结束时间
        result.put("beginTime", DateUtil.toStr(beginTime, DateUtil.YYYY__MM));
        result.put("endTime", DateUtil.toStr(endTime, DateUtil.YYYY__MM));
		return result;
	}
	
	/**
	 * 导出饼图
	 * @param customerNumber
	 * @param productType
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportPie(String customerNumber,String productType,Date beginTime,Date endTime){	
		DecimalFormat df=new DecimalFormat("0.00");		
		Map mapPartner=new HashMap();		
		List<AfinPartner> partnerList = ratioStatsDao.selPartnerList();//查所有合作伙伴
		for (AfinPartner afinPartner : partnerList) {			
			double total=0;//当前合作伙伴金额	
			List<AdminProduct> products=new ArrayList<AdminProduct>();//待关联订单的产品集合
			if(productType==null || "".equals(productType)){//判断是否带产品类别条件
				products=ratioStatsDao.queyByPartnerId(afinPartner.getId().toString());//根据合作伙伴id查它所有产品
			}else{				
				products=ratioStatsDao.queyByPartnerId(afinPartner.getId().toString(),productType);//根据合作伙伴&产品类别查它所有产品										
			}				
			for (AdminProduct adminProduct : products) {
				List<Map<String,Object>> orders=new ArrayList<Map<String,Object>>();
				if("网址卫士".equals(adminProduct.getCnname())){//网址卫士包年计费
					if(customerNumber==null || "".equals(customerNumber)){//判断是否客户编码条件
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),null);//根据产品编码查订单
						for (Map<String, Object> map : orders) {
							Date eff_time=(Date) map.get("eff_time");
							Date exp_time=(Date) map.get("exp_time");
							int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
							SimpleDateFormat sdfYMd = new SimpleDateFormat("yyyy-MM-dd");
							String aa=sdfYMd.format(eff_time);
							String bb=sdfYMd.format(exp_time);
							try {
								eff_time=sdfYMd.parse(aa);
								exp_time=sdfYMd.parse(bb);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							int month =0;
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							double feeYear=Double.parseDouble(map.get("sum").toString())/year;//包年总价
							
							//订单开始时间>统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								month = DateUtil.getMonth(eff_time,exp_time)+1;
							}	
							//订单开始时间<统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								month = DateUtil.getMonth(beginTime,endTime)+1;
							}
							//订单开始时间<统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								if(exp_time.getTime()<beginTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(beginTime,exp_time)+1;
								}															
							}							
							//订单开始时间>统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								if(eff_time.getTime()>endTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(eff_time,endTime)+1;
								}															
							}
							
							double ratioFee=feeYear/12/ratioSum*Integer.valueOf(ratio2[1]);
							double monthFee = Double.valueOf(df.format(ratioFee))*month;
							total=total+monthFee;											
						}
					}else{
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),customerNumber);//根据产品编码,客户编码 查订单
						for (Map<String, Object> map : orders) {
							Date eff_time=(Date) map.get("eff_time");
							Date exp_time=(Date) map.get("exp_time");
							int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
							int month =0;							
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							double feeYear=Double.parseDouble(map.get("sum").toString())/year;//包年总价
							
							//订单开始时间>统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								month = DateUtil.getMonth(eff_time,exp_time)+1;
							}	
							//订单开始时间<统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								month = DateUtil.getMonth(beginTime,endTime)+1;
							}
							//订单开始时间<统计开始时间 && 订单结束时间<统计结束时间
							if(eff_time.getTime()<beginTime.getTime() && exp_time.getTime()<endTime.getTime()){
								if(exp_time.getTime()<beginTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(beginTime,exp_time)+1;
								}															
							}							
							//订单开始时间>统计开始时间 && 订单结束时间>统计结束时间
							if(eff_time.getTime()>beginTime.getTime() && exp_time.getTime()>endTime.getTime()){
								if(eff_time.getTime()>endTime.getTime()){
									
								}else{
									month = DateUtil.getMonth(eff_time,endTime)+1;
								}															
							}
							
							double ratioFee=feeYear/12/ratioSum*Integer.valueOf(ratio2[1]);
							
							double monthFee = Double.valueOf(df.format(ratioFee))*month;
							total=total+monthFee;						
						}
					}										
				}else{//乾坤大数据，和位士标签按次计费
					if(customerNumber==null || "".equals(customerNumber)){//判断是否客户编码条件
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),null);//根据产品编码查订单
						for (Map<String, Object> map : orders) {
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							Double fee=ratioStatsDao.
									selFee(map.get("customer_number").toString(),map.get("pro_code").toString(),beginTime,endTime);//查统计表fee
							Double ratioFee=fee/ratioSum*Integer.valueOf(ratio2[1]);
							total=total+ratioFee;
						}
					}else{
						orders=ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),customerNumber);//根据产品编码查订单
						for (Map<String, Object> map : orders) {
							String ratio=(String) map.get("ratio");
							if(ratio==null || "".equals(ratio)) {
								continue;
							}
							String[] ratio2 = ratio.split(":");
							int ratioSum=Integer.valueOf(ratio2[0])+Integer.valueOf(ratio2[1]);
							Double fee=ratioStatsDao.
									selFee(map.get("customer_number").toString(),map.get("pro_code").toString(),beginTime,endTime);//查统计表fee
							Double ratioFee=fee/ratioSum*Integer.valueOf(ratio2[1]);
							total=total+ratioFee;
						}
					}					
				}								
			}//遍历product结束
			if(total>0){
				mapPartner.put(afinPartner.getName(), Double.parseDouble(df.format(total)));		
			}
		}//遍历partner结束
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","合作伙伴","开始时间","结束时间","分成金额"};
		List<Object[]>  dataList = new ArrayList<Object[]>();				
		Object[] objs = null;
		int index=1;
		for (Object key : mapPartner.keySet()) {
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = key;
			objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
			objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
			objs[4] = mapPartner.get(key);
			dataList.add(objs);			
			index++;			  
		}
		return new ExportExcel("合作伙伴分成比例", rowsName, dataList);		
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
	 * 查1级产品类别
	 * @return
	 */
	public List selProductTypeLev1(){
		return ratioStatsDao.selProductTypeLev1();
		
	}
}
