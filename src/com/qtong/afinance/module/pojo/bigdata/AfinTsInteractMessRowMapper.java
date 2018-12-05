package com.qtong.afinance.module.pojo.bigdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AfinTsInteractMessRowMapper implements RowMapper<AfinTsInteractMess>{
	
	public AfinTsInteractMess mapRow (ResultSet rs, int arg1) throws SQLException {

		AfinTsInteractMess aim = new AfinTsInteractMess();
    	aim.setRequestTime(rs.getTimestamp("request_time"));		
		aim.setRequestRefId(rs.getString("request_refid"));
		aim.setSecretId(rs.getString("secret_id"));
		aim.setSignature(rs.getString("signature"));
		aim.setMobile(rs.getString("mobile"));
		aim.setCertNo(rs.getString("cert_no"));
		aim.setName(rs.getString("name"));
		aim.setCertType(rs.getString("cert_type"));		
		aim.setAuthCode(rs.getString("auth_code"));
		aim.setProductPackageOrdId(rs.getString("product_package_ord_id"));
		return aim;   	
	}

}
