package com.qtong.afinance.module.pojo.urlGuard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AfinUrlGuardCodeRowMapper implements RowMapper<AfinUrlGuardCode>{

	@Override
	public AfinUrlGuardCode mapRow(ResultSet rs, int arg1) throws SQLException {
		AfinUrlGuardCode afinUrlGuardCode = new AfinUrlGuardCode();
		afinUrlGuardCode.setUrl(rs.getString("url"));
		afinUrlGuardCode.setCounterfeitDomain(rs.getString("counterfeit_domain"));
		afinUrlGuardCode.setSnapshot(rs.getString("snapshot"));
		afinUrlGuardCode.setCustomerNo(rs.getString("customer_no"));
		afinUrlGuardCode.setCustomerName(rs.getString("customer_name"));
		afinUrlGuardCode.setCheckTime(rs.getString("check_time"));
		afinUrlGuardCode.setInterceptCount(rs.getInt("intercept_count"));
		afinUrlGuardCode.setInvalidTime(rs.getString("invalid_time"));
		afinUrlGuardCode.setSiteState(rs.getString("site_state"));
		afinUrlGuardCode.setServerIp(rs.getString("server_ip"));
		afinUrlGuardCode.setServerLocation(rs.getString("server_location"));
		afinUrlGuardCode.setRegisterPerson(rs.getString("register_person"));
		afinUrlGuardCode.setRegisterMai(rs.getString("register_mail"));
		afinUrlGuardCode.setRegisterPhone(rs.getString("register_phone"));
		afinUrlGuardCode.setPartnerNo(rs.getString("partner_no"));
		afinUrlGuardCode.setPartnerName(rs.getString("partner_name"));
		afinUrlGuardCode.setPushTime(rs.getString("push_time"));
		afinUrlGuardCode.setState(rs.getString("state"));
		afinUrlGuardCode.setOptTime(rs.getString("opt_time"));
		afinUrlGuardCode.setOptId(rs.getString("opt_id"));
		afinUrlGuardCode.setOptName(rs.getString("opt_name"));
		afinUrlGuardCode.setCode(rs.getString("code"));
		afinUrlGuardCode.setDisplay(rs.getString("display"));
		afinUrlGuardCode.setDescription(rs.getString("description"));
		return afinUrlGuardCode;
	}

}
