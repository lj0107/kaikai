package com.qtong.afinance.module.pojo.product;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class AdminBossProRowMapper implements RowMapper<AdminBossPro> {

	@Override
	public AdminBossPro mapRow(ResultSet rs, int arg1) throws SQLException {

		AdminBossPro ap = new AdminBossPro();
		ap.setProCode(rs.getString("product_code"));
		ap.setProName(rs.getString("product_package_name"));
		ap.setSubProCode(rs.getString("product_package_code"));
		ap.setSubProName(rs.getString("product_name"));
		ap.setDescription(rs.getString("description"));
		ap.setSyncTime(rs.getString("sync_time"));
		ap.setProductState(rs.getString("product_state"));
		return ap;
	}

}
