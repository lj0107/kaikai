package com.qtong.afinance.module.pojo.urlGuard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
/**
 * 恶意网址库
 *
 */
public class AfinUrlGuardSpiteStoreroomRowMapper implements RowMapper<AfinUrlGuardSpiteStoreroom> {

	@Override
	public AfinUrlGuardSpiteStoreroom mapRow(ResultSet rs, int arg1) throws SQLException {
		AfinUrlGuardSpiteStoreroom afinUrlguardSpiteStoreroom = new AfinUrlGuardSpiteStoreroom();
		afinUrlguardSpiteStoreroom.setUrl(rs.getString("url"));
		afinUrlguardSpiteStoreroom.setSnapshot(rs.getString("snapshot"));
		afinUrlguardSpiteStoreroom.setCustomerName(rs.getString("customer_name"));
		afinUrlguardSpiteStoreroom.setCreateTime(rs.getString("create_time"));
		afinUrlguardSpiteStoreroom.setPartnerName(rs.getString("partner_name"));
		afinUrlguardSpiteStoreroom.setState(rs.getString("state"));
		return afinUrlguardSpiteStoreroom;
	}

}
