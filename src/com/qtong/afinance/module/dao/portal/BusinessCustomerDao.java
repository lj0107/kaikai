package com.qtong.afinance.module.dao.portal;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
/**
 *  门户-控制台首页 -最新动态(当前登录的用户的信息)dao
 */
@Repository
public class BusinessCustomerDao extends BaseJdbcDao{
	public Map<String, Object> selectLogUser(String logUser) {			  
		StringBuffer sqlBuffer=new StringBuffer("SELECT t1.*,tg.parent_customer_number,tg.industry_id,tg.manager_name,tg.manager_phone" + 
				"		FROM afin_boss_customer t1 LEFT join afin_boss_customer_group tg" + 
				"		ON t1.customer_number=tg.customer_number  where t1.login_name=?");
        Object[] params = new Object[]{logUser};	
		 Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
	return forMap;
}

	
}
