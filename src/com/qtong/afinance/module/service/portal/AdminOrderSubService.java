package com.qtong.afinance.module.service.portal;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.portal.AdminOrderSubDao;
import com.qtong.afinance.module.dao.portal.AdminRatioDao;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.product.AdminProRatio;
import com.qtong.afinance.module.pojo.product.AdminProRatioInfo;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
@Service
public class AdminOrderSubService {
	@Autowired
	private AdminOrderSubDao orderSubDao;
	@Autowired
	private AdminRatioDao adminRatioDao;
	
	
	public List<AdminOrderSub> getProductByProductOrderId(String productOrderId) {
		return orderSubDao.getProductByProductOrderId(productOrderId);
	}
	/**  
	 * 2.控制台-乾坤大数据-我的订单  根据订单编码查看详情页
	 * @author mh
	 */
	public String selectOrderSubByTermList(String selectByTerm) {
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String productName = jasonObject.get("productName").toString();
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
	    String productOrderId = jasonObject.get("productOrderId").toString();
	    PageData pageData = orderSubDao.selectOrderSubByTermList(pageIndex,productOrderId,productName);
	    JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
	 	String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);			 		
		return jsonString;		
	}
	/**
	 * 2.控制台-乾坤大数据-我的订单  根据订单编码查看详情页-导出
	 * @param productName
	 * @param productOrderId
	 * @param effTime
	 * @return
	 */
	public String excleOrderSubByTermList(String productName, String productOrderId,String effTime) {
		//导出数据录入
		String[] rowsName = new String[]{"订购流水号","下单时间","状态","标签名称","目录价（元/次）","折扣","实际单价（元/次）"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		
		
	 List<AdminOrderSub> excleOrderSubByTermList = orderSubDao.excleOrderSubByTermList(productName, productOrderId);
   for (AdminOrderSub adminOrderSub : excleOrderSubByTermList) {
			objs = new Object[rowsName.length];
			int state = adminOrderSub.getState();
			String stateStr="";
			if(state==1) {
				stateStr="已开通";
			}else if(state==2){
				stateStr="已退订";
			}else if(state==3){
				stateStr="已暂停";
			}else if(state==4){
				//stateStr="已恢复";
				stateStr="已开通";
			}			
			objs[0] = adminOrderSub.getSubOrderNum();
			objs[1] =DateUtil.toStr(adminOrderSub.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			objs[2] = stateStr;
			objs[3]=adminOrderSub.getProName();
			objs[4]=adminOrderSub.getPrice();
			objs[5]=adminOrderSub.getDiscount();
			objs[6]=adminOrderSub.getSum();
			dataList.add(objs);
		}
        String str = DateUtil.toStr(DateUtil.toDate(effTime,DateUtil.YYYY__MM__DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS);
		ExportExcel export=new ExportExcel("订单号："+productOrderId+"  下单时间："+str+"   产品名称："+productName,"订单详情", rowsName, dataList);
		String result=JSON.toJSONString(export);
		return result;
	}

	/**
	 * 3.控制台-网址卫士与和卫士-我的订单  根据客户编码查询
     * @param pageIndex  当前页
	 * @param customerNumber 客户编码
	 * @param proName  产品名称
	 * @param productOrderNumber  产品订单号
	 * @param state  产品状态
	 * 下单（订购）生效时间 范围
	 * @param firstTime  1.第一时间
	 * @param secondTime 2.第二时间
	 * @param parentName  上级名称：网址卫士或者和卫士
	 * @return
	 */
	public String selectOrderByCusList(String selectByTerm) {
	     
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();		
	    String customerNumber = jasonObject.get("customerNumber").toString();
	    java.lang.String productName = jasonObject.get("productName").toString();
	        
		Object proNameObj = jasonObject.get("proName");
		String proName="";
		if(!"".equals(proNameObj)&&proNameObj!=null) {
			proName = proNameObj.toString();
		}
		Object stateObj = jasonObject.get("state");
		String state="";
		if(!"".equals(stateObj)&&stateObj!=null) {
			state = stateObj.toString();
		}
		Object subOrderNumObj = jasonObject.get("subOrderNum");
		String subOrderNum="";
		if(!"".equals(subOrderNumObj)&&subOrderNumObj!=null) {
			subOrderNum = subOrderNumObj.toString();
		}
		Object firstTimeObj = jasonObject.get("firstTime");
		String firstTime="";
		if(!"".equals(firstTimeObj)&&firstTimeObj!=null) {
			firstTime = firstTimeObj.toString();
		}
		Object secondTimeObj = jasonObject.get("secondTime");
		String secondTime="";
		if(!"".equals(secondTimeObj)&&secondTimeObj!=null) {
			 secondTime = secondTimeObj.toString();
		}			
		PageData pageData = orderSubDao.selectOrderByCusList(pageIndex,customerNumber,proName,subOrderNum,state,firstTime,secondTime,productName);		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);					
		JSONObject parseObject = JSON.parseObject(jsonString);
		return jsonString;
	}
	/**
	 * 3.后台管理-订单管理-订单详情
	 * @param selectByTerm
	 * @return
	 */
	public String selectOrderSubList(String selectByTerm) {
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
	    String productOrderId = jasonObject.get("productOrderId").toString();
	    PageData pageData = orderSubDao.selectOrderSubList(pageIndex,productOrderId);
	    JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);							
	    return jsonString;		
	}
	/**
	 * 4.后台管理-订单管理-订单详情-设置分成比例
	 * @param adminProRatio
	 * @return
	 */
	public int updRatio(AdminProRatio adminProRatio) {
		adminProRatio.setOptMatter("维护分成比例");
		int updRatio = orderSubDao.updRatio(adminProRatio);
		String subOrderNum = adminProRatio.getSubOrderNum();//流水号
		List<AdminProRatioInfo> selectAdminProRatio = adminRatioDao.selectAdminProRatio(subOrderNum);//根据流水号判断是否有数据
		if(selectAdminProRatio.size()==0) {//没有数据
			adminRatioDao.insertRatioInfo(adminProRatio);
		}else {
			adminRatioDao.updateRatioInfoState(subOrderNum);
			adminRatioDao.insertRatioInfo(adminProRatio);
		}
		return updRatio;
	}
	/**
	 * 5.后台管理-订单管理-订单详情-分成比例记录
	 * @param selectByTerm
	 * @return
	 */
	public String selectRatioList(String selectByTerm) {
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
	    String subOrderNum = jasonObject.get("subOrderNum").toString();
	    PageData pageData = orderSubDao.selectRatioList(pageIndex,subOrderNum);
	    JSON.DEFFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);							
		return jsonString;		
	}
	
	/**
	 * 添加订单详情（测试）没用删除
	 * @param s
	 * @return
	 */
/*	public int insert(AdminOrderSub s){
		return orderSubDao.insert(s);
	}*/
	/**
	 * 导出-订单管理-查看详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String ExportOrderSubByList(String  productOrderId,String productName,String customerName,String effTime,
			String contractName) throws Exception {
    	//导出数据录入
		String[] rowsName = new String[]{"序号","订购流水号","状态","下单时间","产品名称","目录价（元）","折扣","实际单价（元）","合作伙伴","分成比例","待审核分成比例"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		
		int index=1;
		 List<AdminOrderSub> exportOrderSubByList = orderSubDao.ExportOrderSubByList(productOrderId);//内容list
		 for (AdminOrderSub adminOrderSub : exportOrderSubByList) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = adminOrderSub.getSubOrderNum();
			objs[3]=DateUtil.toStr(adminOrderSub.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			objs[4]=adminOrderSub.getProName();
			int state = adminOrderSub.getState();
			String stateStr="";
			if(state==1) {
				stateStr="已开通";
			}else if(state==2){
				stateStr="已退订";
			}else if(state==3){
				stateStr="已暂停";
			}else if(state==4){
				stateStr="已开通";
			}			
			objs[2]=stateStr;
			objs[5]=adminOrderSub.getPrice();
			if(adminOrderSub.getDiscount()==0) {
				objs[6]="";
			}else {
				objs[6]=adminOrderSub.getDiscount();
			}
			
			objs[7]=adminOrderSub.getSum();
			objs[8]=adminOrderSub.getName();
			if(adminOrderSub.getRatio()==null) {
				objs[9]="";
			}else {
				objs[9]=adminOrderSub.getRatio();
			}
			
			String reserveRatio="";
			  if(adminOrderSub.getReserveRatio()==null){
				  objs[10]=reserveRatio;
			  }else{
				  if(adminOrderSub.getRatio()!=""&&adminOrderSub.getReserveRatio()!=null){
					  objs[10]=reserveRatio;
				  }else{
					  objs[10]=adminOrderSub.getReserveRatio();
				  }
			  }
			dataList.add(objs);
			index++;
		}
		 String str = DateUtil.toStr(DateUtil.toDate(effTime,DateUtil.YYYY__MM__DD_HH_MM_SS), DateUtil.YYYY_MM_DD_HH_MM_SS);
		ExportExcel export=new ExportExcel("订单号："+productOrderId+"   下单时间："+str+"   产品名称："+productName+"   客户名称："+customerName+"  合同名称："+contractName, "订单详情",rowsName, dataList);
		String result=JSON.toJSONString(export);
		return result;
	}
	/**
	 * 
	 * 3.控制台-网址卫士与和位士-我的订单  根据客户编码查询-导出
	 * @param proName
	 * @param subOrderNum
	 * @param state
	 * @param firstTime
	 * @param secondTime
	 * @param productName
	 * @param customerNumber
	 * @return
	 */
	public String excleOrderByCusList(String proName, String subOrderNum, String state, String firstTime,
			String secondTime, String productName, String customerNumber) {
		//导出数据录入
		String[] rowsName =new String[]{};
		String name="";
		String result="";
		if(productName.equals("和位士")) {
			name="和位士";
			 rowsName = new String[]{"产品包名称","订单号","状态","下单时间","合同名称","实际单价（元/次）","目录价（元/次）","折扣"};
				List<Object[]>  dataList = new ArrayList<Object[]>();
				Object[] objs = null;
				
			
				List<AdminOrderSub> excleOrderByCusList = orderSubDao.excleOrderByCusList(customerNumber,proName,subOrderNum,state,firstTime,secondTime,productName);		
			for (AdminOrderSub adminOrderSub2 : excleOrderByCusList) {
					objs = new Object[rowsName.length];
					String stateStr="";
		        	if(adminOrderSub2.getState()==1){
		        		stateStr="已开通";				   
					}		
		        	if(adminOrderSub2.getState()==3){
		        		stateStr="已暂停";				   
		        	}						      
		        	if(adminOrderSub2.getState()==4){
		        		stateStr="已恢复";				   
		        	}		
		        	if(adminOrderSub2.getState()==2){
		        		stateStr="已退订";				   
		        	}						    									
					objs[0] = adminOrderSub2.getProName();
					objs[1] = adminOrderSub2.getProductOrderId();
					objs[2]=stateStr;
				    objs[3]=DateUtil.toStr(adminOrderSub2.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
					objs[4]=adminOrderSub2.getContractName();
					objs[5]=adminOrderSub2.getSum();
					objs[6]=adminOrderSub2.getPrice();
					objs[7]=adminOrderSub2.getDiscount();
					dataList.add(objs);
				
				}
				ExportExcel export=new ExportExcel(name,"订单详情",rowsName, dataList);
				 result=JSON.toJSONString(export);
		}else if(productName.equals("网址卫士")) {
			name="网址卫士";
			 rowsName = new String[]{"产品包名称","订单号","状态","下单时间","合同名称","实际单价（万元/年）","目录价（万/年）","折扣"};
				List<Object[]>  dataList = new ArrayList<Object[]>();
				Object[] objs = null;
				
			
				List<AdminOrderSub> excleOrderByCusList = orderSubDao.excleOrderByCusList(customerNumber,proName,subOrderNum,state,firstTime,secondTime,productName);		
			for (AdminOrderSub adminOrderSub2 : excleOrderByCusList) {
					objs = new Object[rowsName.length];
					String stateStr="";
		        	if(adminOrderSub2.getState()==1){
		        		stateStr="已开通";				   
					}		
		        	if(adminOrderSub2.getState()==3){
		        		stateStr="已暂停";				   
		        	}						      
		        	if(adminOrderSub2.getState()==4){
		        		stateStr="已恢复";				   
		        	}		
		        	if(adminOrderSub2.getState()==2){
		        		stateStr="已退订";				   
		        	}						    									
					objs[0] = adminOrderSub2.getProName();
					objs[1] = adminOrderSub2.getProductOrderId();
					objs[2]=stateStr;
					objs[3]=DateUtil.toStr(adminOrderSub2.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
					objs[4]=adminOrderSub2.getContractName();
					objs[5]=adminOrderSub2.getSum()/10000;
					objs[6]=adminOrderSub2.getPrice()/10000;
					objs[7]=adminOrderSub2.getDiscount();
					dataList.add(objs);
				
				}
				ExportExcel export=new ExportExcel(name,"订单详情", rowsName, dataList);
				 result=JSON.toJSONString(export);
		}
		return result;
	}
	
}
