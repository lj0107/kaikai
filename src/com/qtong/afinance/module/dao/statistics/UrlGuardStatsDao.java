package com.qtong.afinance.module.dao.statistics;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.statistics.UrlGuardStats;
import com.qtong.afinance.module.pojo.statistics.UrlGuardStatsRowMapper;

/**
 * 网址卫士业务统计Dao
 *
 */
@Repository
public class UrlGuardStatsDao extends BaseJdbcDao{
	/**
	 * 添加新的统计信息（按天）
	 * @param orderStatistical
	 * @return
	 */
	public int insert(UrlGuardStats stats){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_urlguard_stats);
		
		sqlBuffer.append(" (customer_number,state,record_time,count) values (?,?,?,?)");
		Object[] params=new Object[]{stats.getCustomerNumber(),stats.getState(),stats.getRecordTime(),stats.getCount()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	/**
	 * 根据客户编码查询对应的网址状况
	 * @return
	 */
	public List<UrlGuardStats> getUrlGuardStats(String customerNumber,String state,String beginTime,String endTime){
		
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_urlguard_stats);
		sqlBuffer.append(" where customer_number=? and state=? and record_time BETWEEN ? AND ? ORDER BY record_time");
		Object[] params = new Object[]{customerNumber,state,beginTime, endTime};
		List<UrlGuardStats> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new UrlGuardStatsRowMapper(),params);
		
		return query;
	}
}
