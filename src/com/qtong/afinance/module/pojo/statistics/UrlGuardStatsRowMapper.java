package com.qtong.afinance.module.pojo.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UrlGuardStatsRowMapper implements RowMapper<UrlGuardStats>{

	@Override
	public UrlGuardStats mapRow(ResultSet rs, int arg1) throws SQLException {
		UrlGuardStats stats=new UrlGuardStats();
		stats.setId(rs.getInt("id"));
		stats.setCustomerNumber(rs.getString("customer_number"));
		stats.setState(rs.getString("state"));
		stats.setRecordTime(rs.getTimestamp("record_time"));
		stats.setCount(rs.getInt("count"));
		
		return stats;
	}

}