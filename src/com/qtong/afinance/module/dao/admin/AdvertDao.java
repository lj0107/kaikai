package com.qtong.afinance.module.dao.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.dao.TableInfo;
import com.qtong.afinance.module.pojo.admin.BusinessAdvert;
import com.qtong.afinance.module.pojo.admin.BusinessAdvertRowMapper;

/**
 * 推荐位dao
 *
 *
 */
@Repository
public class AdvertDao extends BaseJdbcDao {

	/**
	 * 推荐位根据id查询
	 * 
	 * @return
	 */
	public BusinessAdvert advertUpdate(String id) {
		StringBuffer stringBuffer = new StringBuffer("select * from ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" where id = ?");
		Object[] params = new Object[] { id };
		BusinessAdvert advert = this.getJdbcTemplate().queryForObject(stringBuffer.toString(),
				new BusinessAdvertRowMapper(), params);
		return advert;
	}

	/**
	 * 查询总数
	 * 
	 * @param advert_address
	 * @return
	 */
	public int getAdvertCount(String advert_address) {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" where advert_states > 0 And advert_address = ?");
		Object[] params = new Object[] { advert_address };
		return  this.getJdbcTemplate().queryForObject(stringBuffer.toString(),Integer.class, params);
	}

	/**
	 * 推荐位上线或者下线
	 * 
	 * @param advert
	 * @return
	 */
	public int updateAdvertStates(String id,String states) {
		StringBuffer stringBuffer = new StringBuffer("update ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" set advert_states = ? ,update_time=now()");
		if("1".equals(states)) {
			stringBuffer.append(" ,online_time=now()");
		}
		stringBuffer.append(" where id = ?");
		Object[] params = new Object[] { states, id};
		return this.getJdbcTemplate().update(stringBuffer.toString(), params);
	}

	/**
	 * 推荐位搜索及列表
	 * 
	 * @param advert
	 * @return
	 */
	public PageData advertFind(String advert_title, String create_time, String custom_time, String advert_states,
			String advert_address, String pageIndex) {
		PageData pageData = new PageData();// 分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		String tableName = TableInfo.afinbusinessAdvert;// 表名
		String fieldSelect = "";
		String fieldOrderName = "advert_sort";
		String fieldOrderDirection = "asc";
		StringBuilder strWhere = new StringBuilder();
		strWhere.append("1=1");
		 List <Object> queryList = new  ArrayList<Object>();
		if (!"".equals(create_time) && create_time != null) {
			strWhere.append(" and create_time >= ? ");
			queryList.add(create_time);
		}
		if (!"".equals(custom_time) && custom_time != null) {
			strWhere.append(" and create_time <= ? ");
			queryList.add(custom_time);
		}
		if ("1".equals(advert_states)) {
			strWhere.append(" and advert_states = ? ");
			queryList.add(advert_states);
		}
		if ("2".equals(advert_states)) {
			strWhere.append(" and advert_states =? ");
			queryList.add(advert_states);
		}
		if ("0".equals(advert_states)) {
			strWhere.append(" and advert_states > ? ");
			queryList.add(advert_states);
		}
		if (!"".equals(advert_title) && advert_title != null) {
			strWhere.append(" and advert_title like ? ");
			queryList.add("%" + advert_title + "%");
		}
		if (!"".equals(advert_address) && advert_address != null) {
			strWhere.append(" and advert_address = ? ");
			queryList.add(advert_address);
		}
		PageData queryPage = this.queryPage(pageData, tableName,"id", fieldSelect, fieldOrderName, fieldOrderDirection,
				strWhere.toString(), queryList.toArray(), new BusinessAdvertRowMapper());
		return queryPage;
	}

	/**
	 * 推荐位删除
	 * 
	 * @param id
	 * @return
	 */
	public int advertDelete(String id) {
		StringBuffer stringBuffer = new StringBuffer("DELETE from ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" where id =?");
		Object[] params = new Object[] { id };
		return this.getJdbcTemplate().update(stringBuffer.toString(),params);
	}

	/**
	 * 推荐位添加
	 * 
	 * @param advert
	 * @return
	 */
	public int addAdvert(BusinessAdvert advert) {
		StringBuffer stringBuffer = new StringBuffer("insert into ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(
				"(id,advert_model,advert_address,advert_size,advert_title,advert_pic,advert_url,advert_sort,create_time,advert_states");
		if ("1".equals(advert.getAdvert_states())) {
			stringBuffer.append(",online_time ");
		}
		stringBuffer.append(" )");
		stringBuffer.append("  values(?,?,?,?,?,?,?,?,?,?");
		if ("1".equals(advert.getAdvert_states())) {
			stringBuffer.append(",now()");
		}
		stringBuffer.append(" )");
		Object[] params = new Object[] { advert.getId(), advert.getAdvert_model(), advert.getAdvert_address(),
				advert.getAdvert_size(), advert.getAdvert_title(), advert.getAdvert_pic(), advert.getAdvert_url(),
				advert.getAdvert_sort(), advert.getCreate_time(), advert.getAdvert_states() };
		return this.getJdbcTemplate().update(stringBuffer.toString(), params);
	}

	/**
	 * 推荐位修改
	 * 
	 * @param advert
	 * @return
	 */
	public int updateAdvert(BusinessAdvert advert) {
		StringBuffer stringBuffer = new StringBuffer("update ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" set advert_model = ? ,advert_address = ?, advert_size = ?,advert_title=?,"
				+ "advert_pic = ?, advert_url = ?,advert_sort = ?,advert_states = ?,update_time=now() ");
		if(advert.getAdvert_states().equals("1")) {
			stringBuffer.append(" ,online_time=now()");
		}
		stringBuffer.append(" where id = ? ");
		Object[] params = new Object[] { advert.getAdvert_model(), advert.getAdvert_address(),
				advert.getAdvert_size(), advert.getAdvert_title(), advert.getAdvert_pic(), 
				advert.getAdvert_url(),advert.getAdvert_sort(), advert.getAdvert_states(),advert.getId()};
		return this.getJdbcTemplate().update(stringBuffer.toString(),params);
	}

	/**
	 * 推荐位判断是否上线五个
	 * @param advert_address
	 * @return
	 */
	public int advertIfFives(String advert_address) {
		StringBuffer stringBuffer = new StringBuffer("select count(1) from ");
		stringBuffer.append(" afin_advert ");
		stringBuffer.append(" where advert_states = 1 and advert_title<>'默认广告' and ISNULL(custom_time) is TRUE And advert_address = ?");
		Object[] params = new Object[] { advert_address };
		return  this.getJdbcTemplate().queryForObject(stringBuffer.toString(),Integer.class, params);
	}

	/**
	 * 查询已上线的条数
	 * @param advert_address
	 * @return
	 */
	public List<Map<String, Object>> advertCount(String advert_address) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select * from afin_advert where advert_states=1 and ISNULL(custom_time) is TRUE and advert_address = ? "
				+ "ORDER BY advert_sort,create_time DESC");
		Object[] params = new Object[] { advert_address };
		return this.getJdbcTemplate().queryForList(stringBuffer.toString(), params);
	}

	/**
	 * 修改状态
	 * @param string
	 * @return
	 */
	public int update(String string) {
		StringBuffer stringBuffer = new StringBuffer("update ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" set advert_states = '2',update_time=now() where id = ? ");
		Object[] params = new Object[] { string };
		return this.getJdbcTemplate().update(stringBuffer.toString(), params);
	}

	/**
	 * 推荐位查询广告
	 * @param advert_address
	 * @return
	 */
	public List<Map<String,Object>> advertFindState(String advert_address) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select * from afin_advert WHERE afin_advert.advert_states='1' "
				+ "and afin_advert.advert_address = ? and advert_title<>'默认广告' and ISNULL(custom_time) is TRUE ORDER BY advert_sort,create_time DESC");
		Object[] params = new Object[] { advert_address };
		try {
			return this.getJdbcTemplate().queryForList(stringBuffer.toString(), params);
		} catch (DataAccessException e) {
			return null;
		}
	}

	/**
	 * 默认广告
	 * @param advert_address
	 * @return
	 */
	public List<Map<String, Object>> findDefault(String advert_address) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select * from afin_advert WHERE advert_title='默认广告' "
				+ "and ISNULL(custom_time) is FALSE and afin_advert.advert_address = ? ");
		Object[] params = new Object[] { advert_address };
		return this.getJdbcTemplate().queryForList(stringBuffer.toString(), params);
	}
	
	/**
	 * 默认广告上线下线
	 * 
	 * @param i
	 * @param onlineId 
	 * @param advert_address 
	 */
	public void defaultOnline(String i, String id) {
		StringBuffer stringBuffer = new StringBuffer("update ");
		stringBuffer.append(TableInfo.afinbusinessAdvert);
		stringBuffer.append(" set advert_states = ? , update_time=now() where id = ? ");
		Object[] params = new Object[] { i,id };
		this.getJdbcTemplate().update(stringBuffer.toString(), params);
	}

	/**
	 * 获取默认广告的id
	 * 
	 * @param advert_address
	 * @return
	 */
	public List<Map<String, Object>> getDefaultOnlineById(String advert_address) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select * from afin_advert WHERE advert_title='默认广告' "
				+ " and custom_time <>'' and afin_advert.advert_address = ? ");
		Object[] params = new Object[] { advert_address };
		return this.getJdbcTemplate().queryForList(stringBuffer.toString(), params);
	}
}
