package com.qtong.afinance.module.dao.statistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.module.pojo.partners.AfinPartner;
import com.qtong.afinance.module.pojo.partners.AfinPartnerRowMapper;
import com.qtong.afinance.module.pojo.product.AdminProType;
import com.qtong.afinance.module.pojo.product.AdminProTypeRowMapper;
import com.qtong.afinance.module.pojo.product.AdminProduct;
import com.qtong.afinance.module.pojo.product.AdminProductRowMapper;
import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.util.SqlHelper;


/**
 * 后台统计-合作伙伴分成比例统计
 *
 */
@Repository
public class RatioStatsDao extends BaseJdbcDao{
	
	final Logger logger = LoggerFactory.getLogger(RatioStatsDao.class);

	
	/**
	 * 通过合作伙伴name查出所有的产品编码 
	 * @param partnerId
	 * @return
	 */
	public List<AdminProduct> queyByPartnerName(String partnerName){
	
		String sql = "select pro.* from afin_product pro where  pro.partner_id in (select ner.id from afin_partner ner where ner.name=?)";
		
		Object[] params= new Object[]{partnerName};
		
		List<AdminProduct> adminProducts= this.getJdbcTemplate().query(sql, new AdminProductRowMapper(),params);	 
		
		return adminProducts;
	}
	
	/**
	 * 通过合作伙伴id查出所有的产品编码 
	 * @param partnerName
	 * @return
	 */
	public List<AdminProduct> queyByPartnerId(String id){
		
		String sql = "select pro.* from afin_product pro where  pro.partner_id = ?";
		
		Object[] params= new Object[]{id};
		
		List<AdminProduct> adminProducts= this.getJdbcTemplate().query(sql, new AdminProductRowMapper(),params);	 
		
		return adminProducts;
	}

	
	/**
	 * 通过合作伙伴id查出所有的产品编码 (限制产品类别)
	 * @param partnerId
	 * @param pro_type_id
	 * @return
	 */
	public List<AdminProduct> queyByPartnerId(String partnerId,String pro_type_id){		
		String sqlBuffer = "select pro.* from afin_product pro where pro.partner_id=? and pro.pro_type_id like ?";
		Object[] params = new Object[]{partnerId,pro_type_id+"%"};	

		List<AdminProduct> adminProducts= this.getJdbcTemplate().query(sqlBuffer, new AdminProductRowMapper(),params);	 
		return adminProducts;
	}
	
	//通过产品编码 查出对应的分成比例
	/*select os.pro_code,os.ratio,ra.confirm_time
	from afin_admin_order_sub os
	left join afin_admin_pro_ratio ra
	on os.sub_order_num=ra.sub_order_num
	where os.pro_code = 'A12340'*/

	

	/**
	 * 查1级产品类别
	 * @return
	 */
	public List<AdminProType> selProductTypeLev1(){
		String sql="select * from afin_product_type where lev='1'";
		return this.getJdbcTemplate().query(sql, new AdminProTypeRowMapper());	
	}
	
	/**
	 * 根据1级类别 查所有产品编码
	 * @param parentId
	 * @return
	 */
	public List<String> getProCodeByType(String pro_type_id){
		String sql="select package_code from afin_product_type where pro_type_id like ?";
		
		Object[] params = new Object[]{pro_type_id+"%"};	
		return this.getJdbcTemplate().queryForList(sql, String.class);	
	}
	
	
	/**
	 * 根据产品编码 查出对应的分成比例 和 客户编码
	 * @param productCode
	 * @param customerNumber
	 * @return
	 */
	public List<Map<String, Object>> queryByProductCode(String productCode,String customerNumber){
		
		
		
		SqlHelper sqlHelper=new SqlHelper("afin_order_sub os left join afin_order o on os.product_order_id = o.product_order_id")
//								.addResult("o.eff_time,o.exp_time,os.price,os.discount,os.pro_code,os.ratio,o.customer_number")
								.addResult("o.eff_time,o.exp_time,os.price,os.discount,os.sum,os.pro_code,os.ratio,o.customer_number")
								.addCondition("os.pro_code = ?", productCode)
								.addCondition(customerNumber!=null && !"".equals(customerNumber), "o.customer_number=?", customerNumber);
								
								
		List<Map<String, Object>> queryForMap = this.getJdbcTemplate().queryForList(sqlHelper.getQueryListSql(),sqlHelper.getParameters());
		return queryForMap;
	
	}
	

	
	/**
	 * 通过产品编码  和开始时间 结束时间 统计费用   （客户编码）
	 * @param productCode 产品编码
	 * @param customerNumber 客户编码
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public  Map<String, Object> queryCountFee(String productCode,String customerNumber,String startTime,String endTime){
		
		
		SqlHelper sqlHelper=new SqlHelper("afin_order_stats","os")
								.addResult("ANY_VALUE(os.product_code) product_code,ANY_VALUE(os.product_name) product_name,sum(fee) fee")
								.addCondition("os.record_time>=?", startTime)
								.addCondition("os.record_time<=?", endTime)
								.addCondition("os.product_code =?", productCode)
								.addCondition(customerNumber!=null && !"".equals(customerNumber), "os.customer_number=?", customerNumber);
		
		
		
		Map<String, Object> queryForMap = this.getJdbcTemplate().queryForMap(sqlHelper.getQueryListSql(),sqlHelper.getParameters());
		
		return queryForMap;
	
		
	}
	
	/**
	 * 查合作伙伴list
	 * @return
	 */
	public List<AfinPartner> selPartnerList(){
		String sql="select * from afin_partner";
		return this.getJdbcTemplate().query(sql,new AfinPartnerRowMapper());
	}
	
	/**
	 * 统计当前订单sum(fee)
	 * @param customer_number
	 * @param product_code
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Double selFee(String customer_number,String product_code,Date beginTime,Date endTime){
		String sql="select sum(fee) as fee from afin_order_stats where customer_number=? and product_code=? and "
				+ "record_time>=? and record_time<=?";
		Object[] params = new Object[]{customer_number,product_code,beginTime,endTime};
//		try{
//			return this.getJdbcTemplate().queryForObject(sql, params, Integer.class); 		
//		}catch(NullPointerException e){
//			return 0;
//		}
		Map<String, Object> queryForMap = this.getJdbcTemplate().queryForMap(sql, params);
		return (Double)queryForMap.get("fee")==null?0.00:(Double)queryForMap.get("fee");
		
	}
}
