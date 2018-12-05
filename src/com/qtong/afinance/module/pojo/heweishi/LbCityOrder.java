package com.qtong.afinance.module.pojo.heweishi;

import java.io.Serializable;
import java.sql.Timestamp;

public class LbCityOrder implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mobile;//手机号
	private String productName;
	private Timestamp requestTime;//请求时间
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
