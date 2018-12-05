package com.qtong.afinance.module.controller.bill;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.bill.AdminBillService;
import com.qtong.afinance.module.service.bill.PortalBillService;

/**
 * 后台管理-账单
 *
 */
@Controller
@RequestMapping("/admin/bill")
public class AdminBillController {
	
	@Autowired
	private AdminBillService adminBillService;
	@Autowired
	private PortalBillService portalBillService;
	
	/**
	 * 后台管理-账单管理
	 * 包括条件查询
	 * @param pageIndex
	 * @param billNumber 账单号
	 * @param customerNumber 客户名称
	 * @param productName 产品名称
	 * @param startTime  起始时间
	 * @param endTime 结束时间
	 * @return 
     */
	@RequestMapping(value = "/getAllBills")
	@ResponseBody
	public void getAllBills(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");
		String billNumber=(String) map.get("billNumber");
		String customerName=(String) map.get("customerName");
		String productName=(String) map.get("productName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		String timeType=(String) map.get("timeType");
		String countsType=(String) map.get("countsType");
		String allBills = adminBillService.getAllBills(pageIndex,billNumber, customerName,  productName, startTime, endTime,flag,timeType,countsType);
        try {
			response.getOutputStream().write(allBills.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *导出后台管理-账单管理
	 * @param 包括条件查询
	 * @param pageIndex
	 * @param billNumber 账单号
	 * @param customerNumber 客户名称
	 * @param productName 产品名称
	 * @param startTime  起始时间
	 * @param endTime 结束时间
	 * @return 
	 */
	@RequestMapping(value = "/exportBills",method= RequestMethod.POST)
	@ResponseBody
	public String exportExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String billNumber=(String) map.get("billNumber");
		String customerName=(String) map.get("customerName");
		String productName=(String) map.get("productName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		ExportExcel export = adminBillService.exportExcel(billNumber, customerName, productName, startTime, endTime, flag);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	
	/**  
	 * 后台管理-账单-查看详单
	 * @author mh
	 */
	@RequestMapping(value = "/getOneBillDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void getOneBillDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	//根据账单号查看详单
		
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");
		String productOrderId=(String) map.get("productOrderId");
		String customerNumber=(String) map.get("customerNumber");
		String requestRefid=(String) map.get("requestRefid");
		String productName=(String) map.get("productName");
		String keyWords=(String) map.get("keyWords");
		String billTime=(String) map.get("billTime");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		PageData pageData = new PageData();
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String oneBillDetail = "";
		
		if("和位士".equals(productName)) {
			oneBillDetail = portalBillService.getOneHWSBillDetail(pageIndex, productOrderId,billTime, customerNumber, requestRefid,keyWords, startTime, endTime);
		}else {
			oneBillDetail = portalBillService.getOneBDBillDetail(pageIndex, productOrderId, billTime,customerNumber, requestRefid, keyWords, startTime, endTime);
		}
		
        try {
			response.getOutputStream().write(oneBillDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  
	 * 后台管理-账单-查看详单 计算金额
	 * @author mh
	 */
	@RequestMapping(value = "/getOneBillDetailFee",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void getOneBillDetailFee(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	//根据账单号查看详单
		
		Map map=JSON.parseObject(reqMess,Map.class);
		String productOrderId=(String) map.get("productOrderId");
		String requestRefid=(String) map.get("requestRefid");
		String productName=(String) map.get("productName");
		String keyWords=(String) map.get("keyWords");
		String billTime=(String) map.get("billTime");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String oneBillDetail = "";
		
		if("和位士".equals(productName) ) {
			oneBillDetail = portalBillService.getOneHWSBillFee( productOrderId,billTime,requestRefid, keyWords,startTime, endTime);
		}else {
			oneBillDetail = portalBillService.getOneBDBillFee( productOrderId, billTime, requestRefid, keyWords, startTime, endTime);
		}
		
		//String whiteList = hwsWhiteListService.queryAll(pageIndex, customerNumber, customerName, startTime, endTime);
		try {
			response.getOutputStream().write(oneBillDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 * 后台管理-账单-详单-导出
	 * @author mh
	 */
	@RequestMapping(value = "/exportOneBillDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String exportOneBillDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	//根据账单号查看详单
		
		Map map=JSON.parseObject(reqMess,Map.class);
		String billNumber=(String) map.get("billNumber");
		String billTime=(String) map.get("billTime");
		String productOrderId=(String) map.get("productOrderId");
		String requestRefid=(String) map.get("requestRefid");
		String productName=(String) map.get("productName");
		String customerName=(String) map.get("customerName");
		String keyWords=(String) map.get("keyWords");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		ExportExcel export =null;
		
		if("和位士".equals(productName)) {
			export = portalBillService.exportHWSBill(billNumber,billTime,productName, productOrderId, requestRefid,keyWords,startTime, endTime);
		}else {
			export = portalBillService.exportBDBill(billNumber,billTime,productName,productOrderId, requestRefid, keyWords, startTime, endTime);
		}
		
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
}
