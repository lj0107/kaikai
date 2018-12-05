package com.qtong.afinance.module.pojo.bigdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 乾坤大数据-授权核验-客户列表RowMapper
 *
 */
public class AuthorizationCheckRowMapper implements RowMapper<AuthorizationCheck>{

	@Override
	public AuthorizationCheck mapRow(ResultSet rs, int arg1) throws SQLException {
		AuthorizationCheck authorizationCheck = new AuthorizationCheck();
		authorizationCheck.setProductOrderId(rs.getString("product_order_id"));
		authorizationCheck.setEffTime(rs.getTimestamp("eff_time"));
		authorizationCheck.setCustomerName(rs.getString("customer_name"));
		authorizationCheck.setProductName(rs.getString("product_name"));
		
		authorizationCheck.setSelCounts((rs.getString("counts")==null?0:rs.getString("counts")).toString());
		return authorizationCheck;
	}
	
}
