package com.qtong.afinance.module.pojo.heweishi;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 位置比对交互信息详细表 实体类
 *
 */
public class LbcmpDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String streamNumber;//话单流水号
	private String requestRefId;//城市比对外键
	private String actionCode;//功能标识
	private String result;//比对结果
	private String cityCode;//定位的城市编码
	private String errorDesc;//错误消息
	private String msgSendCode;//发送短信返回码
	private Timestamp responseTime;//响应返回时间
	private double fee;//费用
	private String productName;//产品名称
	private String productCode;//产品编码
	
	private Timestamp requestTime;//请求时间
	
	
	public String getStreamNumber() {
		return streamNumber;
	}
	public void setStreamNumber(String streamNumber) {
		this.streamNumber = streamNumber;
	}
	
	public String getActionCode() {
		return actionCode;
	}
	
	public String getRequestRefId() {
		return requestRefId;
	}
	public void setRequestRefId(String requestRefId) {
		this.requestRefId = requestRefId;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCityCode() {
		return cityCode;
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
	public Timestamp getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(Timestamp responseTime) {
		this.responseTime = responseTime;
	}
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	

	
}

