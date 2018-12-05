package com.qtong.afinance.module.pojo.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 角色映射
 */
public class AdminRoleRowMapper implements RowMapper<AdminRole>{

	@Override
	public AdminRole mapRow(ResultSet arg0, int arg1) throws SQLException {
		AdminRole r=new AdminRole();
		r.setId(arg0.getInt("id"));
		r.setName(arg0.getString("name"));
		r.setParentId(arg0.getString("parent_id"));
		r.setDescription(arg0.getString("description"));
		r.setCreator(arg0.getString("creator"));
		r.setCreateTime(arg0.getTimestamp("create_time"));
		r.setUpdateTime(arg0.getTimestamp("update_time"));
		r.setState(arg0.getInt("state"));
		return r;
	}

}
