package com.qtong.afinance.module.pojo.bill;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BillRowMapper implements RowMapper<Bill>{

	@Override
	public Bill mapRow(ResultSet rs, int arg1) throws SQLException {
		Bill bill = new Bill();
		try {
			if(rs.findColumn("bill_number")>0) {
				bill.setBillNumber(rs.getString("bill_number"));
			}
		}catch (Exception e) {
			}
		try {
			if (rs.findColumn("bill_time")>0) {
					bill.setBillTime(rs.getTimestamp("bill_time"));
			}	
		} catch (Exception e) {
		}
		
		bill.setProductOrderId(rs.getString("product_order_id"));
		bill.setOrderTime(rs.getTimestamp("order_time"));
		bill.setCustomerNumber(rs.getString("customer_number"));
		bill.setCustomerName(rs.getString("customer_name"));
		bill.setProductName(rs.getString("product_name"));
		bill.setFee(rs.getDouble("fee"));
		return bill;
	}

}
