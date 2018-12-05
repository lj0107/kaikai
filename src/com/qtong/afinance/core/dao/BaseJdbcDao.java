package com.qtong.afinance.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.util.SqlHelper;


@Repository
public class BaseJdbcDao {

    final Logger log = LoggerFactory.getLogger(getClass());
    private JdbcTemplate jdbcTemplate;
    private JdbcTemplate postgreJdbcTemplate;
    
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    
    public JdbcTemplate getPostgreJdbcTemplate() {
		return postgreJdbcTemplate;
	}
    @Autowired
	public void setPostgreJdbcTemplate(JdbcTemplate postgreJdbcTemplate) {
		this.postgreJdbcTemplate = postgreJdbcTemplate;
	}

	/**
     * 
     * @param pageData
     * @param tableName 表名
     * @param primaryKey 主键
     * @param fieldSelect 可为空
     * @param strWhere where条件
     * @param params 参数
     * @param rowMapper
     * @return
     */
    public PageData queryPage(PageData pageData,String tableName,String primaryKey,String fieldSelect,String strWhere,Object[] params,RowMapper<?> rowMapper) {
    	return queryPage(pageData,tableName,primaryKey,fieldSelect,null,null,strWhere,params,rowMapper);
    }
    /**
     * 公用分页dao方法
     * 
     * @param pageData
     * @param tableName 表名
     * @param primaryKey 主键
     * @param fieldSelect 可为空
     * @param fieldOrderName 根据哪个字段排序
     * @param fieldOrderDirection 升序asc 降序desc
     * @param strWhere where条件
     * @param params 参数
     * @param rowMapper
     * @return
     */
    public PageData queryPage(PageData pageData,String tableName,String primaryKey,String fieldSelect,String fieldOrderName,String fieldOrderDirection,String strWhere,Object[] params,RowMapper<?> rowMapper) {
    	
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		
		sql.append("select t1.* from ").append(tableName).append(" t1 inner join ( select ")//inner join 提高查询效率
			.append(primaryKey).append(" from ").append(tableName);
		sqlCount.append("select count(1) from ").append(tableName).append(" t1");
		
		if (strWhere!=null && !strWhere.isEmpty()) {
			sql.append(" where ");
			sql.append(strWhere);
//			sql.append(" and ");
			
			sqlCount.append(" where ").append(strWhere);
		}
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,params).intValue());
		

		if (fieldOrderName!=null && !fieldOrderName.isEmpty()) {
			sql.append(" order by ").append(fieldOrderName);
			if (fieldOrderDirection!=null && !fieldOrderDirection.isEmpty()) {
				sql.append(" ").append(fieldOrderDirection);
			}
		}


		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(",").append(pageData.getPageSize()).append(") t2 on t1."+primaryKey+"=t2."+primaryKey+"");

//		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		//注释的原SQL语句因为有子查询，且同样使用params传过来的参数，这样在使用此SQL时，参数就是翻倍，所以设置param!
		Object[] param = new Object[params.length*2];
		System.arraycopy(params, 0, param, 0, params.length);
		System.arraycopy(params, 0, param, params.length, params.length);
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), params, rowMapper));

		return pageData;
    }
    public PageData queryPageNew(PageData pageData,String tableName,String primaryKey,String fieldSelect,String fieldOrderName,String fieldOrderDirection,String strWhere,Object[] params,RowMapper<?> rowMapper) {
    	
		StringBuilder sql = new StringBuilder();
		StringBuilder sqlCount = new StringBuilder();
		
		sql.append("select t2.* from ").append(tableName).append(" t1 inner join ( select ")//inner join 提高查询效率
			.append(" * from ").append(tableName);
		sqlCount.append("select count(1) from ").append(tableName).append(" t1");
		
		if (strWhere!=null && !strWhere.isEmpty()) {
			sql.append(" where ");
			sql.append(strWhere);
//			sql.append(" and ");
			
			sqlCount.append(" where ").append(strWhere);
		}
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount.toString(),Integer.class,params).intValue());
		

		if (fieldOrderName!=null && !fieldOrderName.isEmpty()) {
			sql.append(" order by ").append(fieldOrderName);
			if (fieldOrderDirection!=null && !fieldOrderDirection.isEmpty()) {
				sql.append(" ").append(fieldOrderDirection);
			}
		}


		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(",").append(pageData.getPageSize()).append(") t2 on t1."+primaryKey+"=t2."+primaryKey+"");

//		sql.append(" LIMIT ").append(((pageData.getPageIndex()-1)*pageData.getPageSize())).append(" , ").append(pageData.getPageSize());
		
		//注释的原SQL语句因为有子查询，且同样使用params传过来的参数，这样在使用此SQL时，参数就是翻倍，所以设置param!
		Object[] param = new Object[params.length*2];
		System.arraycopy(params, 0, param, 0, params.length);
		System.arraycopy(params, 0, param, params.length, params.length);
		
		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), params, rowMapper));
		return pageData;
    }

    /**
     * 公用分页dao方法
     * @param pageData
     * @param sqlHelper
     * @param rowMapper
     * @return
     */
    public PageData queryPage(PageData pageData,SqlHelper sqlHelper,RowMapper<?> rowMapper) {
    	
		String sql = sqlHelper.getQueryListSql();
		String sqlCount = sqlHelper.getQueryCountSql();
		Object [] params=sqlHelper.getParameters();
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount,Integer.class,params).intValue());
		
		sql+=" LIMIT "+(((pageData.getPageIndex()-1)*pageData.getPageSize()))+","+pageData.getPageSize();

		pageData.setLst(this.getJdbcTemplate().query(sql.toString(), params, rowMapper));

		return pageData;
    } 
    
    
    
    /**
     * 公用分页dao方法
     * @param pageData
     * @param sqlHelper
     * @param rowMapper
     * @return
     */
    public PageData queryPage(PageData pageData,SqlHelper sqlHelper) {
    	
		String sql = sqlHelper.getQueryListSql();
		String sqlCount = sqlHelper.getQueryCountSql();
		Object [] params=sqlHelper.getParameters();
		
		pageData.setRowCount(this.getJdbcTemplate().queryForObject(sqlCount,Integer.class,params).intValue());
		
		sql+=" LIMIT "+(((pageData.getPageIndex()-1)*pageData.getPageSize()))+","+pageData.getPageSize();

		pageData.setLst(this.getJdbcTemplate().queryForList(sql.toString(), params));

		return pageData;
    } 
    
    
}
