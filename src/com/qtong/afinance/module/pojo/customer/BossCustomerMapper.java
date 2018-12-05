package com.qtong.afinance.module.pojo.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BossCustomerMapper implements RowMapper<BossCustomer> {

    @Override
    public BossCustomer mapRow(ResultSet arg0, int arg1) throws SQLException {
    	BossCustomer bossCustomer = new BossCustomer();
    	bossCustomer.setCustomerNumber(arg0.getString("customer_number"));
    	bossCustomer.setOprType(arg0.getString("opr_type"));
    	bossCustomer.setCustType(arg0.getString("cust_type"));
    	bossCustomer.setCustomerName(arg0.getString("customer_name"));
    	bossCustomer.setCompanyId(arg0.getString("company_id"));
    	bossCustomer.setLoginName(arg0.getString("login_name"));
    	bossCustomer.setLoginPwd(arg0.getString("login_pwd"));
    	bossCustomer.setNationId(arg0.getString("nation_id"));
    	bossCustomer.setProvince(arg0.getString("province"));
    	bossCustomer.setCity(arg0.getString("city"));
    	bossCustomer.setIcType(arg0.getString("ic_type"));
    	bossCustomer.setIcNumber(arg0.getString("ic_number"));
    	bossCustomer.setCusPhone(arg0.getString("cus_phone"));
    	bossCustomer.setCusEmail(arg0.getString("cus_email"));
    	bossCustomer.setCusFax(arg0.getString("cus_fax"));
    	bossCustomer.setCusAddress(arg0.getString("cus_address"));
    	bossCustomer.setCusZipCode(arg0.getString("cus_zip_code"));
    	bossCustomer.setCusWeb(arg0.getString("cus_web"));
    	bossCustomer.setAttributeId(arg0.getString("attribute_id"));
    	bossCustomer.setAttributeName(arg0.getString("attribute_name"));
    	bossCustomer.setAttributeValue(arg0.getString("attribute_value"));
    	bossCustomer.setNotes(arg0.getString("notes"));
    	bossCustomer.setPicName(arg0.getString("pic_name"));
    	bossCustomer.setPicPhone(arg0.getString("pic_phone"));
    	bossCustomer.setSubType(arg0.getString("sub_type"));
    	bossCustomer.setOperateTime(arg0.getTimestamp("operate_time"));
    	bossCustomer.setOprTime(arg0.getTimestamp("opr_time"));
    	bossCustomer.setUpdpwdTime(arg0.getTimestamp("updpwd_time"));
    	bossCustomer.setStatus(arg0.getInt("status")); 
    	
    	try{
    		if(arg0.findColumn("name")>0){
    			bossCustomer.setMobile(arg0.getString("name"));
        	}
    	}catch(Exception e){    		
    	}

    	try{
    		if(arg0.findColumn("create_time")>0){
    			bossCustomer.setCreateTime(arg0.getTimestamp("create_time"));
        	}
       }catch(Exception e){    		
	   }   	
        return bossCustomer;
    }
}

