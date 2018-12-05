package com.qtong.afinance.module.dao.myProfile;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.pojo.customer.BossCustomerMapper;

@Repository
public class BusinessMyProfileDao extends BaseJdbcDao{

	/**
	 * 
	 * @param customerNumber
	 * @return
	 */

	public  Map<String, Object> getMyProfileList(String customerNumber) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer );
		sqlBuffer.append(" t1 LEFT join ");
		sqlBuffer.append(TableInfo.afin_boss_customer_group );
		sqlBuffer.append(" t2 ON t1.customer_number=t2.customer_number where t1.customer_number=?");
        Object[] params = new Object[]{customerNumber};	
        Map<String, Object> list = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
	    return list;
	}
	/**
	 *查询码表 行业
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="省编码" and id="100"
	 */
	public Map<String, Object> selectIndustryIdCoding(String industryId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type='行业编码'");
        Object[] params = new Object[]{industryId};	
        try {
			Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
			return forMap;
		} catch (DataAccessException e) {
			return null;
		}
	}
	/**
	 *查询码表 国家
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="省编码" and id="100"
	 */
	public Map<String, Object> selectNationIdCoding(String nationId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type='国家编码'");
        Object[] params = new Object[]{nationId};	
        try {
			Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
			return forMap;
		} catch (DataAccessException e) {
			return null;
		}
	}
	/**
	 *查询码表 省
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="" and id="100"
	 */
	public Map<String, Object> selectCompanyIdCoding(String companyId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type='省编码'");
        Object[] params = new Object[]{companyId};	
        try {
			Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
			return forMap;
		} catch (DataAccessException e) {
			return null;
		}
	}
	

	public int updateMyProfilePassword(BossCustomer advert) {
		StringBuffer sqlBuffer = new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" set login_pwd=?,updpwd_time=? where Customer_number=?");
		Object[] params = new Object[]{advert.getLoginPwd(),advert.getUpdpwdTime(),advert.getCustomerNumber()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}


	public BossCustomer getUserPassword(String customer_Number) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" where Customer_number='"+customer_Number+"'");
        BossCustomer queryForObject = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(), new BossCustomerMapper());
		return queryForObject;
	}
	
}
