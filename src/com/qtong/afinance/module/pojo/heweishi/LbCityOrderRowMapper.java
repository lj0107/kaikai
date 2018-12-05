package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LbCityOrderRowMapper implements RowMapper<LbCityOrder>{

	@Override
	public LbCityOrder mapRow(ResultSet rs, int arg1) throws SQLException {
		LbCityOrder lbCityOrder = new LbCityOrder();
		lbCityOrder.setMobile(rs.getString("mobile"));
		lbCityOrder.setProductName(rs.getString("product_name"));
		lbCityOrder.setRequestTime(rs.getTimestamp("request_time"));
		return lbCityOrder;
	}

}
