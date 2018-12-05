package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class LbCityAuthorizationRowMapper implements RowMapper<LbCityAuthorization>{

	@Override
	public LbCityAuthorization mapRow(ResultSet rs, int arg1) throws SQLException {
		LbCityAuthorization la = new LbCityAuthorization();
		la.setChanlCustNo(rs.getString("chanl_cust_no"));
		la.setActionCode(rs.getString("action_code"));
		la.setUserName(rs.getString("user_name"));
		la.setPwd(rs.getString("pwd"));
		la.setLogserial(rs.getString("logserial"));
		la.setMobile(rs.getString("mobile"));
		la.setMsgContent(rs.getString("msg_content"));
		la.setProOrdId(rs.getString("pro_ord_id"));
		la.setRequestTime(rs.getTimestamp("request_time"));
		la.setStreamNumber(rs.getString("stream_number"));
		la.setRequestRefId(rs.getString("request_refid"));
		la.setResult(rs.getString("result"));
		la.setCityCode(rs.getString("city_code"));
		la.setCityCodes(rs.getString("citycode"));
		la.setErrorDesc(rs.getString("error_desc"));
		la.setMsgSendCode(rs.getString("msg_send_code"));
		la.setResponseTime(rs.getTimestamp("response_time"));
		return la;
	}

}
