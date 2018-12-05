package com.qtong.afinance.module.pojo.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BossCustomerPersonageMapper implements RowMapper<BossCustomerPersonage>{

	@Override
	public BossCustomerPersonage mapRow(ResultSet arg0, int arg1) throws SQLException {
		BossCustomerPersonage bossCustomerPersonage = new BossCustomerPersonage();
		bossCustomerPersonage.setGender(arg0.getString("gender"));
		bossCustomerPersonage.setNationality(arg0.getString("nationality"));
		bossCustomerPersonage.setMarriedStatus(arg0.getString("married_status"));
		bossCustomerPersonage.setEducation(arg0.getString("education"));
		bossCustomerPersonage.setOccupation(arg0.getString("occupation"));
		bossCustomerPersonage.setSalary(arg0.getString("salary"));
		bossCustomerPersonage.setNickName(arg0.getString("nick_name"));
		return bossCustomerPersonage;
	}


	
}

