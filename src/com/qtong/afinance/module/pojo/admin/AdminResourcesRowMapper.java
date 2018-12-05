package com.qtong.afinance.module.pojo.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdminResourcesRowMapper implements RowMapper<AdminResources>{

	@Override
	public AdminResources mapRow(ResultSet arg0, int arg1) throws SQLException {
		AdminResources r=new AdminResources();
		r.setId(arg0.getInt("id"));
		r.setName(arg0.getString("name"));
		r.setLev(arg0.getInt("lev"));
		r.setParentId(arg0.getString("parent_id"));
		r.setType(arg0.getString("type"));
		r.setUrl(arg0.getString("url"));
		r.setOrd(arg0.getInt("ord"));
		r.setCreator(arg0.getString("creator"));
		r.setCreateTime(arg0.getTimestamp("create_time"));
		r.setUpdateTime(arg0.getTimestamp("update_time"));
		r.setState(arg0.getInt("state"));
		r.setDescription(arg0.getString("description"));
		r.setMark(arg0.getInt("mark"));
		
		return r;
	}

}
