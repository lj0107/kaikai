package com.qtong.afinance.module.pojo.product;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class AdminOrderRowMapper implements RowMapper<AdminOrder> {

	@Override
	public AdminOrder mapRow(ResultSet rs, int arg1) throws SQLException {

		AdminOrder ap = new AdminOrder();
		ap.setProductOrderId(rs.getString("product_order_id"));
		ap.setProductNumber(rs.getString("product_number"));
		ap.setCustomerNumber(rs.getString("customer_number"));		
		ap.setCustomerName(rs.getString("customer_name"));
		ap.setProductName(rs.getString("product_name"));
		ap.setEffTime(rs.getTimestamp("eff_time"));
		ap.setExpTime(rs.getTimestamp("exp_time"));		
		ap.setBillEffTime(rs.getTimestamp("bill_eff_time"));
		ap.setState(rs.getInt("state"));
		ap.setContractName(rs.getString("contract_name"));
		ap.setContractPdfUrl(rs.getString("contract_pdf_url"));
		ap.setContractWordUrl(rs.getString("contract_word_url"));
		ap.setProductRatio(rs.getInt("product_ratio"));
		ap.setParentOrderName(rs.getString("parent_order_name"));
		//ap.setProTypeId(rs.getString("pro_type_id"));
		return ap;
	}
}
