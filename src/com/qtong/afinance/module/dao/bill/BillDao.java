package com.qtong.afinance.module.dao.bill;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.bigdata.DetailRecord;
import com.qtong.afinance.module.pojo.bigdata.DetailRecordRowMapper;
import com.qtong.afinance.module.pojo.bill.Bill;
import com.qtong.afinance.module.pojo.bill.BillRowMapper;
import com.qtong.afinance.module.pojo.heweishi.LbcmpDetail;
import com.qtong.afinance.module.pojo.heweishi.LbcmpDetailRowMapper;

/**
 * 控制台账单dao层
 *
 */
@Repository
public class BillDao extends BaseJdbcDao{

	
	/**
	 * 查询所有账单
	 * @param pageData
	 * @param billNumber
	 * @param customerName
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @param countsType 
	 * @param timeType 
	 * @return
	 */
	public PageData queryBills(PageData pageData,String customerNumber,String billNumber,String productOrderId,String customerName,String productName,
			String startTime,String endTime,String flag, String timeType, String countsType){
		boolean isAsc = true;
		if(timeType.equals("desc")) {
			isAsc = false;
		}
		if(countsType.equals("desc")) {
			isAsc = false;
		}
		SqlHelper sqlHelper = new SqlHelper("afin_bill")
				 .addCondition(customerNumber!=null && customerNumber.trim().length()>0, "customer_number = ?",customerNumber)
				 .addConditionLike(billNumber!=null && billNumber.trim().length()>0,"bill_number like ?", billNumber)
				 .addConditionLike(productOrderId!=null && productOrderId.trim().length()>0,"product_order_id like ?", productOrderId)
				 .addConditionLike(customerName!=null && customerName.trim().length()>0, "customer_name like ?",customerName)
				 .addCondition(productName!=null && productName.trim().length()>0, "product_name like ?",productName)
				 .addCondition(startTime!=null && startTime.trim().length()>0, "bill_time >= ?",startTime)
				 .addCondition(endTime!=null && endTime.trim().length()>0, "bill_time <= ?",endTime)
				 .addCondition(flag!=null && flag.trim().length()>0, "product_name = ?",flag)
				 .addOrder(timeType!=null && timeType.trim().length()>0, "bill_time", isAsc)
				 .addOrder(countsType!=null && countsType.trim().length()>0, "customer_name", isAsc);

		this.queryPage(pageData, sqlHelper, new BillRowMapper());
		
		return pageData;
	}
	
	/**
	 * 后台到导出所有账单
	 * @param pageData
	 * @param billNumber
	 * @param customerName
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Bill> exportBills(String customerNumber,String billNumber,String productOrderId,String customerName,String productName,
			String startTime,String endTime,String flag){
		
		
		SqlHelper sqlHelper = new SqlHelper("afin_bill");
		sqlHelper.addCondition(customerNumber!=null && customerNumber.trim().length()>0, "customer_number = ?",customerNumber)
				 .addConditionLike(billNumber!=null && billNumber.trim().length()>0,"bill_number like ?", billNumber)
				 .addConditionLike(productOrderId!=null && productOrderId.trim().length()>0,"product_order_id like ?", productOrderId)
				 .addConditionLike(customerName!=null && customerName.trim().length()>0, "customer_name like ?",customerName)
				 .addConditionLike(productName!=null && productName.trim().length()>0, "product_name like ?",productName)
				 .addCondition(startTime!=null && startTime.trim().length()>0, "bill_time >= ?",startTime)
				 .addCondition(endTime!=null && endTime.trim().length()>0, "bill_time <= ?",endTime)
				 .addCondition(flag!=null && flag.trim().length()>0, "product_name = ?",flag);
		
		
		return this.getJdbcTemplate().query(sqlHelper.getQueryListSql(), new BillRowMapper(),sqlHelper.getParameters());
	}
	

	
	
	/**
	 * 查询所有乾坤大数据账单
	 * @param pageData
	 * @param billNumber
	 * @param customerName
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PageData queryBDBills(PageData pageData,String customerNumber,String billNumber,String productOrderId,String productName,
			String startTime,String endTime){
		
		

		SqlHelper sqlHelper=new SqlHelper("afin_bill")
					.addCondition(customerNumber!=null && customerNumber.trim().length()>0, "customer_number =?", customerNumber)
					.addConditionLike(billNumber!=null && billNumber.trim().length()>0, "bill_number like ?", billNumber)
					.addConditionLike(productOrderId!=null && productOrderId.trim().length()>0, "product_order_id like ?", productOrderId)
					.addConditionLike(productName!=null && productName.trim().length()>0, "product_name like ?", productName)
					.addCondition(startTime!=null && startTime.trim().length()>0, "bill_time >=?", startTime)
					.addCondition(endTime!=null && endTime.trim().length()>0, "bill_time <= ?", endTime)
					.addCondition("product_name not in(?,?)", "和位士","网址卫士");

		
		
		PageData queryPage = this.queryPage(pageData, sqlHelper, new BillRowMapper());
		
		
		return queryPage;
	}
	/**
	 * 查询所有乾坤大数据账单 导出
	 * @param pageData
	 * @param billNumber
	 * @param customerName
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<Bill> exportBDBills(String customerNumber,String billNumber,String productOrderId,String productName,
			String startTime,String endTime){
		
		
		
		SqlHelper sqlHelper=new SqlHelper("afin_bill")
			.addCondition(customerNumber!=null && customerNumber.trim().length()>0, "customer_number =?", customerNumber)
			.addConditionLike(billNumber!=null && billNumber.trim().length()>0, "bill_number like ?", billNumber)
			.addConditionLike(productOrderId!=null && productOrderId.trim().length()>0, "product_order_id like ?", productOrderId)
			.addConditionLike(productName!=null && productName.trim().length()>0, "product_name like ?", productName)
			.addCondition(startTime!=null && startTime.trim().length()>0, "bill_time >=?", startTime)
			.addCondition(endTime!=null && endTime.trim().length()>0, "bill_time <= ?", endTime)
			.addCondition("product_name not in(?,?)", "和位士","网址卫士");
	
		
		return this.getJdbcTemplate().query(sqlHelper.getQueryListSql(), new BillRowMapper(),sqlHelper.getParameters());
	}
	
	

	/**
	 * 乾坤大数据-查看详单
	 * @param pageData
	 * @param proOrdId
	 * @param requestRefid
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public PageData queryOneBDBillDetail(PageData pageData,String proOrdId,String requestRefid,String productName,String startTime,String endTime){
		
		
		StringBuilder sqlBuffer = new StringBuilder("select bdr.*,m.request_time from ");
		sqlBuffer.append(TableInfo.afin_datamark_msg).append(" m right join ")
				 .append(TableInfo.afin_datamark_msg_detail).append(" bdr ")
				 .append(" on m.request_refid = bdr.request_refid ")
				 .append("where m.product_package_ord_id = ?");//账单号=产品包订购关系id
		List<Object> list = new ArrayList<Object>();
		list.add(proOrdId);
		
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");
		sqlCount.append(TableInfo.afin_datamark_msg).append(" m right join ")
				 .append(TableInfo.afin_datamark_msg_detail).append(" bdr ")
				 .append(" on m.request_refid = bdr.request_refid ")
				 .append("where m.product_package_ord_id = ?");

		if (requestRefid!=null && requestRefid.trim().length()>0) {
			sqlBuffer.append(" and bdr.request_refid like ? ");			
			sqlCount.append(" and bdr.request_refid like ? ");
			list.add("%" + requestRefid + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time >= ? ");			
			sqlCount.append(" and m.request_time >= ? ");
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time <= ? ");			
			sqlCount.append(" and m.request_time <= ? ");	
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		if (productName!=null && productName.trim().length()>0) {
			sqlBuffer.append(" and bdr.product_name like ? ");			
			sqlCount.append(" and bdr.product_name like ? ");	
			list.add("%" + productName + "%");
		}
		
		
		pageData.setRowCount(this.getPostgreJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sqlBuffer.append(" LIMIT ").append(pageData.getPageSize()).append(" OFFSET ").append(((pageData.getPageIndex()-1)*pageData.getPageSize()));
		
		pageData.setLst(this.getPostgreJdbcTemplate().query(sqlBuffer.toString(), new DetailRecordRowMapper(),list.toArray()));
		
		return pageData;
	}

	/**
	 * 乾坤大数据-查看详单无分页
	 * @param proOrdId
	 * @param requestRefid
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<DetailRecord> queryOneBDBill(String proOrdId,String requestRefid,String productName,String startTime,String endTime){
		
		StringBuilder sqlBuffer = new StringBuilder("select bdr.*,m.request_time from ");
		sqlBuffer.append(TableInfo.afin_datamark_msg).append(" m right join ")
				 .append(TableInfo.afin_datamark_msg_detail).append(" bdr ")
				 .append(" on m.request_refid = bdr.request_refid ")
				 .append("where m.product_package_ord_id = ?");//账单号=产品包订购关系id
		List<Object> list = new ArrayList<>();
		list.add(proOrdId);
		if (requestRefid!=null && requestRefid.trim().length()>0) {
			sqlBuffer.append(" and bdr.request_refid like ? ");			
			list.add("%" + requestRefid + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time >= ? ");			
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time <= ? ");			
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		if (productName!=null && productName.trim().length()>0) {
			sqlBuffer.append(" and bdr.product_name like ? ");			
			list.add("%" + productName + "%");
		}
		
		return this.getPostgreJdbcTemplate().query(sqlBuffer.toString(), new DetailRecordRowMapper(),list.toArray());
		
	}

	/**
	 * 乾坤大数据-查看详单-查看标签种类个数
	 * @param proOrdId
	 * @param requestRefid
	 * @param productName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int queryOneBDCount(String proOrdId,String requestRefid,String productName,String startTime,String endTime){
		StringBuilder sqlBuffer = new StringBuilder("select count(1) as counts from (select distinct(bdr.product_code) from ");
		sqlBuffer.append(TableInfo.afin_datamark_msg).append(" m right join ")
		.append(TableInfo.afin_datamark_msg_detail).append(" bdr ")
		.append(" on m.request_refid = bdr.request_refid ")
		.append("where m.product_package_ord_id = ?");//账单号=产品包订购关系id
		List<Object> list = new ArrayList<>();
		list.add(proOrdId);
		if (requestRefid!=null && requestRefid.trim().length()>0) {
			sqlBuffer.append(" and bdr.request_refid like ? ");			
			list.add("%" + requestRefid + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time >= ? ");			
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time <= ? ");			
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		if (productName!=null && productName.trim().length()>0) {
			sqlBuffer.append(" and bdr.product_name like ? ");			
			list.add("%" + productName + "%");
		}
		sqlBuffer.append(" ) t");
		
		Map<String, Object> queryForMap = this.getPostgreJdbcTemplate().queryForMap(sqlBuffer.toString(),list.toArray());
		
		return Integer.parseInt(queryForMap.get("counts").toString()); 
		
	}
	
	
	
	/**
	 * 和卫士-账单-查看详单
	 * @param pageData 当前页
	 * @param proOrdId 账单号=产品包订购关系id 
	 * @param requestRefid 流水号
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public PageData queryOneHWSBillDetail(PageData pageData,String proOrdId,String requestRefid,String keyWords,String startTime,String endTime){
		
	
		StringBuilder sqlBuffer = new StringBuilder("select ld.*,m.request_time");
		sqlBuffer.append(" from afin_lbcmp m ")
				.append(" left join afin_lbcmp_detail ld on m.chanl_cust_no = ld.request_refid ")
				 .append(" where m.pro_ord_id = ?");//账单号=产品包订购关系id
		List<Object> list = new ArrayList<>();
		list.add(proOrdId);
		
		StringBuilder sqlCount = new StringBuilder("select count(*) ");
		sqlCount.append(" from afin_lbcmp m ")
				.append(" left join afin_lbcmp_detail ld on m.chanl_cust_no = ld.request_refid ")
				 .append(" where m.pro_ord_id = ?");//账单号=产品包订购关系id

		
		
		if (requestRefid!=null && requestRefid.trim().length()>0) {
			sqlBuffer.append(" and m.chanl_cust_no like ? ");			
			sqlCount.append(" and m.chanl_cust_no like ? ");	
			list.add("%" + requestRefid + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time >= ? ");			
			sqlCount.append(" and m.request_time >= ? ");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time <= ? ");			
			sqlCount.append(" and m.request_time <= ? ");	
			list.add(endTime);
		}
		if (keyWords!=null && keyWords.trim().length()>0) {
			sqlBuffer.append(" and ld.product_name like ? ");			
			sqlCount.append(" and ld.product_name like ? ");	
			list.add("%" + keyWords + "%");
		}
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sqlBuffer.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sqlBuffer.toString(), new LbcmpDetailRowMapper(),list.toArray()));
		
		return pageData;
	}
	
	

	/**
	 * 和卫士-账单-查看详单-无分页
	 * @param proOrdId 产品包订购关系id 
	 * @param requestRefid 流水号
	 * @param keyWords
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public List<LbcmpDetail> queryOneHWSBill(String proOrdId,String requestRefid,String keyWords,String startTime,String endTime){
		
		
		
		StringBuilder sqlBuffer = new StringBuilder("select ld.*,m.request_time");
		sqlBuffer.append(" from afin_lbcmp m ")
				.append(" left join afin_lbcmp_detail ld on m.chanl_cust_no = ld.request_refid ")
				.append(" where m.pro_ord_id = ?");//账单号=产品包订购关系id
		
		List<Object> list = new ArrayList<>();
		list.add(proOrdId);
		
		if (requestRefid!=null && requestRefid.trim().length()>0) {
			sqlBuffer.append(" and m.chanl_cust_no like ? ");
			list.add("%" + requestRefid + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time >= ? ");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time <= ? ");
			list.add(endTime);
		}
		
		if (keyWords!=null && keyWords.trim().length()>0) {
			sqlBuffer.append(" and ld.product_name like ? ");	
			list.add("%" + keyWords + "%");
		}
		
		
		return this.getJdbcTemplate().query(sqlBuffer.toString(), new LbcmpDetailRowMapper(),list.toArray());
	}
	
	/**
	 * 和卫士-账单-查看详单-查看标签个数
	 * @param proOrdId 产品包订购关系id 
	 * @param requestRefid 流水号
	 * * @param keyWords
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public int queryOneHWSCount(String proOrdId,String requestRefid,String keyWords,String startTime,String endTime){
		
		
		
		StringBuilder sqlBuffer = new StringBuilder("select count(1) counts from( select distinct(product_code) ");
		sqlBuffer.append(" from afin_lbcmp  m ")
				.append(" left join afin_lbcmp_detail ld on m.chanl_cust_no = ld.request_refid ")
				.append(" where m.pro_ord_id = ?");//账单号=产品包订购关系id
		
		List<Object> list = new ArrayList<>();
		list.add(proOrdId);
		
		if (requestRefid!=null && requestRefid.trim().length()>0) {
			sqlBuffer.append(" and m.chanl_cust_no like ? ");
			list.add("%" + requestRefid + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time >= ? ");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sqlBuffer.append(" and m.request_time <= ? ");
			list.add(endTime);
		}
		
		if (keyWords!=null && keyWords.trim().length()>0) {
			sqlBuffer.append(" and ld.product_name like ? ");	
			list.add("%" + keyWords + "%");
		}
		sqlBuffer.append(" ) t");
		Map<String, Object> queryForMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),list.toArray());
		
		return Integer.parseInt(queryForMap.get("counts").toString()); 
				
	}
	
	
	
	/**
	 * 统计每个月的账单信息
	 * @param bills
	 */
	public void insertBills(List<Bill> bills){
		{
			final List<Bill> tempBpplist = bills;
			String sql ="insert into afin_bill(bill_number,bill_time,product_order_id,order_time,customer_number,customer_name,product_name,fee) values(?,?,?,?,?,?,?,?)";
			this.getJdbcTemplate().batchUpdate(sql,new BatchPreparedStatementSetter() {   
				  
	            @Override  
	            public int getBatchSize() {   
	                 return tempBpplist.size();    
	            }   
	            @Override  
	            public void setValues(PreparedStatement ps, int i)   
	                    throws SQLException { 
	            	ps.setString(1,tempBpplist.get(i).getBillNumber());
	               ps.setTimestamp(2, tempBpplist.get(i).getBillTime());
	               ps.setString(3, tempBpplist.get(i).getProductOrderId());
	               ps.setTimestamp(4, tempBpplist.get(i).getOrderTime());
	               ps.setString(5, tempBpplist.get(i).getCustomerNumber());
	               ps.setString(6, tempBpplist.get(i).getCustomerName());
	               ps.setString(7, tempBpplist.get(i).getProductName());
	               ps.setDouble(8, tempBpplist.get(i).getFee());
	            }
	      });   
		}
	}
	
	
}
