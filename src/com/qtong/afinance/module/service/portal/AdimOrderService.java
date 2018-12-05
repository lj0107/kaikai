package com.qtong.afinance.module.service.portal;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.component.IJedisClient;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.portal.AdminOrderDao;
import com.qtong.afinance.module.pojo.product.AdminOrder;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCount;

/**
 * 控制台-乾坤大数据-我的订单Service
 */
@Service
@Transactional
public class AdimOrderService {
	@Autowired
	private   AdminOrderDao adminOrderDao;
	
	/**
	 * 根据客户编码和产品名称查询所有订单
	 * @param customerNumber
	 * @param productName
	 * @return
	 */
	public String getOrderByCustomerNumber(int pageIndex,String customerNumber,String productName) {
		PageData pageData = adminOrderDao.getOrders(pageIndex,customerNumber,productName);
		JSON.DEFFAULT_DATE_FORMAT = "yyyy年MM月dd日 HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	}
	
	
	
	
	
	

	@Autowired
	private  IJedisClient jedisClient;
	/**
	 * 控制台-乾坤大数据-我的订单  根据客户编码查询
	 * 包括条件查询
	 * @param pageIndex
	 * @param customerNumber
	 * @param orderNumber
	 * @param state
	 * @param firstTime
	 * @param secondTime
	 * @return 
     */
	public String selectOrderByTermList(String selectByTerm) {
     
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
	    String customerNumber = jasonObject.get("customerNumber").toString();
	    String productC = jasonObject.get("productC").toString();
		
		Object productOrderIdObj = jasonObject.get("productOrderId");
		String productOrderId="";
		if(!"".equals(productOrderIdObj)&&productOrderIdObj!=null) {
			productOrderId = productOrderIdObj.toString();
		}
			
		Object stateObj = jasonObject.get("state");
		String state="";
		if(!"".equals(stateObj)&&stateObj!=null) {
			state = stateObj.toString();
		}
		Object productNameObj = jasonObject.get("productName");
		String productName="";
		if(!"".equals(productNameObj)&&productNameObj!=null) {
			productName = productNameObj.toString();
		}
		Object firstTimeObj = jasonObject.get("firstTime");
		String firstTime="";
		if(!"".equals(firstTimeObj)&&firstTimeObj!=null) {
			firstTime = firstTimeObj.toString();
		}
		Object secondTimeObj = jasonObject.get("secondTime");
		String secondTime="";
		if(!"".equals(secondTimeObj)&&secondTimeObj!=null) {
			String secondTimeDay = secondTimeObj.toString();
			 secondTime=secondTimeDay+" 24:60:60";//精确的到秒
		}			
		PageData pageData = adminOrderDao.selectOrderByTermListNew(pageIndex,productC,customerNumber,productName,productOrderId,state,firstTime,secondTime);		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	}
	/**
	 * 后台管理-订单管理 
	 * 包括条件查询
	 * @param pageIndex
	 * @param customerNumber
	 * @param orderNumber
	 * @param state
	 * @param firstTime
	 * @param secondTime
	 * @return 
     */
	public String selectOrderByList(String selectByTerm) {
		getOrderRatio();
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
		Object timeTypeObj = jasonObject.get("timeType");
		String timeType="";
		if(!"".equals(timeTypeObj)&&timeTypeObj!=null) {
			timeType = timeTypeObj.toString();
		}
		Object countsTypeObj = jasonObject.get("countsType");
		String countsType="";
		if(!"".equals(countsTypeObj)&&countsTypeObj!=null) {
			countsType = countsTypeObj.toString();
		}
		Object productOrderIdObj = jasonObject.get("productOrderId");
		String productOrderId="";
		if(!"".equals(productOrderIdObj)&&productOrderIdObj!=null) {
			productOrderId = productOrderIdObj.toString();
		}
		Object customerNameObj = jasonObject.get("customerName");
		String customerName="";
		if(!"".equals(customerNameObj)&&customerNameObj!=null) {
			customerName = customerNameObj.toString();
		}		
		Object stateObj = jasonObject.get("state");
		String state="";
		if(!"".equals(stateObj)&&stateObj!=null) {
			state = stateObj.toString();
		}
		Object productRatioObj = jasonObject.get("productRatio");
		String productRatio="";
		if(!"".equals(productRatioObj)&&productRatioObj!=null) {
			productRatio = productRatioObj.toString();
		}
		Object productNameObj = jasonObject.get("productName");
		String productName="";
		if(!"".equals(productNameObj)&&productNameObj!=null) {
			productName = productNameObj.toString();
		}
		Object firstTimeObj = jasonObject.get("firstTime");
		String firstTime="";
		if(!"".equals(firstTimeObj)&&firstTimeObj!=null) {
			firstTime = firstTimeObj.toString();
		}
		Object secondTimeObj = jasonObject.get("secondTime");
		String secondTime="";
		if(!"".equals(secondTimeObj)&&secondTimeObj!=null) {
			String secondTimeDay = secondTimeObj.toString();
			 secondTime=secondTimeDay+" 24:60:60";//精确的到秒
		}			
		PageData pageData = adminOrderDao.selectOrderByTermList(pageIndex,timeType,countsType,customerName,productName,productOrderId,state,firstTime,secondTime,productRatio);		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		return jsonString;
	}
	
	/**
	 * productName  产品包名称 分成比例状态
	 * @return
	 */
	public  void getOrderRatio(){		
		List<Map<String, Object>> ratio = adminOrderDao.getRatio();	
		Map<String,String> mapReturn = new HashMap<String,String>();
		for (Map<String, Object> map : ratio) {			
				Object object = map.get("ratio");		
					Object object2 = map.get("product_order_id");
					mapReturn.put(object2.toString(), "1");
				/*else {
					Object object3 = map.get("product_order_id");
					mapReturn.put(object3.toString(), "1");
					System.out.println("1:"+object);
				}*/
				
			}
	    List<AdminOrder> arrayList = new ArrayList<AdminOrder>();
		Set<String> keySet = mapReturn.keySet();
		for (String string : keySet) {
			String string2 = mapReturn.get(string);		
			AdminOrder adminOrder = new AdminOrder();
			adminOrder.setProductOrderId(string);
			adminOrder.setProductRatio(Integer.parseInt(string2));
			arrayList.add(adminOrder);			
		}
		adminOrderDao.getsate();
		adminOrderDao.batchUpdateLinkset(arrayList);
		
	}
	
	
	/**
	 * 根据产品包订购关系Id获取产品包订单
	 * @return
	 */
	public AdminOrder getOrderByProductOrderId(String productOrderId){
		return adminOrderDao.getOrderByProductOrderId(productOrderId);
	}
	
	/**
	 * 查询所有订单
	 * @return
	 */
	public List<AdminOrder> getOrders(){
		return adminOrderDao.getOrders();
	}
	/**
	 * 5.1门户订购状态（和位士，网址卫士，乾坤大数据（数据标签））
	 * productName  产品包名称
	 * @return
	 */
	public String getOrderState(String selectByTerm){		
		
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String productName = jasonObject.get("productName").toString();
		java.lang.String customerNumber = jasonObject.get("customerNumber").toString();
			 String reqStateObj = jedisClient.hget("cusAndSta", customerNumber);
			 @SuppressWarnings("unchecked")
			Map<Object,Object> mapOBJ = JSON.parseObject(reqStateObj, Map.class);
			 String reqState="";
			 
			if(mapOBJ==null||mapOBJ.equals(null)) {
				 List<Map<String, Object>> orderState = adminOrderDao.getOrderState(productName,customerNumber);
				  int state1=0;//开通次数
				  int state2=0;//暂停次数
				  int state3=0;//恢复次数
				  for (Map<String, Object> map : orderState) {
					  String state = map.get("state").toString();
					  if(state.equals("1")) {//开通
						  state1++;
					  }
					  if(state.equals("3")) {//暂停
						  state2++;
					  }
					  if(state.equals("4")) {//恢复
						  state3++;
					  }
				}
				  
					if(state1>0) {
						reqState="已开通";
					}else {
						if(state3>0) {
							reqState="已开通";
						}else {
							if(state2>0) {
								reqState="已暂停";
							}else {
								reqState="已退订";
							}
						}
					}			
					Map<String,String> newMap=new HashMap<String,String>();
					newMap.put(productName, reqState);
				 jedisClient.hset("cusAndSta", customerNumber,JSON.toJSONString(newMap));				
			 }else if(mapOBJ.containsKey(productName)==false) {
				 List<Map<String, Object>> orderState = adminOrderDao.getOrderState(productName,customerNumber);
				  int state1=0;//开通次数
				  int state2=0;//暂停次数
				  int state3=0;//恢复次数
				  for (Map<String, Object> map : orderState) {
					  String state = map.get("state").toString();
					  if(state.equals("1")) {//开通
						  state1++;
					  }
					  if(state.equals("3")) {//暂停
						  state2++;
					  }
					  if(state.equals("4")) {//恢复
						  state3++;
					  }
				}
				  
					if(state1>0) {
						reqState="已开通";
					}else {
						if(state3>0) {
							reqState="已开通";
						}else {
							if(state2>0) {
								reqState="已暂停";
							}else {
								reqState="已退订";
							}
						}
					}			
					Map<String,String> newMap=new HashMap<String,String>();
					newMap.put(productName, reqState);
				 jedisClient.hset("cusAndSta", customerNumber,JSON.toJSONString(newMap));				
			 }else {
				 reqState=mapOBJ.get(productName).toString();
			}
			
		//String jsonString = JSON.toJSONString(reqState);					
		return reqState;
	}
	/*
	 5.2定时清空redis里cusAndSta表的数据*/
	 public  void clenCusAndSta() {
		jedisClient.del("cusAndSta");
	 }
	/**
	 * 6.门户首页产品包名称
	 * @param selectByTerm
	 * @return
	 */
	public String getOrdeProductName(String customerNumberObj) {
	     
		
		String customerNumb="";
		if(!"".equals(customerNumberObj)&&customerNumberObj!=null) {
			customerNumb = customerNumberObj.toString();
		}				
		/*List<Map<String, Object>> ordeProductName = adminOrderDao.getOrdeProductName(customerNumb);		
		for (Map<String, Object> map : ordeProductName) {
			String string = map.get("parent_name").toString();
		}*/
		List<Map<String, Object>> ordeProductName = adminOrderDao.getOrdeProductName(customerNumb);
		String jsonString = JSON.toJSONString(ordeProductName);					
		return jsonString;
	}
	
	/**
	 *  7.门户首页产品包名称(客户产品全部退订)
	 * @param customerNumb
	 * @return
	 */
	public boolean getOrdeProductName2(String customerNumberObj) {
		String customerNumb="";
		if(!"".equals(customerNumberObj)&&customerNumberObj!=null) {
			customerNumb = customerNumberObj.toString();
		}	
			Boolean ordeProductNameNew = adminOrderDao.getOrdeProductNameNew(customerNumb);
			return ordeProductNameNew;
		/*List<Map<String, Object>> ordeProductName1 = adminOrderDao.getOrdeProductName3(customerNumb);//乾坤大数据	
		
		List<Map<String, Object>> ordeProductName = adminOrderDao.getOrdeProductName2(customerNumb);	
		ArrayList<Object> arrayList = new ArrayList<>();
		for (Map<String, Object> map : ordeProductName) {
			String string = map.get("parent_name").toString();
			arrayList.add(string);
		}
		if(arrayList.size()>=ordeProductName1.size()) {
			return true;
		}else {
			return false;
		}*/

	}

	/**
	 * 订单管理导出
	 * @param reqMess
	 * @return
	 */
	public List<AdminOrder> ExportOrderByList(String reqMess) {
		 this.getOrderRatio();
		 Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String productName= (String) parseObject.get("productName");
		String productOrderId= (String) parseObject.get("productOrderId");
		String state= (String) parseObject.get("state");
		String customerName= (String) parseObject.get("customerName");
		String productRatio= (String) parseObject.get("productRatio");
		String firstTime= (String) parseObject.get("firstTime");
		String secondTime= (String) parseObject.get("secondTime");
	
		List<AdminOrder> pageData = adminOrderDao.exportOrderByTermList(customerName,productName,productOrderId,state,firstTime,secondTime,productRatio);		
		return pageData;
	}
	/**
	 * 1.控制台-乾坤大数据-我的订单-导出  根据客户编码查询
	 * @param productName
	 * @param productOrderId
	 * @param secondTime
	 * @param state
	 * @param firstTime
	 * @param customerNumber
	 * @return
	 */
	public String excleOrderByTermList(String productName, String productOrderId, String secondTime, String state,
			String firstTime, String customerNumber) {
		//导出数据录入
		String[] rowsName = new String[]{"序号","产品包名称","数据标签合同","订单号","状态","下单时间"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		
		int index=1;
	List<AdminOrder> exportOrderSubByList = adminOrderDao.excleOrderByTermList(productName, productOrderId,
				secondTime, state, firstTime, customerNumber);
     for (AdminOrder adminOrder : exportOrderSubByList) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = adminOrder.getProductName();
			objs[2]=adminOrder.getContractName();
			objs[3]=adminOrder.getProductOrderId();
			String stateStr="";
			if(adminOrder.getState()==1) {
				stateStr="已开通";
			}else if(adminOrder.getState()==2) {
				stateStr="已退订";
			}else if(adminOrder.getState()==3) {
				stateStr="已暂停";
			}else if(adminOrder.getState()==4) {
				stateStr="已开通";
			}
			objs[4]=stateStr;
			objs[5]=DateUtil.toStr(adminOrder.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			dataList.add(objs);
			index++;
		}
		ExportExcel export=new ExportExcel("门户-我的订单-乾坤大数据", rowsName, dataList);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	/**
	 * 添加Order数据
	 */
	public int insert(AdminOrder order){
		return adminOrderDao.insert(order);		
	}
	/**
	 * 查询上个月统计的客户
	 * @param dateStr 上个月时间到月
	 * @param beginTime  当前时间
	 * @return
	 */
	public List<OrderCustomerCount> selectCumCount(String dateStr, String beginTime) {
		List<OrderCustomerCount> selectCumCount = adminOrderDao.selectCumCount(dateStr,beginTime);
		return selectCumCount;
	}
	/**
	 * 重新把统计数椐录入数据库，时间为当月时间
	 * @param selectCumCount
	 */
	public void insertCustomerCountNo(List<OrderCustomerCount> selectCumCount) {
		
		 adminOrderDao.insertCustomerCountNo(selectCumCount);
	}
	
	
	
}
