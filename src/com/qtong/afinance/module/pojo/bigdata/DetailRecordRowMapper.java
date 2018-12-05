package com.qtong.afinance.module.pojo.bigdata;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DetailRecordRowMapper implements RowMapper<DetailRecord>{
	
	public DetailRecord mapRow (ResultSet rs, int arg1) throws SQLException {
		DetailRecord dr = new DetailRecord();
		dr.setAuthCode(rs.getString("auth_code"));
		dr.setFee(rs.getDouble("fee"));
		dr.setIsValid(rs.getInt("is_valid"));
		dr.setRequestRefId(rs.getString("request_refid"));
		dr.setStreamNumber(rs.getString("stream_number"));
		dr.setProductCode(rs.getString("product_code"));
		dr.setProductName(rs.getString("product_name"));
		dr.setUsermark(rs.getString("usermark"));
		dr.setUsermarkValue(rs.getString("usermark_value"));
		
		dr.setResponseCode(rs.getString("response_code"));
		dr.setResponseMsg(rs.getString("response_msg"));
		dr.setResponseRefid(rs.getString("response_refid"));
		dr.setResponseTime(rs.getTimestamp("response_time"));
		try {
			if(rs.findColumn("request_time")>0) {
				dr.setRequestTime(rs.getTimestamp("request_time"));
			}
			if(rs.findColumn("mobile")>0) {
				dr.setMobile(rs.getString("mobile"));
			}
			if(rs.findColumn("signature")>0) {
				dr.setSignature(rs.getString("signature"));
			}
		} catch (Exception e) {
		}
		return dr;
	}

}
