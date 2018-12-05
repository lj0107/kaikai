package com.qtong.afinance.module.pojo.consulting;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * 门户-项目咨询 实体类RowMapper
 */
public class ConsultingRowMapper implements RowMapper<Consulting>{

	@Override
	public Consulting mapRow(ResultSet rs, int arg1) throws SQLException {
		Consulting businessConsulting = new Consulting();
		businessConsulting.setId(rs.getInt("id"));
		businessConsulting.setName(rs.getString("name"));
		businessConsulting.setJob(rs.getString("job"));
		businessConsulting.setCompanyName(rs.getString("company_name"));
		businessConsulting.setEmail(rs.getString("email"));
		businessConsulting.setMobile(rs.getString("mobile"));
		businessConsulting.setReqDep(rs.getString("req_dep"));
		businessConsulting.setUpdateTime(rs.getTimestamp("update_time"));
		businessConsulting.setCreateTime(rs.getTimestamp("create_time"));
		businessConsulting.setState(rs.getInt("state"));
		businessConsulting.setProduct(rs.getString("product"));
		businessConsulting.setResult(rs.getString("result"));
		return businessConsulting;
	}

}
