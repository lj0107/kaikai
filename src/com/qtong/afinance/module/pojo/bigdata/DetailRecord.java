package com.qtong.afinance.module.pojo.bigdata;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 话单记录表
 */
public class DetailRecord implements Serializable{
	
private static final long serialVersionUID = 1L;
	
	private String streamNumber;//话单流水号
	private String requestRefId;//客户请求流水号
	private String productCode;//产品编码
	private String productName;//boss产品名称
	private String usermark;//标签	
	private String usermarkValue;//标签值
	private Integer isValid;//标签是否有效
	private double fee;//费用

	private String authCode;//授权码
	private String responseRefid;//能力平台响应流水号
	private String responseCode;//返回码
	private String responseMsg;//返回说明
	private Timestamp responseTime;//响应时间戳
	
	private String mobile;//手机号
	private Timestamp requestTime;//查询时间
	private String signature;//请求签名
	
	public DetailRecord() {
		super();
	}

	public Timestamp getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public String getRequestRefId() {
		return requestRefId;
	}

	public void setRequestRefId(String requestRefId) {
		this.requestRefId = requestRefId;
	}

	public String getUsermark() {
		return usermark;
	}
	public void setUsermark(String usermark) {
		this.usermark = usermark;
	}
	public String getUsermarkValue() {
		return usermarkValue;
	}
	public void setUsermarkValue(String usermarkValue) {
		this.usermarkValue = usermarkValue;
	}
	public Integer getIsValid() {
		return isValid;
	}
	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}
	
	public String getStreamNumber() {
		return streamNumber;
	}
	public void setStreamNumber(String streamNumber) {
		this.streamNumber = streamNumber;
	}
	

	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getResponseRefid() {
		return responseRefid;
	}

	public void setResponseRefid(String responseRefid) {
		this.responseRefid = responseRefid;
	}
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
