package com.qtong.afinance.module.quartz;

import java.util.HashMap;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


@Controller
@Component
@RequestMapping("/refreshQuartz")
public class RefreshQuartz {

	private static int count=1;
	private static int time=0;
	
	/**
	 * 判断刷不刷新页面
	 */
	@RequestMapping(value = "/refresh",method= RequestMethod.POST)
	@ResponseBody
  public String  refresh() {
	Map<String, Integer> hashMap = new HashMap<String,Integer>();
	  if(count==0) {
		if(time==1) {
			count=1;
		}
	}else {
		time=0;
	}
	hashMap.put("time", time);
	return JSON.toJSONString(hashMap);
  }
	
	//0 0 0 1 * ?
    //@Scheduled(cron = "0 0 0 1 * ? ")//每月1日0点执行
	@Scheduled(cron = "0 10 0 1 * ?")//每月第一天的零点10分执行
    public void execute() {
    	
    	 time=1;
   	  	 count=0;
    	
    }
	
	
}
