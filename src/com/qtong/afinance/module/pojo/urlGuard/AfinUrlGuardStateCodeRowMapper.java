package com.qtong.afinance.module.pojo.urlGuard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 网址卫士状态码
 *
 */
public class AfinUrlGuardStateCodeRowMapper implements RowMapper<AfinUrlGuardStateCode> {

	@Override
	public AfinUrlGuardStateCode mapRow(ResultSet rs, int arg1) throws SQLException {
		AfinUrlGuardStateCode afinUrlGuardStateCode = new AfinUrlGuardStateCode();
		afinUrlGuardStateCode.setCode(rs.getString("code"));
		afinUrlGuardStateCode.setDisplay(rs.getString("display"));
		afinUrlGuardStateCode.setDescription(rs.getString("description"));
		return afinUrlGuardStateCode;
	}

}
