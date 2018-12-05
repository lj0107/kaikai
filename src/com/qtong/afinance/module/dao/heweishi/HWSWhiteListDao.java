package com.qtong.afinance.module.dao.heweishi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.heweishi.WhiteList;
import com.qtong.afinance.module.pojo.heweishi.WhiteListMobile;
import com.qtong.afinance.module.pojo.heweishi.WhiteListMobileRowMapper;
import com.qtong.afinance.module.pojo.heweishi.WhiteListRowMapper;

/**
 * 和卫士-白名单列表dao层
 */
@Repository
public class HWSWhiteListDao extends BaseJdbcDao{

	
	/**
	 * 白名单list
	 * @param pageData
	 * @param account
	 * @param startTime
	 * @param endTime
	 * @return
	 * select w.account,w.pwd,w.request_time,m.counts
	 * from afin_admin_lbwhitelist w
	 * left join(
	 * select account,count(mobile) counts
	 * from afin_admin_whitelist_mobile 
	 * group by account) m
	 * on w.account=m.account
	 */
	public PageData queryAll(PageData pageData,String customerNumber,String customerName,String startTime,String endTime) {
		
		List<Object> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("select w.account,w.pwd,w.request_time,w.customer_number,w.customer_name,m.counts from ");				
		sql.append(TableInfo.afin_lb_whitelist).append(" as w left join ("
				+ " select account,count(mobile) counts from ")
			.append(TableInfo.afin_lb_whitelist_mobile)
			.append(" group by account) m ")
			.append(" on w.account=m.account where 1=1");
	
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");		
		sqlCount.append(TableInfo.afin_lb_whitelist).append(" as w left join ("
				+ " select account,count(mobile) counts from ")
			.append(TableInfo.afin_lb_whitelist_mobile)
			.append(" group by account) m ")
			.append(" on w.account=m.account where 1=1");
		
		
		if (customerNumber!=null && customerNumber.trim().length()>0) {//判断订单号是否为空
			sql.append(" and w.customer_number =?");			
			sqlCount.append(" and w.customer_number =?");	
			list.add(customerNumber);
		}
		
		if (customerName!=null && customerName.trim().length()>0) {//判断订单号是否为空
			sql.append(" and w.customer_name like ? ");			
			sqlCount.append(" and w.customer_name like ?");	
			list.add("%"+customerName+"%");
		}
		

		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and w.request_time >= ? ");			
			sqlCount.append(" and w.request_time >= ?");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and w.request_time <= ? ");			
			sqlCount.append(" and w.request_time <= ? ");
			list.add(endTime);
		}
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), list.toArray(), new WhiteListRowMapper()));
		
		return pageData;
		
	}
	
	/**
	 * 查询所有白名单手机号
	 * @param pageData 当前页
	 * @param account 帐号
	 * @param mobile 手机号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public PageData queryAllMobile(PageData pageData,String account,String mobile,String startTime,String endTime) {
		
		List<Object> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select mobile,insert_time from ");				
		sql.append(TableInfo.afin_lb_whitelist_mobile).append(" as m where 1=1 ");
	
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");		
		sqlCount.append(TableInfo.afin_lb_whitelist_mobile).append(" as m where 1=1 ");
		
		if (account!=null && account.trim().length()>0) {
			sql.append(" and m.account = ? ");			
			sqlCount.append(" and m.account = ? ");	
			list.add(account);
		}
		
		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and m.mobile like ? ");			
			sqlCount.append(" and m.mobile like ? ");
			list.add("%"+mobile+"%");
		}

		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and m.insert_time >= ? ");			
			sqlCount.append(" and m.insert_time >= ? ");
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and m.insert_time <= ? ");			
			sqlCount.append(" and m.insert_time <= ? ");
			list.add(endTime);
		}
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), list.toArray(), new WhiteListMobileRowMapper()));
		
		return pageData;
		
	}
	
	/**
	 * 导出所有白名单
	 * @param customerNumber
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WhiteList> exportQueryAll(String customerNumber,String customerName,String startTime,String endTime) {
		List<Object> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("select w.account,w.pwd,w.request_time,w.customer_number,w.customer_name,m.counts from ");				
		sql.append(TableInfo.afin_lb_whitelist).append(" as w left join ("
				+ " select account,count(mobile) counts from ")
			.append(TableInfo.afin_lb_whitelist_mobile)
			.append(" group by account) m ")
			.append(" on w.account=m.account where 1=1");
	
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");		
		sqlCount.append(TableInfo.afin_lb_whitelist).append(" as w left join ("
				+ " select account,count(mobile) counts from ")
			.append(TableInfo.afin_lb_whitelist_mobile)
			.append(" group by account) m ")
			.append(" on w.account=m.account where 1=1");
		
		
		if (customerNumber!=null && customerNumber.trim().length()>0) {//判断订单号是否为空
			sql.append(" and w.customer_number =?");			
			sqlCount.append(" and w.customer_number =?");
			list.add(customerNumber);
		}
		
		if (customerName!=null && customerName.trim().length()>0) {//判断订单号是否为空
			sql.append(" and w.customer_name like ? ");			
			sqlCount.append(" and w.customer_name like ?");
			list.add("%"+customerName+"%");
		}
		

		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and w.request_time >= ? ");			
			sqlCount.append(" and w.request_time >= ? ");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and w.request_time <= ? ");			
			sqlCount.append(" and w.request_time <= ? ");	
			list.add(endTime);
		}
	    return this.getJdbcTemplate().query(sql.toString(), list.toArray(), new WhiteListRowMapper());
	}

	/**
	 * 导出所有白名单手机号
	 * @param account
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<WhiteListMobile> exportWhiteListMobile(String account, String mobile, String startTime, String endTime) {
		
		List<Object> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder("select mobile,insert_time from ");				
		sql.append(TableInfo.afin_lb_whitelist_mobile).append(" as m where 1=1 ");
	
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");		
		sqlCount.append(TableInfo.afin_lb_whitelist_mobile).append(" as m where 1=1 ");
		
		if (account!=null && account.trim().length()>0) {
			sql.append(" and m.account = ? ");			
			sqlCount.append(" and m.account = ? ");	
			list.add(account);
		}
		
		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and m.mobile like ? ");			
			sqlCount.append(" and m.mobile like ? ");
			list.add("%"+mobile+"%");
		}
	
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and m.insert_time >= ? ");			
			sqlCount.append(" and m.insert_time >= ? ");
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and m.insert_time <= ? ");			
			sqlCount.append(" and m.insert_time <= ? ");
			list.add(endTime);
		}
		
		return this.getJdbcTemplate().query(sql.toString(), list.toArray(), new WhiteListMobileRowMapper());
	}
	
}
