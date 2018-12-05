package com.qtong.afinance.module.dao.interf;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.DateUtil;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.interf.Interface;
import com.qtong.afinance.module.pojo.interf.InterfaceRowMapper;
import com.qtong.afinance.module.pojo.interf.Product;
import com.qtong.afinance.module.pojo.interf.ProductRowMapper;
/**
 * 接口管理Dao
 *
 */
@Repository
public class InterfaceDao extends BaseJdbcDao{
	
	/**
	 * 增加产品
	 * @return
	 */
	public int insertProduct(Product addProduct) {
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append("afin_partner_product");
		sqlBuffer.append("(product_code,secret_id,secret_key,interface_url,create_time) values(?,?,?,?,?)"); 	
		Object[] params = new Object[]{addProduct.getProductCode(),addProduct.getSecretId(),addProduct.getSecretKey(),addProduct.getInterfaceUrl(),DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS)};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);		
	}
	/**
	 * 产品管理列表
	 * @param selectProduct
	 * @return
	 */
    public PageData selectProduct(String pageIndex,String productCode, String secretId, String secretKey, String interfaceUrl) {
    	PageData pageData =new PageData();
    	pageData.setPageIndex(Integer.parseInt(pageIndex));
		StringBuilder sqlBuffer = new StringBuilder("select * from afin_partner_product where 1=1 ");
		List<Object> list = new ArrayList<>();
		StringBuilder sqlCount = new StringBuilder("select count(*) from afin_partner_product where 1=1 ");
		
		if (productCode!=null && productCode.trim().length()>0) {
			sqlBuffer.append(" and product_code like ? ");			
			sqlCount.append(" and product_code like ? ");	
			list.add("%" + productCode + "%");
		}
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sqlBuffer.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sqlBuffer.toString(), new ProductRowMapper(),list.toArray()));
	return pageData;
    }
    /**
     * 删除产品
     * @param parseInt
     * @return
     */
	public int deleteProductById(int id) {
		StringBuffer sqlBuffer=new StringBuffer("delete from afin_partner_product ");
		sqlBuffer.append(" where id=?");
		Object[] params = new Object[]{id};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}
	/**
	 * 更改产品
	 * @param updateProduct
	 * @return
	 */
	public int updateProduct(Product i) {
		StringBuffer sqlBuffer=new StringBuffer("update afin_partner_product ");
		sqlBuffer.append(" set product_code=?,secret_id=?,secret_key=?,interface_url=? where id=?");
		Object[] params = new Object[]{i.getProductCode(),i.getSecretId(),i.getSecretKey(),i.getInterfaceUrl(),i.getId()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	/**
	 * 查询产品byid
	 * @param id
	 * @return
	 */
	public Product selectProductByid(String id) {
		StringBuffer sqlBuffer=new StringBuffer("select * from afin_partner_product ");
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};	
        Product queryForObject = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new ProductRowMapper(),params);	
	   return queryForObject;
	}
	
	
	
	/**
	 * 新增接口
	 * @return
	 */
	public int insert(Interface i) {
		StringBuffer sqlBuffer=new StringBuffer("insert into ");
		sqlBuffer.append("afin_interface");
		sqlBuffer.append("(interface_name,interface_url,partner_name,afin_account,afin_key,service_code,customer_number,customer_name,secret_id,secret_key,partner_id,account_type,state,product_code,create_time,value) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); 	
		Object[] params = new Object[]{i.getInterfaceName(),i.getInterfaceUrl(),i.getPartnerName(),i.getAfinAccount(),i.getAfinKey(),i.getServiceCode(),i.getCustomerNumber()
				                      ,i.getCustomerName(),i.getSecretId(),i.getSecretKey(),i.getPartnerId(),i.getAccountType(),i.getState(),i.getProductCode(),
				                      DateUtil.toStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS),i.getValue()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);	
		
	}
   /**
    * * 服务编码相同时，和金融分配账号不能重复
    * @param afinAccount
    * @param serviceCode
    * @return
    */
	public int distinctAfinCount(String afinAccount, String serviceCode) {
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append("afin_interface");
		sqlBuffer.append(" where service_code = ? and afin_account=?");	
		Object[] params = new Object[]{serviceCode,afinAccount};
		try {
			List<Map<String,Object>> queryForObject = this.getJdbcTemplate().queryForList(sqlBuffer.toString(),params);
			return queryForObject.size();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public PageData queryProducts(PageData pageData, Integer state,String productName1, String productName2, String productName3,String productEn,String customerNumber) {
		
		SqlHelper sqlHelper=new SqlHelper("(SELECT IF(t.parent_name!='',t.parent_name,t.type_name) parent_name,t.type_name,o.product_name,o.state,p.en_name,p.product_code,o.customer_number,o.service_code FROM afin_order_relation o,afin_product_type t,afin_product p WHERE t.package_code=p.pro_type_id AND o.product_code=p.product_code AND o.state!=2) temp")
			.addResult("parent_name,type_name,product_name,state,en_name,product_code,service_code")
			.addCondition(state!=null,"state=?",state)
			.addCondition(customerNumber!=null&&!customerNumber.isEmpty(),"customer_number=?",customerNumber)
			.addConditionLike(productName1!=null&&!productName1.isEmpty(), "parent_name LIKE ?", productName1)
			.addConditionLike(productName2!=null&&!productName2.isEmpty(), "type_name LIKE ?",productName2)
			.addConditionLike(productName3!=null&&!productName3.isEmpty(), "product_name LIKE ?", productName3)
			.addConditionLike(productEn!=null&&!productEn.isEmpty(), "en_name LIKE ?", productEn);
		 
		this.queryPage(pageData, sqlHelper);
		
		return pageData;
		
	}
	
	/**
	 * 根据服务编码和客户编码查询绑定的产品信息
	 * @return
	 */
	public List<Map<String, Object>> queryBindProducts(String serviceCode,String customerNumber) {
		
		SqlHelper sqlHelper=new SqlHelper("(SELECT IF(t.parent_name!='',t.parent_name,t.type_name) parent_name,t.type_name,o.product_name,o.state,p.en_name,p.product_code,o.customer_number,o.service_code FROM afin_order_relation o,afin_product_type t,afin_product p WHERE t.package_code=p.pro_type_id AND o.product_code=p.product_code AND o.state!=2) temp")
			.addResult("parent_name,type_name,product_name,state,en_name,product_code,service_code")
			.addCondition(customerNumber!=null&&!customerNumber.isEmpty(),"customer_number=?",customerNumber)
			.addCondition(serviceCode!=null&&!serviceCode.isEmpty(), "service_code = ?", serviceCode);
		
		return this.getJdbcTemplate().queryForList(sqlHelper.getQueryListSql(), sqlHelper.getParameters());
		
	}
	
	
	
	/**
	 * 接口管理列表和条件查询
	 * @param addInterface
	 * @return
	 */
    public PageData selectInterf(String pageIndex,String afinAccount, String serviceCode, String secretId, String customerName,
		String partnerName, Integer state,String accountType) {
    	PageData pageData =new PageData();
    	pageData.setPageIndex(Integer.parseInt(pageIndex));
		StringBuilder sqlBuffer = new StringBuilder("select * from afin_interface where 1=1 ");
		List<Object> list = new ArrayList<>();
		StringBuilder sqlCount = new StringBuilder("select count(*) from afin_interface where 1=1 ");

		if (afinAccount!=null && afinAccount.trim().length()>0) {
			sqlBuffer.append(" and afin_account like ? ");			
			sqlCount.append(" and afin_account like ? ");	
			list.add("%" + afinAccount + "%");
		}
		
		if (serviceCode!=null && serviceCode.trim().length()>0) {
			sqlBuffer.append(" and service_code like ? ");			
			sqlCount.append(" and service_code like ? ");	
			list.add("%" + serviceCode + "%");
		}
		
		if (secretId!=null && secretId.trim().length()>0) {
			sqlBuffer.append(" and secret_id like ? ");			
			sqlCount.append(" and secret_id like ? ");	
			list.add("%" + secretId + "%");
		}
		if (customerName!=null && customerName.trim().length()>0) {
			sqlBuffer.append(" and customer_name like ? ");			
			sqlCount.append(" and customer_name like ? ");	
			list.add("%" + customerName + "%");
		}
		if (partnerName!=null && partnerName.trim().length()>0) {
			sqlBuffer.append(" and partner_name like ? ");			
			sqlCount.append(" and partner_name like ? ");	
			list.add("%" + partnerName + "%");
		}
		if (state!=null) {
			sqlBuffer.append(" and state = ? ");			
			sqlCount.append(" and state = ? ");	
			list.add(state);
		}
		if (accountType!=null && accountType.trim().length()>0) {
			sqlBuffer.append(" and account_type = ? ");			
			sqlCount.append(" and account_type = ? ");	
			list.add(accountType);
		}
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,list.toArray()).intValue());
		
		sqlBuffer.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		pageData.setLst(this.getJdbcTemplate().query(sqlBuffer.toString(), new InterfaceRowMapper(),list.toArray()));
	return pageData;
    }
    /**
     * 删除
     * @param parseInt
     * @return
     */
	public int deleteInterFById(int id) {
		StringBuffer sqlBuffer=new StringBuffer("delete  from afin_interface ");
		sqlBuffer.append(" where id=?");
		Object[] params = new Object[]{id};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}
	/**
	 * 更改
	 * @param updateInterface
	 * @return
	 */
	public int updateInterf(Interface i) {
		StringBuffer sqlBuffer=new StringBuffer("update afin_interface ");
		sqlBuffer.append(" set interface_name=?,interface_url=?,partner_name=?,afin_account=?,afin_key=?,service_code=?,customer_number=?,customer_name=?,secret_id=?,secret_key=?,partner_id=?,account_type=?,state=?,product_code=?,value=? where id=?");
		Object[] params = new Object[]{i.getInterfaceName(),i.getInterfaceUrl(),i.getPartnerName(),i.getAfinAccount(),i.getAfinKey(),i.getServiceCode(),i.getCustomerNumber()
                ,i.getCustomerName(),i.getSecretId(),i.getSecretKey(),i.getPartnerId(),i.getAccountType(),i.getState(),i.getProductCode(),i.getValue(),i.getId()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	/**
	 * 查询
	 * @param id
	 * @return
	 */
	public Interface selectByid(String id) {
		StringBuffer sqlBuffer=new StringBuffer("select * from afin_interface ");
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};	
        Interface queryForObject = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new InterfaceRowMapper(),params);	
	   return queryForObject;
	}
	/**
	 * 保存绑定服务编码
	 * @param bind
	 * @return 
	 */
	public void saveServiceCode(final List<Map<String, String>> bind) {
		
		String sql = "update afin_order_relation set service_code=? where product_order_id=?";
		this.getJdbcTemplate().batchUpdate(sql.toString(),new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				 ps.setString(1, bind.get(i).get("value"));   
	             ps.setString(2, bind.get(i).get("key"));  
			}
			
			@Override
			public int getBatchSize() {
				return bind.size();
			}
		});
		
	}
}


