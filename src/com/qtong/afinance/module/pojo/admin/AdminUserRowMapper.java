package com.qtong.afinance.module.pojo.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;



/**
 * 用户表映射
 */
public class AdminUserRowMapper implements RowMapper<AdminUser>{

	@Override
	public AdminUser mapRow(ResultSet arg0, int arg1) throws SQLException {
		AdminUser u=new AdminUser();
		u.setId(arg0.getInt("id"));
		u.setMobile(arg0.getString("mobile"));
		u.setPassword(arg0.getString("password"));
		u.setName(arg0.getString("name"));
		u.setEcid(arg0.getString("ecid"));
		u.setMail(arg0.getString("mail"));
		u.setJob(arg0.getString("job"));
		u.setDepartment(arg0.getString("department"));
		u.setDescription(arg0.getString("description"));
		u.setCreator(arg0.getString("creator"));
		u.setCreateTime(arg0.getTimestamp("create_time"));
		u.setUpdateTime(arg0.getTimestamp("update_time"));
		u.setUpdpwdTime(arg0.getTimestamp("updpwd_time"));
		u.setState(arg0.getInt("state"));
		try{
    		if(arg0.findColumn("role_name")>0){
    			u.setRoleName(arg0.getString("role_name"));
        	}    		
    	}catch(Exception e){    		
    	}
		return u;
	}

}
