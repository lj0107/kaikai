package com.qtong.afinance.module.pojo.urlGuard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 网址卫士操作日志表
 *
 */
public class AfinUrlGuardProcessRowMapper implements RowMapper<AfinUrlGuardProcess>{

	@Override
	public AfinUrlGuardProcess mapRow(ResultSet rs, int arg1) throws SQLException {
		AfinUrlGuardProcess afinUrlguardProcess = new AfinUrlGuardProcess();
		afinUrlguardProcess.setUrl(rs.getString("url"));
		afinUrlguardProcess.setCurrentState(rs.getString("current_state"));
		afinUrlguardProcess.setLastState(rs.getString("last_state"));
		afinUrlguardProcess.setRemark(rs.getString("remark"));
		afinUrlguardProcess.setOptId(rs.getString("opt_id"));
		afinUrlguardProcess.setOptName(rs.getString("opt_name"));
		afinUrlguardProcess.setOptTime(rs.getString("opt_time"));
		afinUrlguardProcess.setOptType(rs.getString("opt_type"));
		return afinUrlguardProcess;
	}

	

}
