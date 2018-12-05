package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WhiteListRowMapper implements RowMapper<WhiteList>{

	@Override
	public WhiteList mapRow(ResultSet rs, int arg1) throws SQLException {
		WhiteList whiteList = new WhiteList();
		whiteList.setAccount(rs.getString("account"));
		whiteList.setPwd(rs.getString("pwd"));
		whiteList.setRequestTime(rs.getTimestamp("request_time"));
		whiteList.setCustomerName(rs.getString("customer_name"));
		whiteList.setCustomerNumber(rs.getString("customer_number"));
		whiteList.setCounts(rs.getString("counts"));
		
		
		return whiteList;
	}

}
