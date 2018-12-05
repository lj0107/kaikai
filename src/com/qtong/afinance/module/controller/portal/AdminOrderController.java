package com.qtong.afinance.module.controller.portal;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.ExportExcel;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.product.AdminOrder;
import com.qtong.afinance.module.pojo.product.AdminProRatio;
import com.qtong.afinance.module.service.portal.AdimOrderService;
import com.qtong.afinance.module.service.portal.AdminOrderSubService;


/**
 * 控制台-乾坤大数据-我的订单Controller
 */
@Controller
@RequestMapping("/portal/order")
public class AdminOrderController {
	@Autowired
	private  AdimOrderService adimOrderService;
	
	@Autowired
	private   AdminOrderSubService adminOrderSubService;
	
	/**
	 * 乾坤大数据—业务统计——获取当前登录客户的所有数据标签包
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/selectOrder")
	@ResponseBody
	public String selectOrder(HttpServletRequest request) {
		
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		Integer pageIndex=(Integer) map.get("pageIndex");
		String customerNumber=(String) map.get("customerNumber");
		String productName=(String) map.get("productName");
		
		return adimOrderService.getOrderByCustomerNumber(pageIndex,customerNumber,productName);
		
	}
	
	
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
	@RequestMapping(value = "/selectOrderByTermList",method= RequestMethod.POST)	
	public void selectOrderByTermList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		adimOrderService.getOrderRatio();
		String recentNewsList = adimOrderService.selectOrderByTermList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	/**
	 * 1.控制台-乾坤大数据-我的订单-导出  根据客户编码查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excleOrderByTermList",method= RequestMethod.POST)
	@ResponseBody
	public String excleOrderByTermList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String productName = (String) parseObject.get("productName");
		String productOrderId = (String) parseObject.get("productOrderId");
		String secondTime = (String) parseObject.get("secondTime");
		String state = (String) parseObject.get("state");
		String firstTime = (String) parseObject.get("firstTime");
		String customerNumber = (String) parseObject.get("customerNumber");
		String result=adimOrderService.excleOrderByTermList(productName,productOrderId,secondTime,state,firstTime,customerNumber);
		return result;
	}
	
	
	
	/**  
	 * 2.控制台-乾坤大数据-我的订单  根据订单编码查看详情页
	 * @author mh
	 */
	@RequestMapping(value = "/selectOrderSubByTermList",method= RequestMethod.POST)
	
	public void selectOrderSubByTermList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminOrderSubService.selectOrderSubByTermList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	/**
	 *   2.控制台-乾坤大数据-我的订单  根据订单编码查看详情页-导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excleOrderSubByTermList",method= RequestMethod.POST)
	@ResponseBody
	public String excleOrderSubByTermList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String productName = (String) parseObject.get("productName");
		String productOrderId = (String) parseObject.get("productOrderId");
		String effTime = (String) parseObject.get("effTime");
		String result=adminOrderSubService.excleOrderSubByTermList(productName,productOrderId,effTime);
		return result;
	}
	
	/**  
	 * 3.控制台-网址卫士与和卫士-我的订单  根据客户编码查询
	 * @author mh
	 */
	@RequestMapping(value = "/selectOrderByCusList",method= RequestMethod.POST)	
	public void selectOrderByCusList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminOrderSubService.selectOrderByCusList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	/**
	 * 3.控制台-网址卫士与和位士-我的订单  根据客户编码查询-导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/excleOrderByCusList",method= RequestMethod.POST)
	@ResponseBody
	public String excleOrderByCusList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String proName = (String) parseObject.get("proName");
		String subOrderNum = (String) parseObject.get("subOrderNum");
		String state = (String) parseObject.get("state");
		String firstTime = (String) parseObject.get("firstTime");
		String secondTime = (String) parseObject.get("secondTime");
		String productName = (String) parseObject.get("productName");
		String customerNumber = (String) parseObject.get("customerNumber");
		String result=adminOrderSubService.excleOrderByCusList(proName,subOrderNum,state,firstTime,secondTime,productName,customerNumber);
		return result;
	}

	/**
	 * 4.后台管理-订单管理
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectOrderByList",method= RequestMethod.POST)	
	public void selectOrderByList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adimOrderService.selectOrderByList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}	
	/**
	 * 5.后台管理-订单管理-订单详情
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectOrderSubList",method= RequestMethod.POST)	
	public void selectOrderSubList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminOrderSubService.selectOrderSubList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}		
	/**
	 * 6.后台管理-订单管理-订单详情-更改分成比例
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updRatio",method= RequestMethod.POST)	
	public void updRatio(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		AdminProRatio adminProRatio=JSON.parseObject(reqMess,AdminProRatio.class);
		Timestamp d = new Timestamp(System.currentTimeMillis()); 
		adminProRatio.setApplyTime(d);
	    int updRatio = adminOrderSubService.updRatio(adminProRatio);
	    String resp=null;
		if(updRatio==2) {
			resp="success";
		}else{
			resp="erro";
		}
		try {
		response.getOutputStream().write(resp.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	
	
	/**
	 * 7.后台管理-订单管理-订单详情-分成比例记录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/selectRatioList",method= RequestMethod.POST)	
	public void selectRatioList(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String recentNewsList = adminOrderSubService.selectRatioList(reqMess);
		try {
		response.getOutputStream().write(recentNewsList.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}	
	/**
	 * 8.门户订购状态（和位士，网址卫士，乾坤大数据（数据标签））
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOrderState",method= RequestMethod.POST)	
	public void getOrderState(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String jsonString = adimOrderService.getOrderState(reqMess);
		
		try {
		response.getOutputStream().write(jsonString.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}	
	
	/**
	 * 9.门户首页产品包名称
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getOrdeProductName",method= RequestMethod.POST)	
	public void getOrdeProductName(HttpServletRequest request,HttpServletResponse response) {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		String jsonString = adimOrderService.getOrdeProductName(reqMess);
		
		try {
		response.getOutputStream().write(jsonString.getBytes("utf-8"));
	    } catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
	/**
	 * 订单管理导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ExportOrderByList",method= RequestMethod.POST)
	@ResponseBody
	public String ExportOrderByList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
    	//导出数据录入
		String[] rowsName = new String[]{"序号","订单号","状态","下单时间","客户名称","产品类别","分成比例状态"};
		List<Object[]>  dataList = new ArrayList<Object[]>();
		Object[] objs = null;
		
		int index=1;
		List<AdminOrder> list = adimOrderService.ExportOrderByList(reqMess);//内容list
		for (AdminOrder adminOrder : list) {
			objs = new Object[rowsName.length];
			objs[0] = index;
			objs[1]=adminOrder.getProductOrderId();
			int state = adminOrder.getState();
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
			objs[3]=DateUtil.toStr(adminOrder.getEffTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
			objs[4]=adminOrder.getCustomerName();
			objs[5]=adminOrder.getParentOrderName();
			String productRatio="";
			if(adminOrder.getProductRatio()==0){
				productRatio="已设置";				   
			}
        	if(adminOrder.getProductRatio()==1){
        		productRatio="待设置";				   
			}
			objs[6]=productRatio;
			dataList.add(objs);
			index++;
		}
		
		ExportExcel export=new ExportExcel("和金融平台-订单管理", rowsName, dataList);
		
		String result=JSON.toJSONString(export);
		return result;
	}
	/**
	 * 订单管理导出
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ExportOrderSubByList",method= RequestMethod.POST)
	@ResponseBody
	public String ExportOrderSubByList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map parseObject = JSONObject.parseObject(reqMess,Map.class);
		String productOrderId = (String) parseObject.get("productOrderId");
		String productName = (String) parseObject.get("productName");
		String customerName = (String) parseObject.get("customerName");
		String effTime = (String) parseObject.get("effTime");
		String contractName = (String) parseObject.get("contractName");
		String result=adminOrderSubService.ExportOrderSubByList(productOrderId,productName,customerName,effTime,contractName);
		return result;
	}
	
}
