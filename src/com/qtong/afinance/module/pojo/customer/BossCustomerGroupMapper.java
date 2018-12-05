package com.qtong.afinance.module.pojo.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BossCustomerGroupMapper implements RowMapper<BossCustomerGroup>{
	@Override
	public BossCustomerGroup mapRow(ResultSet arg0, int arg1) throws SQLException {
		BossCustomerGroup bossCustomerGroup = new BossCustomerGroup();
		bossCustomerGroup.setParentCustomerNumber(arg0.getString("parent_customer_number"));
		bossCustomerGroup.setOrgType(arg0.getString("org_type"));
		bossCustomerGroup.setServiceLevel(arg0.getString("service_level"));
		bossCustomerGroup.setIndustryId(arg0.getString("industry_id"));
		bossCustomerGroup.setTurnover(arg0.getString("turnover"));
		bossCustomerGroup.setEmployeeCount(arg0.getString("employee_count"));
		bossCustomerGroup.setIsStrategic(arg0.getString("is_strategic"));
		bossCustomerGroup.setManagerName(arg0.getString("manager_name"));
		bossCustomerGroup.setManagerPhone(arg0.getString("manager_phone"));
		bossCustomerGroup.setName(arg0.getString("name"));
		bossCustomerGroup.setPhone(arg0.getString("phone"));
		bossCustomerGroup.setEmail(arg0.getString("email"));
		bossCustomerGroup.setAddress(arg0.getString("address"));
		bossCustomerGroup.setZipCode(arg0.getString("zip_code"));
		return bossCustomerGroup;
	}

	
	
}

