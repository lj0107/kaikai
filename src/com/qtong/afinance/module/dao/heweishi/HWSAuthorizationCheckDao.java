package com.qtong.afinance.module.dao.heweishi;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.bigdata.AuthorizationCheck;
import com.qtong.afinance.module.pojo.bigdata.AuthorizationCheckRowMapper;
import com.qtong.afinance.module.pojo.heweishi.LbCityAuthorization;
import com.qtong.afinance.module.pojo.heweishi.LbCityAuthorizationRowMapper;
import com.qtong.afinance.module.pojo.heweishi.LbCityComparisonRowMapper;
import com.qtong.afinance.module.pojo.heweishi.LbCityOrder;
import com.qtong.afinance.module.pojo.heweishi.LbCityOrderRowMapper;

/**
 * 和卫士-授权核验dao层
 *
 */
@Repository
public class HWSAuthorizationCheckDao extends BaseJdbcDao{

	/**
	 * 查询授权核验-授权核验列表
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
			
			
		List<Object> list = new ArrayList<>();
		
		StringBuilder sql = new StringBuilder(" SELECT * FROM ");				
		sql.append(" afin_order ").append(" AS o left JOIN ")
			.append("( SELECT pro_ord_id,count(1) AS counts FROM ")
			.append(TableInfo.afin_lbcmp)
			.append(" GROUP BY pro_ord_id) AS os ")
			.append(" ON o.product_order_id = os.pro_ord_id WHERE o.product_name='和位士'");
		
		StringBuilder sqlCount = new StringBuilder(" select count(1) from ");
		sqlCount.append(" afin_order ").append(" as o left join ")
		.append("( select pro_ord_id,count(1) as counts from ")
		.append(TableInfo.afin_lbcmp)
		.append(" group by pro_ord_id) os ")
		.append(" on o.product_order_id = os.pro_ord_id where o.product_name='和位士'");
		
		if (productOrderId!=null && productOrderId.trim().length()>0) {//判断订单号是否为空
			sql.append(" and o.product_order_id =?");			
			sqlCount.append(" and o.product_order_id =?");	
			list.add(productOrderId);
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and o.customer_name like ? ");			
			sqlCount.append(" and o.customer_name like ? ");
			list.add("%"+customerName+"%");
		}
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and o.eff_time >= ? ");			
			sqlCount.append(" and o.eff_time >= ? ");
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and o.eff_time <= ? ");		
			sqlCount.append(" and o.eff_time <= ? ");
			list.add(endTime);
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
			sql.append(" o.eff_time  "+timeType);
			sql.append(",");
		}
		
		if (countsType!=null && countsType.trim().length()>0) {
			sql.append("os.counts "+countsType);
		}
		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), list.toArray(), new AuthorizationCheckRowMapper()));
		
		return pageData;
	}
	
	/**
	 * 和卫士-授权核验列表
	 * @param pageData
	 * @param productOrderId 产品包订购关系id
	 * @param mobile 手机号
	 * @param startTime 开始时间
	 * @return

	 *select m.mobile,m.request_time,bdr.*
	 *from afin_ts_interact_msg m
	 *left join afin_boss_detail_record bdr
	 *on m.request_refid=bdr.request_refid
	 *where m.pro_ord_id=1 and bdr.usermark='和卫士的标签英文名'
	 */
	public PageData queryAllMobile(PageData pageData,String proId,String mobile
			,String startTime,String endTime) {

		Object[] params = new Object[]{};
		List<Object> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from ");				
		sql.append(TableInfo.afin_lbcmp).append(" as m left join ")
			.append(TableInfo.afin_lbcmp_detail).append(" as bdr on m.chanl_cust_no=bdr.request_refid where m.pro_ord_id = ?");
			
		
		
		StringBuilder sqlCount = new StringBuilder("select count(1) from ");
		sqlCount.append(TableInfo.afin_lbcmp).append(" as m left join ")
		.append(TableInfo.afin_lbcmp_detail).append(" as bdr on m.chanl_cust_no=bdr.request_refid where m.pro_ord_id = ?");
		
		list.add(proId);
		
		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and m.mobile like ? ");			
			sqlCount.append(" and m.mobile like ? ");	
			list.add("%"+mobile+"%");
		}
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and m.request_time >= ? ");			
			sqlCount.append(" and m.request_time >= ? ");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and m.request_time <= ? ");			
			sqlCount.append(" and m.request_time <= ? ");
			list.add(endTime);
		}
		
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), list.toArray(), new LbCityComparisonRowMapper()));
		
		return pageData;
		
	}

	/**
	 * 查看详情
	 * @param id
	 * @return
	 */
	public List<LbCityAuthorization> getAuthorizationCheck(String id) {
		StringBuilder sql = new StringBuilder(" select *,cm.citycode from ");				
		sql.append(TableInfo.afin_lbcmp).append(" as m left join ")
			.append(" afin_lbcmp_city_msg cm on m.chanl_cust_no = cm.citycompar_id")
			.append(" left join ")
			.append(TableInfo.afin_lbcmp_detail).append(" as d ")
			.append(" on  m.chanl_cust_no=d.request_refid")
			.append(" where d.request_refid =?");
		Object[] params = new Object[]{id};
		return this.getJdbcTemplate().query(sql.toString(),new LbCityAuthorizationRowMapper(),params);
	} 
	
	/**
	 * 导出和卫士-授权核验
	 * @param productOrderId
	 * @param customerName
	 * @param startTime
	 * @param endTime
	 * @param minCounts
	 * @param maxCounts
	 * @return
	 */
	public List<AuthorizationCheck> exportExcel(String productOrderId, String customerName,
			String startTime, String endTime, String minCounts, String maxCounts) {
		Object[] params = new Object[]{};
		
		StringBuilder sql = new StringBuilder(" SELECT * FROM ");				
		sql.append(" afin_order ").append(" AS o left JOIN ")
			.append("( SELECT pro_ord_id,count(1) AS counts FROM ")
			.append(TableInfo.afin_lbcmp)
			.append(" GROUP BY pro_ord_id) AS os ")
			.append(" ON o.product_order_id = os.pro_ord_id WHERE o.product_name='和位士'");

		List<Object> list = new ArrayList<>();
		if (productOrderId!=null && productOrderId.trim().length()>0) {//判断订单号是否为空
			sql.append(" and o.product_order_id =?");
			list.add(productOrderId);
			
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sql.append(" and o.customer_name like ? ");	
			list.add("%"+customerName+"%");
		}
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and o.eff_time >=?");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and o.eff_time <= ? ");	
			list.add(endTime);
		}
		
		if (minCounts!=null && minCounts.trim().length()>0) {
			sql.append(" and os.counts >=?");	
			list.add(minCounts);
		}
		
		if (maxCounts!=null && maxCounts.trim().length()>0) {
			sql.append(" and os.counts <=?");	
			list.add(maxCounts);
		}
		
		return this.getJdbcTemplate().query(sql.toString(), list.toArray(), new AuthorizationCheckRowMapper());
	}


	/**
	 * 导出和卫士-授权核验列表
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<LbCityOrder> exportAllMobile(String proId,String mobile, String startTime, String endTime) {
		List<Object> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select * from ");				
		sql.append(TableInfo.afin_lbcmp).append(" as m inner join ")
			.append(TableInfo.afin_order).append(" as bdr on m.pro_ord_id=bdr.product_order_id where m.pro_ord_id = ?");
			list.add(proId);
			
		if (mobile!=null && mobile.trim().length()>0) {
			sql.append(" and m.mobile = ? ");
			list.add(mobile);
		}
		if (startTime!=null && startTime.trim().length()>0) {
			sql.append(" and m.request_time >= ? ");	
			list.add(startTime);
		}
		
		if (endTime!=null && endTime.trim().length()>0) {
			sql.append(" and m.request_time <= ? ");
			list.add(endTime);
		}
		return this.getJdbcTemplate().query(sql.toString(), list.toArray(), new LbCityOrderRowMapper());
	} 
}
