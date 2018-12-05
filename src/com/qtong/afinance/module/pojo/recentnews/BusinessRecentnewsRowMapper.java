package com.qtong.afinance.module.pojo.recentnews;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class BusinessRecentnewsRowMapper implements RowMapper<BusinessRecentnews>{
	
	public BusinessRecentnews mapRow (ResultSet rs, int arg1) throws SQLException {
		BusinessRecentnews o = new BusinessRecentnews();
		o.setId(rs.getInt("id"));
    	o.setTitle(rs.getString("title"));//标题    	    	
    	o.setImgUrl(rs.getString("img_url"));
    	o.setSource(rs.getString("source"));
    	o.setContent(rs.getString("content"));
    	o.setStickStatus(rs.getInt("stick_status"));
    	o.setOnlineStatus(rs.getInt("online_status"));
    	o.setOnlineTime(rs.getTimestamp("online_time"));    	
    	o.setCreateTime(rs.getTimestamp("create_time"));
    	o.setUpdateTime(rs.getTimestamp("update_time"));
     	return o;   	
	}
}
