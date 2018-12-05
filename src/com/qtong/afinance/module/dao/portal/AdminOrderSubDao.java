package com.qtong.afinance.module.dao.portal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.pojo.product.AdminOrderSub;
import com.qtong.afinance.module.pojo.product.AdminOrderSubRowMapper;
import com.qtong.afinance.module.pojo.product.AdminProRatio;
import com.qtong.afinance.module.pojo.product.AdminProRatioRowMapper;

/**
 * 订单产品Dao
 *
 */
@Repository
public class AdminOrderSubDao extends BaseJdbcDao{
	
	public List<AdminOrderSub> getProductByProductOrderId(String productOrderId,String proName) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(" afin_order_sub ");
		sqlBuffer.append(" where product_order_id=? and pro_name like ?");
		Object[] params=new Object[]{productOrderId,"%"+proName+"%"};
		return this.getJdbcTemplate().query(sqlBuffer.toString(),new AdminOrderSubRowMapper(),params);
	}
	public List<AdminOrderSub> getProductByProductOrderId(String productOrderId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(" afin_order_sub ");
		sqlBuffer.append(" where product_order_id=?");
		Object[] params=new Object[]{productOrderId};
		return this.getJdbcTemplate().query(sqlBuffer.toString(),new AdminOrderSubRowMapper(),params);
	}
	
	
	/**  
	 * 2.控制台-乾坤大数据-我的订单  根据订单编码查看详情页
	 * @param pageIndex   当前页
	 * @param product_order_id 产品包订购关系ID（订单号）
	 * @param parentName  上级名称：乾坤大数据
	 */
	public PageData selectOrderSubByTermList(String pageIndex, String product_order_id,String product_name) {			
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select aos.* from  ");				
		sql.append(" afin_order ").append(" ao  LEFT JOIN  afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.product_order_id=? and  ao.product_name=?");
		
		StringBuilder sqlCount = new StringBuilder(" select count(*) from ");
		sqlCount.append(" afin_order ").append(" ao  LEFT JOIN ")
		   .append(" afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.product_order_id=? and  ao.product_name=?");		
		listSql.add(product_order_id);
		listSql.add(product_name);
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,listSql.toArray()).intValue());		
		sql.append(" order by aos.eff_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(),listSql.toArray(), new AdminOrderSubRowMapper()));			
		return pageData;
	}
	/**
	 * 3.控制台-网址卫士与和卫士-我的订单  根据客户编码查询
     * @param pageIndex  当前页
	 * @param customer_number 客户编码
	 * @param proName  产品名称
	 * @param productOrderNumber  产品订单号
	 * @param state  产品状态
	 * 下单（订购）生效时间 范围
	 * @param firstTime  1.第一时间
	 * @param secondTime 2.第二时间
	 * @param productName  产品包名称：网址卫士或者和卫士
	 * @return
	 */
	
	public PageData selectOrderByCusList(String pageIndex,String customer_number,String proName, String subOrderNum, String state,String firstTime,String secondTime,String productName) {			
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select aos.*,ao.contract_pdf_url,ao.product_name,ao.contract_name from  ");				
		sql.append(" afin_order ").append(" ao  LEFT JOIN afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.customer_number=? and  ao.product_name=?");
		
		StringBuilder sqlCount = new StringBuilder(" select count(*) from ");
		sqlCount.append(" afin_order ").append(" ao  LEFT JOIN afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.customer_number=? and  ao.product_name=?");	
		listSql.add(customer_number);
		listSql.add(productName);
		if (proName!=null && proName.trim().length()>0) {
			sql.append(" and aos.pro_name like ? ");			
			sqlCount.append(" and aos.pro_name like ? ");
			listSql.add("%"+proName+"%");
		}
		if (subOrderNum!=null && subOrderNum.trim().length()>0) {
			sql.append(" and aos.product_order_id like ? ");			
			sqlCount.append(" and aos.product_order_id like ? ");
			listSql.add("%"+subOrderNum+"%");
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and aos.eff_time>=?");
			sqlCount.append(" and aos.eff_time>=?");
			listSql.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and aos.eff_time<=?");
			sqlCount.append(" and aos.eff_time<=?");
			listSql.add(secondTime);
		}		
		if (state!=null && state.trim().length()>0) {
			sql.append(" and aos.state in(?) ");			
			sqlCount.append(" and aos.state in(?) ");
			listSql.add(state);
		}		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,listSql.toArray()).intValue());		
		sql.append(" order by aos.eff_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), listSql.toArray(), new AdminOrderSubRowMapper()));	
		return pageData;
	}
	/**
	 * 3.后台管理-订单管理-订单详情
	 * @param pageIndex  当前页
	 * @param product_order_id  通过产品包订购关系ID进行查询
	 * @return
	 * 
     */
	public PageData selectOrderSubList(String pageIndex, String product_order_id) {			
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select ao.product_name,ao.contract_name,ao.contract_word_url,t2.* from  ");						
		sql.append(" afin_order ").append(" ao LEFT JOIN ")
		   .append(" (select t1.name,t1.pro_type_id,aos.* from ")
		   .append(" afin_order_sub ").append(" aos LEFT JOIN ")
		   .append(" (select aap.pro_type_id,aap.product_code,aps.name from ")
		   .append(" afin_product ").append(" aap LEFT JOIN ")
		   .append(" afin_partner ")
		   .append(" aps on aap.partner_id=aps.id) t1 on aos.pro_code=t1.product_code) t2 ")
		   .append(" on ao.product_order_id=t2.product_order_id where ao.product_order_id=? ");

		
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");				
		sqlCount.append(" afin_order ").append(" ao LEFT JOIN ")
		   .append(" (select t1.name,t1.pro_type_id,aos.* from ")
		   .append(" afin_order_sub ").append(" aos LEFT JOIN ")
		   .append(" (select aap.pro_type_id,aap.product_code,aps.name from ")
		   .append(" afin_product ").append(" aap LEFT JOIN ")
		   .append(" afin_partner ")
		   .append(" aps on aap.partner_id=aps.id) t1 on aos.pro_code=t1.product_code) t2 ")
		   .append(" on ao.product_order_id=t2.product_order_id where ao.product_order_id=? ");
		listSql.add(product_order_id);	
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,listSql.toArray()).intValue());		
		sql.append(" order by t2.eff_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), listSql.toArray(), new AdminOrderSubRowMapper()));			
		return pageData;
	}
	/**
	 * 4.后台管理-订单管理-订单详情-设置分成比例
	 * @param adminProRatio
	 */
	
	public int updRatio(AdminProRatio ap) {
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(" afin_ratio_confirm ");
		sqlBuffer.append(" (sub_order_num,oper_state,ratio_before,ratio_after,apply_user,apply_time,state,opt_matter,update_time) VALUES(?,?,?,?,?,?,?,?,?) ");
		sqlBuffer.append(" ON DUPLICATE KEY UPDATE sub_order_num=?,oper_state=?, ");
		sqlBuffer.append(" ratio_before=?,ratio_after=?,apply_user=?, ");
		sqlBuffer.append(" apply_time=?,state=?,opt_matter=?,update_time=? ");
		Object[] params = new Object[]{ap.getSubOrderNum(),ap.getOperState(),ap.getRatioBefore(),ap.getRatioAfter(),ap.getApplyUser(),ap.getApplyTime(),ap.getState(),ap.getOptMatter(),ap.getApplyTime(),
				ap.getSubOrderNum(),ap.getOperState(),ap.getRatioBefore(),ap.getRatioAfter(),ap.getApplyUser(),ap.getApplyTime(),ap.getState(),ap.getOptMatter(),ap.getApplyTime()};
		
		StringBuffer sql=new StringBuffer("update ");
		sql.append(" afin_order_sub ");
		sql.append(" set reserve_ratio=? where sub_order_num=?");
		Object[] params1 = new Object[]{ap.getRatioAfter(),ap.getSubOrderNum()};
		this.getJdbcTemplate().update(sql.toString(),params1);	
		return this.getJdbcTemplate().update(sqlBuffer.toString(),params);		
	}
	/**
	 * 5.后台管理-订单管理-订单详情-分成比例记录
	 * @param adminProRatio
	 * select apr.* from afin_admin_pro_ratio apr LEFT JOIN afin_admin_order_sub 
	 * aos on  apr.sub_order_num=aos.sub_order_num where aos.sub_order_num='asd123'
	 */	
	public PageData selectRatioList(String pageIndex, String subOrderNum) {			
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select apr.* from  ");						
		sql.append("   afin_ratio_info ").append(" apr LEFT JOIN ")
		   .append(" afin_order_sub")
		   .append(" aos on apr.sub_order_num=aos.sub_order_num where aos.sub_order_num=? ");
		
		StringBuilder sqlCount = new StringBuilder("select count(*) from ");				
		sqlCount.append(" afin_ratio_info  ").append(" apr LEFT JOIN ")
		   .append(" afin_order_sub ")
		   .append(" aos on apr.sub_order_num=aos.sub_order_num where aos.sub_order_num=? ");
		listSql.add(subOrderNum);
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,listSql.toArray()).intValue());		
		sql.append(" order by apr.confirm_time desc ").append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), listSql.toArray(), new AdminProRatioRowMapper()));			
		return pageData;
	}
	
	/**
	 * 添加订单详情（测试）
	 * @param s
	 * @return
	 */
	public int insert(AdminOrderSub s){
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append(" afin_order_sub ");
		
		sqlBuffer.append(" (sub_order_num,product_order_id,product_order_number,pro_code,pro_name,eff_time,state,update_time,price,discount,sum,ratio,reserve_ratio) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
		Object[] params=new Object[]{
				s.getSubOrderNum(),s.getProductOrderId(),s.getProductOrderNumber(),s.getProCode(),s.getProName(),s.getEffTime(),s.getState(),s.getUpdateTime(),s.getPrice(),s.getDiscount(),s.getSum(),s.getRatio(),s.getReserveRatio()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	
	/**
	 * 查询所有产品
	 * @return
	 */
	public List<AdminOrderSub> getOrderSubs(){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(" afin_order_sub ");
		List<AdminOrderSub> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminOrderSubRowMapper());		
		return query;
	}
	/**
	 * 
	 * 导出-订单管理-查看详情
	 * @param reqMess
	 * @return
	 */
	public List<AdminOrderSub> ExportOrderSubByList(String product_order_id) {
		StringBuilder sql = new StringBuilder("select ao.product_name,ao.contract_name,ao.contract_word_url,t2.* from  ");						
		sql.append(" afin_order ").append(" ao LEFT JOIN ")
		   .append(" (select t1.name,t1.pro_type_id,aos.* from ")
		   .append(" afin_order_sub ").append(" aos LEFT JOIN ")
		   .append(" (select aap.pro_type_id,aap.product_code,aps.name from ")
		   .append(" afin_product ").append(" aap LEFT JOIN ")
		   .append(" afin_partner ")
		   .append(" aps on aap.partner_id=aps.id) t1 on aos.pro_code=t1.product_code) t2 ")
		   .append(" on ao.product_order_id=t2.product_order_id where ao.product_order_id=? order by t2.eff_time desc");
		Object[] params = new Object[]{product_order_id};
		return this.getJdbcTemplate().query(sql.toString(), params, new AdminOrderSubRowMapper());
	}
	/**
	 *  2.控制台-乾坤大数据-我的订单  根据订单编码查看详情页-导出
	 * @param productName
	 * @param productOrderId
	 * @return
	 */
	public List<AdminOrderSub> excleOrderSubByTermList(String productName, String productOrderId) {
       
		StringBuilder sql = new StringBuilder("select aos.* from  ");				
		sql.append(" afin_order ").append(" ao  LEFT JOIN  afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.product_order_id=? and  ao.product_name=?")
		   .append("  order by aos.eff_time desc ");
		 Object[] params = new Object[]{productOrderId,productName};
		return this.getJdbcTemplate().query(sql.toString(), params, new AdminOrderSubRowMapper());
	}
	/**
	 *  3.控制台-网址卫士与和位士-我的订单  根据客户编码查询-导出
	 * @param customerNumber
	 * @param proName
	 * @param subOrderNum
	 * @param state
	 * @param firstTime
	 * @param secondTime
	 * @param productName
	 * @return
	 */
	public List<AdminOrderSub> excleOrderByCusList(String customerNumber, String proName, String subOrderNum,
			String state, String firstTime, String secondTime, String productName) {
		
		Object[] params = new Object[]{};
		List<Object> listSql = new ArrayList<>();
		StringBuilder sql = new StringBuilder("select aos.*,ao.contract_pdf_url,ao.product_name,ao.contract_name from  ");				
		sql.append(" afin_order ").append(" ao  LEFT JOIN afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.customer_number=? and  ao.product_name=?");
		
		StringBuilder sqlCount = new StringBuilder(" select count(*) from ");
		sqlCount.append(" afin_order ").append(" ao  LEFT JOIN afin_order_sub ")
		   .append(" aos on ao.product_order_id=aos.product_order_id ")
		   .append("  where ao.customer_number=? and  ao.product_name=?");	
		listSql.add(customerNumber);
		listSql.add(productName);
		if (proName!=null && proName.trim().length()>0) {
			sql.append(" and aos.pro_name like ? ");			
			sqlCount.append(" and aos.pro_name like ? ");
			listSql.add("%"+proName+"%");
		}
		if (subOrderNum!=null && subOrderNum.trim().length()>0) {
			sql.append(" and aos.product_order_id like ? ");			
			sqlCount.append(" and aos.product_order_id like ? ");
			listSql.add("%"+subOrderNum+"%");
		}
		if(!"".equals(firstTime)&&firstTime!=null) {
			sql.append(" and aos.eff_time>=?");
			sqlCount.append(" and aos.eff_time>=?");
			listSql.add(firstTime);
		}
		if(!"".equals(secondTime)&&secondTime!=null) {
			sql.append(" and aos.eff_time<=?");
			sqlCount.append(" and aos.eff_time<=?");
			listSql.add(secondTime);
		}		
		if (state!=null && state.trim().length()>0) {
			sql.append(" and aos.state in(?) ");			
			sqlCount.append(" and aos.state in(?) ");
			listSql.add(state);
		}		
		sql.append(" order by aos.eff_time desc ");
		return this.getJdbcTemplate().query(sql.toString(), listSql.toArray(), new AdminOrderSubRowMapper());
	}
	/**
	 * 更改订单中的产品中文名
	 * @param proName产品中文名
	 * @param proCode产品编码
	 */
	public void updateProName(String proName,String proCode) {
		StringBuffer sql=new StringBuffer("update ");
		sql.append(" afin_order_sub ");
		sql.append(" set pro_name=? where pro_code=?");
		Object[] params1 = new Object[]{proName,proCode};
		this.getJdbcTemplate().update(sql.toString(),params1);	
	}
	
}
