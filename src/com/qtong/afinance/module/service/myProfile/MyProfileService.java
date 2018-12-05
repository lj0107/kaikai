package com.qtong.afinance.module.service.myProfile;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.qtong.afinance.core.util.CustMap;
import com.qtong.afinance.module.dao.myProfile.BusinessMyProfileDao;
import com.qtong.afinance.module.pojo.customer.BossCustomer;

@Service
@Transactional
public class MyProfileService {
	@Autowired
	private BusinessMyProfileDao businessMyProfileDao;

	public String getMyProfileList(String customerNumber) {
		    Map<String, Object> map = businessMyProfileDao.getMyProfileList(customerNumber);		    	   
			String icType = CustMap.icTypeHashMap.get(map.get("ic_type").toString());
			map.put("ic_type", icType);
			String employeeCount = CustMap.ecHashMap.get(map.get("employee_count").toString());
			map.put("employee_count", employeeCount);
			String orgType = CustMap.orgTypeHashMap.get(map.get("org_type").toString());
			map.put("org_type", orgType);
			String serviceLevel = CustMap.slHashMap.get(map.get("service_level").toString());
			map.put("service_level", serviceLevel);
			String turnover = CustMap.turnoverHashMap.get(map.get("turnover").toString());
			map.put("turnover", turnover);		
			Map<String, Object> selectCoding = businessMyProfileDao.selectIndustryIdCoding(map.get("industry_id").toString());		 
			if(selectCoding!=null)
				map.put("industry_id", selectCoding.get("ic_name"));
			Map<String, Object> selectNationIdCoding = businessMyProfileDao.selectNationIdCoding(map.get("nation_id").toString());		 
			if(selectNationIdCoding!=null)
				map.put("nation_id", selectNationIdCoding.get("ic_name"));
			Map<String, Object> selectCompanyIdCoding = businessMyProfileDao.selectCompanyIdCoding(map.get("company_id").toString());		 
			if(selectCompanyIdCoding!=null)
				map.put("company_id", selectCompanyIdCoding.get("ic_name"));
			String BusinessStr = JSON.toJSONString(map);		
			return BusinessStr;
	}


	public int updateMyProfilePassword(BossCustomer advert) {
		return businessMyProfileDao.updateMyProfilePassword(advert);
	}


	public String getUserPassword(BossCustomer advert) {
		String customerNumber = advert.getCustomerNumber();
		BossCustomer bossCustomer = businessMyProfileDao.getUserPassword(customerNumber);
		String loginPwd = bossCustomer.getLoginPwd();
		return loginPwd;
	}


}
