package com.qtong.afinance.module.pojo.product;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AdminOrderSubRowMapper implements RowMapper<AdminOrderSub> {

	@Override
	public AdminOrderSub mapRow(ResultSet rs, int arg1) throws SQLException {
		AdminOrderSub ap = new AdminOrderSub();
		ap.setSubOrderNum(rs.getString("sub_order_num"));
		ap.setProductOrderId(rs.getString("product_order_id"));
		ap.setProductOrderNumber(rs.getString("product_order_number"));
		ap.setProCode(rs.getString("pro_code"));
		ap.setProName(rs.getString("pro_name"));
		ap.setEffTime(rs.getTimestamp("eff_time"));
		ap.setState(rs.getInt("state"));
		ap.setUpdateTime(rs.getTimestamp("update_time"));
		ap.setPrice(rs.getDouble("price"));
		ap.setDiscount(rs.getDouble("discount"));
		ap.setSum(rs.getDouble("sum"));
		ap.setRatio(rs.getString("ratio"));
		ap.setReserveRatio(rs.getString("reserve_ratio"));
		try {
			if (rs.findColumn("name") > 0) {
				ap.setName(rs.getString("name"));
			}
		} catch (Exception e) {
		}
		try {
			if (rs.findColumn("product_name") > 0) {
				ap.setProductName(rs.getString("product_name"));
			}
		} catch (Exception e) {
		}
		try {
			if (rs.findColumn("contract_name") > 0) {
				ap.setContractName(rs.getString("contract_name"));
			}
		} catch (Exception e) {
		}
		try {
			if (rs.findColumn("contract_word_url") > 0) {
				ap.setContractWordUrl(rs.getString("contract_word_url"));
			}
		} catch (Exception e) {
		}
		try {
			if (rs.findColumn("contract_pdf_url") > 0) {
				ap.setContractPdfUrl(rs.getString("contract_pdf_url"));
			}
		} catch (Exception e) {
		}
		try {
			if (rs.findColumn("type_name") > 0) {
				ap.setTypeName(rs.getString("type_name"));
			}
		} catch (Exception e) {
		}

		return ap;
	}

}
