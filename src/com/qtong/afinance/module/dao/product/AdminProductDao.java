package com.qtong.afinance.module.dao.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.product.AdminProduct;
import com.qtong.afinance.module.pojo.product.AdminProductRowMapper;

/**
 * adminProductDao
 * 
 *
 */
@Repository
public class AdminProductDao extends BaseJdbcDao {

	/**
	 * 查询
	 * 
	 * @param name
	 * @param name2
	 * @param code
	 * @param proCode
	 * @param partner_id
	 * @param state
	 * @param pageIndex
	 * @return
	 */
	public List<Map<String, Object>> getAdminProductList(String type_name, String cn_name, String parent_name,
			String code, String proCode, String partner_id, String state, String pageIndex) {
		StringBuffer str = new StringBuffer();
		str.append("select * from afin_product LEFT JOIN "
				+ "afin_product_type on afin_product.pro_type_id=afin_product_type.package_code "
				+ "LEFT JOIN afin_boss_pro on afin_product.product_code=afin_boss_pro.product_code "
				+ "LEFT JOIN afin_partner on afin_product.partner_id=afin_partner.id");
		str.append(" where 1=1 ");
		List<Object> list = new ArrayList<>();
		if (!"".equals(type_name) && type_name != null) {
			if (!"".equals(parent_name) && parent_name != null) {
				str.append(" and type_name = ? and parent_name = ?");
				list.add(parent_name);
				list.add(type_name);
			}else if("乾坤大数据".equals(type_name)) {
				str.append(" and parent_name = ? ");
				list.add(type_name);
			}else {
				str.append(" and type_name = ? ");
				list.add(type_name);
			}
		}
		if (!"".equals(cn_name) && cn_name != null) {
			str.append(" and cn_name LIKE ? ");
			list.add("%" + cn_name + "%");
		}
		if (!"".equals(code) && code != null) {
			str.append(" and code LIKE ? ");
			list.add("%" + code + "%");
		}
		if (!"".equals(proCode) && proCode != null) {
			str.append(" and afin_boss_pro.product_code LIKE ? ");
			list.add("%" + proCode + "%");
		}
		if (!"".equals(partner_id) && partner_id != null) {
			str.append(" and partner_id LIKE ? ");
			list.add("%" + partner_id + "%");
		}
		if (!"2".equals(state) && state != null) {
			str.append(" and is_connect = ? ");
			list.add(state);
		}
		if (!"".equals(pageIndex) && pageIndex != null) {
			int page = 0;
			page = (Integer.valueOf(pageIndex) - 1) * 10;
			str.append(" limit ?,10");
			list.add(page);
		}
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString(), list.toArray());
		return queryForList;
	}
	
	
	/**
	 * 根据产品类别查询产品List
	 * @param typeName
	 * @return
	 */
	public List<AdminProduct> getAdminProductList(String typeName) {
		
		SqlHelper sqlHelper=new SqlHelper("afin_product LEFT JOIN afin_product_type on afin_product.pro_type_id=afin_product_type.package_code")//
								.addResult("afin_product.*")//
								.addCondition(!"".equals(typeName) && typeName != null, "type_name = ?", typeName);
								
		List<AdminProduct> query = this.getJdbcTemplate().query(sqlHelper.getQueryListSql(),new AdminProductRowMapper(), sqlHelper.getParameters());
		return query;
							
	}
	
	/**
	 * 根据产品中文名称查询产品信息
	 * @param cnName
	 * @return
	 */
	public List<AdminProduct> getAdminProductByCnName(String cnName){
		StringBuffer sqlBuffer=new StringBuffer("select * from afin_product");
		sqlBuffer.append(" where cn_name = ? "); 	
		Object[] params = new Object[]{cnName};
		List<AdminProduct> query = this.getJdbcTemplate().query(sqlBuffer.toString(), new AdminProductRowMapper(), params);
		return query;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	public int getAdminProductCount(String type_name, String cn_name, String parent_name,
			String code, String proCode, String partner_id, String state) {
		StringBuffer str = new StringBuffer();
		str.append("select * from afin_product LEFT JOIN "
				+ "afin_product_type on afin_product.pro_type_id=afin_product_type.package_code "
				+ "LEFT JOIN afin_boss_pro on afin_product.product_code=afin_boss_pro.product_code "
				+ "LEFT JOIN afin_partner on afin_product.partner_id=afin_partner.id");
		str.append(" where 1=1 ");
		List<Object> list = new ArrayList<>();
		if (!"".equals(type_name) && type_name != null) {
			if (!"".equals(parent_name) && parent_name != null) {
				str.append(" and type_name = ? and parent_name = ?");
				list.add(parent_name);
				list.add(type_name);
			}else if("乾坤大数据".equals(type_name)) {
				str.append(" and parent_name = ? ");
				list.add(type_name);
			}else {
				str.append(" and type_name = ? ");
				list.add(type_name);
			}
		}
		if (!"".equals(cn_name) && cn_name != null) {
			str.append(" and cn_name LIKE ? ");
			list.add("%" + cn_name + "%");
		}
		if (!"".equals(code) && code != null) {
			str.append(" and code LIKE ? ");
			list.add("%" + code + "%");
		}
		if (!"".equals(proCode) && proCode != null) {
			str.append(" and afin_boss_pro.product_code LIKE ? ");
			list.add("%" + proCode + "%");
		}
		if (!"".equals(partner_id) && partner_id != null) {
			str.append(" and partner_id LIKE ? ");
			list.add("%" + partner_id + "%");
		}
		if (!"2".equals(state) && state != null) {
			str.append(" and is_connect = ? ");
			list.add(state);
		}
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString(),list.toArray());
		return queryForList.size();
	}

	/**
	 * 查询一级类
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getTypeNameByAdminProType() {
		StringBuffer str = new StringBuffer();
		str.append("select * from ");
		str.append(TableInfo.afin_admin_pro_type);
		str.append(" where lev = 1");
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString());
		return queryForList;
	}

	/**
	 * 查询二级类
	 * 
	 * @param lev
	 * @param type_name
	 * @return
	 */
	public List<Map<String, Object>> getTypeName(String lev, String type_name) {
		StringBuffer str = new StringBuffer();
		str.append("select * from ");
		str.append(TableInfo.afin_admin_pro_type);
		str.append(" where lev = ? and parent_name = ?");
		Object[] params = new Object[]{lev, type_name};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString(), params);
		return queryForList;
	}

	/**
	 * 查询来源
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPartners() {
		StringBuffer str = new StringBuffer();
		str.append("select * from afin_partner");
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString());
		return queryForList;
	}

	/**
	 * 添加
	 * 
	 * @param type1
	 * @param type2
	 * @param cn_name
	 * @param user_id 
	 * @param typdescriptione1
	 * @param partners_id
	 * @return
	 */
	public int addAdminProduct(String code, String type1, String type2, String cn_name, String en_name, String description,
			String partner_id, String user_id) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("insert into ");
		stringBuffer.append(TableInfo.afin_admin_product);
		stringBuffer.append(
				"(code,pro_type_id,cn_name,en_name,description,partner_id,is_connect,user_id,create_time,update_time,state) ");
		String pro_type_id = "";
		if (!"".equals(type1) && type1 != null) {
			if (!"".equals(type2) && type2 != null) {
				pro_type_id = type2;
			}else{
				pro_type_id = type1;
			}
		} else {
			pro_type_id = "";
		}
		stringBuffer.append("values(?,?,?,?,?,?,'1',?,now(),now(),1)");
		Object[] p = new Object[] {code, pro_type_id, cn_name, en_name, description, partner_id, user_id};
		return this.getJdbcTemplate().update(stringBuffer.toString(), p);
	}

	/**
	 * 修改
	 * 
	 * @param code
	 * @param type1
	 * @param type2
	 * @param cn_name
	 * @param typdescriptione1
	 * @param partners_id
	 * @return
	 */
	public int updateAdminProduct(String code, String type, String cn_name, String en_name, String description,
			String partner_id) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("update ");
		stringBuffer.append(TableInfo.afin_admin_product);
		stringBuffer.append(" set ");
		if(!type.isEmpty()){
			stringBuffer.append(" pro_type_id=" + type + ",");
		}
		if(!partner_id.isEmpty()&&!"undefined".equals(partner_id)){
			stringBuffer.append(" partner_id=" + partner_id + ",");
		}else {
			stringBuffer.append(" partner_id='',");
		}
		stringBuffer.append(" cn_name = ?, en_name = ?, description = ?, update_time = now() where code = ?");
		Object[] p = new Object[] {cn_name,en_name, description, code};
		return this.getJdbcTemplate().update(stringBuffer.toString(), p);
	}

	/**
	 * 根据code获取详细信息
	 * 
	 * @param code
	 * @return
	 */
	public List<Map<String, Object>> getAdminProductInfo(String code) {
		StringBuffer str = new StringBuffer();
		str.append("select afin_boss_pro.product_code , afin_boss_pro.product_name,afin_boss_pro.product_package_name,"
				+ "afin_product.*,afin_product_type.*,afin_partner.*   from afin_product "
				+ "LEFT JOIN afin_product_type on afin_product.pro_type_id=afin_product_type.package_code "
				+ "LEFT JOIN afin_partner on afin_product.partner_id=afin_partner.id "
				+ "LEFT JOIN afin_boss_pro on afin_product.product_code=afin_boss_pro.product_code "
				+ "where code = ? ");
		Object[] p = new Object[] {code};
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString(), p);
		return queryForList;
	}

	/**
	 * 根据code删除
	 * 
	 * @param code
	 * @return
	 */
	public int delAdminProduct(String code) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("delete from ");
		stringBuffer.append(TableInfo.afin_admin_product);
		stringBuffer.append(" where code = ? ");
		Object[] p = new Object[] {code};
		return this.getJdbcTemplate().update(stringBuffer.toString(), p);
	}

	/**
	 * 查询名字
	 * @param cn_name
	 * @return
	 */
	public int getCnNameByAdminProductCount(String cn_name) {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
		stringBuffer.append(TableInfo.afin_admin_product);
		stringBuffer.append(" where cn_name=?");
		Object[] params = new Object[] { cn_name };
		return this.getJdbcTemplate().queryForObject(stringBuffer.toString(), Integer.class, params);
	}

	/**
	 * 查询产品类别id
	 * @param type1
	 * @param type2
	 * @return
	 */
	public List<Map<String, Object>> getPackage_code(String type1, String type2) {
		StringBuffer stringBuffer = new StringBuffer("select * from ");
		stringBuffer.append(TableInfo.afin_admin_pro_type);
		stringBuffer.append(" where 1=1 ");
		List<Object> list = new ArrayList<>();
		if(!type1.isEmpty()&&!"undefined".equals(type1)){
			if(!type2.isEmpty()&&!"undefined".equals(type2)){
				stringBuffer.append(" and type_name = ? and parent_name = ? ");
				list.add(type2);
				list.add(type1);
			}else{
				stringBuffer.append(" and type_name = ? ");
				list.add(type1);
			}
		}
		return this.getJdbcTemplate().queryForList(stringBuffer.toString(), list.toArray());
	}

	/**
	 * 查询订购信息
	 * @param codeId
	 * @return
	 */
	public List<Map<String, Object>> getOder(String codeId) {
		StringBuffer stringBuffer = new StringBuffer("SELECT * from afin_order,"
				+ "afin_order_sub "
				+ "where afin_order.product_order_id=afin_order_sub.product_order_id "
				+ "AND pro_code = ? ");
		Object[] params = new Object[] { codeId };
		return this.getJdbcTemplate().queryForList(stringBuffer.toString(), params);
	}

	/**
	 * 查询单个一级类对应所有二级标签名称
	 * 
	 * @param type_name
	 * @return
	 */
	public Map<String, Object> getProuct(String type_name) {
		StringBuffer sql = new StringBuffer("SELECT GROUP_CONCAT(package_code) type_name from afin_product_type  where  afin_product_type.parent_name = ? ");
		Object[] params = new Object[] { type_name };
		return this.getJdbcTemplate().queryForMap(sql.toString(), params);
	}

}
