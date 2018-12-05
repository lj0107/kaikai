package com.qtong.afinance.module.controller.portal;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.service.bill.PortalBillService;
import com.qtong.afinance.module.service.statistics.OrderStatsService;

/**
 * 控制台-账单管理
 *
 */
@Controller
@RequestMapping("/portal/bill")
public class PortalBillController {
	@Autowired
	private PortalBillService billService;
	@Autowired
	private OrderStatsService orderStatsService;
	
	/**
	 * 控制台-和卫士-账单
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
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String productName=(String) map.get("productName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		
		String allBills = billService.getAllBills(pageIndex,customerNumber, billNumber, productOrderId, productName, startTime, endTime, flag);
		
        try {
			response.getOutputStream().write(allBills.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**  
	 * 门户控制台-账单-导出
	 */
	@RequestMapping(value = "/exportBills",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String exportBills(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	//根据账单号查看详单
		
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");
		String billNumber=(String) map.get("billNumber");
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String productName=(String) map.get("productName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String flag=(String) map.get("flag");
		
		ExportExcel export = billService.exportExcel(customerNumber, billNumber, productOrderId, productName, startTime, endTime, flag);
		String result=JSON.toJSONString(export);
		return result;
	}
	
	
	
	
	
	
	
	
	

	/**  
	 * 控制台-账单-查看账单
	 */
	@RequestMapping(value = "/getOneOrderBill")
	@ResponseBody
	public Map<String, Object> getOneOrderBill(HttpServletRequest request,HttpServletResponse response){
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String productOrderId=(String) map.get("productOrderId");
		String billTime=(String) map.get("billTime");
		
		return orderStatsService.getBill(productOrderId,DateUtil.toDate(billTime, DateUtil.YYYY_MM));
		
	}
	
	/**  
	 * 控制台-账单-查看账单-导出
	 */
	@RequestMapping(value = "/exportOneBill")
	@ResponseBody
	public String exportOneBill(HttpServletRequest request,HttpServletResponse response){
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String productOrderId=(String) map.get("productOrderId");
		String billTime=(String) map.get("billTime");
		String billNumber=(String) map.get("billNumber");
		String productName=(String) map.get("productName");
		
		ExportExcel export = orderStatsService.exportBill(billNumber,productName,productOrderId,DateUtil.toDate(billTime, DateUtil.YYYY_MM));
		String result=JSON.toJSONString(export);
		return result;
	}
	/**  
	 * 后台管理-账单-查看账单-导出
	 */
	@RequestMapping(value = "/exportAdminOneBill")
	@ResponseBody
	public String exportAdminOneBill(HttpServletRequest request,HttpServletResponse response){
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String productOrderId=(String) map.get("productOrderId");
		String billNumber=(String) map.get("billNumber");
		String billTimeStr=(String) map.get("billTimeStr");
		String billTime=(String) map.get("billTime");
		String productName=(String) map.get("productName");
		String customerName=(String) map.get("customerName");
		
		
		
		ExportExcel export = orderStatsService.exportAdminBill(productOrderId,billNumber,billTimeStr,DateUtil.toDate(billTime, DateUtil.YYYY_MM),productName,customerName);
		String result=JSON.toJSONString(export);
		return result;
	}
	

	/**  
	 * 控制台-和卫士-账单-查看详单 计算金额
	 */
	@RequestMapping(value = "/getOneHWSBillDetailFee",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void getOneHWSBillDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);

		String productOrderId=(String) map.get("productOrderId");
		String requestRefid=(String) map.get("requestRefid");
		String billTime=(String) map.get("billTime");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		String productName=(String) map.get("productName");//输入的产品名称
		
		String oneBillDetail = billService.getOneHWSBillFee(productOrderId,billTime,requestRefid,productName,startTime,endTime);
        try {
			response.getOutputStream().write(oneBillDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 * 控制台-和卫士-账单-查看详单
	 */
	@RequestMapping(value = "/getOneHWSBillDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void getOneHWSBillDetailFee(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String pageIndex=(String) map.get("pageIndex");
		String customerNumber=(String) map.get("customerNumber");
		String productOrderId=(String) map.get("productOrderId");
		String requestRefid=(String) map.get("requestRefid");
		String productName=(String) map.get("productName");
		String billTime=(String) map.get("billTime");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		
		
		String oneBillDetail = billService.getOneHWSBillDetail(pageIndex,productOrderId,billTime, customerNumber,requestRefid,productName,startTime,endTime);
		try {
			response.getOutputStream().write(oneBillDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	/**  
	 * 乾坤大数据-账单-查看详单
	 */
	@RequestMapping(value = "/getOneBDBillDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void getOneBillDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String pageIndex=(String) map.get("pageIndex");//当前页
		String billTime=(String) map.get("billTime");//当前页
		String productOrderId=(String) map.get("productOrderId");//账单号=产品包订购关系id
		String customerNumber=(String) map.get("customerNumber");//客户编号
		String requestRefid=(String) map.get("requestRefid");//客户编号
		String productName=(String) map.get("productName");//输入的产品名称
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		
		String oneBillDetail = billService.getOneBDBillDetail(pageIndex,productOrderId,billTime, customerNumber, requestRefid,productName,startTime,endTime);
		
        try {
			response.getOutputStream().write(oneBillDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**  
	 * 乾坤大数据-账单-查看详单 计算金额
	 */
	@RequestMapping(value = "/getOneBDBillDetailFee",produces="text/html;charset=UTF-8")
	@ResponseBody
	public void getOneBDBillDetailFee(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess,Map.class);
		String productOrderId=(String) map.get("productOrderId");//账单号=产品包订购关系id
		String billTime=(String) map.get("billTime");//账单号=产品包订购关系id
		String customerNumber=(String) map.get("customerNumber");//客户编号
		String requestRefid=(String) map.get("requestRefid");
		String keyWords=(String) map.get("keyWords");//输入的产品名称
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		
		String oneBillDetail = billService.getOneBDBillFee(productOrderId,billTime, requestRefid,keyWords,startTime,endTime);
		
		try {
			response.getOutputStream().write(oneBillDetail.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**  
	 * 门户控制台-乾坤大数据-详单-导出
	 */
	@RequestMapping(value = "/exportOneBDBillDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String exportOneBDBillDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	//根据账单号查看详单
		
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String billNumber=(String) map.get("billNumber");
		String billTime=(String) map.get("billTime");
		//String customerName=(String) map.get("customerName");
		String customerNumber=(String) map.get("customerNumber");
		
		String productOrderId=(String) map.get("productOrderId");
		String requestRefid=(String) map.get("requestRefid");
		String productName=(String) map.get("productName");
		String keyWords=(String) map.get("keyWords");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		
		ExportExcel export = billService.exportBDBill(billNumber, billTime, productName,productOrderId, requestRefid, keyWords, startTime, endTime);
		
		String result=JSON.toJSONString(export);
		return result;
	}
	
	/**  
	 * 门户控制台-和位士-详单-导出
	 */
	@RequestMapping(value = "/exportOneHWSBillDetail",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String exportOneHWSBillDetail(HttpServletRequest request,HttpServletResponse response){
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	//根据账单号查看详单
		
		Map map=JSON.parseObject(reqMess,Map.class);
		String productOrderId=(String) map.get("productOrderId");
		String billNumber=(String) map.get("billNumber");
		String billTime=(String) map.get("billTime");
		String requestRefid=(String) map.get("requestRefid");
		String productName=(String) map.get("productName");
		String startTime=(String) map.get("startTime");
		String endTime=(String) map.get("endTime");
		
		ExportExcel export = billService.exportHWSBill(billNumber, billTime, productName, productOrderId, requestRefid,"", startTime, endTime);
		
		String result=JSON.toJSONString(export);
		return result;
	}
}
