package com.qtong.afinance.module.dao.record;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.statistics.RequestCounts;

/**
 * 控制台-乾坤大数据-业务统计-详情Dao
 *
 */
@Repository
public class RecordDao extends BaseJdbcDao {
	
	
	
	/**
	 * 获取每天的对应客户和产品的使用费用
	 * @param customerNumber
	 * @param proCode
	 * @return 
	 * @return
	 */
	public  Map<String, Object> getStatsCondition(String orderId){
		StringBuffer sqlBuffer=new StringBuffer("Select SUM(fee) as fee, Count(*) as count from ");
		sqlBuffer.append("afin_record");
		sqlBuffer.append(" where order_id =? and to_timestamp(begin_time,'YYYYMMDDHH24MISS') BETWEEN ? AND ?");
		Object[] params = new Object[]{orderId,new Timestamp(DateUtil.getBeforeDay(DateUtil.getMinDayDateTime(new Date())).getTime()),new Timestamp(DateUtil.getBeforeDay(DateUtil.getMaxDayDateTime(new Date())).getTime())};
		
		return this.getPostgreJdbcTemplate().queryForMap(sqlBuffer.toString(), params);
	}
	
	
	
	
	/**
	 * 获取每天的对应客户和产品的请求次数
	 * @param customerNumber
	 * @param proCode
	 * @return
	 */
	public int getCountByCondition(String orderId){
		
		SqlHelper sqlHelper = new SqlHelper("afin_record")//
			.addCondition("order_id =?", orderId)//
			.addCondition("to_timestamp(begin_time,'YYYYMMDDHH24MISS') BETWEEN ? AND ?", new Timestamp(DateUtil.getBeforeDay(new Date()).getTime()),new Timestamp(new Date().getTime()));
		
	return this.getPostgreJdbcTemplate().queryForInt(sqlHelper.getQueryCountSql(),sqlHelper.getParameters());
	}
	
	/**
	 * 获取每天的对应客户和产品的使用费用
	 * @param customerNumber
	 * @param proCode
	 * @return 
	 * @return
	 */
	public  Double getFeeByCondition(String orderId){
		StringBuffer sqlBuffer=new StringBuffer("Select SUM(fee) as fee from ");
		sqlBuffer.append("afin_record");
		sqlBuffer.append(" where order_id =? and to_timestamp(begin_time,'YYYYMMDDHH24MISS') BETWEEN ? AND ?");
		Object[] params = new Object[]{orderId,new Timestamp(DateUtil.getBeforeDay(DateUtil.getMinDayDateTime(new Date())).getTime()),new Timestamp(DateUtil.getBeforeDay(DateUtil.getMaxDayDateTime(new Date())).getTime())};
		Map<String, Object> map = this.getPostgreJdbcTemplate().queryForMap(sqlBuffer.toString(), params);//(sqlBuffer.toString(),params);
		
	    return (Double) map.get("fee");
		
	}
	
	/**
	 * 统计乾坤大数据每天详单的请求次数
	 * @param date
	 * @return
	 * 
	 */
	public List<Map<String, Object>> queryCounts() {
		StringBuffer sql = new StringBuffer();
		sql.append("select product_package_ord_id,count(1) as counts ")
		   .append(" from (select * from afin_datamark_msg where request_time BETWEEN ? AND ?) m ") 
			.append(" group by m.product_package_ord_id");
		Object[] params = new Object[]{new Timestamp(DateUtil.getBeforeDay(DateUtil.getMinDayDateTime(new Date())).getTime()),new Timestamp(DateUtil.getBeforeDay(DateUtil.getMaxDayDateTime(new Date())).getTime())};
		
		return this.getPostgreJdbcTemplate().queryForList(sql.toString(), params);
	}
	
	/**
	 * 批量插入请求次数表
	 * @param requestCounts
	 * @return
	 */
	public int insertRequestCounts(List<RequestCounts> requestCounts) {
		final List<RequestCounts> list = requestCounts;
		 final String sql = "insert into afin_request_counts(product_package_order_id,count,record_time) values(?,?,?)";
	        int[] batchUpdate = this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
	            
	            public void setValues(PreparedStatement ps, int i) throws SQLException {
	               
	                ps.setString(1, list.get(i).getProductOrderId());
	                ps.setInt(2, list.get(i).getCount());
	                ps.setTimestamp(3, list.get(i).getRecordTime());
	            }
	            
	            public int getBatchSize() {
	                return list.size();
	            }
	        });
	      
		return batchUpdate.length;
	}
}