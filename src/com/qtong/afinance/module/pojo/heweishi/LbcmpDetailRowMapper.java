package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LbcmpDetailRowMapper implements RowMapper<LbcmpDetail>{

	@Override
	public LbcmpDetail mapRow(ResultSet rs, int arg1) throws SQLException {
		LbcmpDetail lbcmpDetail = new LbcmpDetail();
		lbcmpDetail.setStreamNumber(rs.getString("stream_number"));
		lbcmpDetail.setActionCode(rs.getString("action_code"));
		lbcmpDetail.setRequestRefId(rs.getString("request_refid"));
		lbcmpDetail.setResult(rs.getString("result"));
		lbcmpDetail.setCityCode(rs.getString("city_code"));
		lbcmpDetail.setErrorDesc(rs.getString("error_desc"));
		lbcmpDetail.setMsgSendCode(rs.getString("msg_send_code"));
		lbcmpDetail.setResponseTime(rs.getTimestamp("response_time"));
		lbcmpDetail.setProductCode(rs.getString("product_code"));
		lbcmpDetail.setProductName(rs.getString("product_name"));
		lbcmpDetail.setFee(rs.getDouble("fee"));
		
		try {
			if(rs.findColumn("request_time")>0) {
				lbcmpDetail.setRequestTime(rs.getTimestamp("request_time"));
			}
		} catch (Exception e) {
		}
		
		return lbcmpDetail;
	}

}
