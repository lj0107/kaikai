package com.qtong.afinance.module.pojo.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrderStatsRowMapper implements RowMapper<OrderStats>{

	@Override
	public OrderStats mapRow(ResultSet rs, int arg1)
			throws SQLException {
		OrderStats o=new OrderStats();
		o.setId(rs.getInt("id"));
		o.setProductOrderId(rs.getString("product_order_id"));
		o.setProductCode(rs.getString("product_code"));
		o.setProductName(rs.getString("product_name"));
		o.setRecordTime(rs.getTimestamp("record_time"));
		o.setCustomerNumber(rs.getString("customer_number"));
		o.setCount(rs.getInt("count"));
		o.setFee(rs.getDouble("fee"));
		return o;
	}

}
