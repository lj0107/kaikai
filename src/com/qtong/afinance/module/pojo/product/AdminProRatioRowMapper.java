package com.qtong.afinance.module.pojo.product;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

public class AdminProRatioRowMapper implements RowMapper<AdminProRatio> {

	@Override
	public AdminProRatio mapRow(ResultSet rs, int arg1) throws SQLException {

		AdminProRatio ap = new AdminProRatio();
		ap.setId(rs.getInt("id"));
		ap.setSubOrderNum(rs.getString("sub_order_num"));
		ap.setOperState(rs.getString("oper_state"));
		ap.setRatioBefore(rs.getString("ratio_before"));
		ap.setRatioAfter(rs.getString("ratio_after"));
		ap.setApplyUser(rs.getString("apply_user"));
		ap.setConfirmUser(rs.getString("confirm_user"));
		ap.setApplyTime(rs.getTimestamp("apply_time"));
		ap.setConfirmTime(rs.getTimestamp("confirm_time"));
		ap.setUpdateTime(rs.getTimestamp("update_time"));
		ap.setSuggest(rs.getString("suggest"));
		ap.setState(rs.getString("state"));			
		ap.setOptMatter(rs.getString("opt_matter"));			
		
		return ap;
	}

}
