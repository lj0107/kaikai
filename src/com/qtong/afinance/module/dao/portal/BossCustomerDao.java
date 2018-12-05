package com.qtong.afinance.module.dao.portal;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.pojo.customer.BossCustomerMapper;

/**
 * 客户dao
 *
 */
@Repository
public class BossCustomerDao extends BaseJdbcDao{
	
	final org.slf4j.Logger logger = LoggerFactory.getLogger(BossCustomerDao.class);
	
	/**
	 * 验证登录客户
	 * @param loginName
	 * @param loginPwd
	 * @return 
	 */
	public BossCustomer getCustomer(String loginName,String loginPwd){
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" where login_name=? and login_pwd=? and status=?"); 	
		Object[] params = new Object[]{loginName,loginPwd,0};
		logger.info(sqlBuffer.toString());
		try {
			return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new BossCustomerMapper(),params);
		} catch (EmptyResultDataAccessException e) {
		}
		return null;
	}
	
	
	/**
	 * 根据用户登录名称查询用户列表
	 * @param loginName
	 * @return
	 */
	public List<BossCustomer> getCustomers(String loginName){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" where login_name=? and status=?"); 
		Object[] params = new Object[]{loginName,0};
		logger.info(sqlBuffer.toString());
		List<BossCustomer> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new BossCustomerMapper(),params);
		
		return query;
	}
	
	
}
