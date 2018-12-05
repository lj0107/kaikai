package com.qtong.afinance.module.service.bill;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.module.dao.bill.BillDao;
import com.qtong.afinance.module.pojo.bigdata.DetailRecord;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.heweishi.LbcmpDetail;

/**
 * 控制台-账单
 *
 */
@Service
@Transactional
public class PortalBillService {
	@Autowired
	private BillDao billDao;
	
	
	/**
	 * 控制台-账单-账单list
	 * @param pageIndex
	 * @param billNumber 账单号
	 * @param customerNumber 客户名称
	 * @param productName 产品名称
	 * @param startTime  起始时间
	 * @param endTime 结束时间
	 * @return 
	 */
	public String getAllBills(String pageIndex,String customerNumber,String billNumber,String productOrderId, String productName,
			String startTime,String endTime,String flag) {
		PageData pageData = new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		
		if(endTime!=null && !"".equals(endTime)) {
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTime,DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		}
		String type="";
		PageData queryBills = null;
		//  1代表和位士  2代表网址卫士 
		if (flag.equals("和位士")) {//查所有和位士
			queryBills = billDao.queryBills(pageData,customerNumber, billNumber,productOrderId, "", productName, startTime, endTime, flag,type,type);
		}else if(flag.equals("网址卫士")) {//查所有网址卫士
			
			queryBills = billDao.queryBills(pageData,customerNumber, billNumber,productOrderId,  "", productName, startTime, endTime, flag,type,type);
		}else {//查看所有乾坤大数据的数据
			queryBills = billDao.queryBDBills(pageData, customerNumber,billNumber,productOrderId, productName, startTime, endTime);
		}
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(queryBills, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
		
		
	}
	
	
	
	/**
	 * 导出控制台账单
	 * @param json
	 * @return
	 */
	public ExportExcel exportExcel(String customerNumber,String billNumber,String productOrderId, String productName,
			String startTime,String endTime,String flag) {
		
		List<Bill> exportBills = null;
		if(endTime!=null && !"".equals(endTime)) {
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(endTime,DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		}
		//  1代表和位士  2代表网址卫士 
		if (flag.equals("和位士")) {//查所有和位士
			exportBills = billDao.exportBills(customerNumber,billNumber,productOrderId, "", productName, startTime, endTime, flag);
		}else if(flag.equals("网址卫士")) {//查所有网址卫士
			
			exportBills = billDao.exportBills(customerNumber,billNumber,productOrderId,  "", productName, startTime, endTime, flag);
		}else {//查看所有乾坤大数据的数据
			exportBills = billDao.exportBDBills(customerNumber, billNumber,productOrderId, productName, startTime, endTime);
		}
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","账单号","账单时间","订单号","下单时间","产品名称","金额(元)"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		int index=1;
		DecimalFormat df=new DecimalFormat("0.00");
		
		for (Bill bill : exportBills) {
			String time = "";
			if(bill.getProductName().equals("网址卫士")) {
				time = DateUtil.toStr(bill.getBillTime(), DateUtil.YYYY);
			}else {
				time = DateUtil.toStr(bill.getBillTime(), DateUtil.YYYY_MM);
			}
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1] = bill.getBillNumber();
			objs[2] = time;
			objs[3] = bill.getProductOrderId();
			objs[4] = DateUtil.toStr(bill.getOrderTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			objs[5] = bill.getProductName();
			objs[6] = df.format(bill.getFee());
			dataList.add(objs);
			
			index++;
		}
		
		return new ExportExcel(flag+"账单",flag+"账单", rowsName, dataList);
	}
	
	
	/**
	 * 控制台-和位士-账单-查看详单
	 * @param billNumber 账单号
	 * @param customerNumber 客户编码
	 * @param requestRefid 流水号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String getOneHWSBillDetail(String pageIndex,String productOrderId,String billTime, String customerNumber,String requestRefid,String keyWords,String startTime,String endTime) {
		PageData pageData = new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		
		if("".equals(startTime) && !"".equals(billTime)) {
			startTime = DateUtil.toStr(DateUtil.getInitMonth(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		
		}
		
		PageData queyAllBDBillDetail = billDao.queryOneHWSBillDetail(pageData,productOrderId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(queyAllBDBillDetail, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	/**
	 * 控制台-和位士-账单-查看详单 计算金额
	 * @param billNumber 账单号
	 * @param customerNumber 客户编码
	 * @param requestRefid 流水号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String getOneHWSBillFee(String productOrderId,String billTime,String requestRefid,String keyWords,String startTime,String endTime) {
		DecimalFormat df=new DecimalFormat("0.00");
		
		double feeCount = 0.00;
		
		
		if("".equals(startTime) && !"".equals(billTime)) {
			startTime = DateUtil.toStr(DateUtil.getInitMonth(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		}
		List<LbcmpDetail> lst = billDao.queryOneHWSBill(productOrderId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		int counts= billDao.queryOneHWSCount(productOrderId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		
		System.out.println(lst.size());
		for (LbcmpDetail lbcmpDetail : lst) {
			
			double fee = lbcmpDetail.getFee();//获取产品费用
			BigDecimal df1 = new BigDecimal(fee/1000.0);
			double doubleValue = df1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			feeCount += doubleValue;//计算总金额
		}
		String jsonString = "{ \"counts\": \""+counts+"\",\"size\": \""+lst.size()+"\", \"feeCount\":\""+df.format(feeCount)+"\" }";
		return jsonString;
	}
	
	/**
	 * 控制台-乾坤大数据-账单-查看详单
	 * @param billNumber 账单号
	 * @param customerNumber 客户编码
	 * @param requestRefid 流水号
	 * @param keyWords 条件查询关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String getOneBDBillDetail(String pageIndex,String productOrderId,String billTime,String customerNumber,String requestRefid,String productName,String startTime,String endTime) {
		PageData pageData = new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		
		if("".equals(startTime) && !"".equals(billTime)) {
			startTime = DateUtil.toStr(DateUtil.getInitMonth(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),"yyyy/MM/dd HH:mm:ss");
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),"yyyy/MM/dd HH:mm:ss");
		
		}
		
		PageData queyAllBDBillDetail = billDao.queryOneBDBillDetail(pageData,productOrderId,requestRefid,productName,startTime,endTime);//通过账单号查看所有详单
		
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(queyAllBDBillDetail, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
		return jsonString;
	}
	
	
	/**
	 * 控制台-乾坤大数据-账单-查看详单 计算金额
	 * @param billNumber 账单号
	 * @param customerNumber 客户编码
	 * @param requestRefid 流水号
	 * @param keyWords 条件查询关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public String getOneBDBillFee(String productOrderId,String billTime,String requestRefid,String productName,String startTime,String endTime) {
		DecimalFormat df=new DecimalFormat("0.00");
		
		double feeCount = 0.00;
		if("".equals(startTime) && !"".equals(billTime)) {
			startTime = DateUtil.toStr(DateUtil.getInitMonth(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),"yyyy/MM/dd HH:mm:ss");
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),"yyyy/MM/dd HH:mm:ss");
		}
		List<DetailRecord> lst = billDao.queryOneBDBill(productOrderId,requestRefid,productName,startTime,endTime);//通过账单号查看所有详单
		int counts = billDao.queryOneBDCount(productOrderId,requestRefid,productName,startTime,endTime);//通过账单号查看所有详单
		
		for (DetailRecord detailRecord : lst) {
			double fee = detailRecord.getFee();//获取产品费用
			BigDecimal df1 = new BigDecimal(fee/1000.0);
			double doubleValue = df1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			feeCount += doubleValue;//计算总金额
			
		}
		String jsonString = "{ \"counts\": \""+counts+"\",\"size\": \""+lst.size()+"\", \"feeCount\":\""+df.format(feeCount)+"\" }";
		
		return jsonString;
	}
	
	
	/**
	 * 导出-乾坤大数据详单
	 * @param customerNumber
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportBDBill(String billNumber,String billTime,String productName,String proOrdId,String requestRefid,String keyWords, String startTime, String endTime){
		
		if("".equals(startTime) && !"".equals(billTime)) {
			
			startTime = DateUtil.toStr(DateUtil.getInitMonth(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),"yyyy/MM/dd HH:mm:ss");
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),"yyyy/MM/dd HH:mm:ss");
		}
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","流水号","标签名称","请求时间","次数","金额(元)"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		double feeCount = 0.00;
		//根据条件查询所有乾坤大数据详单
		List<DetailRecord> queryOneBDBill = billDao.queryOneBDBill(proOrdId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		int counts = billDao.queryOneBDCount(proOrdId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		int index=1;
		int count = 1;
		DecimalFormat df=new DecimalFormat("0.00");
		if(queryOneBDBill.size()>0) {
			for (DetailRecord detailRecord : queryOneBDBill) {
				double fee = detailRecord.getFee()/1000;//获取产品费用
				//封装信息
				objs = new Object[rowsName.length];
				objs[0] = index;
				objs[1] = detailRecord.getRequestRefId();
				objs[2] = detailRecord.getProductName();
				objs[3] = DateUtil.toStr(detailRecord.getRequestTime(),  DateUtil.YYYY_MM_DD_HH_MM_SS);
				objs[4] = count;
				objs[5] = df.format(fee);
				dataList.add(objs);
				
				feeCount += fee;//计算总金额
				
				index++;
			}
			
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = "合计";
			objs[1] = "";
			objs[2] = counts+"类标签";
			objs[3] = "";
			objs[4] = queryOneBDBill.size()+"次";
			objs[5] = df.format(feeCount);
			
			dataList.add(objs);
		}
		String string = "账单号："+billNumber+"   账单时间："+billTime+"   产品名称："+productName+"   订单号："+proOrdId;
		
		return new ExportExcel(string ,"乾坤大数据详单", rowsName, dataList);
		
		
	}
	/**
	 * 导出-和位士详单
	 * @param customerNumber
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public ExportExcel exportHWSBill(String billNumber,String billTime,String productName,String proOrdId,String requestRefid,String keyWords, String startTime, String endTime){
		
		if("".equals(startTime) && !"".equals(billTime)) {
			
			startTime = DateUtil.toStr(DateUtil.getInitMonth(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
			endTime = DateUtil.toStr(DateUtil.getMaxMonthDateTime(DateUtil.toDate(billTime, DateUtil.YYYY_MM)),DateUtil.YYYY_MM_DD_HH_MM_SS);
		}
		
		//导出数据录入
		String[] rowsName = new String[]{"序号","流水号","标签名称","请求时间","次数","金额(元)"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		
		Object[] objs = null;
		
		//根据条件查询所有和位士详单
		List<LbcmpDetail> queryOneHWSBill = billDao.queryOneHWSBill(proOrdId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		int counts= billDao.queryOneHWSCount(proOrdId,requestRefid,keyWords,startTime,endTime);//通过账单号查看所有详单
		int index=1;
		int count = 1;
		double feeCount = 0.00;
		DecimalFormat df=new DecimalFormat("0.00");
	
		if(queryOneHWSBill.size()>0) {
			for (LbcmpDetail lbcmpDetail : queryOneHWSBill) {
				double fee = lbcmpDetail.getFee()/1000;
				//封装信息
				objs = new Object[rowsName.length];
				objs[0] = index;
				objs[1] = lbcmpDetail.getRequestRefId();
				objs[2] = lbcmpDetail.getProductName();
				objs[3] = DateUtil.toStr(lbcmpDetail.getRequestTime(),  DateUtil.YYYY_MM_DD_HH_MM_SS);
				objs[4] = count;
				objs[5] = df.format(fee);
				dataList.add(objs);
				
				feeCount += fee;//计算总金额
				index++;
			}
			
			//封装信息
			objs = new Object[rowsName.length];
			objs[0] = "合计";
			objs[1] = "";
			objs[2] = counts+"类标签";
			objs[3] = "";
			objs[4] = queryOneHWSBill.size()+"次";
			objs[5] = df.format(feeCount);
			
			dataList.add(objs);
		}
		
		
		
		String string = "账单号："+billNumber+"   账单时间："+billTime+"   产品名称："+productName+"   订单号："+proOrdId;
		
		return new ExportExcel(string,"和位士详单", rowsName, dataList);
		
	}
	
	
	/**
	 * 批量插入账单信息
	 * @param bills
	 */
	public void insertBills(List<Bill> bills){
		
		billDao.insertBills(bills);
	
	}
	
	
	
	
}