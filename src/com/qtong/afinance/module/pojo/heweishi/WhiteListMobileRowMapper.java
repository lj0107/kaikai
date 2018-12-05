package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WhiteListMobileRowMapper implements RowMapper<WhiteListMobile>{

	@Override
	public WhiteListMobile mapRow(ResultSet rs, int arg1) throws SQLException {
		WhiteListMobile whiteListMobile = new WhiteListMobile();
		whiteListMobile.setMobile(rs.getString("mobile"));
		whiteListMobile.setInsertTime(rs.getTimestamp("insert_time"));
		
		return whiteListMobile;
	}
}
