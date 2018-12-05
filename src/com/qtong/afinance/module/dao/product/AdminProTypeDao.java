package com.qtong.afinance.module.dao.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.product.AdminProTypeRowMapper;
@Repository
public class AdminProTypeDao extends BaseJdbcDao{

	/**
	 * 产品品类别管理查询
	 * 
	 * @param request
	 * @param response
	 */
	public PageData getAdminProTypeList(String pageIndex) {
		PageData pageData = new PageData();// 分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String tableName = TableInfo.afin_admin_pro_type;// 表名
		String fieldSelect = "";
		String primaryKey = "package_code";
		String fieldOrderName = "";
		String fieldOrderDirection = "";
		StringBuilder strWhere = new StringBuilder();
		Object[] params = new Object[] {};
		PageData queryPage = this.queryPage(pageData, tableName,primaryKey, fieldSelect, fieldOrderName, fieldOrderDirection,
				strWhere.toString(), params, new AdminProTypeRowMapper());
		return queryPage;
	}

	/**
	 * 根据adminProType的id查询有没有产品
	 * 
	 * @param id
	 * @param lev 
	 * @return
	 */
	public int getProductById(String packageCode) {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
			stringBuffer.append(TableInfo.afin_admin_product);
			stringBuffer.append(" where pro_type_id= ?");
		Object[] params = new Object[] { packageCode };
		try {
			return  this.getJdbcTemplate().queryForObject(stringBuffer.toString(),Integer.class, params);
		} catch (DataAccessException e) {
			return 0;
		}
	}

	/**
	 * 产品品类别管理删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteAdminProType(String id) {
		StringBuffer stringBuffer = new StringBuffer("delete from ");
		stringBuffer.append(TableInfo.afin_admin_pro_type);
		stringBuffer.append(" where package_code=?");
		Object[] params = new Object[] { id };
		return this.getJdbcTemplate().update(stringBuffer.toString(), params);
	}

	/**
	 * 产品类别管理查询name</br>
	 * 判断数据库里是否有重复的name
	 * 
	 * @param name
	 * @param lev 
	 * @param parentName 
	 * @return
	 */
	public int getNameByAdminProType(String name,String parentName, String lev) {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
		stringBuffer.append(TableInfo.afin_admin_pro_type);
		List<Object> list = new ArrayList<>();
		stringBuffer.append(" where lev = ? and type_name = ?");
		list.add(lev);
		list.add(name);
		if("2".equals(lev)) {
			stringBuffer.append(" and parent_id = ?");
			list.add(parentName);
		}
		return this.getJdbcTemplate().queryForObject(stringBuffer.toString(), Integer.class,list.toArray());
	}

	/**
	 * 查询package_code
	 * @return
	 */
	public String getTypeCode() {
		StringBuffer stringBuffer = new StringBuffer("select package_code from afin_product_type where package_code LIKE '_'  ORDER BY afin_product_type.package_code DESC LIMIT 1");
		try {
			return this.getJdbcTemplate().queryForObject(stringBuffer.toString(), String.class);
		} catch (DataAccessException e) {
			return "";
		}
	}
	/**
	 * 查询package_code
	 * @param name 
	 * @return
	 */
	public String getTypeCode2(String name) {
		StringBuffer stringBuffer = new StringBuffer("select package_code from afin_product_type where package_code LIKE '__' and afin_product_type.parent_name = ?  ORDER BY afin_product_type.package_code DESC LIMIT 1");
		Object[] params = new Object[] { name };
		try {
			return this.getJdbcTemplate().queryForObject(stringBuffer.toString(), String.class, params);
		} catch (DataAccessException e) {
			return "";
		}
	}
	
	/**
	 * 产品品类别管理添加
	 * 
	 * @param name
	 * @param lev
	 * @param parentName
	 * @param parentId
	 * @param user_id
	 * @return
	 */
	public int addAdminProType(String package_code, String name, String lev, String parentName, String parentId, String user_id) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("insert into ");
		stringBuffer.append(TableInfo.afin_admin_pro_type);
		stringBuffer.append("(package_code,type_name,lev,parent_name,parent_id,user_id,create_time,update_time) ");
		stringBuffer.append("values(?, ?, ?, ?, ?, ?, now(), now() )");
		System.out.println(stringBuffer.toString());
		Object[] params = new Object[] { package_code, name, lev, parentName, parentId, user_id };
		return this.getJdbcTemplate().update(stringBuffer.toString(), params);
	}

	/**
	 * 查询总数</br>
	 * @return
	 */
	public int getAdminProTypeCount() {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
		stringBuffer.append(TableInfo.afin_admin_pro_type);
		Object[] params = new Object[] {};
		return this.getJdbcTemplate().queryForObject(stringBuffer.toString(), Integer.class, params);
	}

	public int getProduct(String packageCode, String lev) {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
		if("1".equals(lev)) {
			stringBuffer.append(TableInfo.afin_admin_pro_type);
			stringBuffer.append(" where parent_id= ?");
		}
		Object[] params = new Object[] { packageCode };
		try {
			return  this.getJdbcTemplate().queryForObject(stringBuffer.toString(),Integer.class, params);
		} catch (DataAccessException e) {
			return 0;
		}
	}

	/**
	 * 根据name取一级id
	 * @param name
	 * @return
	 */
	public String getTypeCodeById(String name) {
		StringBuffer stringBuffer = new StringBuffer("select package_code from afin_product_type where package_code LIKE '_' and afin_product_type.type_name = ?  ORDER BY afin_product_type.package_code DESC LIMIT 1");
		Object[] params = new Object[] {name};
		try {
			return this.getJdbcTemplate().queryForObject(stringBuffer.toString(), String.class, params);
		} catch (DataAccessException e) {
			return "";
		}
	}
}
