package com.qtong.afinance.module.pojo.product;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class AdminProductRowMapper implements RowMapper<AdminProduct> {

	@Override
	public AdminProduct mapRow(ResultSet rs, int arg1) throws SQLException {

		AdminProduct ap = new AdminProduct();
		ap.setCode(rs.getString("code"));
		ap.setCnname(rs.getString("cn_name"));		
		ap.setEnName(rs.getString("en_name"));
		ap.setDescription(rs.getString("description"));
		ap.setIsConnect(rs.getString("is_connect"));		
		ap.setPartnerId(rs.getString("partner_id"));
		ap.setProductCode(rs.getString("product_code"));
		ap.setProTypeId(rs.getString("pro_type_id"));
		ap.setState(rs.getInt("state"));
		ap.setCreateTime(rs.getString("create_time"));
		ap.setUpdateTime(rs.getString("update_time"));
		return ap;
	}

}
