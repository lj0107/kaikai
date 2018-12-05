package com.qtong.afinance.module.pojo.heweishi;

import java.io.Serializable;
import java.sql.Timestamp;

public class WhiteListMobile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mobile;//手机号
	private Timestamp insertTime;//添加时间
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Timestamp getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}
	
	
	
}
