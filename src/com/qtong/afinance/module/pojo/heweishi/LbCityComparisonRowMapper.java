package com.qtong.afinance.module.pojo.heweishi;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
/**
 * 位置基地-城市编码比对RowMapper
 *
 */
public class LbCityComparisonRowMapper implements RowMapper<LbCityComparison>{

	@Override
	public LbCityComparison mapRow(ResultSet rs, int arg1) throws SQLException {
		LbCityComparison lbCityComparison = new LbCityComparison();
		lbCityComparison.setChanlCustNo(rs.getString("chanl_cust_no"));
		lbCityComparison.setActionCode(rs.getString("action_code"));
		lbCityComparison.setLogserial(rs.getString("logserial"));
		lbCityComparison.setUserName(rs.getString("user_name"));
		lbCityComparison.setPwd(rs.getString("pwd"));
		lbCityComparison.setMobile(rs.getString("mobile"));
		lbCityComparison.setMsgContent(rs.getString("msg_content"));
		lbCityComparison.setProOrdId(rs.getString("pro_ord_id"));
		lbCityComparison.setRequestTime(rs.getTimestamp("request_time"));
		
		return lbCityComparison;
	}

}
