package com.qtong.afinance.module.dao.portal;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnewsRowMapper;

/**
 * 门户-最新动态
 */
@Repository("portalBusinessRecentnewsDao")
public class NewsDao extends BaseJdbcDao {
	/**
	 * 门户-最新动态查询  getRecentNewsDetails1
	 * @author mh
	 */
	public PageData getRecentNewsList(String count) {	
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(count));
		String tableName=" afin_news ";//表名
		String fieldSelect="";
		String fieldOrderName="stick_status=1,online_time";
		String fieldOrderDirection="desc";	
		StringBuilder strWhere=new StringBuilder();
		strWhere.append(" online_status=0 ");
		Object[] params = new Object[]{};
		PageData queryPage = this.queryPageNew(pageData,tableName,"id",fieldSelect,fieldOrderName,fieldOrderDirection,strWhere.toString(),params, new BusinessRecentnewsRowMapper());		       			
	    return queryPage;
}
	
	/**
	 * 门户-最新动态查询  getRecentNewsDetails1
	 * @author mh
	 */
	public List<BusinessRecentnews> getRecentNewsList() {	
		
		
		
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(" afin_news where online_status=0 ");
        List<BusinessRecentnews> list = this.getJdbcTemplate().query(sqlBuffer.toString(),new BusinessRecentnewsRowMapper());	
        return list;
}
	
	/**
	 * 首页-最新动态（前三条）
	 * @author mh
	 */
	public List<BusinessRecentnews> getRecentNewsDetails1() {			  
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(" afin_news ");
		sqlBuffer.append("  where online_status=?  order BY stick_status,online_time desc limit 0,3");
		Object[] params = new Object[]{0};
        List<BusinessRecentnews> list = this.getJdbcTemplate().query(sqlBuffer.toString(),new BusinessRecentnewsRowMapper(),params);	
	return list;
}
	
	/**
	 * 门户-最新动态详情
	 * @author mh
	 */	
	public BusinessRecentnews getRecentNewsDetails(int id) {			  
		StringBuffer sqlBuffer=new StringBuffer("select * from ");
		sqlBuffer.append(" afin_news ");
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};	
        BusinessRecentnews list = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new BusinessRecentnewsRowMapper(),params);	
	return list;
}
}	


