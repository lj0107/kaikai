package com.qtong.afinance.module.controller.search;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.HttpTool;
import com.qtong.afinance.module.pojo.search.SearchResult;
import com.qtong.afinance.module.service.search.SearchService;

@Controller
@RequestMapping("/search")
public class searchController {
	@Autowired
	SearchService searchService;
	
    /**
     * 门户搜索
     * @param queryString 查询条件
     * @param pageIndex 当前页
     * @param rows 每页显示条数
     * @return
     * @throws Exception
     */
	@RequestMapping("/search")
	@ResponseBody
    public SearchResult search(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	
		String reqMess=HttpTool.javaProtogenesisGetRequest(request);
		Map map=JSON.parseObject(reqMess,Map.class);
		
		String queryString=(String) map.get("queryString");
		Integer pageIndex=Integer.valueOf( (String) map.get("pageIndex"));
        return searchService.search(queryString, pageIndex, 10);
    }
	
}

