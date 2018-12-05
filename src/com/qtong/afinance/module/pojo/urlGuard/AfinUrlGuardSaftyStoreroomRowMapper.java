package com.qtong.afinance.module.pojo.urlGuard;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
/**
 * 安全网址库
 */
public class AfinUrlGuardSaftyStoreroomRowMapper implements RowMapper<AfinUrlGuardSaftyStoreroom> {

	@Override
	public AfinUrlGuardSaftyStoreroom mapRow(ResultSet rs, int arg1) throws SQLException {
		AfinUrlGuardSaftyStoreroom afinUrlguardSaftyStoreroom = new AfinUrlGuardSaftyStoreroom();
		afinUrlguardSaftyStoreroom.setUrl(rs.getString("url"));
		afinUrlguardSaftyStoreroom.setCustomerNo(rs.getString("customer_no"));
		afinUrlguardSaftyStoreroom.setCustomerName(rs.getString("customer_name"));
		afinUrlguardSaftyStoreroom.setCreateTime(rs.getTimestamp("create_time"));
		try {
			if(rs.findColumn("snapshot")>0){
				afinUrlguardSaftyStoreroom.setSnapshot(rs.getString("snapshot"));
    		} 
		}catch (Exception e) {
			// TODO: handle exception
		}
		return afinUrlguardSaftyStoreroom;
	}

}
