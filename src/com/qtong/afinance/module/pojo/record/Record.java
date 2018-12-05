package com.qtong.afinance.module.pojo.record;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 话单记录表
 */
public class Record implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String streamNumber;//话单流水号
	private String recType;//话单记录类型
	private String customerNumber;//客户唯一性编码
	private String orderId;//基础产品订购ID
	private String productCode;//产品编码
	private String beginTime;//开始时间
	private String endTime;//结束时间
	private Integer fee;//费用
	private String platformFlowNo;//平台缴费流水号
	
	
	public String getStreamNumber() {
		return streamNumber;
	}
	public void setStreamNumber(String streamNumber) {
		this.streamNumber = streamNumber;
	}
	public String getRecType() {
		return recType;
	}
	public void setRecType(String recType) {
		this.recType = recType;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getFee() {
		return fee;
	}
	public void setFee(Integer fee) {
		this.fee = fee;
	}
	public String getPlatformFlowNo() {
		return platformFlowNo;
	}
	public void setPlatformFlowNo(String platformFlowNo) {
		this.platformFlowNo = platformFlowNo;
	}

	
	
	
}
