package com.qtong.afinance.module.pojo.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class OrderCustomerCountRowMapper implements RowMapper<OrderCustomerCount>{

	@Override
	public OrderCustomerCount mapRow(ResultSet rs, int arg1)
			throws SQLException {
		OrderCustomerCount o=new OrderCustomerCount();
		o.setProName(rs.getString("pro_name"));
		o.setCustomerNumber(rs.getString("customer_number"));
    			o.setId(rs.getInt("id"));
    			o.setProductName(rs.getString("product_name"));
    			o.setRecordTime(rs.getTimestamp("record_time"));
		return o;
	}

}
