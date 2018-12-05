package com.qtong.afinance.module.pojo.urlGuard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 网址卫士信息主表
 *
 */
public class AfinUrlGuardRowMapper implements RowMapper<AfinUrlGuard> {

	@Override
	public AfinUrlGuard mapRow(ResultSet rs, int arg1) throws SQLException {
		AfinUrlGuard afinUrlguard = new AfinUrlGuard();
		afinUrlguard.setUrl(rs.getString("url"));
		afinUrlguard.setCounterfeitDomain(rs.getString("counterfeit_domain"));
		afinUrlguard.setSnapshot(rs.getString("snapshot"));
		afinUrlguard.setCustomerNo(rs.getString("customer_no"));
		afinUrlguard.setCustomerName(rs.getString("customer_name"));
		afinUrlguard.setCheckTime(rs.getString("check_time"));
		afinUrlguard.setInterceptCount(rs.getInt("intercept_count"));
		afinUrlguard.setInvalidTime(rs.getString("invalid_time"));
		afinUrlguard.setSiteState(rs.getString("site_state"));
		afinUrlguard.setServerIp(rs.getString("server_ip"));
		afinUrlguard.setServerLocation(rs.getString("server_location"));
		afinUrlguard.setRegisterPerson(rs.getString("register_person"));
		afinUrlguard.setRegisterMail(rs.getString("register_mail"));
		afinUrlguard.setRegisterPhone(rs.getString("register_phone"));
		afinUrlguard.setPartnerNo(rs.getString("partner_no"));
		afinUrlguard.setPartnerName(rs.getString("partner_name"));
		afinUrlguard.setPushTime(rs.getString("push_time"));
		afinUrlguard.setState(rs.getString("state"));
		afinUrlguard.setOptTime(rs.getString("opt_time"));
		afinUrlguard.setOptId(rs.getString("opt_id"));
		afinUrlguard.setOptName(rs.getString("opt_name"));
		return afinUrlguard;
	}

}
