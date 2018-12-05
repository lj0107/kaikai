package com.qtong.afinance.module.pojo.bigdata;

import java.io.Serializable;
import java.sql.Timestamp;


public class AfinTsInteractMess implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String requestRefId;//请求唯一码
	private String secretId;//身份标识
	private String signature;//请求签名
	private String mobile;//手机号
	private String certNo;//身份证号
	private String name;//姓名
	private String certType;//证件类型
	private Timestamp requestTime;//请求时间戳
	private String productPackageOrdId;//产品包订购关系id
	private String authCode;//授权码
		
	public AfinTsInteractMess() {
		super();
		
	}

	public AfinTsInteractMess(String requestRefId, String secretId,
			String signature, String mobile, String certNo, String name,
			String certType, Timestamp requestTime, String productPackageOrdId,
			String authCode) {
		super();
		this.requestRefId = requestRefId;
		this.secretId = secretId;
		this.signature = signature;
		this.mobile = mobile;
		this.certNo = certNo;
		this.name = name;
		this.certType = certType;
		this.requestTime = requestTime;
		this.productPackageOrdId = productPackageOrdId;
		this.authCode = authCode;
	}

	public String getRequestRefId() {
		return requestRefId;
	}

	public void setRequestRefId(String requestRefId) {
		this.requestRefId = requestRefId;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public Timestamp getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}

	public String getProductPackageOrdId() {
		return productPackageOrdId;
	}

	public void setProductPackageOrdId(String productPackageOrdId) {
		this.productPackageOrdId = productPackageOrdId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
