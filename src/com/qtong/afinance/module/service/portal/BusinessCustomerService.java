package com.qtong.afinance.module.service.portal;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.CustMap;
import com.qtong.afinance.module.dao.admin.CustomerDao;
import com.qtong.afinance.module.dao.portal.BusinessCustomerDao;

/**
 *  门户-控制台首页 -最新动态(当前登录的用户的信息)service
 */
@Service
@Transactional
public class BusinessCustomerService {
	@Autowired
	private BusinessCustomerDao businessCustomerDao;
	@Autowired
	private CustomerDao customerDao;
	public String selectLogUser(String logUser) {
		 Map<String, Object> selectLogUser = businessCustomerDao.selectLogUser(logUser);
		 String industryId = selectLogUser.get("industry_id").toString();//行业类别
		 Map<String, Object> selectCoding = customerDao.selectIndustryIdCoding(industryId);		 
		 selectLogUser.put("industry_id", selectCoding.get("ic_name"));
		 String icType = CustMap.icTypeHashMap.get(selectLogUser.get("ic_type").toString());//证件类型
		 selectLogUser.put("ic_type",icType);
		String bossCustomer = JSON.toJSONString(selectLogUser);
		return bossCustomer;
	}

}
