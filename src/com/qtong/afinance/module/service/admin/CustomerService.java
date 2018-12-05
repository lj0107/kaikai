package com.qtong.afinance.module.service.admin;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.CustMap;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.dao.admin.CustomerDao;
import com.qtong.afinance.module.pojo.customer.BossCustomer;

/**
 * 客户管理Service层
 */
@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;

	/**
	 * 1.查看详情-通过客户编码（唯一）
	 * @param customerNumber
	 * @return
	 */
	public String selectCusByNumber(String customerNumber) {
	    Map<String, Object> map = customerDao.selectCusByNumber(customerNumber);	
	    String parent_customer_number = map.get("parent_customer_number").toString();
		map.put("parent_customer_number", parent_customer_number);
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
		Map<String, Object> selectCityCoding = customerDao.selectCityCoding(map.get("city").toString());		 
		map.put("city", selectCityCoding.get("ic_name"));
		Map<String, Object> selectCoding = customerDao.selectIndustryIdCoding(map.get("industry_id").toString());		 
		map.put("industry_id", selectCoding.get("ic_name"));
		Map<String, Object> selectNationIdCoding = customerDao.selectNationIdCoding(map.get("nation_id").toString());		 
		map.put("nation_id", selectNationIdCoding.get("ic_name"));
		Map<String, Object> selectCompanyIdCoding = customerDao.selectCompanyIdCoding(map.get("company_id").toString());		 
		map.put("company_id", selectCompanyIdCoding.get("ic_name"));
		Map<String, Object> selectProvinceCoding = customerDao.selectCompanyIdCoding(map.get("province").toString());		 
		map.put("province", selectProvinceCoding.get("ic_name"));
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String BusinessStr = JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);			
		return BusinessStr;
	}
	/**
	 *  2.修改状态(正常或禁用)
	 * @param bossCustomer
	 * @return
	 */
	public int updateStatus(BossCustomer bossCustomer) {
		int updateStick = customerDao.updateStatus(bossCustomer);
		return updateStick;
	}
	
	/**
	 * 3. 根据条件查询
	 * @param customerNumber 客户编码
	 * @param customerName 客户名称
	 * @param loginName 登陆名称
	 * @param pageIndex 当前页号
	 * @param opr_time 同步时间
	 *      opr_time_start 
	 *      opr_time_end
	 * @param operate_time 创建时间(登陆账号)
	 *      operate_time_start
	 *      operate_time_end
	 * @param status 状态
	 * @param cjState 创建状态
	 * @return
	 */
	public String selectCusByTermList(String selectByTerm) {
		JSONObject  jasonObject = JSONObject.parseObject(selectByTerm);
		java.lang.String pageIndex = jasonObject.get("pageIndex").toString();
		Object obCustomerNumber = jasonObject.get("customerNumber");
		String customerNumber="";
		if(!"".equals(obCustomerNumber)&&obCustomerNumber!=null) {
		   customerNumber = obCustomerNumber.toString();
		}
		Object obcustomerName = jasonObject.get("customerName");
		String customerName="";
		if(!"".equals(obcustomerName)&&obcustomerName!=null) {
			customerName = obcustomerName.toString();
		}
		Object obloginName = jasonObject.get("loginName");
		String loginName="";
		if(!"".equals(obloginName)&&obloginName!=null) {
			loginName = obloginName.toString();
		}
		Object oboprTimeStart = jasonObject.get("opr_time_start");
		String oprTimeStart="";
		if(!"".equals(oboprTimeStart)&&oboprTimeStart!=null) {
			oprTimeStart = oboprTimeStart.toString();
		}
		Object obopr_time_end = jasonObject.get("opr_time_end");
		String opr_time_end="";
		if(!"".equals(obopr_time_end)&&obopr_time_end!=null) {
			opr_time_end = obopr_time_end.toString();
		}
		Object oboperate_time_start = jasonObject.get("operate_time_start");
		String operate_time_start="";
		if(!"".equals(oboperate_time_start)&&oboperate_time_start!=null) {
			operate_time_start = oboperate_time_start.toString();
		}
		Object oboperate_time_end = jasonObject.get("operate_time_end");
		String operate_time_end="";
		if(!"".equals(oboperate_time_end)&&oboperate_time_end!=null) {
			operate_time_end = oboperate_time_end.toString();
		}
		Object obstatus = jasonObject.get("status");
		String status="";
		if(!"".equals(obstatus)&&obstatus!=null) {
			status = obstatus.toString();
		}
		Object obcjState = jasonObject.get("cjState");
		String cjState="";
		if(!"".equals(obcjState)&&obcjState!=null) {
			cjState = obcjState.toString();
		}
		PageData pageData = customerDao.selectCusByTermList(pageIndex,customerNumber,customerName,loginName,oprTimeStart,opr_time_end,
				operate_time_start,operate_time_end,status,cjState);
		//List<BossCustomer> selectByKeyword = (List<BossCustomer>) pageData.getLst();
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		String jsonString = JSON.toJSONString(pageData, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);			
		return jsonString;
	}
	/**
	 *4. 创建账号
	 * @param customerNumber 客户编码
	 * @param loginName 登陆名称
	 * @param loginPwd 登陆密码
	 */
	public int insertCus(BossCustomer bossCustomer) {		
		Date date=new Date();
		
		String loginPwd = DigestUtils.md5DigestAsHex(bossCustomer.getLoginPwd().getBytes());
		bossCustomer.setLoginPwd(loginPwd);
		bossCustomer.setOperateTime(Timestamp.valueOf(DateUtil.toStr(date, DateUtil.YYYY_MM_DD_HH_MM_SS)));
		bossCustomer.setUpdpwdTime(Timestamp.valueOf(DateUtil.toStr(date, DateUtil.YYYY_MM_DD_HH_MM_SS)));
		int protalUser = customerDao.insertProtalUser(bossCustomer);
		return protalUser;
	}	
	/**
	 *  验证用户名是否重复
	 * @param advert
	 * @return
	 */
	public String getUserName(String loginName) {
		 BossCustomer userPassword = customerDao.getUserPassword(loginName);
		 String jsonString = JSON.toJSONString(userPassword);
		return jsonString;
	}
	
	
	/**
	 * 查询所有订购了网址卫士的客户
	 * @return
	 */
	public List<BossCustomer> getCustomersWeiShi(){
		List<BossCustomer> customerss = customerDao.getCustomersWeiShi();
		return customerss;
	}
	
	/**
	 * 查询所有客户
	 * @return
	 */
	public List<BossCustomer> getCustomers(){
		return customerDao.getCustomers();
	}
	/**
	 *6. 网址卫士-所属客户名称
	 * @param customerNumber 客户编码
	 * @param customerName 客户名称
	 */
	public String selectCusList() {
		 List<Map<String, Object>> selectCusList = customerDao.selectCusList();
		String jsonString = JSON.toJSONString(selectCusList);		
	 return jsonString;
	}
	/**
	 * 更改负责人
	 * @param bossCustomer
	 * @return
	 */
	public int updatePic(BossCustomer bossCustomer) {
		int updatePic = customerDao.updatePic(bossCustomer);
		return updatePic;
	}

}
