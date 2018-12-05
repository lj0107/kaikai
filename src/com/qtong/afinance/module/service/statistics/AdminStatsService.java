package com.qtong.afinance.module.service.statistics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qtong.afinance.module.dao.admin.CustomerDao;
import com.qtong.afinance.module.dao.portal.AdminOrderDao;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.product.AdminProductDao;
import com.qtong.afinance.module.dao.statistics.OrderStatsDao;
import com.qtong.afinance.module.dao.statistics.RatioStatsDao;
import com.qtong.afinance.module.pojo.product.AdminProduct;
import com.qtong.afinance.module.pojo.statistics.OrderStats;

/**
 * 后台统计Service
 * 
 *
 */
@Transactional
@Service
public class AdminStatsService {

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private AdminOrderDao adminOrderDao;
	@Autowired
	private RatioStatsDao ratioStatsDao;
	@Autowired
	private OrderStatsDao statsDao;
	@Autowired
	private AdminProductDao productDao;

	/**
	 * 后台统计-订购客户数
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */

	public Map<String, Object> getOrderState(Date beginTime, Date endTime) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 2、自定义数据类型
		// 2.2 折线图数据类型
		List<String> date = new ArrayList<String>();// 坐标横轴日期数据
		List<String> date_2 = new ArrayList<String>();// 坐标横轴日期数据
		List<Integer> list1 = new ArrayList<>();// 乾坤大数据
		List<Integer> list2 = new ArrayList<>();// 和位士
		List<Integer> list3 = new ArrayList<>();// 网址卫士

		List<Map<String, Object>> cart = new ArrayList<>();// 统计图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]

		// 获取日期数组（横坐标）
		Date date1 = beginTime;
		Date date2 = endTime;
		while (date1.getTime() <= date2.getTime()) {

			int selectTime = statsDao.selectTimeCom(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1));
			if(selectTime>0) {
				// 拼接横坐标
				date.add(DateUtil.toStr(date1, DateUtil.MM));
				date_2.add(DateUtil.toStr(date1, DateUtil.YYYY_MM));
				
			
			List<Map<String, Object>> selectCusList = customerDao.selectCusList();
			int i1 = 0;// 网址卫士的客户统计次数
			int i2 = 0;// 乾坤大数据的客户统计次数
			int i3 = 0; // 和位士的客户统计次数
			List<Map<String, Object>> order1 = adminOrderDao.getOrder1(DateUtil.toStr(DateUtil.getInitMonth(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),
			DateUtil.toStr(DateUtil.getMaxMonthDate(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),"乾坤大数据");
			for (Map<String, Object> map : order1) {
				String string = map.get("customer_number").toString();
				  i1= Integer.parseInt(string);
			}
			List<Map<String, Object>> order2 = adminOrderDao.getOrder1(DateUtil.toStr(DateUtil.getInitMonth(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),
			DateUtil.toStr(DateUtil.getMaxMonthDate(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),"网址卫士");
			Map<String, Object> map2 = new HashMap<>();
			for (Map<String, Object> map : order2) {
				 String string = map.get("customer_number").toString();
				 i2= Integer.parseInt(string);
				
			}
			List<Map<String, Object>> order3 = adminOrderDao.getOrder1(DateUtil.toStr(DateUtil.getInitMonth(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),
			DateUtil.toStr(DateUtil.getMaxMonthDate(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),"和位士");
			for (Map<String, Object> map : order3) {
				 String string = map.get("customer_number").toString();
				 i3= Integer.parseInt(string);
				
			}
			list1.add(i1);
			list2.add(i2);
			list3.add(i3);
			
			
			}
			date1 = DateUtil.getPerFirstDayOfMonth(date1);
		}

		result.put("date", date);
		result.put("date2", date_2);
		// 拼接统计数据
		Map<String, Object> map1 = new HashMap<>();
		map1.put("name", "网址卫士");
		map1.put("value", list2);
		Map<String, Object> map2 = new HashMap<>();
		map2.put("name", "乾坤大数据");
		map2.put("value", list1);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("name", "和位士");
		map3.put("value", list3);

		
		cart.add(map2);
		cart.add(map3);
		cart.add(map1);

		result.put("cart", cart);

		return result;
	}

	/**
	 * 后台统计-客户次数-导出
	 * 
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel excelOrderState(Date beginTime, Date endTime) {
		// 导出数据录入
		String[] rowsName = new String[] { "序号", "产品类别", "月份", "客户次数" };
		List<Object[]> dataList = new ArrayList<Object[]>();

		Object[] objs = null;

		// 获取日期数组（横坐标）
		Date date1 = beginTime;
		Date date2 = endTime;
		int index = 1;
		while (date1.getTime() <= date2.getTime()) {

			int selectTime = statsDao.selectTimeCom(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1));
			if(selectTime>0) {

			int i1 = 0;// 网址卫士的客户统计次数
			int i2 = 0;// 乾坤大数据的客户统计次数
			int i3 = 0; // 和位士的客户统计次数
			List<Map<String, Object>> order1 = adminOrderDao.getOrder1(DateUtil.toStr(DateUtil.getInitMonth(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),
			DateUtil.toStr(DateUtil.getMaxMonthDate(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),"乾坤大数据");
			for (Map<String, Object> map : order1) {
				String string = map.get("customer_number").toString();
				  i1= Integer.parseInt(string);
			}
			List<Map<String, Object>> order2 = adminOrderDao.getOrder1(DateUtil.toStr(DateUtil.getInitMonth(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),
			DateUtil.toStr(DateUtil.getMaxMonthDate(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),"网址卫士");
			Map<String, Object> map2 = new HashMap<>();
			for (Map<String, Object> map : order2) {
				 String string = map.get("customer_number").toString();
				 i2= Integer.parseInt(string);
				
			}
			List<Map<String, Object>> order3 = adminOrderDao.getOrder1(DateUtil.toStr(DateUtil.getInitMonth(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),
			DateUtil.toStr(DateUtil.getMaxMonthDate(DateUtil.getInitMonth(date1)),
					DateUtil.YYYY_MM_DD_HH_MM_SS),"和位士");
			for (Map<String, Object> map : order3) {
				 String string = map.get("customer_number").toString();
				 i3= Integer.parseInt(string);
				
			}
			

			// 封装信息
			objs = new Object[rowsName.length];
			// =>乾坤大数据
			if(i1!=0) {
				objs[0] = index;
				objs[1] = "乾坤大数据";
				objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
				objs[3] = i1;
				dataList.add(objs);
				index++;
			}
			
			// =>和位士
			if(i3!=0) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = "和位士";
			objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
			objs[3] = i3;
			dataList.add(objs);
			index++;
			}
			// =>网址卫士
			if(i2!=0) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = "网址卫士";
			objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
			objs[3] = i2;
			dataList.add(objs);
			index++;
			}
			// =>所有产品
			if(i1+i2+i3!=0) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = "所有产品";
			objs[2] = DateUtil.toStr(date1, DateUtil.YYYY__MM);
			objs[3] = i1+i2+i3;
			dataList.add(objs);
			index++;
			}
			}
			date1 = DateUtil.getPerFirstDayOfMonth(date1);

		}

		return new ExportExcel("订购客户数", rowsName, dataList);
	}

	/**
	 * 后台统计-产品订购客户数
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param productName
	 *            产品名称
	 * @return
	 */
	public Map<String, Object> getCartForCountByProduct(String productName, Date beginTime, Date endTime) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 2、自定义数据类型
		// 2.1 饼状图数据类型
		List<Map<String, Object>> pieCart = new ArrayList<>();// 饼状图最终返回数据 [{"name":"标签一","value":24},{...}]
		List<Map<String, Object>> pieCart2 = new ArrayList<>();// 饼状图最终返回数据 [{"name":"标签一","value":24},{...}]【副本】
		Map<String, Integer> pieTempMap = new TreeMap<String, Integer>();// 饼状图Map临时数据，临时存储所有标签对应的请求次数（为了比较标签的使用次数）[{"标签一":24},{...}]

		
		
		if (productName.equals("乾坤大数据")) {// 乾坤大数据

			// 获取所有的产品的数据
			List<Map<String, Object>> order2 = adminOrderDao.getOrder2("乾坤大数据",
					DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>();
			Map<String, Integer> hashMap = new HashMap<String, Integer>();
			for (Map<String, Object> map : order2) {
				String string = map.get("pro_name").toString();
				int count = Integer.parseInt(map.get("count").toString());
				hashMap.put(string, count);
			}
			for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
				entries.add(entry);
			}

			// 4、拼接返回数据

			// 4.1 拼接饼状图返回数据
			// 4.1.2 拼接排序前6个数据
			int pieflag = 0;
			if(hashMap.size()<6){
			 for (int i = 0; i < hashMap.size(); i++) {
					if (entries.get(i).getValue() > 0) {
						Map<String, Object> map = new HashMap<>();
						map.put("y", entries.get(i).getValue());
						map.put("name", entries.get(i).getKey());
						pieCart.add(map);
						pieCart2.add(map);
						pieflag = i;
					} else {
						break;
					}
				}
           }else {//如果数据超过六个
				for (int i = 0; i < 6; i++) {
					if (entries.get(i).getValue() > 0) {
						Map<String, Object> map = new HashMap<>();
						map.put("y", entries.get(i).getValue());
						map.put("name", entries.get(i).getKey());
						pieCart.add(map);
						pieCart2.add(map);
						pieflag = i;
					} else {
						break;
					}
				}
			}
			// 4.1.3 拼接‘其它标签’ 说明：如果前6个都有为0的，那么‘其它标签’肯定为0
			if (pieflag == 5) {
				Double sum = 0.00;
				for (int i = 6; i < hashMap.size(); i++) {
					sum += entries.get(i).getValue();
					Map<String, Object> map = new HashMap<>();
					map.put("y", entries.get(i).getValue());
					map.put("name", entries.get(i).getKey());
					pieCart2.add(map);
				}

				Map<String, Object> map = new HashMap<>();
				map.put("y", sum);
				map.put("name", "其它标签");
				pieCart.add(map);
			}

		} 
		else if (productName.equals("和位士")) {// 和位士
			// 获取所有的产品的数据
			List<Map<String, Object>> order2 = adminOrderDao.getOrder2("和位士",
					DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			Map<String, Integer> hashMap = new HashMap<String, Integer>();
			Map<String, Object> map1 = new HashMap<>();
			for (Map<String, Object> map : order2) {
				String string = map.get("pro_name").toString();
				int count = Integer.parseInt(map.get("count").toString());
				hashMap.put(string, count);
			}
			// 4、拼接返回数据
			// 4.1 拼接饼状图返回数据
			for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				map1.put("y", value);
				map1.put("name", key);
			}
			pieCart.add(map1);
			pieCart2.add(map1);
		} else {// 网址位士
				// 获取所有的产品的数据
			List<Map<String, Object>> order2 = adminOrderDao.getOrder2("网址卫士",
					DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
			Map<String, Integer> hashMap = new HashMap<String, Integer>();
			Map<String, Object> map1 = new HashMap<>();
			for (Map<String, Object> map : order2) {
				String string = map.get("pro_name").toString();
				int count = Integer.parseInt(map.get("count").toString());
				hashMap.put(string, count);
			}
			// 4、拼接返回数据
			// 4.1 拼接饼状图返回数据
			for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				map1.put("y", value);
				map1.put("name", key);
			}
			pieCart.add(map1);
			pieCart2.add(map1);

		}

		// 返回最终数据
		result.put("pieCart", pieCart);
		result.put("pieCart2", pieCart2);

		// 返回开始时间、结束时间
		result.put("beginTime", DateUtil.toStr(beginTime, DateUtil.YYYY__MM));
		result.put("endTime", DateUtil.toStr(endTime, DateUtil.YYYY__MM));

		return result;

	}

	/**
	 * 后台统计-产品订购客户数-导出
	 * 
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param productName
	 *            产品名称
	 * @return
	 */
	public ExportExcel excelOrderSubState(String productName, Date beginTime, Date endTime) {

		// 导出数据录入
				String[] rowsName = new String[] { "序号", "产品名称", "开始时间", "结束时间", "客户次数" };
				List<Object[]> dataList = new ArrayList<Object[]>();

				Object[] objs = null;
				if (productName.equals("乾坤大数据")) {// 乾坤大数据
					int index = 1;
					// 获取所有的产品的数据
					List<Map<String, Object>> order2 = adminOrderDao.getOrder2("乾坤大数据",
							DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
							DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
				
					for (Map<String, Object> map : order2) {
						String proName = map.get("pro_name").toString();
						int count = Integer.parseInt(map.get("count").toString());
						// 封装信息
						objs = new Object[rowsName.length];
						// =>乾坤大数据
						objs[0] = index;
						objs[1] = proName;
						objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
						objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
						objs[4] = count;
						dataList.add(objs);
						index++;
					}    
				
				} else if (productName.equals("和位士")) {// 和位士
					// 获取所有的产品的数据
					List<Map<String, Object>> order2 = adminOrderDao.getOrder2("和位士",
							DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
							DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));	
					int index = 1;
					for (Map<String, Object> map : order2) {
						String proName = map.get("pro_name").toString();
						int count = Integer.parseInt(map.get("count").toString());
						// 封装信息
						objs = new Object[rowsName.length];
					    // =>和位士
						objs[0] = index;
						objs[1] = proName;
						objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
						objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
						objs[4] = count;
						dataList.add(objs);
						index++;
					}
					
				} else {// 网址位士
						// 获取所有的产品的数据
					List<Map<String, Object>> order2 = adminOrderDao.getOrder2("网址卫士",
							DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
							DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));
					int index = 1;
					for (Map<String, Object> map : order2) {
						String proName = map.get("pro_name").toString();
						int count = Integer.parseInt(map.get("count").toString());
						// 封装信息
						objs = new Object[rowsName.length];
					    // =>和位士
						objs[0] = index;
						objs[1] = proName;
						objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
						objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
						objs[4] = count;
						dataList.add(objs);
						index++;
						
					}
					
				}

				// 返回最终数据
				return new ExportExcel("产品订购客户数", rowsName, dataList);

			}

	/**
	 * 后台统计-业务金额
	 * 
	 * @param customerNumber
	 *            客户编码
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public Map<String, Object> getCartForFee(String customerNumber, Date beginTime, Date endTime) {
		DecimalFormat df=new DecimalFormat("0.00");
		Map<String, Object> result = new HashMap<String, Object>();

		// 2、自定义数据类型
		// 2.2 柱状图数据类型
		List<String> date = new ArrayList<String>();// 坐标横轴日期数据
		List<String> date_2 = new ArrayList<String>();// 坐标横轴日期数据
		List<Double> list1 = new ArrayList<>();// 乾坤大数据
		List<Double> list2 = new ArrayList<>();// 和位士
 		List<Double> list3 = new ArrayList<>();// 网址卫士

		List<Map<String, Object>> cart = new ArrayList<>();// 统计图最终返回数据 [{"name":"标签1","value":[21,31,22]},{...}]
		
		// 获取日期数组（横坐标）
		Date date1 = beginTime;
		Date date2 = endTime;
		while (date1.getTime() <= date2.getTime()) {
			
			boolean falg=false;
			
			/**
			 * 无业务的月
			 */
			
										
			// 拼接柱状图
			// 统计乾坤大数据数据
			Double fee1 = statsDao.getOrderStatsFee(customerNumber, "和位士",
					DateUtil.toStr(DateUtil.getInitMonth(date1), DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(DateUtil.getMaxMonthDateTime(date1), DateUtil.YYYY_MM_DD_HH_MM_SS), true);
			//查询订单生效时间和结束时间
			int selectTime = statsDao.selectTimeQK(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),customerNumber);
			if(selectTime>0) {
				falg=true;
			}else {
				fee1 = null;
			}
			
			// 统计和位士数据
			Double fee2 = statsDao.getOrderStatsFee(customerNumber, "和位士",
					DateUtil.toStr(DateUtil.getInitMonth(date1), DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(DateUtil.getMaxMonthDateTime(date1), DateUtil.YYYY_MM_DD_HH_MM_SS), false);
			int selectTime1 = statsDao.selectTime(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),"和位士",customerNumber);
			if(selectTime1>0) {
			falg=true;
			}else {
				fee2 = null;
			}
		
			// 统计网址卫士
			Double fee3 = 0.00;
			List<AdminProduct> productList = productDao.getAdminProductByCnName("网址卫士");
			if(productList!=null&&productList.size()>0){
				
				AdminProduct adminProduct = productList.get(0);
				// 通过产品编码 查询金额
				List<Map<String, Object>> queryByProductCode = ratioStatsDao
						.queryByProductCode(adminProduct.getProductCode(), customerNumber);
				
				if (adminProduct.getCnname().equals("网址卫士")) {
					
					for (Map<String, Object> map : queryByProductCode) {// 遍历分成比例list
						
						Date eff_time = (Date) map.get("eff_time");// 订单开始时间
						Date exp_time = (Date) map.get("exp_time");// 订单结束时间
						int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
						if (eff_time.getTime() > DateUtil.getPerFirstDayOfMonth(date1, 1).getTime()
								|| exp_time.getTime() < date1.getTime()) {// 订单开始时间>统计开始时间 订单时间结束时间<统计时间
							continue;
						}
						double sum = Double.parseDouble(map.get("sum").toString());
						
						double money = sum/year/12;// 单价*折扣 /12= 每个月份的钱
						
						fee3 += money;
					}
				}
			}
			int selectTime2 = statsDao.selectTime(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),"网址卫士",customerNumber);
			if(selectTime2>0) {
				fee3=Double.valueOf(df.format(fee3));
				falg=true;
			}else {
				fee3=null;
			}
			// 拼接横坐标
			if(falg) {
				
				date.add(DateUtil.toStr(date1, DateUtil.MM));
				date_2.add(DateUtil.toStr(date1, DateUtil.YYYY_MM));
				
				list1.add(fee1);
				list2.add(fee2);
				list3.add(fee3);
				
				
			}
			date1 = DateUtil.getPerFirstDayOfMonth(date1);
		}
		result.put("date", date);
		result.put("date2", date_2);

		// 拼接统计数据
		Map<String, Object> map1 = new HashMap<>();
		map1.put("name", "乾坤大数据");
		map1.put("value", list1);
		
		Map<String, Object> map2 = new HashMap<>();
		map2.put("name", "和位士");
		map2.put("value", list2);
		Map<String, Object> map3 = new HashMap<>();
		map3.put("name", "网址卫士");
		map3.put("value", list3);

		cart.add(map1);
		cart.add(map2);
		cart.add(map3);

		result.put("cart", cart);
		
		return result;
		
	}

	/**
	 * 后台统计-业务金额-产品金额
	 * 
	 * @param productName
	 *            产品名称
	 * @param customerNumber
	 *            客户编码
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public Map<String, Object> getCartForFeeByProduct(String productName, String customerNumber, Date beginTime,
			Date endTime) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 2、自定义数据类型
		// 2.1 饼状图数据类型
		List<Map<String, Object>> pieCart = new ArrayList<>();// 饼状图最终返回数据 [{"name":"标签一","value":24},{...}]
		List<Map<String, Object>> pieCart2 = new ArrayList<>();// 饼状图最终返回数据 [{"name":"标签一","value":24},{...}]【副本】
		Map<String, Double> pieTempMap = new TreeMap<String, Double>();// 饼状图Map临时数据，临时存储所有标签对应的请求次数（为了比较标签的使用次数）[{"标签一":24},{...}]
		Map<String, Integer> pieTempMap2 = new TreeMap<String, Integer>();// 饼状图Map临时数据，临时存储所有标签对应的请求次数（为了比较标签的使用次数）[{"标签一":24},{...}]

		if (productName.equals("乾坤大数据")) {// 乾坤大数据
			// 获取所有标签集合
			List<AdminProduct> productList = productDao.getAdminProductList("数据标签");

			// 根据客户编码、产品编码查询统计信息【时间段内】
			for (AdminProduct product : productList) {
				String proCode = product.getProductCode();// 产品编码
				String proName = product.getCnname();// 产品名称

				List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, proCode,
						DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
						DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));

				// 将数据标签统计信息分别放到临时数据中，待后面处理
				List<Double> list = new ArrayList<>();
				Double sum = 0.00;
				for (OrderStats stats : orderStats) {
					list.add(stats.getFee());
					sum += stats.getFee();
				}
				pieTempMap.put(proName, sum);
				pieTempMap2.put(proName, sum.intValue());
			}

			// 4、拼接返回数据

			// 4.1 拼接饼状图返回数据
			// 4.1.1 对Map进行排序（从大到小），
			ArrayList<Map.Entry<String, Integer>> entries = sortMap(pieTempMap2);
			// 4.1.2 拼接排序前6个数据
			int pieflag = 0;
			if(pieTempMap.size()<6){//如果数据不超过6个
				for (int i = 0; i < pieTempMap.size(); i++) {
					if (entries.get(i).getValue() > 0) {
						Map<String, Object> map = new HashMap<>();
						//map.put("y", entries.get(i).getValue());
						map.put("y", pieTempMap.get(entries.get(i).getKey()));
						map.put("name", entries.get(i).getKey());
						pieCart.add(map);
						pieCart2.add(map);
						pieflag = i;
					} else {
						break;
					}
				}
			}else {//如果数据超过六个
				for (int i = 0; i < 6; i++) {
					if (entries.get(i).getValue() > 0) {
						Map<String, Object> map = new HashMap<>();
						map.put("y", pieTempMap.get(entries.get(i).getKey()));
						map.put("name", entries.get(i).getKey());
						pieCart.add(map);
						pieCart2.add(map);
						pieflag = i;
					} else {
						break;
					}
				}
			}
			

			// 4.1.3 拼接‘其它标签’ 说明：如果前6个都有为0的，那么‘其它标签’肯定为0
			if (pieflag == 5) {
				Double sum = 0.00;
				for (int i = 6; i < pieTempMap.size(); i++) {
					sum += entries.get(i).getValue();
					Map<String, Object> map = new HashMap<>();
					map.put("y", pieTempMap.get(entries.get(i).getKey()));
					map.put("name", entries.get(i).getKey());
					pieCart2.add(map);
				}

				Map<String, Object> map = new HashMap<>();
				map.put("y", sum);
				map.put("name", "其它标签");
				pieCart.add(map);
			}

		} else if (productName.equals("和位士")) {// 和位士
			List<AdminProduct> productList = productDao.getAdminProductByCnName("和位士");

			// 根据客户编码、产品编码查询统计信息【时间段内】
			for (AdminProduct product : productList) {// 实际上只有一条数据【和位士】
				String proCode = product.getProductCode();// 产品编码
				String proName = product.getCnname();// 产品名称

				List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, proCode,
						DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
						DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));

				// 将数据标签统计信息分别放到临时数据中，待后面处理
				List<Double> list = new ArrayList<>();
				Double sum = 0.00;
				for (OrderStats stats : orderStats) {
					list.add(stats.getFee());
					sum += stats.getFee();
				}

				Map<String, Object> map = new HashMap<>();
				map.put("y", sum);
				map.put("name", proName);
				pieCart.add(map);
				pieCart2.add(map);

			}

			// 4.1.4 返回最终数据
			result.put("pieCart", pieCart);

		} else {// 网址位士
			DecimalFormat df=new DecimalFormat("0.00");
			Double fee_3 = 0.00;
			Date date1 = beginTime;
			Date date2 = endTime;
			while (date1.getTime() <= date2.getTime()) {

				Double fee3 = 0.00;
				List<AdminProduct> productList = productDao.getAdminProductByCnName("网址卫士");
				AdminProduct adminProduct = productList.get(0);

				// 通过产品编码 查询金额
				List<Map<String, Object>> queryByProductCode = ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),
						customerNumber);

				if (adminProduct.getCnname().equals("网址卫士")) {

					for (Map<String, Object> map : queryByProductCode) {// 遍历分成比例list

						Date eff_time = (Date) map.get("eff_time");// 订单开始时间
						Date exp_time = (Date) map.get("exp_time");// 订单结束时间
						int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
						if (eff_time.getTime() > DateUtil.getPerFirstDayOfMonth(date1, 1).getTime()
								|| exp_time.getTime() < date1.getTime()) {// 订单开始时间>统计开始时间 订单时间结束时间<统计时间
							continue;
						}
						//double price = Double.parseDouble(map.get("price").toString());
						//double discount = Double.parseDouble(map.get("discount").toString());
						double sum = Double.parseDouble(map.get("sum").toString());
						
						double money = sum/year /12;// 单价*折扣 /12= 每个月份的钱

						fee3 += money;
					}
				}
				fee_3 += fee3;
				date1 = DateUtil.getPerFirstDayOfMonth(date1);
			}

			Map<String, Object> map = new HashMap<>();
			map.put("y", Double.parseDouble(df.format(fee_3)));
			map.put("name", "网址卫士");
			pieCart.add(map);
			pieCart2.add(map);
		}

		// 返回最终数据
		result.put("pieCart", pieCart);
		result.put("pieCart2", pieCart2);

		// 返回开始时间、结束时间
		result.put("beginTime", DateUtil.toStr(beginTime, DateUtil.YYYY__MM));
		result.put("endTime", DateUtil.toStr(endTime, DateUtil.YYYY__MM));

		return result;

	}

	/**
	 * 后台统计-业务金额-导出
	 * 
	 * @return
	 */
	public ExportExcel exportCartForFee(String customerNumber, Date beginTime, Date endTime) {
		DecimalFormat df=new DecimalFormat("0.00");
		
		// 导出数据录入
		String[] rowsName = new String[] { "序号", "产品类别", "月份", "金额" };
		List<Object[]> dataList = new ArrayList<Object[]>();

		Object[] objs = null;

		// 获取日期数组（横坐标）
		Date date1 = beginTime;
		Date date2 = endTime;
		int index = 1;
		while (date1.getTime() <= date2.getTime()) {
	
			// 统计乾坤大数据数据
			Double fee1 = statsDao.getOrderStatsFee(customerNumber, "和位士",
					DateUtil.toStr(DateUtil.getInitMonth(date1), DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(DateUtil.getMaxMonthDateTime(date1), DateUtil.YYYY_MM_DD_HH_MM_SS), true);
			
			// 统计和位士数据
			Double fee2 = statsDao.getOrderStatsFee(customerNumber, "和位士",
					DateUtil.toStr(DateUtil.getInitMonth(date1), DateUtil.YYYY_MM_DD_HH_MM_SS),
					DateUtil.toStr(DateUtil.getMaxMonthDateTime(date1), DateUtil.YYYY_MM_DD_HH_MM_SS), false);
			// 统计网址卫士
			Double fee3 = 0.00;
			List<AdminProduct> productList = productDao.getAdminProductByCnName("网址卫士");
			AdminProduct adminProduct = productList.get(0);

			// 通过产品编码 查询金额
			List<Map<String, Object>> queryByProductCode = ratioStatsDao
					.queryByProductCode(adminProduct.getProductCode(), customerNumber);

			if (adminProduct.getCnname().equals("网址卫士")) {

				for (Map<String, Object> map : queryByProductCode) {// 遍历分成比例list

					Date eff_time = (Date) map.get("eff_time");// 订单开始时间
					Date exp_time = (Date) map.get("exp_time");// 订单结束时间
					int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
					if (eff_time.getTime() > DateUtil.getPerFirstDayOfMonth(date1, 1).getTime()
							|| exp_time.getTime() < date1.getTime()) {// 订单开始时间>统计开始时间 订单时间结束时间<统计时间
						continue;
					}
					//double price = Double.parseDouble(map.get("price").toString());
					//double discount = Double.parseDouble(map.get("discount").toString());
					double sum = Double.parseDouble(map.get("sum").toString());
					
					double money = sum/year /12;// 单价*折扣 /12= 每个月份的钱

					fee3 += money;
				}
			}

			// 封装信息
			Boolean flag=false;
			int selectTime = statsDao.selectTimeQK(DateUtil.getMaxMonthDateTime(date1),DateUtil.getInitMonth(date1),customerNumber);
			if(selectTime>0) {
			objs = new Object[rowsName.length];
			String format = df.format(fee1);
		  
		
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
			//=>所有产品
			if(flag) {
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

		return new ExportExcel("业务金额", rowsName, dataList);
	}

	/**
	 * 后台统计-业务金额-产品金额-导出
	 * 
	 * @param productName
	 *            产品名称
	 * @param customerNumber
	 *            客户编码
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return
	 */
	public ExportExcel exportCartForFeeByProduct(String customerNumber, String productName, Date beginTime,
			Date endTime) {

		DecimalFormat df=new DecimalFormat("0.00");
		
		// 导出数据录入
		String[] rowsName = new String[] { "序号", "产品名称", "开始时间", "结束时间", "金额" };
		List<Object[]> dataList = new ArrayList<Object[]>();

		Object[] objs = null;

		if (productName.equals("乾坤大数据")) {// 乾坤大数据
			// 获取所有标签集合
			List<AdminProduct> productList = productDao.getAdminProductList("数据标签");

			// 根据客户编码、产品编码查询统计信息【时间段内】
			int index = 1;
			for (AdminProduct product : productList) {
				String proCode = product.getProductCode();// 产品编码
				String proName = product.getCnname();// 产品名称

				List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, proCode,
						DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
						DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));

				// 将数据标签统计信息分别放到临时数据中，待后面处理
				List<Double> list = new ArrayList<>();
				Double sum = 0.00;
				for (OrderStats stats : orderStats) {
					list.add(stats.getFee());
					sum += stats.getFee();
				}
				
				if(sum<=0)
					continue;
				
				// 封装信息
				objs = new Object[rowsName.length];
				// =>乾坤大数据
				objs[0] = index;
				objs[1] = proName;
				objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
				objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
				objs[4] = df.format(sum);
				dataList.add(objs);
				index++;

			}

		} else if (productName.equals("和位士")) {// 和位士
			List<AdminProduct> productList = productDao.getAdminProductByCnName("和位士");

			// 根据客户编码、产品编码查询统计信息【时间段内】
			int index = 1;
			for (AdminProduct product : productList) {// 实际上只有一条数据【和位士】
				String proCode = product.getProductCode();// 产品编码
				String proName = product.getCnname();// 产品名称

				List<OrderStats> orderStats = statsDao.getOrderStats(customerNumber, proCode,
						DateUtil.toStr(beginTime, DateUtil.YYYY_MM_DD_HH_MM_SS),
						DateUtil.toStr(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS));

				// 将数据标签统计信息分别放到临时数据中，待后面处理
				List<Double> list = new ArrayList<>();
				Double sum = 0.00;
				for (OrderStats stats : orderStats) {
					list.add(stats.getFee());
					sum += stats.getFee();
				}
				if(sum<=0)
					continue;
				// 封装信息
				objs = new Object[rowsName.length];
				// =>和位士
				objs[0] = index;
				objs[1] = proName;
				objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
				objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
				objs[4] = df.format(sum);
				dataList.add(objs);
				index++;
			}

		} else {// 网址位士
			Double fee_3 = 0.00;
			Date date1 = beginTime;
			Date date2 = endTime;
			while (date1.getTime() <= date2.getTime()) {

				Double fee3 = 0.00;
				List<AdminProduct> productList = productDao.getAdminProductByCnName("网址卫士");
				AdminProduct adminProduct = productList.get(0);

				// 通过产品编码 查询金额
				List<Map<String, Object>> queryByProductCode = ratioStatsDao.queryByProductCode(adminProduct.getProductCode(),
						customerNumber);

				if (adminProduct.getCnname().equals("网址卫士")) {

					for (Map<String, Object> map : queryByProductCode) {// 遍历分成比例list

						Date eff_time = (Date) map.get("eff_time");// 订单开始时间
						Date exp_time = (Date) map.get("exp_time");// 订单结束时间
						int year = DateUtil.getYear(eff_time, exp_time);//订购的年数
						if (eff_time.getTime() > DateUtil.getPerFirstDayOfMonth(date1, 1).getTime()
								|| exp_time.getTime() < date1.getTime()) {// 订单开始时间>统计开始时间 订单时间结束时间<统计时间
							continue;
						}
						//double price = Double.parseDouble(map.get("price").toString());
						//double discount = Double.parseDouble(map.get("discount").toString());
						double sum = Double.parseDouble(map.get("sum").toString());
						
						double money = sum/year /12;// 单价*折扣 /12= 每个月份的钱

						fee3 += money;
					}
				}
				fee_3 += fee3;

				date1 = DateUtil.getPerFirstDayOfMonth(date1);
			}

			// 封装信息
			objs = new Object[rowsName.length];
			// =>网址卫士
			objs[0] = 1;
			objs[1] = "网址卫士";
			objs[2] = DateUtil.toStr(beginTime, DateUtil.YYYY__MM);
			objs[3] = DateUtil.toStr(endTime, DateUtil.YYYY__MM);
			objs[4] = df.format(fee_3);
			dataList.add(objs);
		}

		return new ExportExcel("业务金额", rowsName, dataList);

	}

	private static ArrayList<Map.Entry<String, Integer>> sortMap(Map map) {
		List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
				return obj2.getValue() - obj1.getValue();
			}
		});
		return (ArrayList<Entry<String, Integer>>) entries;
	}

}
