package com.qtong.afinance.module.controller.portal;



import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.domain.ResultObject;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.consulting.Consulting;
import com.qtong.afinance.module.service.portal.ConsultingService;



@Controller("portalConsulting")
@RequestMapping("/consulting")
public class ConsultingController {
	@Autowired
	private ConsultingService consultingService;
	
	/**
	 * 门户-项目咨询 存储数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/insertConsulting",method= RequestMethod.POST)
	@ResponseBody
	public ResultObject insertConsulting(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//1、接收北向接口透传的行业客户request密文
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);	
		Map map=JSON.parseObject(reqMess, Map.class);//将Json转成java对象
		String code=(String) map.get("code");
		String keys=(String) map.get("keys");
		String consulting= (String) map.get("consulting");
		
		Consulting consult=JSON.parseObject(consulting, Consulting.class);//将Json转成java对象
		
		
		ResultObject result = consultingService.insertConsulting(code,keys,consult);//调用Service方法
		return result;
		
	}
	
}
