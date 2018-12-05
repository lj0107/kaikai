package com.qtong.afinance.module.pojo.statistics;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RequestCountsRowMapper implements RowMapper<RequestCounts>{
	@Override
	public RequestCounts mapRow(ResultSet rs, int arg1)
			throws SQLException {
		RequestCounts r=new RequestCounts();
		r.setId(rs.getInt("id"));
		r.setProductOrderId(rs.getString("product_order_id"));
		r.setRecordTime(rs.getTimestamp("record_time"));
		r.setCount(rs.getInt("count"));
		return r;
	}

}
