package com.qtong.afinance.module.pojo.heweishi;

import java.io.Serializable;
import java.sql.Timestamp;

public class LbCityAuthorization implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String chanlCustNo;//主键
	private String actionCode;//功能标识 
	private String userName;//业务名称
	private String pwd;//密码
	private String logserial;//请求流水号
	private String mobile;//手机号
	private String msgContent;//需要发送的短信内容
	
	private String proOrdId;//产品订购关系id
	private Timestamp requestTime;//请求时间
	
	private String streamNumber;//话单流水号
	private String requestRefId;//城市比对外键
	private String result;//比对结果
	private String cityCode;//定位的城市编码
	private String errorDesc;//错误消息
	private String msgSendCode;//发送短信返回码
	private Timestamp responseTime;//响应返回时间
	private String cityCodes;//城市区号集合
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLogserial() {
		return logserial;
	}
	public void setLogserial(String logserial) {
		this.logserial = logserial;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getProOrdId() {
		return proOrdId;
	}
	public void setProOrdId(String proOrdId) {
		this.proOrdId = proOrdId;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStreamNumber() {
		return streamNumber;
	}
	public void setStreamNumber(String streamNumber) {
		this.streamNumber = streamNumber;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public String getChanlCustNo() {
		return chanlCustNo;
	}
	public void setChanlCustNo(String chanlCustNo) {
		this.chanlCustNo = chanlCustNo;
	}
	public String getRequestRefId() {
		return requestRefId;
	}
	public void setRequestRefId(String requestRefId) {
		this.requestRefId = requestRefId;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getMsgSendCode() {
		return msgSendCode;
	}
	public void setMsgSendCode(String msgSendCode) {
		this.msgSendCode = msgSendCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Timestamp getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}
	public String getCityCodes() {
		return cityCodes;
	}
	public void setCityCodes(String cityCodes) {
		this.cityCodes = cityCodes;
	}
	
	
	
	
	
}
