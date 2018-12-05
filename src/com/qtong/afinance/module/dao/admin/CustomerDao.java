package com.qtong.afinance.module.dao.admin;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.customer.BossCustomer;
import com.qtong.afinance.module.pojo.customer.BossCustomerMapper;


/**
 * 门户-最新动态
 */
@Repository
public class CustomerDao extends BaseJdbcDao {
	/**
	 * 1.查看详情-通过客户编码（唯一）
	 * @param customerNumber
	 * @return
	 * SELECT *
       FROM afin_boss_customer t1 LEFT join afin_boss_customer_group t2
       ON t1.customer_number=t2.customer_number where t1.customer_number='E1002016122610000134'
	 */
	public  Map<String, Object> selectCusByNumber(String customerNumber) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer );
		sqlBuffer.append(" t1 LEFT join ");
		sqlBuffer.append(TableInfo.afin_boss_customer_group );
		sqlBuffer.append(" t2 ON t1.customer_number=t2.customer_number where t1.customer_number=?");
        Object[] params = new Object[]{customerNumber};	
        Map<String, Object> list = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
	    return list;
	}
	/**
	 *  2.修改状态(正常或禁用)
	 * @param bossCustomer
	 * @return
	 */
	public int updateStatus(BossCustomer bc) {
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" set status=? where customer_number=?");
		Object[] params = new Object[]{bc.getStatus(),bc.getCustomerNumber()};
       return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
		
	}

	/**
	 * 3. 根据条件查询,分页查询
	 * @param pageIndex 当前页号
     * @param customerNumber 客户编码
	 * @param customerName 客户名称
	 * @param loginName 登陆名称	
	 * @param opr_time 同步时间
	 *      opr_time_start 
	 *      opr_time_end
	 * @param operate_time 创建时间(登陆账号)
	 *      operate_time_start
	 *      operate_time_end
	 * @param status 状态
	 * @param cjState 创建状态
	 * 
	 * @return   select * from afin_boss_customer  ORDER BY
					login_name asc,
					case when login_name is null then operate_time end desc,
					case when login_name is not null then opr_time end desc
					limit 0,10
	 */
	public PageData selectCusByTermList(String pageIndex, String customerNumber, String customerName,String loginName,
		   String opr_time_start,String opr_time_end,String operate_time_start,String operate_time_end,
		   String status,String cjState) {
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));					
		SqlHelper sqlHelper = new SqlHelper("afin_boss_customer")//
				.addCondition("1".equals(cjState)&&cjState!=null,"login_name=?" , "")//
				.addCondition("0".equals(cjState)&&cjState!=null, "login_name!=?", "")//
				.addCondition(!"".equals(status)&&status!=null, "status=?", status)//
				.addCondition(!"".equals(operate_time_start)&&operate_time_start!=null, "operate_time>=?", operate_time_start)
				.addCondition(!"undefined".equals(operate_time_end)&&!"".equals(operate_time_end)&&operate_time_end!=null, "operate_time<=?", operate_time_end)
				.addCondition(!"undefined".equals(opr_time_start)&&!"".equals(opr_time_start)&&opr_time_start!=null, "opr_time>=?", opr_time_start)
				.addCondition(!"undefined".equals(opr_time_end)&&!"".equals(opr_time_end)&&opr_time_end!=null, "opr_time<=?", opr_time_end)
				.addConditionLike(!"undefined".equals(customerNumber)&&!"".equals(customerNumber)&&customerNumber!=null, "customer_number like ?", customerNumber)
				.addConditionLike(!"undefined".equals(customerName)&&!"".equals(customerName)&&customerName!=null, "customer_name like ?", customerName)
				.addConditionLike(!"undefined".equals(loginName)&&!"".equals(loginName)&&loginName!=null, "login_name like ?", loginName)
		        .addOrder("ifnull(operate_time,NOW())", false)
		        .addOrder("opr_time", false);
		PageData page = this.queryPage(pageData, sqlHelper, new BossCustomerMapper());	
		return page;		
	}
	/**
	 * 4. 新增客户账号（门户登录）
	 * @param u
	 * @return
	 */
	public int insertProtalUser(BossCustomer bc){
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" set login_name=?,login_pwd=?,pic_name=?,pic_phone=?,operate_time=?,updpwd_time=? where customer_number=?"); 	
		Object[] params = new Object[]{bc.getLoginName(),bc.getLoginPwd(),bc.getPicName(),bc.getPicPhone(),bc.getOperateTime(),bc.getUpdpwdTime(),bc.getCustomerNumber()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	/**
	 * 更改负责人
	 * @param bc
	 * @return
	 */
	public int updatePic(BossCustomer bc){
		StringBuffer sqlBuffer=new StringBuffer("update ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" set pic_name=?,pic_phone=? where customer_number=?"); 	
		Object[] params = new Object[]{bc.getPicName(),bc.getPicPhone(),bc.getCustomerNumber()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	/**
	 *查询码表 行业
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="省编码" and id="100"
	 */
	public Map<String, Object> selectIndustryIdCoding(String industryId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type=?");
		String type="行业编码";
        Object[] params = new Object[]{industryId,type};	
	    try {
	    	 Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
	 	    return forMap;
		} catch (Exception e) {
			 Map<String, Object> forMap =new HashMap<>();
			 forMap.put("ic_name","");
			 return forMap;
		}
	}
	/**
	 *查询码表 国家
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="省编码" and id="100"
	 */
	public Map<String, Object> selectNationIdCoding(String nationId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type=?");
		String type="国家编码";
        Object[] params = new Object[]{nationId,type};	
        try {
        	 Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
      	    return forMap;
		} catch (Exception e) {
			 Map<String, Object> forMap =new HashMap<>();
			 forMap.put("ic_name","");
			 return forMap;
		}
      
	}
	/**
	 *查询码表 城市区编码
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="省编码" and id="100"
	 */
	public Map<String, Object> selectCityCoding(String City) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type=?");
		String type="城市区编码";
        Object[] params = new Object[]{City,type};	
	    try {
	    	 Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
	 	    return forMap;
		} catch (Exception e) {
			 Map<String, Object> forMap =new HashMap<>();
			 forMap.put("ic_name","");
			 return forMap;
		}
     
	}
	/**
	 *查询码表 省
	 * @param customerNumber
	 * @return
	 * select ic_name from afin_boss_coding where type="" and id="100"
	 */
	public Map<String, Object> selectCompanyIdCoding(String companyId) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_coding );	
		sqlBuffer.append(" where id=? and type=?");
		String type="省编码";
        Object[] params = new Object[]{companyId,type};	
	    try {
	        Map<String, Object> forMap = this.getJdbcTemplate().queryForMap(sqlBuffer.toString(),params);	
		    return forMap;
		} catch (Exception e) {
			 Map<String, Object> forMap =new HashMap<>();
			 forMap.put("ic_name","");
			 return forMap;
		}
	}
	/**
	 *  验证用户名是否重复
	 * @return
	 */
	public BossCustomer getUserPassword(String loginName) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer );	      
		sqlBuffer.append(" where login_name=?" );	
		Object[] params = new Object[]{loginName};	
		try {
			 BossCustomer queryForObject = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new BossCustomerMapper(),params);	
			return queryForObject;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 查询所有客户
	 * @return
	 */
	public List<BossCustomer> getCustomers(){
		StringBuilder sqlBuffer = new StringBuilder("select * from ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" where status=?"); 
		Object[] params = new Object[]{0};
		List<BossCustomer> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new BossCustomerMapper(),params);		
		return query;
	}
	
	/**
	 * 查询所有订购了网址卫士的客户
	 * @return
	 */
	public List<BossCustomer> getCustomersWeiShi(){
		StringBuilder sqlBuffer = new StringBuilder("select abc.* from ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		sqlBuffer.append(" abc left join afin_order ao on abc.customer_number=ao.customer_number where status=? and ao.product_name='网址卫士'"); 
		Object[] params = new Object[]{0};
		List<BossCustomer> query=this.getJdbcTemplate().query(sqlBuffer.toString(), new BossCustomerMapper(),params);		
		return query;
	}
	
	/**
	 *6. 网址卫士-所属客户名称
	 * @param customerNumber 客户编码
	 * @param customerName 客户名称
	 */
	public  List<Map<String, Object>> selectCusList(){
		StringBuilder sqlBuffer = new StringBuilder("select customer_number,customer_name from ");
		sqlBuffer.append(TableInfo.afin_boss_customer);
		 List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(sqlBuffer.toString());		
		return queryForList;
	}
}


