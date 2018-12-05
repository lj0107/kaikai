package com.qtong.afinance.module.dao.admin;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;
import com.qtong.afinance.core.dao.BaseJdbcDao;
import com.qtong.afinance.core.dao.PageData;
import com.qtong.afinance.core.util.SqlHelper;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnews;
import com.qtong.afinance.module.pojo.recentnews.BusinessRecentnewsRowMapper;
import java.sql.Connection;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.mysql.jdbc.Statement;
/**
 * 门户-最新动态
 */
@Repository("adminBusinessRecentnewsDao")
public class BusinessRecentnewsDao extends BaseJdbcDao {
	/**
	 * 增加
	 * @param coPartner
	 * @return
	 */
	public int insertCoPartner(final BusinessRecentnews br) {         
        KeyHolder keyHolder = new GeneratedKeyHolder();
		this.getJdbcTemplate().update( 
	         new PreparedStatementCreator(){
                   @Override
                   public PreparedStatement createPreparedStatement(Connection conn) throws SQLException{
                	   StringBuffer sqlBuffer=new StringBuffer("insert into afin_news");
           			   sqlBuffer.append("(title,source,img_url,content,stick_status,online_status,online_time,create_time) values(?,?,?,?,?,?,?,?)");
           			   
                	   PreparedStatement ps = conn.prepareStatement(sqlBuffer.toString(), Statement.RETURN_GENERATED_KEYS);
                       ps.setString(1, br.getTitle());
                       ps.setString(2, br.getSource());
                       ps.setString(3, br.getImgUrl());
                       ps.setString(4, br.getContent());
                       ps.setInt(5, br.getStickStatus());
                       ps.setInt(6, br.getOnlineStatus());
                       ps.setTimestamp(7, br.getOnlineTime());
                       ps.setTimestamp(8, br.getCreateTime());
                       return ps;
                    }
             },keyHolder
		 );
		return keyHolder.getKey().intValue();//afin_news表中的id是int类型的！！！
	}
	/**
	 * 通过id删除
	 * @param coPartner
	 * @return
	 */
	public int deleteById(int id) {
		StringBuffer sqlBuffer=new StringBuffer("delete from afin_news ");
		//sqlBuffer.append(TableInfo.afin_news);
		sqlBuffer.append(" where id=?");
		Object[] params = new Object[]{id};
        return this.getJdbcTemplate().update(sqlBuffer.toString(),params);
	}
	/**
	 * 批量删除
	 * @param coPartner
	 * @return
	 */
	public int deleteAll(final List id) {
		StringBuffer sb=new StringBuffer("delete from  afin_news ");
		sb.append(" where id=?"); 	
		this.getJdbcTemplate().batchUpdate(sb.toString(), new BatchPreparedStatementSetter() {			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(id.get(i).toString()));		
			}					
			@Override
			public int getBatchSize() {
				return id.size();
			}
		});
		return 1;
	}
	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	public BusinessRecentnews selectCoPById(int id) {
		StringBuffer sqlBuffer=new StringBuffer("select * from afin_news ");
		sqlBuffer.append(" where id=?");
        Object[] params = new Object[]{id};	
        BusinessRecentnews businessRecentnews = this.getJdbcTemplate().queryForObject(sqlBuffer.toString(),new BusinessRecentnewsRowMapper(),params);	
	   return businessRecentnews;
	}
	/**
	 * 更改
	 * @param coPartner
	 * @return
	 */
	public int updateRecentNews(BusinessRecentnews br) {
		StringBuffer sqlBuffer=new StringBuffer("update afin_news ");
		sqlBuffer.append(" set title=?,source=?,img_url=?,content=?,stick_status=?,online_status=?,online_time=? where id=?");
		Object[] params = new Object[]{br.getTitle(),br.getSource(),br.getImgUrl(),br.getContent(),br.getStickStatus(),br.getOnlineStatus(),br.getOnlineTime(),br.getId()};
		return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
	}
	/**
	 * 更改上线状态
	 * @param businessRecentnews
	 * @return
	 */
	public int updateState(BusinessRecentnews br) {
		StringBuffer sqlBuffer=new StringBuffer("update afin_news ");
		sqlBuffer.append(" set online_status=?,online_time=? where id=?");
			Object[] params = new Object[]{br.getOnlineStatus(),br.getOnlineTime(),br.getId()};
       return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
		}
	/**
	 * 更改置顶状态状态
	 * @param businessRecentnews
	 * @return
	 */
	public int updateStick(BusinessRecentnews br) {
		StringBuffer sqlBuffer=new StringBuffer("update afin_news ");
		sqlBuffer.append(" set stick_status=? where id=?");
			Object[] params = new Object[]{br.getStickStatus(),br.getId()};
       return this.getJdbcTemplate().update(sqlBuffer.toString(), params);
		}
	
	/**
	 * 根据条件查询
	 * @param byKeyword 关键字模糊查询
	 * @param stickStatus 置顶状态
	 * @param onlineStatus 上线状态
	 * 根据发布时间查询：
	 * @param firstTime 开始时间
	 * @param secondTime 结束时间
	 * @return
	 */
	public PageData selectByTermList(String pageIndex,String byKeyword,String stickStatus,String onlineStatus,
			                          String firstTime,String secondTime,String onBeginTime,String onEndTime) {
		
		PageData pageData = new PageData();//分页的工具类
		pageData.setPageIndex(Integer.parseInt(pageIndex));
		
		
		SqlHelper sqlHelper=new SqlHelper("afin_news")//
								.addConditionLike(!"".equals(byKeyword)&&byKeyword!=null, "title like ?", byKeyword)//
								.addCondition(!"".equals(stickStatus)&&stickStatus!=null, "stick_status=?", stickStatus)//
								.addCondition(!"".equals(onlineStatus)&&onlineStatus!=null, "online_status=?", onlineStatus)//
								.addCondition(!"".equals(firstTime)&&firstTime!=null, "create_time>=?", firstTime)//
								.addCondition(!"".equals(secondTime)&&secondTime!=null, "create_time<=?", secondTime)//
								.addCondition(!"".equals(onBeginTime)&&onBeginTime!=null, "online_time>=?", onBeginTime)//
								.addCondition(!"".equals(onEndTime)&&onEndTime!=null, "online_time<=?", onEndTime)//
								.addOrder("create_time",false);
		PageData queryPage = this.queryPage(pageData,sqlHelper, new BusinessRecentnewsRowMapper());		       			
		return queryPage;	
	}
	
}


