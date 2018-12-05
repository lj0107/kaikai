package com.qtong.afinance.module.dao.portal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.product.AdminOrder;
import com.qtong.afinance.module.pojo.product.AdminOrderRowMapper;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.product.AdminOrderSubRowMapper;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCount;
import com.qtong.afinance.module.pojo.statistics.OrderCustomerCountRowMapper;

/**
 * 订单Dao
 */
@Repository
public class AdminOrderDao extends BaseJdbcDao {
	/**
	 * 1.控制台-乾坤大数据-我的订单  根据客户编码查询
	 * @param pageIndex  当前页
	 * @param customer_number 客户编码
	 * @param productName  产品包名称
	 * @param productOrderId  产品包订购关系ID（订单号）
	 * @param state  产品包状态
	 * 下单（订购）生效时间 范围
	 * @param firstTime  1.第一时间
	 * @param secondTime 2.第二时间
	 * @param productC 数据标签
	 */
	public PageData selectOrderByTermListNew(String pageIndex,String productC,String customer_number,String productName, String productOrderId, String state,String firstTime,String secondTime) {			
		
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String[] split=null;
		if(state!=null&&!state.equals("")) {
			split= state.split(",");
		}
		SqlHelper sqlHelper=new SqlHelper("afin_order")//
								.addCondition( " product_name!=?", "网址卫士")//
								.addCondition( " product_name!=?", "和位士")//
								.addCondition( " customer_number=?", customer_number)//
								.addConditionLike(productName!=null && productName.trim().length()>0, "product_name like ?", productName)//
								.addConditionLike(productOrderId!=null && productOrderId.trim().length()>0, "product_order_id like ?", productOrderId)//
								.addCondition(!"".equals(firstTime)&&firstTime!=null, "eff_time>=?", firstTime)//
								.addCondition(!"".equals(secondTime)&&secondTime!=null, "eff_time<=?", secondTime);//
								//.addCondition(state!=null && state.trim().length()>0, "state in(?,?)", 1,4)//
								if(split!=null) {
									if(split.length==1) {
										sqlHelper.addCondition("state in(?)", split[0]);//
									}else if (split.length==2) {
										sqlHelper.addCondition("state in(?,?)", split[0],split[1]);//
									}
								}
								sqlHelper.addOrder( "eff_time", false);
		
		PageData queryPage = this.queryPage(pageData,sqlHelper, new AdminOrderRowMapper());		       			
		return queryPage;	
	}
	/**
	 * 2.后台管理-订单管理
	 * @param pageIndex  当前页
	 * @param customer_number 客户编码
	 * @param customerName 客户名称
	 * @param productName  产品包名称
	 * @param productOrderId  产品包订购关系ID（订单号）
	 * @param state  产品包状态
	 * @param productRatio  产品包分成比例
	 * 下单（订购）生效时间 范围
	 * @param firstTime  1.第一时间
	 * @param secondTime 2.第二时间
	 */
	public PageData selectOrderByTermList(String pageIndex,String timeType,String countsType,String customerName,String productName,
			        String productOrderId, String state,String firstTime,String secondTime,String productRatio) {	
		
		
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		Object[] params = new Object[]{};
		StringBuilder sql = new StringBuilder();		
		List<Object> listSql = new ArrayList<>();
		StringBuilder sqlCount = new StringBuilder(" select count(1) from afin_order  where 1=1 ");
				
		if (productName!=null && productName.trim().length()>0) {
			sql.append(" and parent_order_name like ? ");			
			sqlCount.append(" and parent_order_name like ? ");	
			listSql.add("%" + productName + "%");
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and customer_name like ?");			
			sqlCount.append(" and customer_name like ? ");	
			listSql.add("%" + customerName + "%");
		}
		if (productOrderId!=null && productOrderId.trim().length()>0) {
			sql.append(" and product_order_id like ? ");			
			sqlCount.append(" and product_order_id like ? ");			
			listSql.add("%" + productOrderId + "%");
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and eff_time>=?");
			sqlCount.append(" and eff_time>=?");
			listSql.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and eff_time<=?");
			sqlCount.append(" and eff_time<=?");
			listSql.add(secondTime);
		}
		if (state!=null && state.trim().length()>0) {
				sql.append(" and state in(?) ");			
				sqlCount.append(" and state in(?) ");	
				listSql.add(state);
		}	
		if (productRatio!=null && productRatio.trim().length()>0) {
			sql.append(" and product_ratio=? ");			
			sqlCount.append(" and product_ratio=? ");	
			listSql.add(productRatio);
		}	
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,listSql.toArray()).intValue());
		StringBuilder sql1 = new StringBuilder();		
		if(!"".equals(timeType)&&timeType!=null) {
			sql1.append("select * from  afin_order where 1=1 "+ sql.toString()+" order by convert(customer_name USING gbk) COLLATE gbk_chinese_ci  "+timeType+" ");
		}
		else if(!"".equals(countsType)&&countsType!=null) {
			sql1.append( "select * from  afin_order where 1=1 "+ sql.toString()+" order by  eff_time "  + countsType);
		}
		else {
			sql1.append("select * from afin_order where 1=1 "+ sql.toString()+"   " + 
				       " order by eff_time   desc, convert(customer_name USING gbk) COLLATE gbk_chinese_ci asc" );
		}			
		sql1.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sql1.toString(), listSql.toArray(), new AdminOrderRowMapper()));
		
		return pageData;
	}
	/**
	 * 2.后台管理-订单管理-导出excle表
	 */
	public List<AdminOrder> selectOrderByTermListDaoChu(String timeType,String countsType,String customerName,String productName,
			        String productOrderId, String state,String firstTime,String secondTime,String productRatio) {	
		
	
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select orderNumber, from  afin_order where 1=1 ");				
			
		if (productName!=null && productName.trim().length()>0) {
			sql.append(" and product_name like ? ");		
			listSql.add("%" + productName + "%");
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and customer_name like ? ");		
			listSql.add("%" + customerName + "%");
		}
		if (productOrderId!=null && productOrderId.trim().length()>0) {
			sql.append(" and product_order_id like ? ");	
			listSql.add("%" + productOrderId + "%");
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and eff_time>=?");
			listSql.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and eff_time<=?");
			listSql.add(secondTime);
		}		
		if (state!=null && state.trim().length()>0) {
			sql.append(" and state in(?) ");	
			listSql.add(state);
		}		
		if (productRatio!=null && productRatio.trim().length()>0) {
			sql.append(" and product_ratio=? ");		
			listSql.add(productRatio);
		}	
		sql.append(" order by ");
		if(!"".equals(timeType)&&timeType!=null) {
			sql.append(" convert(customer_name USING gbk) COLLATE gbk_chinese_ci ? , ");
			listSql.add(timeType);
		}
		if(!"".equals(countsType)&&countsType!=null) {
			sql.append( " eff_time ?");
			listSql.add(countsType);
		}else {
			sql.append( " eff_time  desc" );
		}
	    List<AdminOrder> pageData= this.getJdbcTemplate().query(sql.toString(),new AdminOrderRowMapper(),listSql.toArray());	
		return pageData;
		
	}
	
	/**
	 * 产品包名称 分成比例状态
	 * @return
	 */
	
	 public List<Map<String, Object>> getRatio(){
		StringBuilder sqlBuffer = new StringBuilder("select ao.product_order_id,ratio from afin_order ao LEFT JOIN ");
		sqlBuffer.append("  afin_order_sub aos on ao.product_order_id=aos.product_order_id where ratio is null or ratio=''  GROUP BY ao.product_order_id,ratio ");
	
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString());	
		return queryForList;
	}
	 public void batchUpdateLinkset(final List<AdminOrder> list) {  		
	        String sql = "update afin_order set product_ratio=? where product_order_id=?";  
	        this.getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {	
	            public int getBatchSize() {  
	                return list.size();  
	                //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
	            }  
	            public void setValues(PreparedStatement ps, int i)throws SQLException { 	            	
	                ps.setInt(1, list.get(i).getProductRatio());   
	                ps.setString(2, list.get(i).getProductOrderId());   
	            }  
	        });  
	    }  
	
	
	/**
	 * 查询所有订单
	 * @return
	 */
	public List<AdminOrder> getOrders(){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(" afin_order where state in(1,4)");
		List<AdminOrder> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminOrderRowMapper());
		
		return query;
	}
	

	/**
	 * 根据客户编码、产品名称获取产品包订单
	 * @return
	 */
	public List<AdminOrder> getOrders(String customerNumber,String productName){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(" afin_order ");
		sqlBuffer.append(" WHERE customer_number=? and product_name=?");
		
		Object[] params = new Object[]{customerNumber,productName};
		List<AdminOrder> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminOrderRowMapper(),params);
		
		return query;
	}
	
	
	/**
	 * 根据客户编码、产品名称获取产品包订单
	 * @return
	 */
	public PageData getOrders(int pageIndex, String customerNumber,String productName) {
		
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(pageIndex);
		pageData.setPageSize(2);
		String tableName=" afin_order ";//表名
		String fieldSelect="";
		
		String fieldOrderName="";
		String fieldOrderDirection="";
		

		List<Object> listSql = new ArrayList<>();
		StringBuilder strWhere=new StringBuilder();
		strWhere.append(" 1=1");		
		if(!"".equals(customerNumber)&&customerNumber!=null) {
			strWhere.append(" and");
			strWhere.append(" customer_number = ?");
			listSql.add(customerNumber);
		}
		if(!"".equals(productName)&&productName!=null) {
			strWhere.append(" and");
			strWhere.append(" product_name=?");
			listSql.add(productName);
		}
		
		 Object[] params = new Object[]{};
		PageData queryPage = this.queryPage(pageData,tableName,"product_order_id",fieldSelect,fieldOrderName,fieldOrderDirection,strWhere.toString(), listSql.toArray(), new AdminOrderRowMapper());		       			
		return queryPage;	
		
	}

	
	
	/**
	 * 根据产品包订购关系Id获取产品包订单
	 * @return
	 */
	public AdminOrder getOrderByProductOrderId(String productOrderId){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(" afin_order ");
		sqlBuffer.append(" WHERE product_order_id=?");
		
		Object[] params = new Object[]{productOrderId};
		AdminOrder query=this.getJdbcTemplate().queryForObject(sqlBuffer.toString(), new AdminOrderRowMapper(),params);
		
		return query;
	}
	/**
	 * 5.门户订购状态（和位士，网址卫士，乾坤大数据（数据标签））
	 * productName  产品包名称
	 * @return
	 */
	public List<Map<String, Object>> getOrderState(String productName,String customerNumber){
		StringBuilder sqlBuffer = new StringBuilder("select state from ");
		sqlBuffer.append(" afin_order ");
		sqlBuffer.append(" WHERE product_name=? and customer_number=?");
		Object[] params = new Object[]{productName,customerNumber};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	}
	/**
	 * 6.门户首页产品包名称
	 * productName  产品包名称
	 * @return
	 */
	public List<Map<String, Object>> getOrdeProductName(String customerNumb){
		String time=DateUtil.toStr(DateUtil.getPerDayOfMonth(new Date(), -6), DateUtil.YYYY_MM_DD_HH_MM_SS);
		StringBuilder sqlBuffer = new StringBuilder("select apt.type_name parent_name FROM   " + 
				"				afin_product_type apt   " + 
				"				where apt.package_code in (   " + 
				"				select LEFT(package_code,1) from  " + 
				"				(select  DISTINCT(type_name),package_code   from afin_product_type  apt LEFT JOIN   " + 
				"								(select aap.pro_type_id,AFIN_time,t1.customer_number,t1.state from afin_product aap LEFT JOIN    " + 
				"							(select aos.pro_code,AFIN_time,ao.customer_number,ao.state from afin_order ao LEFT JOIN    " + 
				"								afin_order_sub aos on ao.product_order_id=aos.product_order_id )  t1     " + 
				"								on  t1.pro_code=aap.product_code)t2    " + 
				"								on t2.pro_type_id=apt.package_code where t2.customer_number=? and t2.AFIN_time>?  )t3 )" );
		Object[] params = new Object[]{customerNumb,time};
		
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	
	}
	
/*	public List<Map<String, Object>> getOrdeProductName(String customerNumb){
		//String time=DateUtil.toStr(DateUtil.getPerDayOfMonth(new Date(), -6), DateUtil.YYYY_MM_DD_HH_MM_SS);
		StringBuilder sqlBuffer = new StringBuilder("select apt.type_name parent_name FROM   " + 
				"				afin_product_type apt   " + 
				"				where apt.package_code in (   " + 
				"				select LEFT(package_code,1) from  " + 
				"				(select  DISTINCT(type_name),package_code   from afin_product_type  apt LEFT JOIN   " + 
				"								(select aap.pro_type_id,AFIN_time,t1.customer_number,t1.state from afin_product aap LEFT JOIN    " + 
				"							(select aos.pro_code,AFIN_time,ao.customer_number,ao.state from afin_order ao LEFT JOIN    " + 
				"								afin_order_sub aos on ao.product_order_id=aos.product_order_id )  t1     " + 
				"								on  t1.pro_code=aap.product_code)t2    " + 
				"								on t2.pro_type_id=apt.package_code where  t2.customer_number=?   )t3 )" );
		Object[] params = new Object[]{customerNumb};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	}*/
	/**
	 *  7.门户首页产品包名称(客户产品全部退订)
	 * @param customerNumb
	 * @return
	 */
	public Boolean getOrdeProductNameNew(String customerNumb){
		String time=DateUtil.toStr(DateUtil.getPerDayOfMonth(new Date(), -6), DateUtil.YYYY_MM_DD_HH_MM_SS);
		StringBuilder sqlBuffer = new StringBuilder("select apt.type_name parent_name FROM   " + 
				"				afin_product_type apt   " + 
				"				where apt.package_code in (   " + 
				"				select LEFT(package_code,1) from  " + 
				"				(select  DISTINCT(type_name),package_code   from afin_product_type  apt LEFT JOIN   " + 
				"								(select aap.pro_type_id,AFIN_time,t1.customer_number,t1.state from afin_product aap LEFT JOIN    " + 
				"							(select aos.pro_code,AFIN_time,ao.customer_number,ao.state from afin_order ao LEFT JOIN    " + 
				"								afin_order_sub aos on ao.product_order_id=aos.product_order_id )  t1     " + 
				"								on  t1.pro_code=aap.product_code)t2    " + 
				"								on t2.pro_type_id=apt.package_code where t2.customer_number=?  and t2.AFIN_time>?  )t3 )" );
		Object[] params = new Object[]{customerNumb,time};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		if(queryForList.size()==0) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param customerNumb
	 * @return
	 *//*
	public List<Map<String, Object>> getOrdeProductName3(String customerNumb){
		String time=DateUtil.toStr(DateUtil.getPerDayOfMonth(new Date(), -6), DateUtil.YYYY_MM_DD_HH_MM_SS);
		StringBuilder sqlBuffer = new StringBuilder("select apt.type_name parent_name FROM   " + 
				"				afin_product_type apt   " + 
				"				where apt.package_code in (   " + 
				"				select LEFT(package_code,1) from  " + 
				"				(select  DISTINCT(type_name),package_code   from afin_product_type  apt LEFT JOIN   " + 
				"								(select aap.pro_type_id,AFIN_time,t1.customer_number from afin_product aap LEFT JOIN    " + 
				"							(select aos.pro_code,AFIN_time,ao.customer_number from afin_order ao LEFT JOIN    " + 
				"								afin_order_sub aos on ao.product_order_id=aos.product_order_id )  t1     " + 
				"								on  t1.pro_code=aap.product_code)t2    " + 
				"								on t2.pro_type_id=apt.package_code where  t2.customer_number=?  )t3 )" );
		Object[] params = new Object[]{customerNumb};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	}
	*//*
	public List<Map<String, Object>> getOrdeProductName2(String customerNumb){
		String time=DateUtil.toStr(DateUtil.getPerDayOfMonth(new Date(), -6), DateUtil.YYYY_MM_DD_HH_MM_SS);
		StringBuilder sqlBuffer = new StringBuilder("select apt.type_name parent_name FROM   " + 
				"				afin_product_type apt   " + 
				"				where apt.package_code in (   " + 
				"				select LEFT(package_code,1) from  " + 
				"				(select  DISTINCT(type_name),package_code   from afin_product_type  apt LEFT JOIN   " + 
				"								(select aap.pro_type_id,AFIN_time,t1.customer_number,t1.state from afin_product aap LEFT JOIN    " + 
				"							(select aos.pro_code,AFIN_time,ao.customer_number,ao.state from afin_order ao LEFT JOIN    " + 
				"								afin_order_sub aos on ao.product_order_id=aos.product_order_id )  t1     " + 
				"								on  t1.pro_code=aap.product_code)t2    " + 
				"								on t2.pro_type_id=apt.package_code where t2.state=2 and t2.customer_number=? and ?>=AFIN_time  )t3 )" );
		Object[] params = new Object[]{customerNumb,time};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	}*/
	public Map<String, Object> getOrderUpdateTime(String customerNumb,String productName){
		StringBuilder sqlBuffer = new StringBuilder("select max(update_time) update_time from afin_order ao right JOIN " + 
				" afin_order_sub aos on ao.product_order_id=aos.product_order_id " + 
				"where customer_number=?  and product_name=?  GROUP BY aos.update_time " );
		Object[] params = new Object[]{customerNumb,productName};
		Map queryForObject = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),Map.class,params);
		return queryForObject;
	}
	public static void main(String[] args) {
		String time=DateUtil.toStr(DateUtil.getPerDayOfMonth(new Date(), -6), DateUtil.YYYY_MM_DD_HH_MM_SS);
		System.out.println(time);
	}

	/**
	 * 
	 * @param productName
	 * @param beginTime
	 * @param endTimae
	 * @return
	 */
	public List<Map<String, Object>> getOrder1(String beginTime,
			String endTimae,String productName){
		StringBuilder sqlBuffer = new StringBuilder("select count(DISTINCT(customer_number)) customer_number   from  afin_order_customer_count where record_time BETWEEN ? AND ?  and product_name=? ");
		Object[] params = new Object[]{beginTime,endTimae,productName};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	}
	public List<Map<String, Object>> getOrder2(String productName,String beginTime,
			String endTimae){
		StringBuilder sqlBuffer = new StringBuilder("select pro_name,count(DISTINCT(customer_number)) count from  afin_order_customer_count where product_name=? and record_time BETWEEN ? AND ? GROUP BY pro_name ORDER BY count(customer_number) desc ");
		Object[] params = new Object[]{productName,beginTime,endTimae};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
		return queryForList;
	}
	/**
	 * 订单管理-后台管理
	 * @param timeType
	 * @param countsType
	 * @param customerName
	 * @param productName
	 * @param productOrderId
	 * @param state
	 * @param firstTime
	 * @param secondTime
	 * @param productRatio
	 * @return
	 */
	public List<AdminOrder> exportOrderByTermList(String customerName,
			String productName, String productOrderId, String state, String firstTime, String secondTime,
			String productRatio) {
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from  afin_order where 1=1 ");				
		StringBuilder sqlCount = new StringBuilder(" select count(1) from afin_order  where 1=1 ");
				
		if (productName!=null && productName.trim().length()>0) {
			sql.append(" and parent_order_name like ?  ");			
			sqlCount.append(" and  parent_order_name like ?  ");		
			listSql.add("%" + productName + "%");
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and customer_name like ? ");			
			sqlCount.append(" and customer_name like ? ");	
			listSql.add("%" + customerName + "%");
		}
		if (productOrderId!=null && productOrderId.trim().length()>0) {
			sql.append(" and product_order_id like ?");			
			sqlCount.append(" and product_order_id like ? ");	
			listSql.add("%" + productOrderId + "%");
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and eff_time>=?");
			sqlCount.append(" and eff_time>=?");
			listSql.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and eff_time<=?");
			sqlCount.append(" and eff_time<=?'");
			listSql.add(secondTime);
		}		
		if (state!=null && state.trim().length()>0) {
			sql.append(" and state in(?) ");			
			sqlCount.append(" and state in(?) ");		
			listSql.add(state);
		}		
		if (productRatio!=null && productRatio.trim().length()>0) {
			sql.append(" and product_ratio=? ");			
			sqlCount.append(" and product_ratio=? ");		
			listSql.add(productRatio);
		}	
		sql.append(" order by ");
			sql.append( " eff_time  desc" );
		
		
			
		return this.getJdbcTemplate().query(sql.toString(), listSql.toArray(), new AdminOrderRowMapper());
	}
	/**
	 * 1.控制台-乾坤大数据-我的订单-导出  根据客户编码查询
	 * @param productName
	 * @param productOrderId
	 * @param secondTime
	 * @param state
	 * @param firstTime
	 * @param customerNumber
	 * @return
	 */
	public List<AdminOrder> excleOrderByTermList(String productName, String productOrderId, String secondTime,
			String state, String firstTime, String customerNumber) {	
		List<Object> listSql = new ArrayList<>();
		StringBuilder strWhere=new StringBuilder();		
		strWhere.append(" select * from  afin_order where ");
			strWhere.append(" product_name!=? and product_name!=?  and customer_number=?");		
			listSql.add("网址卫士");
			listSql.add("和位士");
			listSql.add(customerNumber);
			
		if (productName!=null && productName.trim().length()>0) {
			strWhere.append(" and product_name like ? ");		
			listSql.add("%"+productName+"%");
		}
		if (productOrderId!=null && productOrderId.trim().length()>0) {
			strWhere.append(" and product_order_id like ? ");	
			listSql.add("%"+productOrderId+"%");
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			strWhere.append(" and eff_time>=?");
			listSql.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			strWhere.append(" and eff_time<=?");
			listSql.add(secondTime);
		}		
		if (state!=null && state.trim().length()>0) {
			strWhere.append(" and state in(?) ");	
			listSql.add(state);
		}		
		strWhere.append(" order by eff_time desc ");
		Object[] params = new Object[]{};
		return  this.getJdbcTemplate().query(strWhere.toString(), listSql.toArray(), new AdminOrderRowMapper());
	}
	
	/**
	 * 添加Order数据
	 */
	public int insert(AdminOrder order){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(TableInfo.afin_order);
		sqlBuffer.append("(product_order_id,customer_number,customer_name,product_name,eff_time,exp_time,bill_eff_time,state,product_ratio) values(?,?,?,?,?,?,?,?,?)"); 	
		Object[] params = new Object[]{
				order.getProductOrderId(),
				order.getCustomerNumber(),
				order.getCustomerName(),
				order.getProductName(),
				order.getEffTime(),
				order.getExpTime(),
				order.getBillEffTime(),
				order.getState(),
				order.getProductRatio(),
		};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);		
	}
	
	/**
	 * 查询所有产品
	 * @return
	 */
	public List<AdminOrderSub> getOrderSubs(){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(" afin_order ");
		List<AdminOrderSub> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminOrderSubRowMapper());		
		return query;
	}
	/**
	 * 将状态初始为0
	 */
	public void getsate() {
		  String sql = "update afin_order set product_ratio=0 ";  
	      this.getJdbcTemplate().update(sql.toString());
	}
	
	/**
	 * 查所有数据
	 * @param dateStr  上个月时间，到月
	 * @param bengTime 每个月一号时间
	 * @return
	 */
	public List<OrderCustomerCount> selectCumCount(String dateStr,String bengTime) {
		StringBuilder sqlBuffer = new StringBuilder("select aoc.* from afin_order_customer_count aoc LEFT JOIN afin_order  ao on aoc.customer_number=ao.customer_number " + 
				"where aoc.record_time  like ? and ao.exp_time>?");
		Object[] params = new Object[]{""+dateStr+"%",bengTime};
		List<OrderCustomerCount> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new OrderCustomerCountRowMapper(),params);
		return query;
	}
	//每月增加
	public void insertCustomerCountNo(final List<OrderCustomerCount> list) {  		
        String sql = "insert into afin_order_customer_count (pro_name,customer_number,product_name,record_time) values(?,?,?,?)";  
        String endTimae = DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        this.getJdbcTemplate().batchUpdate(sql.toString(), new BatchPreparedStatementSetter() {	
            public int getBatchSize() {  
                return list.size();  
                //这个方法设定更新记录数，通常List里面存放的都是我们要更新的，所以返回list.size();  
            }  
            public void setValues(PreparedStatement ps, int i)throws SQLException { 	            	
                ps.setString(1, list.get(i).getProName());   
                ps.setString(2, list.get(i).getCustomerNumber());   
                ps.setString(3, list.get(i).getProductName());   
                ps.setTimestamp(4,  Timestamp.valueOf(endTimae));   
            }  
        });  
    }
	

}	


