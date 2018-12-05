package com.qtong.afinance.module.pojo.interf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;


public class InterfaceRowMapper implements RowMapper<Interface>{

	@Override
	public Interface mapRow(ResultSet rs, int arg1) throws SQLException {
		Interface la = new Interface();
		la.setAfinAccount(rs.getString("afin_account"));
		la.setAfinKey(rs.getString("afin_key"));
		la.setInterfaceName(rs.getString("interface_name"));
		la.setInterfaceUrl(rs.getString("interface_url"));
		la.setPartnerName(rs.getString("partner_name"));
		la.setPartnerId(rs.getInt("partner_id"));
		la.setServiceCode(rs.getString("service_code"));
		la.setCustomerNumber(rs.getString("customer_number"));
		la.setCustomerName(rs.getString("customer_name"));
		la.setSecretId(rs.getString("secret_id"));
		la.setSecretKey(rs.getString("secret_key"));
		la.setAccountType(rs.getString("account_type"));
		la.setState(rs.getInt("state"));
		la.setId(rs.getInt("id"));
		la.setProductCode(rs.getString("product_code"));
		la.setValue(rs.getInt("value"));
		la.setCreateTime(rs.getTimestamp("create_time"));
		
		
		return la;
	}

}
