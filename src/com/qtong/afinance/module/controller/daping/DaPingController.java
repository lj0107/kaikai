package com.qtong.afinance.module.controller.daping;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.module.service.daping.DaPingService;

@Controller
@RequestMapping("/daPing")
public class DaPingController {

	@Autowired
	private DaPingService daPingService;
	
	
	@RequestMapping(value = "/getCartForBigDataLine",method= RequestMethod.POST)
	@ResponseBody
	public String getCartForBigDataLine() {
		
		Map<String, Object> cartForBigDataLine = daPingService.getCartForBigDataLine();
		return JSON.toJSONString(cartForBigDataLine);
	}
	
	@RequestMapping(value = "/getCartForTaiBaoLine",method= RequestMethod.POST)
	@ResponseBody
	public String getCartForTaiBaoLine() {
		Map<String, Object> cartForTaiBaoLine = daPingService.getCartForTaiBaoLine();
		return JSON.toJSONString(cartForTaiBaoLine);
	}
	
}
