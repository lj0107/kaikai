package com.qtong.afinance.module.pojo.interf;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;


public class ProductRowMapper implements RowMapper<Product>{

	@Override
	public Product mapRow(ResultSet rs, int arg1) throws SQLException {
		Product ll = new Product();
		
		ll.setProductCode(rs.getString("product_code"));
		ll.setSecretId(rs.getString("secret_id"));
		ll.setSecretKey(rs.getString("secret_key"));
		ll.setInterfaceUrl(rs.getString("interface_url"));	
		ll.setId(rs.getInt("id"));
		ll.setCreateTime(rs.getTimestamp("create_time"));	
		
		return ll;
	}

}
