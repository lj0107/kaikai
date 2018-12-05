package com.qtong.afinance.module.dao.statistics;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.bill.BillRowMapper;


import com.qtong.afinance.module.pojo.statistics.OrderCustomerCount;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCountRowMapper;
import com.qtong.afinance.module.pojo.statistics.OrderStats;
import com.qtong.afinance.module.pojo.statistics.OrderStatsRowMapper;

/**
 * 业务统计Dao
 * 对订单中标签请求次数和账单金额进行统计
 */
@Repository
public class OrderStatsDao extends BaseJdbcDao {
	final Logger logger = LoggerFactory.getLogger(OrderStatsDao.class);

	
	/**
	 * 更改账单统计中的产品中文名
	 * @param proName产品中文名
	 * @param proCode产品编码
	 */
	public void updateProName(String productName,String productCode) {
		StringBuffer sql=new StringBuffer("update ");
		sql.append(" afin_order_stats ");
		sql.append(" set product_name=? where product_code=?");
		Object[] params1 = new Object[]{productName,productCode};
		this.getJdbcTemplate().update(sql.toString(),params1);	
	}
	
	/**
	 * 添加新的统计信息（按天）
	 * @param orderStatistical
	 * @return
	 */
	public int insert(OrderStats orderStats){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_order_stats);
		sqlBuffer.append(" (product_order_id,product_code,product_name,record_time,count,customer_number,fee) values (?,?,?,?,?,?,?)");
		Object[] params=new Object[]{orderStats.getProductOrderId(),orderStats.getProductCode(),orderStats.getProductName(),orderStats.getRecordTime(),orderStats.getCount(),orderStats.getCustomerNumber(),orderStats.getFee()};
		logger.info(sqlBuffer.toString());
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	/**
	 * 根据客户编码和订单号、产品编码查询统计信息【时间段内】
	 * @return
	 */
	public List<OrderStats> getOrderStatsByProductOrderId(String productOrderId,String productCode,String beginTime,String endTime){
		
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_order_stats);
		sqlBuffer.append(" where product_order_id=? and product_code=? and record_time BETWEEN ? AND ? ORDER BY record_time");
		Object[] params = new Object[]{productOrderId, productCode, beginTime, endTime};
		List<OrderStats> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new OrderStatsRowMapper(),params);
		
		return query;
	}
	/**
	 * 根据客户编码和订单号、产品编码查询统计信息【时间段内】
	 * @return
	 */
	public List<OrderStats> getOrderStats(String customerNumber,String productOrderId,String productCode,String beginTime,String endTime){
		
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_order_stats);
		sqlBuffer.append(" where customer_number=? and product_order_id=? and product_code=? and record_time BETWEEN ? AND ? ORDER BY record_time");
		Object[] params = new Object[]{customerNumber, productOrderId, productCode, beginTime, endTime};
		List<OrderStats> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new OrderStatsRowMapper(),params);
		
		return query;
	}
	
	/**
	 * 根据客户编码\产品编码查询统计信息【时间段内】
	 * @return
	 */
	public List<OrderStats> getOrderStats(String customerNumber,String productCode,String beginTime,String endTime){
		
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_order_stats);
		sqlBuffer.append(" where product_code=? and record_time BETWEEN ? AND ?");
		
		Object[] params = new Object[]{productCode, beginTime, endTime};
		
		if(customerNumber!=null&&!customerNumber.equals("")){//判断是否查指定的客户
			sqlBuffer.append(" and customer_number =?");
			params=new Object[]{productCode, beginTime, endTime,customerNumber};
		}
		
		List<OrderStats> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new OrderStatsRowMapper(),params);
		
		return query;
	}
	/**
	 * 无业务的月-非乾坤大数据
	 */
	//查询订单生效时间和结束时间select * from afin_order where eff_time>='2017-9-1' and exp_time>="2017-11-1"
	
	public int selectTime(Date enddate,Date begindate,String productName){
		StringBuilder sqlBuffer = new StringBuilder("select count(1) from afin_order where eff_time<=? and exp_time>=? and product_name=? ");
		Object[] params = new Object[]{enddate,begindate,productName};
	    return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),Integer.class, params);
	}
	/**
	 * 无业务的月-非乾坤大数据(判断是否有客户条件)
	 */
	public int selectTime(Date enddate,Date begindate,String productName,String customerNumber){
	   
		SqlHelper sqlHelper = new SqlHelper("afin_order")//
				.addResult("count(1)")
				.addCondition("eff_time<=? and exp_time>=?", enddate,begindate)
				.addCondition(productName!=null && !"".equals(productName)," product_name=?" ,productName)
				.addCondition(customerNumber!=null && !"".equals(customerNumber),"customer_number=?", customerNumber);
			
		return this.getJdbcTemplate().queryForObject(sqlHelper.getQueryCountSql(),Integer.class, sqlHelper.getParameters());
	}

	/**
	 * 无业务的月-乾坤大数据
	 */
	//查询订单生效时间和结束时间select * from afin_order where eff_time>='2017-9-1' and exp_time>="2017-11-1"
	
	public int selectTimeQK(Date enddate,Date begindate){
		StringBuilder sqlBuffer = new StringBuilder("select count(1) from afin_order where eff_time<=? and exp_time>=? and product_name not in('和位士','网址卫士') ");
		Object[] params = new Object[]{enddate,begindate};
	    return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),Integer.class, params);
	}
	/**
	 * 无业务的月-乾坤大数据(判断是否有客户条件)
	 */
	public int selectTimeQK(Date enddate,Date begindate,String customerNumber){
			
		SqlHelper sqlHelper = new SqlHelper("afin_order")//
				.addResult("count(1)")
				.addCondition("eff_time<=? and exp_time>=?", enddate,begindate)
				.addCondition(customerNumber!=null && !"".equals(customerNumber),"customer_number=?", customerNumber);
			
		return this.getJdbcTemplate().queryForObject(sqlHelper.getQueryCountSql(),Integer.class, sqlHelper.getParameters());
	}
	
	
	/**
	 * 无业务的月客户
	 */
	//查询订单生效时间和结束时间select * from afin_order where eff_time>='2017-9-1' and exp_time>="2017-11-1"
	
	public int selectTimeCom(Date enddate,Date begindate){
		StringBuilder sqlBuffer = new StringBuilder("select count(1) from afin_order_customer_count where record_time<=? ");
		Object[] params = new Object[]{enddate};
	    return this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),Integer.class, params);
	}
	
	
	
	
	/**
	 * 条件查询所有统计数据的使用费用
	 * @param customerNumber 客户编码
	 * @param productName 产品名称
	 * @param beginTime 开始时间段
	 * @param endTime 结束时间段
	 * @param flag 是否乾坤大数据
	 * @return
	 */
	public Double getOrderStatsFee(String customerNumber,String productName,String beginTime,String endTime,boolean flag){
		
		
		SqlHelper sqlHelper = new SqlHelper("afin_order_stats")//
			.addResult("Sum(fee) as fee")//
			.addCondition(beginTime!=null&&!beginTime.isEmpty()&&endTime!=null&&!endTime.isEmpty(), "record_time BETWEEN ? AND ?", beginTime,endTime)//
			.addCondition(flag, "product_name !=?", productName)//
			.addCondition(!flag, "product_name =?", productName)//
			.addCondition(customerNumber!=null&&!customerNumber.isEmpty(), "customer_number =?", customerNumber);
			
		
		Map<String, Object> map = this.getJdbcTemplate().queryForMap(sqlHelper.getQueryListSql(),sqlHelper.getParameters());
		return (Double) map.get("fee")==null?0.00:(Double) map.get("fee");
		
	}
	
	
	
	
	//type 0 按日期从小到大正序排序；type 1 按日期从大到小倒序排序；type 2 按次数从大到小倒序排序；type 3 按次数从小到大正序排序；
	public PageData getListByType(int pageIndex, String customerNumber,String productOrderId, String beginTime, String endTime, int timeOrder,int countOrder) {
		
		
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(pageIndex);
		
		
		SqlHelper sqlHelper=new SqlHelper(TableInfo.afin_order_stats)//
								.addCondition(customerNumber!=null&&!customerNumber.isEmpty(), "customer_number =?", customerNumber)//
								.addCondition(productOrderId!=null&&!productOrderId.isEmpty(), "product_order_id=?", productOrderId)//
								.addCondition(beginTime!=null&&!beginTime.isEmpty(), "record_time>=?", beginTime)//
								.addCondition(endTime!=null&&!endTime.isEmpty(), "record_time<=?", endTime)//
								.addOrder(timeOrder==1||timeOrder==2, "record_time", timeOrder==1?true:false)//
								.addOrder(countOrder==1||countOrder==2, "count", countOrder==1?true:false);
		
		
		PageData queryPage = this.queryPage(pageData,sqlHelper, new OrderStatsRowMapper());		       			
		return queryPage;	

		
		
	}
	
	/**
	 * 根据条件查询出所有统计信息
	 * @param customerNumber
	 * @param productOrderId
	 * @param str
	 * @param str2
	 * @return
	 */
	public List<OrderStats> getList(String customerNumber,String productOrderId, String beginTime, String endTime) {
		
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_order_stats);
		sqlBuffer.append(" where customer_number=? and product_order_id=? and record_time BETWEEN ? AND ?");
		
		Object[] params = new Object[]{customerNumber,productOrderId, beginTime, endTime};
		
		
		List<OrderStats> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new OrderStatsRowMapper(),params);
		
		
		return query;
	}
	/**
	 * 查询所有的lev=1的数据
	 * @param i
	 * @param beginTime
	 * @param endTimae
	 * @return
	 */
	public List<Map<String, Object>> selectType(){
		StringBuilder sqlBuffer = new StringBuilder("select package_code,type_name from afin_product_type WHERE lev=1");
	    List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
	    return queryForList;
	}
	/**
	 * 查询产品的客户次数(按天)
	 * @param i
	 * @param beginTime
	 * @param endTimae
	 * @return
	 */

	public List<Map<String, Object>> selectCustomerCount(String i,String beginTime,String endTimae){
		StringBuilder sqlBuffer = new StringBuilder("SELECT pro_name,ao.customer_number from afin_order ao  " + 
				"				 RIGHT  JOIN   " + 
				"				 (select aos.product_order_id,aos.pro_name from afin_order_sub   " + 
				"			 aos LEFT  JOIN afin_product ap   " + 
				"				 on aos.pro_code=ap.product_code where    " + 
				"				 ap.pro_type_id in   " + 
				"				 (select package_code from afin_product_type WHERE  " + 
				"			 package_code like ?) and aos.state in(1,4) )t1 " + 
				"			 on ao.product_order_id=t1.product_order_id where ao.eff_time BETWEEN ? and ? " );
		Object[] params = new Object[]{""+i+"%",beginTime,endTimae};
	    List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
	    return queryForList;
	}
	/**
	 * 增加
	 * @param i
	 * @return
	 */
	public void insertCustomerCount(final List<OrderCustomerCount> list) {  		
        String sql = "insert into afin_order_customer_count (pro_name,customer_number,product_name,record_time) values(?,?,?,?)";  
          
        this.getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {	
            public int getBatchSize() {  
                return list.size();  
                //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
            }  
            public void setValues(PreparedStatement ps, int i)throws SQLException { 	            	
                ps.setString(1, list.get(i).getProName());   
                ps.setString(2, list.get(i).getCustomerNumber());   
                ps.setString(3, list.get(i).getProductName());   
                ps.setTimestamp(4, list.get(i).getRecordTime());   
            }  
        });  
    }  
	
	/**
	 * 根据时间统计出上一月订单信息
	 * @param dateStr
	 * @return
	 */
	public List<Bill> getOrderStatsByMonth(String dateStr){

		StringBuilder sqlBuffer = new StringBuilder("select o.product_order_id,o.eff_time order_time,o.customer_number,o.customer_name,o.product_name,os.fee ");
		sqlBuffer.append(" from afin_order o left join ( select t.product_order_id,SUM(t.fee) fee from")
			.append(" (select * from afin_order_stats where record_time like ?) t GROUP BY t.product_order_id) os")
			.append(" on o.product_order_id = os.product_order_id where o.product_name != '网址卫士' and o.state!=2 and o.state!=4 ");
		
		Object[] params = new Object[]{""+dateStr+"%"};
		
		List<Bill> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new BillRowMapper(),params);
		
		return query;
	}
	
	/**
	 * 根据时间统计出上一月订单信息
	 * @param dateStr
	 * @return
	 */
	public List<Map<String,Object>> getAllWWSOrder(String dateStr){
		
		StringBuilder sqlBuffer = new StringBuilder();
		sqlBuffer.append("select o.product_order_id,o.product_name,o.eff_time,o.exp_time,o.customer_number,o.customer_name,os.price,os.discount,os.sum from afin_order o") 
		        	.append(" left join afin_order_sub os")
		        	.append(" on o.product_order_id = os.product_order_id")
					.append(" where o.product_name = '网址卫士' and o.eff_time < ?");
		
		Object[] params = new Object[]{""+dateStr+"%"};
		
		return this.getJdbcTemplate().queryForList(sqlBuffer.toString(), params);
		
	}

	
	
}
