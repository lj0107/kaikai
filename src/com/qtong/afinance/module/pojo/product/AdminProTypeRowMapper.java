package com.qtong.afinance.module.pojo.product;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class AdminProTypeRowMapper implements RowMapper<AdminProType> {

	@Override
	public AdminProType mapRow(ResultSet rs, int arg1) throws SQLException {

		AdminProType ap = new AdminProType();
		ap.setPackageCode(rs.getString("package_code"));
		ap.setLev(rs.getString("lev"));
		ap.setTypeName(rs.getString("type_name"));		
		ap.setParentId(rs.getString("parent_id"));
		ap.setParentName(rs.getString("parent_name"));
		ap.setCreate_time(rs.getString("create_time"));
		ap.setUpdate_time(rs.getString("update_time"));
		ap.setUser_id(rs.getString("user_id"));
		ap.setTypeState(rs.getString("type_state"));
		return ap;
	}

}
