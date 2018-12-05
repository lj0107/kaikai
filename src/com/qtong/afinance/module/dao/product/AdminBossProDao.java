package com.qtong.afinance.module.dao.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.TableInfo;

@Repository
public class AdminBossProDao extends BaseJdbcDao{
	

	public List<Map<String, Object>> getAdminBossProList(String pageIndex, String type_name,String cnName, String productCode,
			String syncTimeStart, String syncTimeOut, String state, String parent_name) {
		StringBuffer str = new StringBuffer();
		str.append("select afin_boss_pro.product_code product_codeId,"
				+ "afin_boss_pro.product_package_name,afin_boss_pro.product_name,afin_boss_pro.sync_time, "
				+ "afin_boss_pro.product_state, afin_product.*, afin_product_type.*  "
				+ "from afin_boss_pro LEFT JOIN afin_product on "
				+ "afin_boss_pro.`product_code`=afin_product.`product_code` "
				+ "LEFT JOIN afin_product_type ON  afin_product.pro_type_id=afin_product_type.package_code");
		str.append(" where product_package_name <> '' ");
		List<Object> list = new ArrayList<Object>();
		if (!"请选择".equals(type_name)&&!"".equals(type_name) && type_name != null) {
			if (!"请选择".equals(parent_name)&&!"".equals(parent_name) && parent_name != null) {
				str.append(" and afin_product_type.type_name = ? and afin_product_type.parent_name = ? ");
				list.add(parent_name);
				list.add(type_name);
			}else if("乾坤大数据".equals(type_name)) {
				str.append(" and afin_product_type.parent_name = ?");
				list.add(type_name);
			} else {
				str.append(" and afin_product_type.type_name = ?");
				list.add(type_name);
			}
		}
		if (!"".equals(cnName) && cnName != null) {
			str.append(" and afin_product.cn_name LIKE ? ");
			list.add("%" + cnName + "%");
		}
		if (!"".equals(productCode) && productCode != null) {
			str.append(" and afin_boss_pro.product_code LIKE ? ");
			list.add("%" + productCode + "%");
		}
		if (!"undefined".endsWith(syncTimeStart)&&!"".equals(syncTimeStart) && syncTimeStart != null) {
			str.append(" and afin_boss_pro.sync_time >= ?");
			list.add(syncTimeStart);
		}
		if (!"undefined".endsWith(syncTimeOut)&&!"".equals(syncTimeOut) && syncTimeOut != null) {
			str.append(" and afin_boss_pro.sync_time <= ?");
			list.add(syncTimeOut);
		}
		if (!"2".equals(state) && state != null) {
			str.append(" and product_state = ?");
			list.add(state);
		}
		if (!"".equals(pageIndex) && pageIndex != null) {
			int page = 0;
			page = (Integer.valueOf(pageIndex) - 1) * 10;
			str.append(" ORDER BY sync_time DESC limit ? ,10");
			list.add(page);
		}
		List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString(),list.toArray());
		return queryForList;
	}

	/**
	 * 查询产品
	 * @return
	 */
	public List<Map<String, Object>> getProduct(String type1,String type2) {
		StringBuffer str = new StringBuffer();
		str.append("select * from ");
		str.append(TableInfo.afin_admin_product);
		str.append(" where is_connect='1' and pro_type_id=(select package_code FROM afin_product_type where 1= 1");
		List<Object> list = new ArrayList<Object>();
		if(!"undefined".equals(type1)&&!"".equals(type1)&&null!=type1){
			if(!"undefined".equals(type2)&&!"".equals(type2)&&null!=type2){
				str.append(" and type_name = ?");
				list.add(type2);
			}else{
				str.append(" and type_name = ?");
				list.add(type1);
			}
		}
		str.append(")");
		try {
			List<Map<String, Object>> queryForList = this.getJdbcTemplate().queryForList(str.toString(),list.toArray());
			return queryForList;
		} catch (DataAccessException e) {
			return null;
		}
	}

	/**
	 * 修改产品信息
	 * @param bossCod
	 * @param bossName
	 * @param cp
	 * @param product_package_code 
	 * @param type22 
	 * @param type11 
	 * @return
	 */
	public int updateProduct(String bossCod, String bossName, String cp, String type11, String type22) {
		StringBuffer str = new StringBuffer();
		str.append("update ");
		str.append(TableInfo.afin_admin_product);
		List<Object> list = new ArrayList<Object>();
		str.append(" set is_connect=0, product_code = ?,update_time=now() ");
		list.add(bossCod);
		if(!"".equals(cp)&&null!=cp&&!"undefined".equals(cp)){
			str.append(" where cn_name = ?");
			list.add(cp);
		}else{
			str.append(" where pro_type_id=(select package_code FROM afin_product_type where 1= 1");
			if(!"".equals(type11)&&null!=type11&&!"undefined".equals(type11)){
				if(!"".equals(type22)&&null!=type22&&!"undefined".equals(type11)){
					str.append(" and type_name = ? and parent_name = ? ");
					str.append(")");
					list.add(type22);
					list.add(type11);
				}else{
					str.append(" and type_name = ? ");
					str.append(")");
					list.add(type11);
				}
			}
		}
		return this.getJdbcTemplate().update(str.toString(),list.toArray());
	}

	/**
	 * 查询boss总数
	 * @param parent_name 
	 * @return
	 */
	public int getBossCount(String pageIndex, String type_name,String cnName, String productCode,
			String syncTimeStart, String syncTimeOut, String state, String parent_name) {
		StringBuffer str = new StringBuffer();
		str.append("select count(1) from afin_boss_pro LEFT JOIN afin_product on afin_boss_pro.`product_code`=afin_product.`product_code` "
				+ "LEFT JOIN afin_product_type ON  afin_product.pro_type_id=afin_product_type.package_code where product_package_name <> '' ");
		List<Object> list = new ArrayList<>();
		if (!"请选择".equals(type_name)&&!"".equals(type_name) && type_name != null) {
			if (!"请选择".equals(parent_name)&&!"".equals(parent_name) && parent_name != null) {
				str.append(" and afin_product_type.type_name = ? and afin_product_type.parent_name = ? ");
				list.add(parent_name);
				list.add(type_name);
			}else if("乾坤大数据".equals(type_name)) {
				str.append(" and afin_product_type.parent_name = ? ");
				list.add(type_name);
			} else {
				str.append(" and afin_product_type.type_name = ? ");
				list.add(type_name);
			}
		}
		if (!"".equals(cnName) && cnName != null) {
			str.append(" and afin_product.cn_name LIKE ? ");
			list.add("%" + cnName + "%");
		}
		if (!"".equals(productCode) && productCode != null) {
			str.append(" and afin_boss_pro.product_code LIKE ? ");
			list.add("%" + productCode + "%");
		}
		if (!"undefined".endsWith(syncTimeStart)&&!"".equals(syncTimeStart) && syncTimeStart != null) {
			str.append(" and afin_boss_pro.sync_time >= ? ");
			list.add(syncTimeStart);
		}
		if (!"undefined".endsWith(syncTimeOut)&&!"".equals(syncTimeOut) && syncTimeOut != null) {
			str.append(" and afin_boss_pro.sync_time<= ? ");
			list.add(syncTimeOut);
		}
		if (!"2".equals(state) && state != null) {
			str.append(" and product_state = ? ");
			list.add(state);
		}
		return this.getJdbcTemplate().queryForObject(str.toString(),Integer.class,list.toArray());
	}

	
	/**
	 * 查询指定状态的记录个数
	 * @return
	 */
	public int getCountByState(String state){
		StringBuffer sqlBuffer=new StringBuffer("Select Count(*) from ");
		sqlBuffer.append(TableInfo.afin_admin_boss_pro);
		sqlBuffer.append(" where product_state =?");
		Object[] params=new Object[]{state};
		return this.getJdbcTemplate().queryForInt(sqlBuffer.toString(), params);
	}

	/**
	 * 修改boss的关联状态
	 * @param bossCode
	 * @return
	 */
	public int updateBoss(String bossCode) {
		StringBuffer str = new StringBuffer();
		str.append("update ");
		str.append(TableInfo.afin_admin_boss_pro);
		str.append(" set product_state='0' " );
		str.append(" where product_code = ? ");
		Object[] params=new Object[]{bossCode};
		return this.getJdbcTemplate().update(str.toString(), params);
	}

	/**
	 * 取消关联product
	 * 
	 * @param code
	 * @return
	 */
	public int updateQxglProduct(String code) {
		StringBuffer str = new StringBuffer();
		str.append("update ");
		str.append(TableInfo.afin_admin_product);
		str.append(" set is_connect=1, product_code='',update_time=now() ");
		str.append(" where code = ? ");
		Object[] params=new Object[]{code};
		return this.getJdbcTemplate().update(str.toString(), params);
	}

	/**
	 * 取消关联boss
	 * 
	 * @param bossCode
	 * @return
	 */
	public int updateQxglBoss(String bossCode) {
		StringBuffer str = new StringBuffer();
		str.append("update ");
		str.append(TableInfo.afin_admin_boss_pro);
		str.append(" set product_state='1', sync_time=now() " );
		str.append(" where product_code = ?  ");
		Object[] params=new Object[]{bossCode};
		return this.getJdbcTemplate().update(str.toString(), params);
	}
}
