package com.qtong.afinance.module.pojo.record;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class RecordRowMapper implements RowMapper<Record>{
	
	public Record mapRow (ResultSet rs, int arg1) throws SQLException {
		Record record = new Record();
		record.setStreamNumber(rs.getString("stream_number"));
		record.setRecType(rs.getString("rec_type"));
		record.setCustomerNumber(rs.getString("customer_number"));
		record.setOrderId(rs.getString("order_id"));
		record.setProductCode(rs.getString("product_code"));
		record.setBeginTime(rs.getString("begin_time"));
		record.setEndTime(rs.getString("end_time"));
		record.setFee(rs.getInt("fee"));
		record.setPlatformFlowNo(rs.getString("platform_flow_no"));	
		
		return record;
	}

}
