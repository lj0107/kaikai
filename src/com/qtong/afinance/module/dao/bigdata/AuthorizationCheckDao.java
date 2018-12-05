package com.qtong.afinance.module.dao.bigdata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.module.pojo.bigdata.AfinTsInteractMess;
import com.qtong.afinance.module.pojo.bigdata.AfinTsInteractMessRowMapper;
import com.qtong.afinance.module.pojo.bigdata.AuthorizationCheck;
import com.qtong.afinance.module.pojo.bigdata.AuthorizationCheckRowMapper;
import com.qtong.afinance.module.pojo.bigdata.DetailRecord;
import com.qtong.afinance.module.pojo.bigdata.DetailRecordRowMapper;

/**
 * 乾坤大数据-授权核验dao层
 *
 */
@Repository
public class AuthorizationCheckDao extends BaseJdbcDao{

	/**
	 * 1.查询授权核验-授权核验列表
	 * @param pageData 分页参数
	 * @param orderNumber 订单编号
	 * @param customerName 客户名称
	 * @param startTime 查询下单时间的起始时间
	 * @param endTime 查询下单时间的结束时间
	 * @param minCounts 查询最小次数
	 * @param maxCounts 查询最大次数
	 * @param countsType 根据查询次数排序类型
	 * @param timeType 根据时间的排序类型
	 * @return
	 */
	public PageData queryAllAuthorizationCheck(PageData pageData,String productOrderId,String customerName,
			String startTime,String endTime,String minCounts,String maxCounts,
			String countsType,String timeType) {
			
			
		
		
		StringBuilder sql = new StringBuilder("select o.*,os.counts from afin_order ")				
					.append(" as o left join ")
			        .append("( select product_package_order_id,sum(count) as counts from afin_request_counts ")
			        .append(" group by product_package_order_id) os ")
			        .append(" on o.product_order_id = os.product_package_order_id ")
		            .append("  where o.product_name='数据标签'");
		
		
		
		StringBuilder sqlCount = new StringBuilder(" select count(1) from ");
		sqlCount.append(" afin_order ").append(" as o left join ")
		        .append("( select product_package_order_id,sum(count) as counts from afin_request_counts ")
		        .append(" group by product_package_order_id) os ")
		        .append(" on o.product_order_id = os.product_package_order_id ")
		        .append("  where o.product_name='数据标签'");


		List<Object> list = new ArrayList<>();
		
		if (productOrderId!=null && productOrderId.trim().length()>0) {//判断订单号是否为空
			sql.append(" and o.product_order_id like ?");
			sqlCount.append(" and o.product_order_id like ?");	
			list.add("%" + productOrderId + "%");
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and o.customer_name like ? ");			
			sqlCount.append(" and o.customer_name like ? ");
			list.add("%" + customerName + "%");
		}
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and o.eff_time >= ? ");			
			sqlCount.append(" and o.eff_time >= ? ");	
			list.add(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and o.eff_time <= ? ");			
			sqlCount.append(" and o.eff_time <= ? ");
			list.add(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		}
		
		if (minCounts!=null && minCounts.trim().length()>0) {
			sql.append(" and os.counts >=?");			
			sqlCount.append(" and os.counts >=?");
			list.add(minCounts);
		}
		
		if (maxCounts!=null && maxCounts.trim().length()>0) {
			sql.append(" and os.counts <=?");			
			sqlCount.append(" and os.counts <=?");
			list.add(maxCounts);
		}
		
		
		
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sql.append(" order by ");
		if (timeType!=null && timeType.trim().length()>0) {
			sql.append(" o.eff_time "+timeType);
			sql.append(",");
		}
		
		if (countsType!=null && countsType.trim().length()>0) {
			sql.append("os.counts "+countsType);
		}
		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), new AuthorizationCheckRowMapper(),list.toArray()));
		
		return pageData;
	}
	
	/**
	 * 2.乾坤大数据-授权核-授权码列表
	 * @param pageData
	 * @param productOrderId 产品订购关系id
	 * @param mobile 手机号
	 * @param authCode 授权码
	 * @param startTime 开始时间
	 * @param endTime 请求时间
	 * @return
	 */
	public PageData queryAllMobile(PageData pageData,String productOrderId,String mobile,String authCode
			,String startTime,String endTime) {

		
		StringBuilder sql = new StringBuilder("select * from ");				
		sql.append(TableInfo.afin_datamark_msg).append(" m ")
			.append(" where 1=1");
		
		
		
		StringBuilder sqlCount = new StringBuilder(" select count(1) from ");
		sqlCount.append(TableInfo.afin_datamark_msg).append(" m ")
		.append(" where 1=1");
		
		List<Object> list = new ArrayList<>();
		
		if (productOrderId!=null && productOrderId.trim().length()>0) {//判断订单号是否为空
			sql.append(" and m.product_package_ord_id =?");			
			sqlCount.append(" and m.product_package_ord_id =?");	
			list.add(productOrderId);
		}
		

		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and m.mobile like ? ");			
			sqlCount.append(" and m.mobile like ? ");
			list.add("%" + mobile + "%");
		}
		if (authCode!=null && authCode.trim().length()>0) {
			sql.append(" and m.auth_code like ? ");			
			sqlCount.append(" and m.auth_code like ? ");	
			list.add("%" + authCode + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and m.request_time >= ? ");			
			sqlCount.append(" and m.request_time >= ? ");	
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and m.request_time <= ? ");			
			sqlCount.append(" and m.request_time <= ? ");	
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		
		pageData.setRowCount(this.getPostgreJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		
		sql.append(" LIMIT ").append(pageData.getPageSize()).append(" OFFSET ").append(((pageData.getPageIndex()-1)*pageData.getPageSize()));
		
		pageData.setLst(this.getPostgreJdbcTemplate().query(sql.toString(),new AfinTsInteractMessRowMapper(), list.toArray()));
		
		return pageData;
		
	} 
	
	/**
	 * 根据产品订购关系id查看详情
	 * @param productOrderId
	 * @return
	 */
	public List<DetailRecord> queryOne(String requestRefId) {
		
		StringBuilder sql = new StringBuilder("select * from ");				
		sql.append(TableInfo.afin_datamark_msg).append(" m left join ")
			.append(TableInfo.afin_datamark_msg_detail).append(" bdr ")
			.append(" on m.request_refid = bdr.request_refid ")
			.append(" where m.request_refid = ?");
		Object[] params = new Object[]{requestRefId};
		return this.getPostgreJdbcTemplate().query(sql.toString(),new DetailRecordRowMapper(),params);
		 
	}
	/**
	 * 导出授权核验
	 * @param productOrderId
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @param minCounts
	 * @param maxCounts
	 * @return
	 */
	public List<AuthorizationCheck> exportExcel(String productOrderId,String customerName,
			String startTime,String endTime,String minCounts,String maxCounts) {
		
		List<Object> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select o.*,os.counts from afin_order ");				
		sql.append(" as o left join ")
	        .append("( select product_package_order_id,sum(count) as counts from afin_request_counts ")
	        .append(" group by product_package_order_id) os ")
	        .append(" on o.product_order_id = os.product_package_order_id ")
	        .append("  where o.product_name='数据标签'");


		
		
		
		if (productOrderId!=null && productOrderId.trim().length()>0) {//判断订单号是否为空
			sql.append(" and o.product_order_id like ?");
			list.add("%" + productOrderId + "%");
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and o.customer_name like ? ");	
			list.add("%" + customerName + "%");
		}
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and o.eff_time >= ? ");	
			list.add(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and o.eff_time <= ? ");	
			list.add(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
		}
		
		if (minCounts!=null && minCounts.trim().length()>0) {
			sql.append(" and os.counts >=?");	
			list.add(minCounts);
		}
		
		if (maxCounts!=null && maxCounts.trim().length()>0) {
			sql.append(" and os.counts <=?");			
			list.add(maxCounts);
		}
		
		return this.getJdbcTemplate().query(sql.toString(), new AuthorizationCheckRowMapper(),list.toArray());
	}

	

		
	/**
	 * 导出授权码列表
	 * @param productOrderId
	 * @param mobile
	 * @param authCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<AfinTsInteractMess> exportExcelAll(String productOrderId, String mobile, String authCode,
			String startTime, String endTime) {
		
		
		StringBuilder sql = new StringBuilder("select * from ");				
		sql.append(TableInfo.afin_datamark_msg).append(" m ")
			.append(" where 1=1");
		
		List<Object> list = new ArrayList<>();
		
		if (productOrderId!=null && productOrderId.trim().length()>0) {//判断订单号是否为空
			sql.append(" and m.product_package_ord_id = ?");	
			list.add(productOrderId);
		}
		

		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and m.mobile like ? ");			
			list.add("%" + mobile + "%");
		}
		if (authCode!=null && authCode.trim().length()>0) {
			sql.append(" and m.auth_code like ? ");			
			list.add("%" + authCode + "%");
		}
		
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and m.request_time >= ? ");			
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(startTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and m.request_time <= ?");			
			list.add(Timestamp.valueOf(DateUtil.toStr(DateUtil.toDate(endTime,"yyyy/MM/dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss")));
			
		}
		
		return this.getPostgreJdbcTemplate().query(sql.toString(), new AfinTsInteractMessRowMapper(),list.toArray());
	}
	
	
	
}
