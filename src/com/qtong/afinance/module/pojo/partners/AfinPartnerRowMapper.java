package com.qtong.afinance.module.pojo.partners;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AfinPartnerRowMapper implements RowMapper<AfinPartner>{

	@Override
	public AfinPartner mapRow(ResultSet arg0, int arg1) throws SQLException {
		AfinPartner businessPartners = new AfinPartner();
		businessPartners.setId(arg0.getInt("id"));
		businessPartners.setName(arg0.getString("name"));
		businessPartners.setCooperationContent(arg0.getString("Cooperation_content"));
		businessPartners.setContactPerson(arg0.getString("Contact_person"));
		businessPartners.setContactNumber(arg0.getString("Contact_number"));
		businessPartners.setContactEmail(arg0.getString("Contact_email"));
		businessPartners.setUpdateTime(arg0.getTimestamp("update_time"));
		businessPartners.setCreateTime(arg0.getTimestamp("create_time"));
		businessPartners.setOperatorId(arg0.getInt("operator_id"));
		businessPartners.setFiledCode(arg0.getString("filed_code"));
		return businessPartners;
	}

}
